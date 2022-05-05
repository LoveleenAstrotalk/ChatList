package com.astrotalk.sdk.api.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.utils.TouchImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ChatImageViwerActvity extends AppCompatActivity implements View.OnClickListener {

    private TouchImageView zoomIV;
    String imageUrl="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_chat_image_viewer);
        intViews();
    }

    private void intViews() {
        imageUrl=getIntent().getStringExtra("url");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        zoomIV= (TouchImageView)findViewById(R.id.zoomIV);
        Glide.with(this).load(imageUrl).into(zoomIV);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(ContextCompat.getDrawable(this,R.drawable.at_circular_image))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(zoomIV);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

