package com.example.qqstepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 那个谁 on 2018/2/1.
 * 奥特曼打小怪兽
 * 作用：仿qq计步器
 */

public class QQStepView extends View {

    private int mOuterColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mBorderWidth = 20;//20px
    private int mStepTextSize = 50;
    private int mStepTextColor = Color.RED;
    private Paint mOutePiant, mInnerPiant, mTextPiant;

    //总共步数和当前步数
    private int mStepMax = 0;
    private int mCurrentStep = 0;


    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定之定义属性，编写attrs.xml
        //3.在布局中使用
        //4.在之定义view中获取获取自定义属性
        TypedArray array = context.obtainStyledAttributes(R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);


        //初始外弧化画笔
        mOutePiant = new Paint();
        mOutePiant.setAntiAlias(true);
        mOutePiant.setStyle(Paint.Style.STROKE);
        mOutePiant.setStrokeWidth(mBorderWidth);
        mOutePiant.setColor(mOuterColor);
        mOutePiant.setStrokeCap(Paint.Cap.ROUND);

        //初始内弧化画笔
        mInnerPiant = new Paint();
        mInnerPiant.setAntiAlias(true);
        mInnerPiant.setStyle(Paint.Style.STROKE);
        mInnerPiant.setStrokeWidth(mBorderWidth);
        mInnerPiant.setColor(mInnerColor);
        mInnerPiant.setStrokeCap(Paint.Cap.ROUND);

        //初始文字画笔
        mTextPiant = new Paint();
        mTextPiant.setAntiAlias(true);
        mTextPiant.setColor(mStepTextColor);
        mTextPiant.setTextSize(mStepTextSize);
        //5.onMeasure（）
        //6，画外圆弧 内圆弧 文字
        //7，其他
        //回收
        array.recycle();

    }

    //5.onMeasure（）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度高度不一致 取最小值 确保是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //调用设置方法
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);

    }
    //6，画外圆弧 内圆弧 文字


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorderWidth / 2;

        //画外弧
        RectF rectf = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectf, 135, 270, false, mOutePiant);

        if (mStepMax == 0) {
            return;
        }
        //画外弧
        float sweepAngle = (float) mCurrentStep / mStepMax;
        canvas.drawArc(rectf, 135, sweepAngle * 270, false, mInnerPiant);

        //画文字
        String stepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        mTextPiant.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;
        Paint.FontMetricsInt anInt = mTextPiant.getFontMetricsInt();
        int dy = (anInt.bottom - anInt.top) / 2 - anInt.bottom;
        //基线
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine,mTextPiant);


    }

    //7，其他 动画

    public synchronized void setStepMax(int stepMax){
        this.mStepMax = stepMax;

    }

    public synchronized void setCurrentStep(int currentStep){
        this.mCurrentStep = currentStep;
        //不断绘制  不断调用onDraw()
        invalidate();
    }
}
