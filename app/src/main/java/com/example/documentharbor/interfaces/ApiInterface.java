package com.example.documentharbor.interfaces;

import com.example.documentharbor.servercommunication.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("uploadFile")
    Call<ApiResponse> uploadFile(
            @Part("fileName")RequestBody fileName,
            @Part MultipartBody .Part file
    );
}
