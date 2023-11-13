package com.example.documentharbor.customexceptions;

public class FolderNotFoundException extends RuntimeException {
    public FolderNotFoundException(String folderName) {
        super("Folder not found: " + folderName);
    }
}
