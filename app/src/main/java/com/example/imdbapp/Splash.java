package com.example.imdbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Splash extends AppCompatActivity {
    private SQLiteDB db;
    GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        db = new SQLiteDB(this);
        gifImageView = (GifImageView) findViewById(R.id.gif);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallApi api = retrofit.create(CallApi.class);
        Call<List<MovObj>> call = api.getMovieList();
        call.enqueue(new Callback<List<MovObj>>() {
            @Override
            public void onResponse(Call<List<MovObj>> call, Response<List<MovObj>> response) {
                List<MovObj> movieList = response.body();
                for(MovObj m : movieList){
                    db.addData(m);
                }
            }

            @Override
            public void onFailure(Call<List<MovObj>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);

    }
}