package com.example.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {
    private float angle = 0;
    private Paint paint = null;
    private static final int SIZE = 360;
    private static final int RAD = 160;
    private static final int TEXT_SIZE = 60;
    private static final int PAINT_WIDTH = 8;

    {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(PAINT_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(TEXT_SIZE);
    }

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(SIZE / 2, SIZE / 2, RAD, paint);

        int deltaX = (int)(RAD * Math.cos(angle));
        int sx = SIZE / 2 + deltaX;
        int fx = SIZE / 2 - deltaX;
        int deltaY = (int)(RAD * Math.sin(angle));
        int sy = SIZE / 2 + deltaY;
        int fy = SIZE / 2 - deltaY;

        canvas.drawLine(sx, sy, fx, fy, paint);

        canvas.drawText(String.valueOf(Math.round(Math.toDegrees(angle))) + "\u00B0", SIZE + TEXT_SIZE, TEXT_SIZE, paint);
    }

    public void setAngle(float newAngle) {
        angle = newAngle;
    }
}