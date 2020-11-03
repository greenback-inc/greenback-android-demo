package com.greenback.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.greenback.kit.client.GreenbackConstants;

public class GreenbackPrefs {

    private final SharedPreferences sharedPref;

    public GreenbackPrefs(Context context) {
        this.sharedPref = context.getSharedPreferences("gbdemo", Context.MODE_PRIVATE);
    }

    public String getEndpoint() {
        //return this.sharedPref.getString("endpoint", GreenbackConstants.ENDPOINT_PRODUCTION);
        return BuildConfig.GREENBACK_ENDPOINT;
    }

    public void setEndpoint(String endpoint) {
        //this.sharedPref.edit().putString("endpoint", endpoint).apply();
    }

    public String getAccessToken() {
        //return this.sharedPref.getString("access_token", null);
        return BuildConfig.GREENBACK_ACCESS_TOKEN;
    }

    public void setAccessToken(String accessToken) {
        //this.sharedPref.edit().putString("access_token", accessToken).apply();
    }

}