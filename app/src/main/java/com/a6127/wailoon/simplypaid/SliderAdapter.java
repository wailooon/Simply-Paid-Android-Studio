package com.a6127.wailoon.simplypaid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    // Arrays
    public int[] slide_images = {
//              R.drawable.eat_icon,
//              R.drawable.sleep_icon,
//              R.drawable.code_icon
    };

    public String[] slide_headings = {

            "SCAN",
            "ORDER",
            "PAYMENT"


    };

    public String[] slide_descs = {
            "sfsdfsdf",
            "dfad",
            "DFsdfds"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        TextView slideHeading = (TextView)view.findViewById(R.id.headingTextView);
        TextView slideDescription = (TextView)view.findViewById(R.id.descriptionTextView);

        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position, Object object){
        container.removeView((RelativeLayout)object);

    }
}
