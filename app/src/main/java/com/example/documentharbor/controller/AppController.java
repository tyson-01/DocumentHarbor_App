package com.example.documentharbor.controller;

import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.imaging.PhotoSession;
import com.example.documentharbor.logging.Logger;
import com.example.documentharbor.servercommunication.ServerCommunication;

import java.io.File;

public class AppController {

    private static AppController instance;

    private FolderStructure folderStructure;
    private PhotoSession currentPhotoSession;
    private ServerCommunication serverCommunication;
    private Logger logger;

    private AppController() {
        // Initialize components
        logger = new Logger();
        serverCommunication = new ServerCommunication();
        folderStructure = serverCommunication.getFolderStructure();
        currentPhotoSession = null;
    }

    public static synchronized AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    public void log(String logData) {
        logger.log(logData);
    }

    public FolderStructure getFolderStructure() {
        return folderStructure;
    }

    public void setProcessingMethod(ProcessingMethod method) {
        if (currentPhotoSession != null) {
            currentPhotoSession.setProcessingMethod(method);
        }
    }

    public void createNewSession(String sessionName, String folderPath) {
        currentPhotoSession = new PhotoSession(sessionName, folderPath);
    }

    public void endSession() {
        // End the session and perform necessary actions
        if (currentPhotoSession != null) {
            // Perform actions based on the chosen processing method
            currentPhotoSession.endSession();
            serverCommunication.uploadFile(currentPhotoSession, folderStructure);
        }
    }

    public PhotoSession getCurrentPhotoSession() {
        return currentPhotoSession;
    }
}

