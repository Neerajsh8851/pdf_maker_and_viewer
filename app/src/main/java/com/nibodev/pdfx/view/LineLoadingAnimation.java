package com.nibodev.pdfx.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.nibodev.pdfx.AppUtils;
import com.nibodev.pdfx.R;

public class LineLoadingAnimation extends View {

    private TypedArray attrs;
    private Paint paint;
    private float leftGap = 0;
    private float rightGap = 0;
    private float speedX = 400;
    private long last = System.currentTimeMillis();
    boolean animateLeftGap = true;

    public LineLoadingAnimation(Context context) {
        super(context);
    }

    public LineLoadingAnimation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attrs = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LineLoadingAnimation);
        init();
    }

    public LineLoadingAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        init();
    }

    public LineLoadingAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attrs = attrs;
        init();
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//    }

    private void init() {
        paint = new Paint();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long currentTime = System.currentTimeMillis();
        long delta = currentTime - last;
        last = currentTime;
        update( delta / 1000f);

        paint.setAntiAlias(true);
        paint.setColor(0xFFFF3378);

        canvas.drawRoundRect(0 + leftGap, 0, getWidth() - rightGap, getHeight(), 10, 10, paint);

        invalidate();
        if (AppUtils.isDebugBuild())
        Log.d("dbg", "onDraw: leftGap: " +leftGap + " rightGap: " + rightGap);
    }

    private void update(float dt) {
        float dis =  speedX * dt;
        if (animateLeftGap) {
            leftGap += dis;
            if (leftGap > getWidth()) {
                speedX = -speedX;
                leftGap = getWidth();
            } else if (leftGap < 0) {
                leftGap = 0;
                animateLeftGap = !animateLeftGap;
                speedX = Math.abs(speedX);
            }
        } else  {
            rightGap += dis;
            if (rightGap > getWidth()) {
                speedX = -speedX;
                rightGap = getWidth();
            } else if (rightGap < 0) {
                rightGap = 0;
                animateLeftGap = !animateLeftGap;
                speedX = Math.abs(speedX);
            }
        }
    }
}
