package com.greenback.android;

import android.util.Log;

import com.greenback.kit.client.GreenbackClient;
import com.greenback.kit.client.GreenbackConstants;
import com.greenback.kit.jackson.JacksonGreenbackCodec;
import com.greenback.kit.model.Vision;
import com.greenback.kit.model.VisionRequest;
import com.greenback.kit.okhttp.OkHttpGreenbackClient;
import com.greenback.kit.okhttp.OkHttpHelper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

public class GreenbackService {

    static public final GreenbackService INSTANCE = new GreenbackService();

    final private ExecutorService executors;
    final private OkHttpClient httpClient;
    final private GreenbackClient client;

    private GreenbackService() {
        // non-public
        this.executors = Executors.newSingleThreadExecutor();
        this.httpClient = OkHttpHelper.build()
            .newBuilder()
            .build();
        this.client = new OkHttpGreenbackClient(
            httpClient,
            GreenbackConstants.ENDPOINT_STAGING,
            new JacksonGreenbackCodec(),
                "up-CMK9l-QQ7Jx2nSXQi_bM7h_wpTuoHT-s8L34-nUlG7AzcvnpkHe55zl1C7vel");
    }

    public String prettyPrint(Vision vision) {
        try {
            return this.client.getCodec().prettyPrint(vision);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void processVision(
            File documentFile,
            GreenbackVisionCallback callback) {

        this.executors.submit(() -> {
            try {

                callback.onProgress("Uploading to Greenback...");

                final Vision vision = client.createVision(new VisionRequest()
                    .setAsync(true)
                    .setDocument(documentFile));

                Log.v("", "Vision: " + vision.getId());

                callback.onProgress("Recognizing transaction...");

                // poll for results...
                Vision polledVision = vision;

                while (!polledVision.getStatus().isTerminal()) {
                    polledVision = client.getVisionById(vision.getId());

                    Log.v("","Vision: id="+vision.getId()+", status=" + vision.getStatus());

                    Thread.sleep(500L);
                }

                callback.onSuccess(polledVision);
            }
            catch (Exception e) {
                e.printStackTrace();
                callback.onError(e);
            }
        });
    }

}