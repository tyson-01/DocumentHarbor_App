package com.example.documentharbor.filestructure;

import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.customexceptions.FolderNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;

public class FolderStructure {
    private Folder rootFolder;
    private Folder currentFolder;

    public FolderStructure(Folder root) {
        rootFolder = root;
        currentFolder = root;
    }

    public Folder getRootFolder() {
        return rootFolder;
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(String folderPath) {
        String[] folderNames = folderPath.split("/");
        Folder current = rootFolder;
        for (String folderName : folderNames) {
            Folder nextFolder = current.getSubFolderByName(folderName);
            if (nextFolder != null) {
                current = nextFolder;
            } else {
                throw new FolderNotFoundException(folderName);
            }
        }
        currentFolder = current;
    }

    public void createFolder(String folderName) {
        Folder newFolder = new Folder(folderName, new ArrayList<>());
        currentFolder.addSubFolder(newFolder);
        currentFolder = newFolder;
    }
}
