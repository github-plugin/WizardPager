package widzardlib.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class WizardViewPager extends ViewPager {
    private boolean swipeDisable;

    public WizardViewPager(@NonNull Context context) {
        super(context);
    }

    public WizardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


   /* @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (swipeDisable)
            return false;
        return super.onTouchEvent(ev);
    }*/

    public void disableSwipe(boolean swipeDisable) {
        this.swipeDisable = swipeDisable;
    }
}
