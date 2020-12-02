package com.breejemodi.exxxamdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar =findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<CategoryModel> list =new ArrayList<>();
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","CS201"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","CS203"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","MA201"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","EC201"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","SC201"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","EE170"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","CS261"));
        list.add(new CategoryModel("gs://exxamdesk-app.appspot.com/csimage.jpg","CS263"));

        CategoryAdapter adapter=new CategoryAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}