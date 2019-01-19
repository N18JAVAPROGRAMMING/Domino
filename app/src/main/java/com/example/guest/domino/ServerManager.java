package com.example.guest.domino;

import android.util.Log;

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
  /*private static ServerManager manager;*/

  /*public static synchronized ServerManager getInstance(){
      if(manager==null)
          manager= new ServerManager();
      return manager;
  }*/

  public ServerManager(){
      try {
         retrofit = new Retrofit.Builder()
                  .baseUrl(APIService.HOST)
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();
          service = retrofit.create(APIService.class);
      } catch (Exception e){

      }

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
   }


   public void LogIn(String login, String password, final OnCallBackListenerAuth listener){

          this.listenerAuth=listener;
      Call<APIService.Token> call = service.logIn(login,password);

       call.enqueue(new Callback<APIService.Token>() {
           boolean answer=false;
           @Override
           public void onResponse(Call<APIService.Token> call, Response<APIService.Token> response) {
              answer=true;
              APIService.Token token = response.body();
              listenerAuth.onCallBack(answer,token.token);
           }

           @Override
           public void onFailure(Call<APIService.Token> call, Throwable t) {
               Log.d("Server",t.getMessage());
               listenerAuth.onCallBack(false,"");

           }
       });
   }


   public interface OnCallBackListenerReg{
      void onCallBack(boolean answer,String token);
   }

   public void CreateNewAccount(String name, String password, final OnCallBackListenerReg listener){
      listenerReg=listener;
      Call<APIService.Token> call = service.singUp(name,password);
      call.enqueue(new Callback<APIService.Token>() {
          @Override
          public void onResponse(Call<APIService.Token> call, Response<APIService.Token> response) {
              listenerReg.onCallBack(true,response.body().token);
          }

          @Override
          public void onFailure(Call<APIService.Token> call, Throwable t) {
              listenerReg.onCallBack(false,"");
          }
      });
   }

}
