package com.example.guest.domino;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class ServerManager {
  Retrofit retrofit;
  APIService service;

  public ServerManager(){
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl(APIService.HOST)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
     service=retrofit.create(APIService.class);

  }

   public interface OnCallBackListenerTask{
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

   public interface OnCallBackListenerAuth{
      void onCallBack(boolean answer);
   }


   public void LogIn(String login, String password, final OnCallBackListenerAuth listenerAuth){
      Call<Boolean> call = service.LogIn();

      call.enqueue(new Callback<Boolean>() {
          @Override
          public void onResponse(Call<Boolean> call, Response<Boolean> response) {
              Boolean answer= response.body();
              listenerAuth.onCallBack(answer);
          }

          @Override
          public void onFailure(Call<Boolean> call, Throwable t) {

          }
      });
   }


   public interface OnCallBackListenerReg{
      void onCallBack(boolean answer);
   }

   public void CreateNewAccount(User user, final OnCallBackListenerReg listenerReg){
      Call<Boolean> call = service.CreateAccount();
      call.enqueue(new Callback<Boolean>() {
          @Override
          public void onResponse(Call<Boolean> call, Response<Boolean> response) {
              Boolean answer = response.body();
              listenerReg.onCallBack(answer);
          }

          @Override
          public void onFailure(Call<Boolean> call, Throwable t) {

          }
      });
   }

}
