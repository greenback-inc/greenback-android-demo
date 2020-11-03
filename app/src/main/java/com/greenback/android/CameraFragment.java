package com.greenback.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.greenback.kit.client.GreenbackConstants;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    static final int REQUEST_IMAGE_GALLERY = 100;
    static final int REQUEST_IMAGE_CAPTURE = 101;

    private File capturedImageFile;
    private EditText endpointEditText;
    private EditText accessTokenEditText;
    private GreenbackPrefs prefs;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.prefs = new GreenbackPrefs(this.getContext());

        this.endpointEditText = this.getView().findViewById(R.id.endpointText);
        this.accessTokenEditText = this.getView().findViewById(R.id.accessTokenText);

        this.endpointEditText.setText(this.prefs.getEndpoint());
        this.accessTokenEditText.setText(this.prefs.getAccessToken());

        this.endpointEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.setEndpoint(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.accessTokenEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.setAccessToken(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        view.findViewById(R.id.button_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraFragment.this.startGallery();
            }
        });

        view.findViewById(R.id.button_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraFragment.this.startCapture();
            }
        });
    }

    public void startGallery() {

        Log.v("", "Starting gallery activity");

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    public void startCapture() {
        // Create the File where the photo should go
        File photoFile;
        try {
            photoFile = FileUtil.createImageFile(this.getContext());
        } catch (IOException e) {
            Log.e("", "Unable to create temp image file", e);
            return;
        }

        Log.v("", "Starting capture activity: photoFile=" + photoFile);

        Uri imageUri = FileProvider.getUriForFile(
            this.getContext(),
            "com.greenback.android.fileprovider",
            photoFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        this.capturedImageFile = photoFile;
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void navigateUpload(File imageFile) {
        Bundle args = new Bundle();
        args.putString("image_path", imageFile.getAbsolutePath());
        NavHostFragment.findNavController(CameraFragment.this)
                .navigate(R.id.action_CameraFragment_to_UploadFragment, args);
    }

    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        Log.v("","requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            if (data == null) {
                Log.v("", "Intent data was null!");
                return;
            }

            try {
                Uri uri = data.getData();
                File file = FileUtil.from(this.getContext(), uri);
                Log.v("", "Chosen image file " + file);
                this.navigateUpload(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (this.capturedImageFile == null) {
                Log.v("", "Captured image file was null!");
                return;
            }
            if (!this.capturedImageFile.exists()) {
                Log.v("", "Captured image file " + this.capturedImageFile + " does not exist");
                return;
            }
            System.out.println("Captured image file " + this.capturedImageFile);

            this.navigateUpload(this.capturedImageFile);
        }
    }

}