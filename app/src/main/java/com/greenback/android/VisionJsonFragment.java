package com.greenback.android;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;

public class VisionJsonFragment extends Fragment {

    private String json;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        if (this.getArguments() != null) {
            this.json = this.getArguments().getString("vision_json");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vision_json, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView jsonText = view.findViewById(R.id.jsonText);

        jsonText.setText(this.json);
        jsonText.setMovementMethod(new ScrollingMovementMethod());

        view.findViewById(R.id.anotherButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(VisionJsonFragment.this)
                     .navigate(R.id.action_VisionJsonFragment_to_CameraFragment);
            }
        });
    }
}