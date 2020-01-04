package com.riyagayasen.easyaccordion;

import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

/**
 * Created by riyagayasen on 09/10/16.
 */

class WidgetHelper {
    /***
     * A function to check if a string is null or blank
     *
     * @param string The string.
     * @return A boolean value.
     */
    static boolean isNullOrBlank(String string) {
        if (string == null)
            return true;
        if (string.equals(""))
            return true;
        return string.length() == 0;
    }

    /***
     * Function to check if an object is null or blank
     * @param object The object.
     * @return A boolean value.
     */
    static boolean isNullOrBlank(Object object) {
        if (object instanceof String)
            return isNullOrBlank((String) object);

        if (object == null)
            return true;
        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();

        }

        return false;
    }

    /***
     * Function to check if an integer is zero or non zero
     * @param object The object to check
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isNullOrBlank(int object) {
        return object == 0;
    }

    /***
     * This function returns the actual height the layout. The getHeight() function returns the current height which might be zero if
     * the layout's visibility is GONE
     * @param layout the view group.
     * @return the full height.
     */
    static int getFullHeight(ViewGroup layout) {
        int specWidth = View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED);
        int specHeight = View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED);


        layout.measure(specWidth, specHeight);
        int totalHeight = 0;//layout.getMeasuredHeight();
        int initialVisibility = layout.getVisibility();
        layout.setVisibility(View.VISIBLE);
        int numberOfChildren = layout.getChildCount();
        for (int i = 0; i < numberOfChildren; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                totalHeight += getFullHeight((ViewGroup) child);
            } else {
                int desiredWidth = View.MeasureSpec.makeMeasureSpec(layout.getWidth(),
                        View.MeasureSpec.AT_MOST);
                child.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += child.getMeasuredHeight();
            }

        }
        layout.setVisibility(initialVisibility);
        return totalHeight;
    }
}
