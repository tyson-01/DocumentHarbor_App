package com.example.documentharbor.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.documentharbor.R;
import com.example.documentharbor.controller.AppController;
import com.example.documentharbor.interfaces.EndSignalCallback;
import com.example.documentharbor.interfaces.ImageUploadCallback;
import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public class PhotoSessionActivity extends AppCompatActivity implements ImageUploadCallback, EndSignalCallback {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private Button captureButton;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_session);

        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        startCamera();

        captureButton = findViewById(R.id.btnCapture);
        doneButton = findViewById(R.id.btnDone);

        captureButton.setOnClickListener(view -> {
            captureButton.setEnabled(false);
            doneButton.setEnabled(false);
            captureImage();
        });
        doneButton.setOnClickListener(view -> finishSession());
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                AppController.getInstance().getLogger().log("PhotoSessionActivity:startCamera", "Exception Triggered: " + e.getMessage(), e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);

        imageCapture = new ImageCapture.Builder().build();
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture);
    }

    private void captureImage() {
        if (imageCapture == null) return;

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                handleImageCaptured(image);
            }

            @Override
            public void onError(@NonNull ImageCaptureException e) {
                AppController.getInstance().getLogger().log("PhotoSessionActivity:captureImage", "Exception Triggered: " + e.getMessage(), e);
            }
        });
    }

    private void handleImageCaptured(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        image.close();

        AppController.getInstance().getCurrentPhotoSession().addPhotoToSession(data, this);
    }

    @Override
    public void onImageUploaded(boolean isSuccessful) {
        if (isSuccessful) {
            AppController.getInstance().getCurrentPhotoSession().incrementPhotoIndex();

            AppController.getInstance().getLogger().log("PhotoSessionActivity", "Image uploaded Successfully");
            Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show();
        } else {
            AppController.getInstance().getLogger().log("PhotoSessionActivity", "Image upload Failed");
            Toast.makeText(this, "Upload Failed. Please try again", Toast.LENGTH_SHORT).show();
        }

        captureButton.setEnabled(true);
        doneButton.setEnabled(true);
    }

    private void finishSession() {
        if (AppController.getInstance().getCurrentPhotoSession().numberOfPhotos() < 1) {
            Toast.makeText(this, "Session canceled", Toast.LENGTH_SHORT).show();
            finish();
        }

        AppController.getInstance().endSession(this);
    }

    @Override
    public void onEndSignalSent(boolean isSuccessful) {
        if (isSuccessful) {
            Toast.makeText(this, "Great Success!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
