package com.example.documentharbor.imaging;

import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.servercommunication.ServerCommunication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoSession {
    private String sessionName;
    private int nextPhotoIndex;
    private String folderPath;
    private ProcessingMethod processingMethod;
    private ServerCommunication serverCommunication;

    public PhotoSession(String sessionName, String folderPath, ServerCommunication serverCommunication) {
        this.sessionName = sessionName;
        this.nextPhotoIndex = 1;
        this.folderPath = folderPath;
        this.processingMethod = ProcessingMethod.LEAVE_AS_IMAGES; // Default method
        this.serverCommunication = serverCommunication;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setProcessingMethod(ProcessingMethod method) {
        this.processingMethod = method;
    }

    public ProcessingMethod getProcessingMethod() {
        return this.processingMethod;
    }

    public boolean addPhotoToSession(File photo) {
        String photoName = folderPath + "/" + sessionName + Integer.toString(nextPhotoIndex) + ".jpg";
        boolean uploadSuccessful = serverCommunication.uploadFile(photoName, photo);

        if (uploadSuccessful) {
            nextPhotoIndex++;
            return true;
        }
        return false;
    }

    public boolean endSession() {
        String identifier = folderPath + "/" + sessionName;
        return serverCommunication.sendEndSignal(identifier, processingMethod);
    }

}
