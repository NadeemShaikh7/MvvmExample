package com.example.mvvmexample.view;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvmexample.R;

public class Util {
    public static void loadImage(ImageView imageView,String url){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getProgressDrawable(imageView.getContext()))
                .error(R.mipmap.ic_launcher_round);
        Glide.with(imageView.getContext()).setDefaultRequestOptions(requestOptions).load(url).into(imageView);
    }

    private static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;
    }
    
}
