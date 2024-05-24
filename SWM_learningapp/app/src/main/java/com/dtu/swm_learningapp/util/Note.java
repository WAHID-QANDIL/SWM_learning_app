package com.dtu.swm_learningapp.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Note  implements Parcelable {
    private String name;
    private String Description;
    private String TimeStamp;
    private int imageResourceId;



    public Note(String name, String description, String timeStamp, int imageResourceId) {
        this.name = name;
        Description = description;
        TimeStamp = timeStamp;
        this.imageResourceId = imageResourceId;
    }

    protected Note(Parcel in) {
        name = in.readString();
        Description = in.readString();
        TimeStamp = in.readString();
        imageResourceId = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", TimeStamp='" + TimeStamp + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(Description);
        dest.writeString(TimeStamp);
        dest.writeInt(imageResourceId);
    }
}
