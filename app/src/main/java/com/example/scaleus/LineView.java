package com.example.scaleus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {

    private Paint paint = new Paint();

    private PointF pointA, pointB;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        paint.setStrokeWidth(7);

        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y,paint);


    }

    public void set_color(int num)
    {
        if(num==1)
        {
            paint.setColor(Color.RED);
        }
        else
        {
            paint.setColor(Color.BLUE);
        }
    }
    public void setPointA(PointF point)
    {
        pointA = point ;
    }

    public void setPointB(PointF point)
    {
        pointB = point ;
    }

    public void draw()
    {
        invalidate();
        requestLayout();
    }
}
