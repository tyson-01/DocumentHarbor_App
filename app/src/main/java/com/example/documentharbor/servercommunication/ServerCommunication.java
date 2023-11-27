package com.example.documentharbor.servercommunication;

import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.interfaces.ApiService;
import com.example.documentharbor.interfaces.EndSignalCallback;
import com.example.documentharbor.interfaces.ImageUploadCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
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
            AppController.getInstance().getLogger().log("ServerCommunication:getFolderStructure", "Attempting to GET /getFolderStructure");
            Response<Folder> response = apiService.getFolderStructure().execute();

            if(response.isSuccessful()) {
                AppController.getInstance().getLogger().log("ServerCommunication:getFolderStructure", "Response successful");
                Folder rootFolder = response.body();
                explore(rootFolder);
                return new FolderStructure(rootFolder);
            }
            AppController.getInstance().getLogger().log("ServerCommunication:getFolderStructure", "Response failed");
        } catch (Exception e) {
            AppController.getInstance().getLogger().log("ServerCommunication:getFolderStructure", "Exception triggered: " + e.getMessage(), e);
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

    public void uploadFile(String photoName, byte[] photoData, ImageUploadCallback callback) {
        AppController.getInstance().getLogger().log(photoName);
        AppController.getInstance().getLogger().log("ServerCommunication:uploadFile", "Attempting to POST /uploadImage");

        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), photoName);
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), photoData);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "photo.jpg", fileRequestBody);

        Call<Void> call = apiService.uploadImage(nameRequestBody, filePart);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppController.getInstance().getLogger().log("ServerCommunication:uploadFile", "Upload Successful");
                    callback.onImageUploaded(true);
                } else {
                    AppController.getInstance().getLogger().log("ServerCommunication:uploadFile", "Upload Unsuccessful");
                    callback.onImageUploaded(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                AppController.getInstance().getLogger().log("ServerCommunication:uploadFile", "Upload Failed");
                callback.onImageUploaded(false);
            }
        });
    }



    public void sendEndSignal(String identifier, ProcessingMethod processingMethod, EndSignalCallback callback) {
        AppController.getInstance().getLogger().log("ServerCommunication:sendEndSignal", "Attempting to POST /sendEndSignal");

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("identifier", identifier);
            jsonData.put("processingMethod", processingMethod.toString());
        } catch (JSONException e) {
            AppController.getInstance().getLogger().log("ServerCommunication:sendEndSignal", "Exception triggered: " + e.getMessage(), e);
        }
        AppController.getInstance().getLogger().log("ServerCommunication:sendEndSignal", "Request Data: " + jsonData.toString());
        RequestBody requestBody  = RequestBody.create(MediaType.parse("application/json"), jsonData.toString());

        Call<Void> call = apiService.sendEndSignal(requestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppController.getInstance().getLogger().log("ServerCommunication:sendEndSignal", "End Signal sent Successfully");
                    callback.onEndSignalSent(true);
                } else {
                    AppController.getInstance().getLogger().log("ServerCommunication:sendEndSignal", "End Signal Unsuccessful");
                    callback.onEndSignalSent(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                AppController.getInstance().getLogger().log("ServerCommunication:sendEndSignal", "End Signal Failed");
            }
        });
    }

}
