package io.github.yuezp.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pangzhen on 2017/9/21.
 */

public class Student implements Parcelable {
    public String name;
    public int age;
    public boolean isMale;
    public String address;

    public Student() {
    }

    public Student(String name, int age, boolean isMale, String address) {
        this.name = name;
        this.age = age;
        this.isMale = isMale;
        this.address = address;
    }

    public Student(Parcel source) {
        this.name = source.readString();
        this.age = source.readInt();
        this.isMale = source.readByte() == 1;
        this.address = source.readString();

    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMale=" + isMale +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeString(address);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        age = dest.readInt();
        isMale = dest.readByte() == 1;
        address = dest.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {

        @Override
        public Student createFromParcel(Parcel source) {

            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
