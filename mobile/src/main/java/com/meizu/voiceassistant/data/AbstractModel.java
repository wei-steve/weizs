package com.meizu.voiceassistant.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by weichangtan on 16/9/9.
 */

public abstract class AbstractModel implements Parcelable, Cloneable {


    /** User set values */
    protected ContentValues setValues = null;

    /** Values from database */
    protected ContentValues values = null;

    public AbstractModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(setValues, 0);
        dest.writeParcelable(values, 0);
    }
}