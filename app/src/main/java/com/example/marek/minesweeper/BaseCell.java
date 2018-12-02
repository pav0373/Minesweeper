package com.example.marek.minesweeper;

import android.content.Context;
import android.view.View;

public abstract class BaseCell extends View {

    private int value;

    private boolean isBomb;
    private boolean isRevealed;
    private boolean isClicked;
    private boolean isFlagged;

    private int x, y;


    public BaseCell(Context context){
        super(context);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {

        isBomb=false;
        isRevealed=false;
        isClicked=false;
        isFlagged=false;

        if (value == -1)
        {
            isBomb = true;
        }

        this.value = value;
    }

    public boolean isBomb() {
        return isBomb;
    }


    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
        isClicked = revealed;
        invalidate();
    }


    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked() {

        if(!isFlagged()) {
            isClicked = true;
            this.isRevealed = true;
            invalidate();
        }


    }

    public void bombReveal() {

        if (!isFlagged()) {
            isClicked = true;
            this.isRevealed = true;
            invalidate();
        }
        if (isFlagged() && !isBomb())
        {
            setFlagged(false);
            isClicked = true;
            this.isRevealed = true;
            invalidate();
        }
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;

    }


    public int getXPos() {
        return x;
    }


    public int getYPos() {
        return y;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;

        invalidate();
    }



}
