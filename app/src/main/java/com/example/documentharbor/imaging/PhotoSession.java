package com.example.documentharbor.imaging;

import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.filestructure.FolderStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoSession {
    private String sessionName;
    private String folderPath;
    private ProcessingMethod processingMethod;
    private List<File> capturedPhotos;

    public PhotoSession(String sessionName, String folderPath) {
        this.sessionName = sessionName;
        this.folderPath = folderPath;
        this.processingMethod = ProcessingMethod.LEAVE_AS_IMAGES; // Default method
        this.capturedPhotos = new ArrayList<>();
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

    public void capturePhoto(File photo) {
        capturedPhotos.add(photo);
    }

    public List<File> getCapturedPhotos() {
        return new ArrayList<>(capturedPhotos);
    }

    public void endSession() {
        // End the session (no actual processing here)
    }

}
