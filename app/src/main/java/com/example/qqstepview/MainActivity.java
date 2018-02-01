package com.example.qqstepview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    private QQStepView step_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        step_view = (QQStepView) findViewById(R.id.step_view);

        //设置最大值
        step_view.setStepMax(4000);

        //属性动画
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //当前步数
                float currentStep = (float) animation.getAnimatedValue();

                step_view.setCurrentStep((int)currentStep);


            }
        });
        animator.start();
    }
}
