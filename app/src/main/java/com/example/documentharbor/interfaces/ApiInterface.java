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
    //TODO: This interface is as of yet unused, it is for when I choose to implement the upload image api call
    // we can probably just put it in the ApiService class I made for the GET api call and rename the
    // references to it in the as of yet unimplemented upload image api function in servercommunication
}
