package com.doctor;

/**
 * Created by HJL on 2017/1/8.
 */

public interface CircleProgressAttrs {

    /**
     * the Ring width
     * @param width
     */
    void setCircleWidth(float width);
    float getCircleWidth();

    /**
     * set color array
     * @param colors
     */
    void setSchemeColors(int... colors);
    int[] getSchemeColors();
}
