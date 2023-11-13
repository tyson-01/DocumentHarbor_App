package com.example.documentharbor.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.imaging.PhotoSession;

public class PhotoSessionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_session);

        //TODO: implement photo capture, testerino is here to make sure shit works before this
        testerino();
    }

    public void testerino() {
        PhotoSession ps = AppController.getInstance().getCurrentPhotoSession();
        String s1 = ps.getSessionName();
        String s2 = ps.getFolderPath();
        String s3 = ps.getProcessingMethod().toString();

        TextView tv = findViewById(R.id.testTextDisplay);
        tv.setText(s2 + "/" + s1 + "\n" + s3);
    }
}
