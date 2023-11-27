package com.example.documentharbor.controller;

import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.imaging.PhotoSession;
import com.example.documentharbor.interfaces.EndSignalCallback;
import com.example.documentharbor.logging.Logger;
import com.example.documentharbor.servercommunication.ServerCommunication;

public class AppController {

    private static AppController instance;

    private FolderStructure folderStructure;
    private PhotoSession currentPhotoSession;
    private final ServerCommunication serverCommunication;
    private final Logger logger;

    private AppController() {
        logger = new Logger();
        serverCommunication = new ServerCommunication();
        folderStructure = null;
        currentPhotoSession = null;
    }

    public static synchronized AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void updateFolderStructure() {
        this.folderStructure = serverCommunication.getFolderStructure();
    }

    public FolderStructure getFolderStructure() {
        return folderStructure;
    }

    public void createNewSession(String sessionName, String folderPath) {
        currentPhotoSession = new PhotoSession(sessionName, folderPath, serverCommunication);
    }

    public PhotoSession getCurrentPhotoSession() {
        return currentPhotoSession;
    }

    public void setProcessingMethod(ProcessingMethod method) {
        if (currentPhotoSession != null) {
            currentPhotoSession.setProcessingMethod(method);
        }
    }

    public void endSession(EndSignalCallback callback) {
        currentPhotoSession.endSession(callback);
    }
}

