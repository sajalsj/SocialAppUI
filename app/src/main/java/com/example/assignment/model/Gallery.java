package com.example.assignment.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Gallery {
    private final static String TAG = Gallery.class.getSimpleName();
    private List<String> photosList;

    public Gallery(JSONArray photosArray) {
        photosList = new ArrayList<>();
        if (photosArray != null) {
            for (int i = 0; i < photosArray.length(); i++) {
                try {
                    JSONObject userPosts = photosArray.getJSONObject(i);
                    String fileName = userPosts.getString("filename");
                    photosList.add(fileName);
                    Log.d(TAG, "post ==" + userPosts.toString());
                } catch (JSONException jsonException) {
                    Log.d(TAG, jsonException.toString());
                }
            }
        }
    }

    public List<String> getPhotosList() {
        return photosList;
    }
}
