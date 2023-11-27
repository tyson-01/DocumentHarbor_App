package com.example.documentharbor.interfaces;

import com.example.documentharbor.filestructure.Folder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("/getFolderStructure")
    Call<Folder> getFolderStructure();

    @Multipart
    @POST("/uploadImage")
    Call<Void> uploadImage(
            @Part("photoName") RequestBody photoName,
            @Part MultipartBody.Part file
            );

    @POST("/sendEndSignal")
    Call<Void> sendEndSignal(
            @Body RequestBody requestBody
    );
}
