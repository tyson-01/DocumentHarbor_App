package com.example.documentharbor.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.ui.folderexplorer.FolderExplorerActivity;

public class InitiateSessionActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private static final String[] REQUIRED_PERMISSIONS = new String[] {
            "android.permission.CAMERA",
            "android.permission.INTERNET"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_session);

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (allPermissionsGranted()) {
            new UpdateFolderStructureTask().execute();
        } else {
            AppController.getInstance().getLogger().log("InitiateSessionActivity", "User refused permissions");
            Toast.makeText(this, "Permissions Required, Goodbye", Toast.LENGTH_LONG).show();
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            AppController.getInstance().getLogger().log("main", "permission " + permission);
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                AppController.getInstance().getLogger().log("main", "permission " + permission + " Rejected");
                return false;
            }
            AppController.getInstance().getLogger().log("main", "permission " + permission + " Granted!");
        }
        return true;
    }

    public class UpdateFolderStructureTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            AppController.getInstance().updateFolderStructure();
            return AppController.getInstance().getFolderStructure() != null;
        }
        @Override
        protected void onPostExecute(Boolean isSuccessful) {
            if (isSuccessful) {
                setupUI();
            } else {
                AppController.getInstance().getLogger().log("async UpdateFolderStructureTask", "onPostExecute() not successful");
                Toast.makeText(InitiateSessionActivity.this, "Server can't be reached", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setupUI() {
        EditText etSessionName;
        etSessionName = findViewById(R.id.etSessionName);
        etSessionName.setText("");

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
