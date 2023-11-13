package com.example.documentharbor.ui.folderexplorer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.filestructure.Folder;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.interfaces.OnSubFolderClickedListener;

public class FolderExplorerActivity extends AppCompatActivity implements OnSubFolderClickedListener {

    private TextView tvCurrentFolder;
    private RecyclerView recyclerViewFolders;
    private Button btnNavigateUp;

    private FolderStructure folderStructure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_explorer);

        setupUIReferences();
        initializeFolderStructure();
        displaySubFolders();
        setupNavigateUp();
    }

    private void setupUIReferences() {
        tvCurrentFolder = findViewById(R.id.tvCurrentFolder);
        recyclerViewFolders = findViewById(R.id.recyclerViewFolders);
        btnNavigateUp = findViewById(R.id.btnNavigateUp);
    }

    private void initializeFolderStructure() {
        folderStructure = AppController.getInstance().getFolderStructure();
    }

    private void displaySubFolders() {
        tvCurrentFolder.setText(folderStructure.getCurrentFolder().getName());

        FolderAdapter folderAdapter = new FolderAdapter(folderStructure.getCurrentFolder().getSubFolders(), this);
        recyclerViewFolders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFolders.setAdapter(folderAdapter);
    }

    @Override
    public void onSubFolderClick(int position) {
        folderStructure.setCurrentFolder(folderStructure.getCurrentFolder().getSubFolders().get(position));
        displaySubFolders();
    }

    private void setupNavigateUp() {
        btnNavigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderStructure.getCurrentFolder().equals(folderStructure.getRootFolder())) {
                    Toast.makeText(v.getContext(), "Already At Root", Toast.LENGTH_SHORT).show();
                } else {
                    folderStructure.setCurrentFolder(folderStructure.getParentOf(folderStructure.getCurrentFolder()));
                    displaySubFolders();
                }
            }
        });
    }

}
