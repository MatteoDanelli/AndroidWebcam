package com.blogspot.cyberteo.android.webcam;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.blogspot.euroteo.webcam.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

/**
 * Created by mattd on 3/10/14.
 */
public class WebcamActivity extends Activity{

    private static final int REFRESH = 0;
    private String webcamUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcam);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String webcamLink = getIntent().getStringExtra("LINK");
        webcamLink = "http://www.foto-webcam.eu".concat(webcamLink);
        webcamUrl = webcamLink.concat("current/1200.jpg");

        //Set title
        this.setTitle(getIntent().getStringExtra("TITLE"));

        loadImage(webcamUrl, imageView, progressBar);
    }


    /*
     * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, REFRESH, 0, "Refresh");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (REFRESH):
                final ImageView imageView = (ImageView) findViewById(R.id.imageView);
                final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                loadImage(webcamUrl, imageView, progressBar);
                break;
        }
        return false;
    }

    private void loadImage(String url, final ImageView imageView, final ProgressBar progressBar) {
        ImageLoader imageLoader = ImageLoader.getInstance();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                //TODO
                .build();
        ImageLoader.getInstance().init(config);
        // Load image, decode it to Bitmap and return Bitmap to callback
        imageLoader.loadImage(url, null, null, new ImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Set bitmap and attach animation
                        progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(loadedImage);
                        new PhotoViewAttacher(imageView);
                    }
                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        // Do nothing
                    }
                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                        Toast.makeText(getApplicationContext(), "Error loading webcam", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        // Do nothing
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
    }

}