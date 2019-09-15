package com.example.galleryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit.RestAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ImageView imageView;
 //   GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    LinearLayout linearLayout1;
    //Pagnation varible
    ProgressBar progressBar;
    int currentitem,totaliem,outitem;
    boolean check=true;
    int PAGE_API=1;
    Main3Activity m=new Main3Activity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.prograss);
        linearLayout1=findViewById(R.id.ide);
       // gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2); for splite in 2
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //for pagination changes the page
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                check=true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //GridLayoutManager variable,s
                //currentitem = gridLayoutManager.getChildCount();
                //totaliem = gridLayoutManager.getItemCount();
                //outitem = gridLayoutManager.findFirstVisibleItemPosition();
                currentitem = linearLayoutManager.getChildCount();
                totaliem = linearLayoutManager.getItemCount();
                outitem = linearLayoutManager.findFirstVisibleItemPosition();

                if (check && currentitem + outitem == totaliem) {
                    check=false;
                    PAGE_API++;
                    int a=PAGE_API-1;
                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                    Toast.makeText(MainActivity.this, "Page "+a+" End", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //here is the connection
     getData();
    }
    //Data getting new method
    void getData(){
        String URL_API="/services/rest/?method=flickr.photos.getRecent&per_page=20&page=";
        String URL_API1="&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s";
      //  String URL_NEW="/services/rest/?method=flickr.photos.search&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s&text=flower";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
       Call<Pojo> call=api.setPhot(URL_API+PAGE_API+URL_API1);
       // Call<Pojo> call=api.setPhot(URL_NEW);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo  result = response.body();
                //adapter attach
                recyclerView.setAdapter(new DataAdpter(MainActivity.this,result.getPhotos().getPhoto()));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                //snackbar when no connection
                Snackbar.make(linearLayout1,"No internet connection.",Snackbar.LENGTH_INDEFINITE)
                        .setAction("retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "retrying", Toast.LENGTH_SHORT).show();
                                getData();
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();
            }
        });
    }

    public void click(View view)
    {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
        finish();
    }

}
