package com.example.galleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main3Activity extends AppCompatActivity {
      RecyclerView recyclerView1;
      LinearLayout linearLayout;
      LinearLayoutManager linearLayoutManager;
    String s;
    Boolean flag=true;
    String URL_NEW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        linearLayout=findViewById(R.id.main3);
       recyclerView1=(RecyclerView) findViewById(R.id.serchrecycle);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(linearLayoutManager);
        android.widget.SearchView searchView1=findViewById(R.id.searchv);
        //instagram like search
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String sa) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String sa) {
                flag=false;
                if(sa.isEmpty()){
                    URL_NEW="/services/rest/?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s";
                }
             else{
                 s=sa;
                    URL_NEW = "/services/rest/?method=flickr.photos.search&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s&text="+
                            s;
                }
                getData();
                return false;
            }
        });


        getData();
    }
    //getting the searched data
    void getData(){

        if(flag) {
            URL_NEW="/services/rest/?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s";

        }
        else {
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Pojo> call=api.setPhot(URL_NEW);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo  result = response.body();
                recyclerView1.setAdapter(new DataAdpter(Main3Activity.this,result.getPhotos().getPhoto()));
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                //snackbar when no connection
                Snackbar.make(linearLayout,"No internet connection.",Snackbar.LENGTH_INDEFINITE)
                        .setAction("retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getData();
                                Toast.makeText(Main3Activity.this, "retrying", Toast.LENGTH_SHORT).show();
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();
            }
        });
    }
}
