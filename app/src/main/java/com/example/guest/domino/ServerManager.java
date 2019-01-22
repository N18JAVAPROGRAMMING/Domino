package com.example.guest.domino;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class ServerManager {
    private Retrofit retrofit;
    private APIService service;

    OnCallBackListenerAuth listenerAuth;
    OnCallBackListenerReg listenerReg;
    OnCheckTokenListener listenerToken;
    onPeerConnectListener peerConnectListener;
    onPeerDisonnectListener peerDisonnectListener;
    OnGetDependencies dependencies;




    Context context;
    /*private static ServerManager manager;*/

  /*public static synchronized ServerManager getInstance(){
      if(manager==null)
          manager= new ServerManager();
      return manager;
  }*/

    public interface GlobalScoreGetter{
        void ok(int score);
        void fail();
    }

    public void getGlobalScore(String username, final GlobalScoreGetter listener){
        Call<APIService.ModelScore> call = service.getGlobalScore(
           APIService.Token.getToken(context),username);
        call.enqueue(new Callback<APIService.ModelScore>() {
            @Override
            public void onResponse(Call<APIService.ModelScore> call, Response<APIService.ModelScore> response) {
                if (response.body()==null){
                    listener.fail();
                }else{
                listener.ok(response.body().score);}

            }

            @Override
            public void onFailure(Call<APIService.ModelScore> call, Throwable t) {
                 listener.fail();
            }
        });
    }




    public interface OnCreateRoomListener{
        void create(Room room);
    }



    public interface OnGetDependencies {
        void response(APIService.Dependencies dependencies);

        void fail();
    }

    public void setDependencies(int id, final OnGetDependencies listener) {
        Call<APIService.Dependencies> call = service.dependencies(String.valueOf(id),
                APIService.Token.getToken(context));
        call.enqueue(new Callback<APIService.Dependencies>() {
            @Override
            public void onResponse(Call<APIService.Dependencies> call, Response<APIService.Dependencies> response) {
                if (response.body() == null) {
                    listener.fail();
                    return;
                }
                listener.response(response.body());
            }

            @Override
            public void onFailure(Call<APIService.Dependencies> call, Throwable t) {
                listener.fail();
            }
        });

    }


    public void createRoom(String room_name, int capacity, final OnCreateRoomListener listener) {

        APIService.ModelCreateRoom model = new APIService.ModelCreateRoom(
                APIService.Token.getToken(context), String.valueOf(28), room_name, capacity);
        Call<Room> call = service.createRoom(model);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                listener.create(response.body());
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {

            }
        });

    }

    public ServerManager(Context context) {
        this.context = context;
        try {
            GsonBuilder builder = new GsonBuilder();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.HOST)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .client(client)
                    .build();
            service = retrofit.create(APIService.class);
        } catch (Exception e) {

        }

    }


    public void CheckToken(final OnCheckTokenListener checkTokenListener, Context context) {
        listenerToken = checkTokenListener;
        Call<APIService.Status> call = service.checkToken(APIService.Token.getToken(context));
        call.enqueue(new Callback<APIService.Status>() {
            @Override
            public void onResponse(Call<APIService.Status> call, Response<APIService.Status> response) {
                if (response.body().status.equals("success")){
                    checkTokenListener.ok();
                } else {
                    checkTokenListener.error();
                }
            }

            @Override
            public void onFailure(Call<APIService.Status> call, Throwable t) {
                checkTokenListener.error();
            }
        });

    }

    public interface OnCheckTokenListener {
        void ok();

        void error();
    }

    public static interface OnCallBackListenerTask {
        void onCallBack(Task task);

        void error(String masg);
    }

    public void getTask(int id, final OnCallBackListenerTask listener) {


        Call<Task> call = service.getTask(APIService.Token.getToken(context),String.valueOf(id));

        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Task task = response.body();
                if (response.body() == null) {
                    listener.error("Пустое тело ответа: Task");
                    return;
                }
                listener.onCallBack(task);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                listener.error("Ошибка подключения");
            }
        });


    }


    public interface OnCallBackListenerAuth {
        void onCallBack(boolean answer, String token);

        void error();
    }


    public void LogIn(String login, String password, final OnCallBackListenerAuth listener) {

        this.listenerAuth = listener;
        APIService.User user = new APIService.User();
        user.username = login;
        user.password = password;
        Call<APIService.Token> call = service.logIn(user);

        call.enqueue(new Callback<APIService.Token>() {
            boolean answer = false;

            @Override
            public void onResponse(Call<APIService.Token> call, Response<APIService.Token> response) {
                answer = true;
                APIService.Token token = response.body();
                if (token == null) {
                    listenerAuth.onCallBack(false, "Неправильная пара логин пароль");
                } else {
                    listenerAuth.onCallBack(answer, token.token);
                }
            }

            @Override
            public void onFailure(Call<APIService.Token> call, Throwable t) {

                listenerAuth.error();

            }
        });
    }


    public interface OnCallBackListenerReg {
        void onCallBack(boolean answer, String token);

        void error();
    }

    public void CreateNewAccount(String name, String password, final OnCallBackListenerReg listener) {
        listenerReg = listener;
        APIService.User user = new APIService.User();
        user.username = name;
        user.password = password;
        Call<APIService.Token> call = service.singUp(user);
        call.enqueue(new Callback<APIService.Token>() {
            @Override
            public void onResponse(Call<APIService.Token> call, Response<APIService.Token> response) {
                if (response.body() == null) {
                    listenerReg.onCallBack(false, "Такой логин уже сущесвует");
                } else {
                    listenerReg.onCallBack(true, response.body().token);
                }
            }

            @Override
            public void onFailure(Call<APIService.Token> call, Throwable t) {
                listenerReg.error();
            }
        });
    }


    public interface UpdateRoomListListener {
        void onUpdate(List<Room> main);

        void error(String msg);
    }

    public interface onPeerConnectListener {
        void connect(Room room);

        void fail();
    }

    public interface onPeerDisonnectListener {
        void disconnect(List<Room> list);

        void error();
    }

    public void peerConnect(int room_id, final onPeerConnectListener listener) {
        APIService.ModelPeer model = new APIService.ModelPeer(String.valueOf(room_id),
                APIService.Token.getToken(context));
        Call<Room> call = service.peerConnect(model);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                Log.d("listener","peerConnect");
                Room room = response.body();
                listener.connect(room);
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                listener.fail();
            }
        });
    }

    public void peerDisconnect(int room_id, final onPeerDisonnectListener listener) {
        APIService.ModelPeer model = new APIService.ModelPeer(String.valueOf(room_id),
                APIService.Token.getToken(context));
        Call<List<Room>> call = service.peerDisconnect(model);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                listener.disconnect(response.body());
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                listener.error();
            }
        });
    }


    public void setScore(int add, int task_id){
        APIService.ModelPostTask model =  new APIService.ModelPostTask(add,
                APIService.Token.getToken(context),task_id);

        Call call = service.setScore(model);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }



    public static class BackgroundThread extends Thread {

        private Retrofit retrofit;
        private APIService service;
        Context context;
        volatile boolean runflag;

        UpdateRoomListListener updateRoomListListener;
        OnLoadRoomInformation loadRoomInformation;
        DominoStatusCheckListener dominoListener;

        interface OnLoadRoomInformation {
            void ok(Room room);

            void fail();
        }

        int mode = -1;

        public void setUpdateRoomListListener(UpdateRoomListListener listListener) {
            updateRoomListListener = listListener;
        }

        public static final int UPDATE_ROOMLIST = 0;
        public static final int UPDATE_SCORE = 1;
        public static final int UPDATE_ROOMINFO = 2;
        public static final int UPDATE_TASKS=3;

        int delay;
        int room_id;

        public BackgroundThread(Context context, int mode, int delay) {
            this.delay = delay;
            this.mode = mode;
            this.context = context;


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            service = retrofit.create(APIService.class);
        }

        @Override
        public synchronized void start() {
            runflag = true;
            super.start();
        }

        @Override
        public void run() {
            while (runflag) {
                switch (mode) {
                    case UPDATE_ROOMLIST:
                        OnUpdateRoomList();
                        break;
                    case UPDATE_SCORE:
                        break;
                    case UPDATE_ROOMINFO:
                        onUpdateRoomInfo();
                    case UPDATE_TASKS:
                        onUpdateDomino();
                        break;
                }

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void OnUpdateRoomList() {

            Call<List<Room>> call = service.getAllRooms(APIService.Token.getToken(context));
            call.enqueue(new Callback<List<Room>>() {
                @Override
                public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                    if (response.body()!=null){
                        updateRoomListListener.onUpdate(response.body());
                    } else {
                        updateRoomListListener.error("Пустой ответ List<Room>");
                    }

                }

                @Override
                public void onFailure(Call<List<Room>> call, Throwable t) {
                    updateRoomListListener.error("Ошибка подключения");

                }
            });

        }

        public void onUpdateRoomInfo() {
            Call<Room> call = service.roomStatus(APIService.Token.getToken(context), String.valueOf(room_id));
            call.enqueue(new Callback<Room>() {
                @Override
                public void onResponse(Call<Room> call, Response<Room> response) {

                    if (response.body() == null) {
                        loadRoomInformation.fail();
                    } else {
                        loadRoomInformation.ok(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Room> call, Throwable t) {
                    loadRoomInformation.fail();
                }
            });
        }

        public void setLoadRoomInformation(int room_id, final OnLoadRoomInformation onLoadRoomInformation) {
            loadRoomInformation = onLoadRoomInformation;
            this.room_id = room_id;



        }

        public interface DominoStatusCheckListener{
            void onUpdate(APIService.CaptureModel model);
            void  fail();
        }

        public void setOnDominoListener(int room_id, final DominoStatusCheckListener listener){
            this.room_id=room_id;
            dominoListener=listener;
        }

        public void onUpdateDomino(){
            Call<APIService.CaptureModel> call = service.statusTasks(APIService.Token.getToken(context),
                    String.valueOf(room_id));
            call.enqueue(new Callback<APIService.CaptureModel>() {
                @Override
                public void onResponse(Call<APIService.CaptureModel> call, Response<APIService.CaptureModel> response) {
                    if (response.body()==null){
                        dominoListener.fail();
                    } else {
                        dominoListener.onUpdate(response.body());
                    }
                }

                @Override
                public void onFailure(Call<APIService.CaptureModel> call, Throwable t) {
                       dominoListener.fail();
                }
            });
        }

        public void setRunFlag(boolean value) {
            runflag = value;
        }


    }


}
