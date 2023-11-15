package com.example.documentharbor.ui.folderexplorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.enums.ProcessingMethod;
import com.example.documentharbor.filestructure.FolderStructure;
import com.example.documentharbor.interfaces.OnSubFolderClickedListener;
import com.example.documentharbor.ui.PhotoSessionActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        setupBottomSheetDialog();
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
        btnNavigateUp.setOnClickListener(v -> {
            if (folderStructure.getCurrentFolder().equals(folderStructure.getRootFolder())) {
                Toast.makeText(v.getContext(), "Already At Root", Toast.LENGTH_SHORT).show();
            } else {
                folderStructure.setCurrentFolder(folderStructure.getParentOf(folderStructure.getCurrentFolder()));
                displaySubFolders();
            }
        });
    }

    private void setupBottomSheetDialog() {
        FloatingActionButton fab = findViewById(R.id.fabAddOptions);
        fab.setOnClickListener(v -> showBottomSheetDialog());
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        Button addSubFolderButton = bottomSheetDialog.findViewById(R.id.btnAddSubfolder);
        Button startImageCaptureButton = bottomSheetDialog.findViewById(R.id.btnStartImageCapture);

        addSubFolderButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Add Folder");

            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String folderName = input.getText().toString().trim();
                if (!folderName.isEmpty()) {
                    folderStructure.createFolder(folderName);
                    displaySubFolders();
                    Toast.makeText(v.getContext(), "Folder Created", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
            bottomSheetDialog.cancel();
        });
        startImageCaptureButton.setOnClickListener(v -> {
            String sessionName = getIntent().getStringExtra("SESSION_NAME");
            String folderPath = folderStructure.getCurrentFolderPath();

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Select Processing Mode");

            ProcessingMethod[] items = ProcessingMethod.values();
            final int[] selectedItem = {-1};

            String[] itemNames = new String[items.length];
            for (int i = 0; i < items.length; i++) {
                itemNames[i] = items[i].toString();
            }

            builder.setSingleChoiceItems(itemNames, selectedItem[0], (dialog, which) -> selectedItem[0] = which);
            builder.setPositiveButton("OK", (dialog, which) -> {
                if (selectedItem[0] != -1) {
                    AppController.getInstance().createNewSession(sessionName, folderPath);
                    AppController.getInstance().setProcessingMethod(items[selectedItem[0]]);
                    Intent intent = new Intent(v.getContext(), PhotoSessionActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
            bottomSheetDialog.cancel();
        });

        bottomSheetDialog.show();
    }

}
