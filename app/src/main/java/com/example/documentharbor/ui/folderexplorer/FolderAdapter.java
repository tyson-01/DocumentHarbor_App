package com.example.documentharbor.ui.folderexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.documentharbor.R;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.interfaces.OnSubFolderClickedListener;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderViewHolder> {

    private final List<Folder> subFolders;
    private final OnSubFolderClickedListener listener;

    public FolderAdapter(List<Folder> subFolders, OnSubFolderClickedListener listener) {
        this.subFolders = subFolders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder subfolder = subFolders.get(position);
        holder.bind(subfolder);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSubFolderClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subFolders.size();
    }

}
