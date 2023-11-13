package com.example.documentharbor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.ui.folderexplorer.FolderExplorerActivity;

public class InitiateSessionActivity extends AppCompatActivity {

    private EditText etSessionName;
    private Button btnStartSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_session);
        AppController.getInstance();

        etSessionName = findViewById(R.id.etSessionName);
        btnStartSession = findViewById(R.id.btnStartSession);

        btnStartSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewSession(etSessionName.getText().toString().trim());
            }
        });
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
