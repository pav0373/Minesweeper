package com.example.marek.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class Cell extends BaseCell {

    public Cell(Context context, int position){

        super(context);

        setPosition(position);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        drawButton(canvas);
    }

    private void drawButton(Canvas canvas){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.unclicked);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }
}
