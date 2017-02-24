package io.github.yuezp.camerademo.utils;

import android.support.annotation.NonNull;

/**
 * Created by YueZp on 17/2/24.
 */

public class MyUtils {

    public static <T> T checkNotNUll(T reference, @NonNull Object warningMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(warningMessage));
        }
        return reference;
    }
}
