package com.example.assignment.webservices;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class AppService {
    private static AppService instance = null;
    private final RequestQueue requestQueue;
    private AppService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized AppService getInstance(Context context) {
        if (instance == null) {
            instance = new AppService(context);
        }
        return instance;
    }

    public void setRequestQueue(JsonObjectRequest request) {
        requestQueue.add(request);
    }
}
