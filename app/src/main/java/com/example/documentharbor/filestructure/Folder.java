package com.example.documentharbor.filestructure;

import java.util.List;

public class Folder {
    private String name;
    private List<Folder> subfolders;

    public Folder(String name, List<Folder> subfolders) {
        this.name = name;
        this.subfolders = subfolders;
    }

    public String getName() {
        return name;
    }

    public List<Folder> getSubFolders() {
        return subfolders;
    }

    public Folder getSubFolderByName(String name) {
        for (Folder subFolder : subfolders) {
            if (subFolder.getName().equals(name)) {
                return subFolder;
            }
        }
        return null;
    }

    public void addSubFolder(Folder subfolder) {
        subfolders.add(subfolder);
    }
}
