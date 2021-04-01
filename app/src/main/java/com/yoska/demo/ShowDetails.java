package com.yoska.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ShowDetails extends AppCompatActivity {

    TextView name;
    TextView flavour;
    TextView price;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        //set activity title
        setTitle("Ice Cream");
        //set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        flavour = findViewById(R.id.flavour);
        price  = findViewById(R.id.price);
        imageView = findViewById(R.id.imageView);

        name.setText(getIntent().getStringExtra("name"));
        flavour.setText(getIntent().getStringExtra("flavour"));
        price.setText(getIntent().getStringExtra("price"));

        //set image to imageView
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image")).into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}