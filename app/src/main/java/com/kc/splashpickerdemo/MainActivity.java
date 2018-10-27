package com.kc.splashpickerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kc.splashpicker.SplashPicker;
import com.kc.splashpickerdemo.databinding.ActivityMainBinding;
import com.kc.unsplash.models.Photo;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    private final String CLIENT_ID = BuildConfig.Unsplash;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.pickButton.setOnClickListener(v -> SplashPicker.open(this, CLIENT_ID));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SplashPicker.PICK_IMAGE_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                Photo photo  = data.getParcelableExtra(SplashPicker.KEY_IMAGE);
                displayPhoto(photo);
            }
        }
    }

    private void displayPhoto(Photo photo) {
        Picasso.with(this)
                .load(photo.getUrls().getRegular())
                .resize(300, 300)
                .centerCrop()
                .into(mBinding.imageView);
    }
}
