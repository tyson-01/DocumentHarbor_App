package com.example.documentharbor.servercommunication;

import android.util.Log;

import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.imaging.PhotoSession;
import com.google.gson.Gson;

import java.io.File;

public class ServerCommunication {
    private Gson gson;

    public ServerCommunication() {
        this.gson = new Gson();
    }

    public FolderStructure getFolderStructure() {
        //TODO: setup to work with API when server is done
        //currently just mocks up a folder structure
        String jsonResponse = "{\"name\":\"Root\",\"subFolders\":[{\"name\":\"Documents\",\"subFolders\":[{\"name\":\"Subfolder1\",\"subFolders\":[]}]}]}";

        Folder rootFolder = gson.fromJson(jsonResponse, Folder.class);
        explore(rootFolder);
        return new FolderStructure(rootFolder);
    }

    public void explore(Folder root) {
        //TODO: this is temporary for two reasons
        //  1. check that the api call is only made once, if not the singleton isn't persisting
        //  2. easy way to check that the gson is creating the right folder tree
        //  delete this method when you are done debugging
        if (root == null) return;
        Log.e("YA", "name: " + root.getName());
        for (Folder f : root.getSubFolders()) {
            explore(f);
        }
    }

    public boolean uploadFile(String photoName, File photo) {
        //TODO: replace with api stuff
        // convert to json
        // make api call
        // if 200, then return true. Else return false
        return true;
    }

    public boolean sendEndSignal(String identifier, ProcessingMethod processingMethod) {
        //TODO: send the signal to the server
        return true;
    }

}
