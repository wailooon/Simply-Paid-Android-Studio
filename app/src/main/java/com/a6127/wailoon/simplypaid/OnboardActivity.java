package com.a6127.wailoon.simplypaid;

import android.os.Bundle;
import android.support.v4.text.HtmlCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnboardActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout DotLayout;

    private Button backBtn;
    private  Button nextBtn;

    private int currentPage;

    private TextView[] mDots;

    private  SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        slideViewPager = (ViewPager)findViewById(R.id.slideViewPager);
        DotLayout = (LinearLayout)findViewById(R.id.dots);

        backBtn = (Button)findViewById(R.id.back_Btn);
        nextBtn = (Button)findViewById(R.id.next_Btn);

        sliderAdapter = new SliderAdapter(this);

        slideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(viewListener);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(currentPage + 1);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(currentPage - 1);

            }
        });
    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        DotLayout.removeAllViews();

        for(int i = 0;i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(HtmlCompat.fromHtml("&#8226",HtmlCompat.FROM_HTML_MODE_LEGACY));
            mDots[i].setTextSize(45);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentBlack));

            DotLayout.addView(mDots[i]);


        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            currentPage = i;

            if(i == 0){
                nextBtn.setEnabled(true);
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Next");
                backBtn.setText("");
            }else if(i == mDots.length - 1){
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Finish");
                backBtn.setText("Back");

            }else{
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Next");
                backBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
