package com.example.youku;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

/**
 * Created by 江浩 on 2016/11/16.
 *
 */
public class Tools {
   public static  void hideView(ViewGroup view){
       hideView(view,0);
   }
   public static void  showView(ViewGroup view){
       showView(view,0);
   }

    public static void hideView(ViewGroup view, int startOffset) {
        /*RotateAnimation rotateAnimation=new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
        rotateAnimation.setDuration(500);
        //播放完停留状态
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(startOffset);
        view.startAnimation(rotateAnimation);
        for (int i=0;i<view.getChildCount();i++){
            View child=view.getChildAt(i);
            child.setEnabled(false);
        }*/
        //视图动画--属性动画
        //view.setRotation();
        ObjectAnimator animaor=ObjectAnimator.ofFloat(view,"rotation",0,180);
        animaor.setDuration(500);
        animaor.setStartDelay(startOffset);
        animaor.start();
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }

    public static void showView(ViewGroup view, int startOffset) {
       /*RotateAnimation ro=new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
        ro.setStartOffset(startOffset);
        ro.setDuration(500);
        ro.setFillAfter(true);
        view.startAnimation(ro);
        for (int i=0;i<view.getChildCount();i++){
            View child=view.getChildAt(i);
            child.setEnabled(true);
        }*/
        ObjectAnimator animaor=ObjectAnimator.ofFloat(view,"rotation",180,360);
        animaor.setDuration(500);
        animaor.setStartDelay(startOffset);
        animaor.start();
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());

    }
}
