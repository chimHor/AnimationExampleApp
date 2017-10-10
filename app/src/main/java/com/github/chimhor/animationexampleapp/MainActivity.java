package com.github.chimhor.animationexampleapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FrameLayout canvasView;
    List<ImageView> rects = new ArrayList<ImageView>();

    int mDuration = 3000;

    //prop animation
    AnimatorSet animatorSet;
    ValueAnimator mPosAnimator;
    ValueAnimator mAlphaAnimator;
    View propAnimationTarget;


    //tween animation
    TranslateAnimation translateAnimation;
    AlphaAnimation alphaAnimation;
    AnimationSet animationSet;
    View tweenAnimationTarget;

    private ValueAnimator.AnimatorUpdateListener posListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int curValue = (int)animation.getAnimatedValue();
            propAnimationTarget.setY(curValue);
        }
    };

    private ValueAnimator.AnimatorUpdateListener alphaListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float curValue = (float)animation.getAnimatedValue();
            propAnimationTarget.setAlpha(curValue);
        }
    };

    private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasView = (FrameLayout) findViewById(R.id.canvas);
        int color[] = {Color.BLUE, Color.GREEN};
        generateRectView(color);
        canvasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    void generateRectView(int color[]) {
        if (color == null) return;

        if (rects.size() > 0) {
            canvasView.removeAllViews();
            rects.clear();
        }
        for (int i = 0; i < color.length; i++) {
            ImageView v = new ImageView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(70,70);
            v.setLayoutParams(params);
            v.setImageDrawable(new ColorDrawable(color[i]));
            rects.add(v);
            canvasView.addView(v);
        }
    }

    void configAnimationIfNeed() {
        // prop animation
        if (mPosAnimator == null)
            mPosAnimator = ValueAnimator.ofInt(0, 500).setDuration(mDuration);
        if (mAlphaAnimator == null)
            mAlphaAnimator = ValueAnimator.ofFloat(1.0f,0.4f).setDuration(mDuration);
        mPosAnimator.addUpdateListener(posListener);
        mAlphaAnimator.addUpdateListener(alphaListener);
        if (animatorSet == null) {
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(mPosAnimator, mAlphaAnimator);
            animatorSet.addListener(animationListener);
        }
        propAnimationTarget = rects.get(0);

        //teen animation
        if (translateAnimation == null)
            translateAnimation = new TranslateAnimation(100f, 100f, 0f, 500f);
        if (alphaAnimation == null)
            alphaAnimation = new AlphaAnimation(1.0f, 0.4f);

        if (animationSet == null) {
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
        }
        animationSet.setDuration(mDuration);
        tweenAnimationTarget = rects.get(1);
    }

    void startAnimation() {
        configAnimationIfNeed();
        animatorSet.start();
        tweenAnimationTarget.startAnimation(animationSet);
    }



}
