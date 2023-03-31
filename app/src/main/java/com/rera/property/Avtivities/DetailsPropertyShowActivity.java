package com.rera.property.Avtivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.rera.property.Fragments.AddImages;
import com.rera.property.Fragments.DeleteFragment;
import com.rera.property.Fragments.PicZoomFragment;
import com.rera.property.Fragments.PostAnswer;
import com.rera.property.Fragments.PropertyDeatialFragment;
import com.rera.property.R;
import com.rera.property.Utils.ListingData;

public class DetailsPropertyShowActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_property_show);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);


        Fragment fragment = new PropertyDeatialFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerfragment, fragment)
                .commit();
    }


    @Override
    public void onBackPressed() {


        Fragment fragment1 = getSupportFragmentManager().findFragmentById(R.id.containerfragment);

        System.out.println("fragmenttttt :"+fragment1);

        if(fragment1 instanceof PropertyDeatialFragment){
            ListingData.getInstance(getApplicationContext()).Logout();
            super.onBackPressed();        }

        if(fragment1 instanceof AddImages){
            getSupportFragmentManager().popBackStack();
        }
        if(fragment1 instanceof PicZoomFragment){
            getSupportFragmentManager().popBackStack();
        }
        if(fragment1 instanceof PostAnswer){
            getSupportFragmentManager().popBackStack();
        }

        if(fragment1 instanceof DeleteFragment){
            getSupportFragmentManager().popBackStack();
        }





    }

//    @Override
//    protected void onStop() {
//        ListingData.getInstance(getApplicationContext()).Logout();
//        super.onStop();
//    }
}