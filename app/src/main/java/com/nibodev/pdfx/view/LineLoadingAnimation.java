package com.nibodev.pdfx.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.nibodev.pdfx.AppUtils;
import com.nibodev.pdfx.R;

public class LineLoadingAnimation extends View {
    private TypedArray attr;
    private Paint paint;
    private int backgroundColor = 0xffffffff;
    private int color = 0xff000000;
    private float leftGap = 0;
    private float rightGap = 0;
    private float speedX = 400;
    private int thickness = 10;
    private long last = System.currentTimeMillis();
    boolean animateLeftGap = true;

    public LineLoadingAnimation(Context context) {
        super(context);
    }

    public LineLoadingAnimation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LineLoadingAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LineLoadingAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attributeSet) {
        paint = new Paint();
        try {
            if (attributeSet != null) {
                attr = getContext().obtainStyledAttributes(attributeSet, R.styleable.LineLoadingAnimation);
                speedX = attr.getInt(R.styleable.LineLoadingAnimation_animation_speed, (int) speedX);
                thickness = attr.getInt(R.styleable.LineLoadingAnimation_line_thickness, thickness);
                color = attr.getColor(R.styleable.LineLoadingAnimation_color, color);
                backgroundColor = attr.getColor(R.styleable.LineLoadingAnimation_bg_color, backgroundColor);
            }
        } finally {
            if (attr != null) {
                attr.recycle();
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, thickness);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - last;
        last = currentTime;
        update(delta / 1000f);
        render(canvas);
        invalidate();
    }

    private void render(Canvas canvas) {
        clear(canvas);
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, getWidth(), thickness, paint);

        paint.setColor(color);
        canvas.drawRect(
                0 + leftGap, 0,
                getWidth() - rightGap, thickness,
                paint
        );
    }

    private void update(float dt) {
        float dis = speedX * dt;
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
        } else {
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

    private void clear(Canvas canvas) {
        paint.setColor(0x000);
        canvas.drawRect(
                0, 0,
                getWidth(), getHeight(),
                paint
        );
    }
}
