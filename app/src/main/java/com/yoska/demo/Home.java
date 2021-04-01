package com.yoska.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    private FirebaseAuth auth;

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    RecyclerAdapter adapter;
    AlertDialog.Builder dlgAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dlgAlert = new AlertDialog.Builder(Home.this);


        recyclerView = findViewById(R.id.list_data);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        sendRequestresponse();

        auth = FirebaseAuth.getInstance();
    }


    // using Volley to fetch data
    private void sendRequestresponse() {
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(
                Request.Method.GET,
                MyUtil.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String JsonString = new String(response);
                        Log.i("jsonData",JsonString);

                        if(JsonString != null) {
                            JSONObject jsonObject = null;
                            try {

                                jsonObject = new JSONObject(JsonString);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                //calling recycleAdapter class
                                adapter = new RecyclerAdapter(jsonArray, getApplicationContext());

                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(stringRequest);
    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                dlgAlert.setTitle("Confirm Exit");
                dlgAlert.setMessage("Are you sure you want to exit?");
                dlgAlert.setCancelable(true);
                dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                        startActivity(new Intent(Home.this,MainActivity.class));
                        finish();
                    }
                });

                dlgAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog alertDialog = dlgAlert.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                //auth.signOut();
                //startActivity(new Intent(Home.this,MainActivity.class));
                //finish();
        }
        return super.onOptionsItemSelected(item);
    }
}