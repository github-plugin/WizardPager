package widzardlib.viewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class WizardViewModel extends ViewModel {
    private List<Fragment> fragments = new ArrayList<>();
    private boolean swipeDisable = true;
    private String previousButtonName = "Prev";
    private String nextButtonName = "Next";

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void disableSwipe(boolean disable) {
        swipeDisable = disable;
    }

    public boolean isSwipeDisable() {
        return swipeDisable;
    }

    public void setNextButtonName(String nextButtonName) {
        this.nextButtonName = nextButtonName;
    }

    public void setPrevButtonName(String previousButtonName) {
        this.previousButtonName = previousButtonName;
    }

    public String getPreviousButtonName() {
        return previousButtonName;
    }

    public String getNextButtonName() {
        return nextButtonName;
    }
}
