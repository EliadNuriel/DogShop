package com.example.dog_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dog_app.models.Dog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements DogItemClickListener {

    private RecyclerView dogsRv;
    private DogsRvAdapter dogsRvAdapter;
    private  LocalStore store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store = LocalStore.getInstance(getApplicationContext());
        FloatingActionButton add_button = findViewById(R.id.add_new_dog_button);
        FloatingActionButton about_button = findViewById(R.id.about_us_button);
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AboutUsActivity.class);
                startActivity(i);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddDogActivity.class);
                startActivity(i);
            }
        });
        dogsRv = findViewById(R.id.dogs_rv);
        dogsRv.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(store.getDogs());
    }

    @Override
    protected void onResume() {
        super.onResume();
       dogsRvAdapter = new DogsRvAdapter(store.getDogs(),this);
       dogsRv.setAdapter(dogsRvAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickedShowMore(Dog dog) {
        Intent i = new Intent(this,DogDetailsActivity.class);
        store.setDogForDetails(dog);
        startActivity(i);
    }

    @Override
    public void clickedDelete(Dog dog) {
        store.removeDogFromStorage(getApplicationContext(),dog);
        dogsRvAdapter.changeDataSet(store.getDogs());
    }
}