package com.example.guest.domino;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

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
  /*private static ServerManager manager;*/

  /*public static synchronized ServerManager getInstance(){
      if(manager==null)
          manager= new ServerManager();
      return manager;
  }*/

  public ServerManager(){
      try {
          GsonBuilder builder =  new GsonBuilder();
         retrofit = new Retrofit.Builder()
                  .baseUrl(APIService.HOST)
                  .addConverterFactory(GsonConverterFactory.create(builder.create()))

                  .build();
          service = retrofit.create(APIService.class);
      } catch (Exception e){

      }

  }


  public void CheckToken(OnCheckTokenListener checkTokenListener, Context context){
      listenerToken=checkTokenListener;
      Call<List<Room>> call = service.getAllRooms(APIService.Token.getToken(context));
      call.enqueue(new Callback<List<Room>>() {
          @Override
          public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
              if(response.body()==null){
                 listenerToken.error();
              }else {
                  listenerToken.ok();
              }

          }

          @Override
          public void onFailure(Call<List<Room>> call, Throwable t) {
              listenerToken.error();
          }
      });

  }

  public interface OnCheckTokenListener{
      void ok();
      void error();
    }

   public  static interface OnCallBackListenerTask{
       void onCallBack(Task task);
   }

   public void getTask(int id, final OnCallBackListenerTask listener){


        Call<Task> call = service.getTask(String.valueOf(id));

                call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Task task = response.body();
                listener.onCallBack(task);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

            }
        });


    }



   public static interface OnCallBackListenerAuth{
      void onCallBack(boolean answer,String token);
      void error();
   }


   public void LogIn(String login, String password, final OnCallBackListenerAuth listener){

          this.listenerAuth=listener;
          APIService.User user = new APIService.User();
          user.username=login;
          user.password=password;
      Call<APIService.Token> call = service.logIn(user);

       call.enqueue(new Callback<APIService.Token>() {
           boolean answer=false;
           @Override
           public void onResponse(Call<APIService.Token> call, Response<APIService.Token> response) {
              answer=true;
              APIService.Token token = response.body();
              if (token==null){
                  listenerAuth.onCallBack(false,"Неправильная пара логин пароль");
              } else {
              listenerAuth.onCallBack(answer,token.token);}
           }

           @Override
           public void onFailure(Call<APIService.Token> call, Throwable t) {

               listenerAuth.error();

           }
       });
   }


   public interface OnCallBackListenerReg{
      void onCallBack(boolean answer,String token);
      void error();
   }

   public void CreateNewAccount(String name, String password, final OnCallBackListenerReg listener){
      listenerReg=listener;
       APIService.User user = new APIService.User();
       user.username=name;
       user.password=password;
      Call<APIService.Token> call = service.singUp(user);
      call.enqueue(new Callback<APIService.Token>() {
          @Override
          public void onResponse(Call<APIService.Token> call, Response<APIService.Token> response) {
              if (response.body()==null){
                  listenerReg.onCallBack(false,"Такой логин уже сущесвует");
              } else {
              listenerReg.onCallBack(true,response.body().token);}
          }

          @Override
          public void onFailure(Call<APIService.Token> call, Throwable t) {
              listenerReg.error();
          }
      });
   }



   public interface UpdateRoomListListener{
      void onUpdate(List<Room> main);
      void error(String msg);
   }




   public static class BackgroundThread extends Thread {

       private Retrofit retrofit;
       private APIService service;
       Context context;

       UpdateRoomListListener updateRoomListListener;

      int mode=-1;

       public void setUpdateRoomListListener(UpdateRoomListListener listListener){
           updateRoomListListener=listListener;
       }

      public static final int UPDATE_ROOMLIST=0;
      public static final int UPDATE_SCORE=1;



       public BackgroundThread(Context context, int mode
       ){
           this.mode=mode;
           this.context=context;
           retrofit = new Retrofit.Builder()
                   .baseUrl(APIService.HOST)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
           service = retrofit.create(APIService.class);
       }

       @Override
       public void run() {
           switch (mode){
               case UPDATE_ROOMLIST:
                   OnUpdateRoomList();
                   break;
               case UPDATE_SCORE:
                   break;
           }

           try {
               Thread.sleep(5000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

       public void OnUpdateRoomList(){

           Call<List<Room>> call = service.getAllRooms(APIService.Token.getToken(context));
           call.enqueue(new Callback<List<Room>>() {
               @Override
               public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                      updateRoomListListener.onUpdate(response.body());
               }

               @Override
               public void onFailure(Call<List<Room>> call, Throwable t) {

               }
           });

       }
   }


}
