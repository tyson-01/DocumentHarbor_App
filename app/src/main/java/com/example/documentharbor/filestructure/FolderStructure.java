package com.example.documentharbor.filestructure;

import com.example.documentharbor.customexceptions.FolderNotFoundException;

import java.util.ArrayList;

public class FolderStructure {
    private final Folder rootFolder;
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

    public void setCurrentFolder(Folder folder) {
        currentFolder = folder;
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

    public Folder getParentOf(Folder child) {
        return findParentOf(rootFolder, child);
    }

    private Folder findParentOf(Folder current, Folder child) {
        if (current == null || current.getSubFolders().isEmpty()) {
            return null;
        }

        for (Folder folder : current.getSubFolders()) {
            if (folder.equals(child)) {
                return current;
            }

            Folder parent = findParentOf(folder, child);
            if (parent != null) {
                return parent;
            }
        }

        return null;
    }

    public String getCurrentFolderPath() {
        if (rootFolder == null || currentFolder == null) {
            return null;
        }

        return getCurrentFolderRecursiveHelper(rootFolder, rootFolder.getName());
    }

    private String getCurrentFolderRecursiveHelper(Folder folder, String path) {
        if (folder == currentFolder) {
            return path;
        }

        for (Folder child : folder.getSubFolders()) {
            String childPath = getCurrentFolderRecursiveHelper(child, path + "/" + child.getName());
            if (childPath != null) {
                return childPath;
            }
        }

        return null;
    }

}
