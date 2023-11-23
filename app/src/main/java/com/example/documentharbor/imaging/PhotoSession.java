package com.example.documentharbor.imaging;

import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.interfaces.ImageUploadCallback;
import com.example.documentharbor.servercommunication.ServerCommunication;

public class PhotoSession {
    private final String sessionName;
    private int nextPhotoIndex;
    private final String folderPath;
    private ProcessingMethod processingMethod;
    private final ServerCommunication serverCommunication;

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
    public int numberOfPhotos() {
        return nextPhotoIndex - 1;
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

    public void addPhotoToSession(byte[] photoData, ImageUploadCallback callback) {
        String photoName = folderPath + "/" + sessionName + Integer.toString(nextPhotoIndex) + ".jpg";
        serverCommunication.uploadFile(photoName, photoData, callback);
    }

    public void incrementPhotoIndex() {
        nextPhotoIndex++;
    }

    public boolean endSession() {
        String identifier = folderPath + "/" + sessionName;
        return serverCommunication.sendEndSignal(identifier, processingMethod);
    }

}
