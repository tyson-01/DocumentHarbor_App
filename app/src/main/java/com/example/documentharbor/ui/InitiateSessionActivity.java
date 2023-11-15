package com.example.documentharbor.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.ui.folderexplorer.FolderExplorerActivity;

public class InitiateSessionActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[] {
            "android.permission.CAMERA",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_session);

        boolean sessionStatusGood = validateServerConnection(AppController.getInstance());

        if (sessionStatusGood && allPermissionsGranted()) {
            setupUI();
        } else if (sessionStatusGood) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        } else {
            Toast.makeText(this, "Server Connection Error", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateServerConnection(AppController instance) {
        instance.updateFolderStructure();
        return instance.getFolderStructure() != null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if(allPermissionsGranted()) {
                setupUI();
            } else {
                Toast.makeText(this, "Permissions Required, Goodbye", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void setupUI() {
        EditText etSessionName;
        etSessionName = findViewById(R.id.etSessionName);
        Button btnStartSession = findViewById(R.id.btnStartSession);

        btnStartSession.setOnClickListener(v -> startNewSession(etSessionName.getText().toString().trim()));
    }

    private void startNewSession(String sessionName) {
        if (sessionName.isEmpty()) {
            Toast.makeText(this, "Enter Session Name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, FolderExplorerActivity.class);
        intent.putExtra("SESSION_NAME", sessionName);
        startActivity(intent);
    }

}
