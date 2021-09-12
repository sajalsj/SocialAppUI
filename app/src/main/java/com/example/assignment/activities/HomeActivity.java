package com.example.assignment.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.assignment.adapters.PostAdapter;
import com.example.assignment.databinding.ActivityHomeBinding;
import com.example.assignment.model.Gallery;
import com.example.assignment.webservices.ApiInterface;
import com.example.assignment.webservices.AppService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public final static String TAG = HomeActivity.class.getSimpleName();
    private PostAdapter photosAdapter;
    private List<Gallery> userPosts;
    private ActivityHomeBinding homeBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        userPosts = new ArrayList<>();
        homeBinding.userPostRv.setLayoutManager(new LinearLayoutManager(this));
        photosAdapter = new PostAdapter(this, userPosts);
        homeBinding.userPostRv.setAdapter(photosAdapter);
        homeBinding.activityHomePb.setVisibility(View.VISIBLE);
        getUserPosts();
    }

    public void getUserPosts() {
        JsonObjectRequest getUserPosts = new JsonObjectRequest(Request.Method.GET, ApiInterface.BASE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    homeBinding.activityHomePb.setVisibility(View.INVISIBLE);
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userPost = jsonArray.getJSONObject(i);
                        JSONArray postJSONArray = userPost.getJSONArray("GALLERY");
                        Gallery gallery = new Gallery(postJSONArray);
                        userPosts.add(gallery);
                    }

                    photosAdapter.setUserPosts(userPosts);
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }, error -> Log.d(TAG, error.toString()));
        AppService.getInstance(this).setRequestQueue(getUserPosts);
    }

}
