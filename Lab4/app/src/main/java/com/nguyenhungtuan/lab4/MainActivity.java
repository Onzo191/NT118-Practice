package com.nguyenhungtuan.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml,
            btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml,
            btnZoomOutCode, btnRotateXml,
            btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode,
            btnBounceXml,
            btnBounceCode, btnCombineXml, btnCombineCode;
    private ImageView ivUitLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initAnimationXML();
        initAnimationCode();
        handlePageTransition();
    }

    private void initViews() {
        // Map component
        ivUitLogo = findViewById(R.id.iv_uit_logo);
        btnFadeInXml = findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = findViewById(R.id.btn_blink_xml);
        btnBlinkCode = findViewById(R.id.btn_blink_code);
        btnZoomInXml = findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = findViewById(R.id.btn_rotate_xml);
        btnRotateCode = findViewById(R.id.btn_rotate_code);
        btnMoveXml = findViewById(R.id.btn_move_xml);
        btnMoveCode = findViewById(R.id.btn_move_code);
        btnSlideUpXml = findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = findViewById(R.id.btn_slide_up_code);
        btnBounceXml = findViewById(R.id.btn_bounce_xml);
        btnBounceCode = findViewById(R.id.btn_bounce_code);
        btnCombineXml = findViewById(R.id.btn_combine_xml);
        btnCombineCode = findViewById(R.id.btn_combine_code);
    }

    private void initAnimationXML() {
        //HandleClickAnimationXML
        handleClickAnimationXML(btnFadeInXml, R.anim.anim_fade_in);
        handleClickAnimationXML(btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXML(btnBlinkXml, R.anim.anim_blink);
        handleClickAnimationXML(btnZoomInXml, R.anim.anim_zoom_in);
        handleClickAnimationXML(btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXML(btnRotateXml, R.anim.anim_rotate);
        handleClickAnimationXML(btnMoveXml, R.anim.anim_move);
        handleClickAnimationXML(btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXML(btnBounceXml, R.anim.anim_bounce);
        handleClickAnimationXML(btnCombineXml, R.anim.anim_combine);

    }

    private void handleClickAnimationXML(Button btn, int animId) {
        btn.setOnClickListener(v -> ivUitLogo.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, animId)));
    }

    private void initAnimationCode() {
        //HandleClickAnimationCode
        handleClickAnimationCode(btnFadeInCode, initFadeInAnimation());
        handleClickAnimationCode(btnFadeOutCode, initFadeOutAnimation());
        handleClickAnimationCode(btnBlinkCode, initBlinkAnimation());
        handleClickAnimationCode(btnZoomInCode, initZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode, initZoomOutAnimation());
        handleClickAnimationCode(btnRotateCode, initRotateAnimation());
        handleClickAnimationCode(btnMoveCode, initMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode, initSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode, initBounceAnimation());
        handleClickAnimationCode(btnCombineCode, initCombineAnimation());
    }

    private void handleClickAnimationCode(Button btn, final Animation animation) {
        btn.setOnClickListener(v -> ivUitLogo.startAnimation(animation));
    }

    private void handlePageTransition() {
        ivUitLogo.setOnClickListener(v -> {
            Intent iNewActivity = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(iNewActivity);

            //overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            overridePendingTransition(R.anim.anim_activity_in, R.anim.anim_activity_out);
        });
    }

    private Animation initFadeInAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initFadeOutAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initBlinkAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(300);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(3);
        
        return animation;
    }

    private Animation initZoomInAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initZoomOutAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initRotateAnimation() {
        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(600);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(2);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initMoveAnimation() {
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.75f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        animation.setDuration(800);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initSlideUpAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initBounceAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        animation.setInterpolator(bounceInterpolator);
        animation.setFillAfter(true);
        
        return animation;
    }

    private Animation initCombineAnimation() {
        //scale
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 3.0f,
                1.0f, 3.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(4000);

        Interpolator linearInterpolator = new LinearInterpolator();
        scaleAnimation.setInterpolator(linearInterpolator);

        //rotate
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setRepeatMode(Animation.RESTART);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillAfter(true);

        return animationSet;
    }
}