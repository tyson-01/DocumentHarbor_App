package com.example.documentharbor.filestructure;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String name;
    private List<Folder> subFolders;

    public Folder(String name, List<Folder> subFolders) {
        this.name = name;
        this.subFolders = (subFolders != null) ? subFolders : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public Folder getSubFolderByName(String name) {
        for (Folder subFolder : subFolders) {
            if (subFolder.getName().equals(name)) {
                return subFolder;
            }
        }
        return null;
    }

    public void addSubFolder(Folder subfolder) {
        subFolders.add(subfolder);
    }

}
