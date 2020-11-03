package com.greenback.android;

import com.greenback.kit.model.Vision;

public interface GreenbackVisionCallback {

    void onProgress(String progressMessage);

    void onSuccess(Vision vision);

    void onError(Exception e);

}