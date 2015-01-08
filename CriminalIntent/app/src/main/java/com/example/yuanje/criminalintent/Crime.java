package com.example.yuanje.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yuanje on 01/08/2015.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    private boolean mSolved;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }
    @Override
    public String toString() {
        return mTitle;
    }
}
