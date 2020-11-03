package com.greenback.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.greenback.kit.model.Vision;
import java.io.File;

public class UploadFragment extends Fragment {

    private File imageFile;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        if (this.getArguments() != null) {
            this.imageFile = new File(this.getArguments().getString("image_path"));
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v("", "Upload Fragment with: " + this.imageFile);

        final ProgressBar uploadProgressBar = view.findViewById(R.id.uploadProgressBar);
        final TextView uploadText = view.findViewById(R.id.jsonText);

        uploadText.setText("Will begin upload...");

        GreenbackService.INSTANCE.processVision(this.imageFile, new GreenbackVisionCallback() {
            @Override
            public void onProgress(String progressMessage) {
                uploadText.setText(progressMessage);
            }

            @Override
            public void onSuccess(Vision vision) {
                uploadText.setText("Success!");
                UploadFragment.this.navigateResults(vision);
            }

            @Override
            public void onError(Exception e) {
                uploadText.setText("ERROR: " + e.getMessage());
            }
        });
    }

    public void navigateResults(Vision vision) {

        final String json = GreenbackService.INSTANCE.prettyPrint(vision);

        Bundle args = new Bundle();
        args.putString("vision_json", json);
        NavHostFragment.findNavController(UploadFragment.this)
                .navigate(R.id.action_UploadFragment_to_VisionJsonFragment, args);
    }

}