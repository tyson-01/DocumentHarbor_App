package com.example.documentharbor.servercommunication;

import android.util.Log;

import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.interfaces.ApiService;

import retrofit2.Response;
import retrofit2.Retrofit;

public class ServerCommunication {
    private ApiService apiService;

    public ServerCommunication() {
        Retrofit retrofit = ApiClient.getClient();
        this.apiService = retrofit.create(ApiService.class);
    }

    public FolderStructure getFolderStructure() {
        try {
            AppController.getInstance().getLogger().log("ServerCommunication", "Attempting to GET /getFolderStructure");
            Response<Folder> response = apiService.getFolderStructure().execute();

            if(response.isSuccessful()) {
                AppController.getInstance().getLogger().log("ServerCommunication", "Response successful");
                Folder rootFolder = response.body();
                explore(rootFolder);
                return new FolderStructure(rootFolder);
            }
            AppController.getInstance().getLogger().log("ServerCommunication", "Response failed");
        } catch (Exception e) {
            AppController.getInstance().getLogger().log("ServerCommunication", "Exception triggered: " + e.getMessage(), e);
        }
        return null;
    }

    private void explore(Folder root) {
        //for debugging, dfs of folder structure
        if (root == null) return;
        AppController.getInstance().getLogger().log("ServerCommunication:folderDFS", root.getName());
        for (Folder f : root.getSubFolders()) {
            explore(f);
        }
    }

    public boolean uploadFile(String photoName, byte[] photoData) {
        //TODO: uncomment out the logic once api is set up
        try{Thread.sleep(3000);} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;

        /*
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), photoData);
        RequestBody fileNameBody = RequestBody.create(MediaType.parse("text/plain"), photoName);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", photoName, requestBody);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.uploadFile(fileNameBody, filePart);

        try {
            Response<ApiResponse> response = call.execute();
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
         */
    }

    public boolean sendEndSignal(String identifier, ProcessingMethod processingMethod) {
        //TODO: send the signal to the server
        return true;
    }

}
