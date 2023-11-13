package com.example.documentharbor.ui.folderexplorer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.documentharbor.R;
import com.example.documentharbor.filestructure.Folder;

public class FolderViewHolder extends RecyclerView.ViewHolder {

    private TextView tvFolderName;

    public FolderViewHolder(@NonNull View itemView) {
        super(itemView);
        tvFolderName = itemView.findViewById(R.id.tvFolderName);
    }

    public void bind(Folder folder) {
        tvFolderName.setText(folder.getName());
    }

}
