package com.example.documentharbor.ui.folderexplorer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.filestructure.FolderStructure;

public class FolderExplorerActivity extends AppCompatActivity {

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
        tvCurrentFolder.setText(folderStructure.getCurrentFolder().getName());
    }

    private void displaySubFolders() {
        FolderAdapter folderAdapter = new FolderAdapter(folderStructure.getCurrentFolder().getSubFolders());
        recyclerViewFolders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFolders.setAdapter(folderAdapter);
    }

    private void setupNavigateUp() {
        btnNavigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Handle navigate up action
            }
        });
    }
}
