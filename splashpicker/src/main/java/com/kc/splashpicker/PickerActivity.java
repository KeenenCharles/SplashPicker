package com.kc.splashpicker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.splashpicker.databinding.ActivityPickerBinding;
import com.kc.splashpicker.views.EndlessRecyclerViewScrollListener;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PickerActivity extends AppCompatActivity implements PhotoRecyclerAdapter.OnPhotoClickedListener{

    private final String TAG = "PlasterMain";

    private Unsplash unsplash;
    private PhotoRecyclerAdapter adapter;

    private int page = 1;
    private ActivityPickerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_picker);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String clientID = intent.getStringExtra("CLIENT_ID");
        String title = intent.getStringExtra("TITLE");

        getSupportActionBar().setTitle(title);
        unsplash = new Unsplash(clientID);

        setupPhotoGrid();
        setupSearch();
    }

    private void setupSearch() {
        mBinding.searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                    search(mBinding.searchBar.getText().toString());
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    private void setupPhotoGrid() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoRecyclerAdapter(new ArrayList<Photo>(), this, this);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadPhotos();
            }
        });

        loadPhotos();
    }

    @Override
    public void photoClicked(Photo photo, ImageView imageView) {
        Intent intent = new Intent();
        intent.putExtra(SplashPicker.KEY_IMAGE, photo);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void loadPhotos() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        unsplash.getPhotos(page, null, Order.LATEST, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                Log.d("Photos", "Photos Fetched " + photos.size());
                //add to adapter
                page++;
                adapter.addPhotos(photos);
                mBinding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void search(String query){
        if(query != null && !query.equals("")) {
            mBinding.progressBar.setVisibility(View.VISIBLE);
            unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
                @Override
                public void onComplete(SearchResults results) {
                    Log.d("Photos", "Total Results Found " + results.getTotal());
                    List<Photo> photos = results.getResults();
                    PhotoRecyclerAdapter adapter = new PhotoRecyclerAdapter(photos, PickerActivity.this, PickerActivity.this);
                    mBinding.recyclerView.setAdapter(adapter);
                    mBinding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(String error) {
                    Log.d("Unsplash", error);
                }
            });
        }
        else {
            loadPhotos();
        }
    }

    private void showSearchBar() {
        mBinding.appBarLayout.setVisibility(View.GONE);
        mBinding.searchBox.setVisibility(View.VISIBLE);
        mBinding.searchBar.requestFocus();
    }

    public void hideSearchBar(View view) {
        mBinding.searchBox.setVisibility(View.GONE);
        mBinding.appBarLayout.setVisibility(View.VISIBLE);
        mBinding.searchBar.clearFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search) {
            Log.d(TAG, "Search bar open");
            showSearchBar();
            return true;
        }
        if(item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mBinding.searchBox.getVisibility() == View.VISIBLE){
            Log.d(TAG, "Search bar visible");
            hideSearchBar(null);
            return;
        }
        super.onBackPressed();
    }


}
