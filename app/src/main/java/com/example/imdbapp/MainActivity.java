package com.example.imdbapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    MovieRecycFragment firstFragment;
    ViewGroup vGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vGroup = (ViewGroup) findViewById(R.id.fragmentContainer);
        intent = getIntent();

        if (savedInstanceState != null) {
            return;
        }
        // Create a new Fragment to be placed in the activity layout
        firstFragment = new MovieRecycFragment();
        // Add the fragment to the 'fragmentContainer' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, firstFragment).commit();

    }

    public void changeFragment (String item){
        switch (item) {
            case "QR":{
                Bundle bundle = new Bundle();
                bundle.putString("item",item);
                QRCodeFragment qrCodeFragment = new QRCodeFragment();
                qrCodeFragment.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, qrCodeFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
            break;
            case "first_no":{
                ShowSnackBar();
                getSupportFragmentManager().popBackStack();
            }
            break;
            case "first_yes":{
                Toast.makeText(this, "A new movie added to your list.", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().popBackStack();
            }
            break;
            default:{
                Bundle bundle = new Bundle();
                bundle.putString("item",item);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, detailsFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
            break;
        }
    }

    public void ShowSnackBar(){
        Snackbar.make(this.vGroup, "Current movie already exist in the Database", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

}