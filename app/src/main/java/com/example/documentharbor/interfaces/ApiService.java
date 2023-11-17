package com.example.documentharbor.interfaces;

import com.example.documentharbor.filestructure.Folder;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/getFolderStructure")
    Call<Folder> getFolderStructure();
}
