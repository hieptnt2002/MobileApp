package com.example.mobileapp.Banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mobileapp.Banner.Banner;
import com.example.mobileapp.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    List<Banner> imgBannerList;

    public BannerAdapter(Context context, List<Banner> imgBannerList) {
        this.context = context;
        this.imgBannerList = imgBannerList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_img,container,false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView = view.findViewById(R.id.imageViewBanner);

        Banner imgBanner = imgBannerList.get(position);
        if(imgBanner != null){
            Glide.with(context).load(imgBanner.getImg()).into(imageView);
        }
        // add view to group
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if(imgBannerList != null){
            return imgBannerList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
