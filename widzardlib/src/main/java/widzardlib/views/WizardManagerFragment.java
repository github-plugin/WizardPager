package widzardlib.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import practice.com.widzardlib.R;
import widzardlib.interfaces.ActionListener;
import widzardlib.interfaces.ValidateListener;
import widzardlib.viewModel.FragmentAdapter;
import widzardlib.viewModel.WizardViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class WizardManagerFragment extends Fragment implements View.OnClickListener, ActionListener {

    //private FragmentWizardManagerBinding binding;
    private WizardViewPager vpFragments;
    private Button btnNext;
    private Button btnPrevious;

    public WizardManagerFragment() {
        // Required empty public constructor
    }


    public static WizardManagerFragment newInstance() {
        return new WizardManagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wizard_manager, container);
        vpFragments = view.findViewById(R.id.vpFragments);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        WizardViewModel viewModel = new ViewModelProvider(requireActivity()).get(WizardViewModel.class);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(requireActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.POSITION_NONE);
        fragmentAdapter.setFragments(viewModel.getFragments());
        vpFragments.disableSwipe(viewModel.isSwipeDisable());
        vpFragments.setAdapter(fragmentAdapter);
        vpFragments.post(new Runnable() {
            @Override
            public void run() {
                if (vpFragments.getAdapter() != null) {

                    FragmentAdapter fragmentAdapter = (FragmentAdapter) vpFragments.getAdapter();
                    Fragment fragmentFocus = fragmentAdapter.getItem(vpFragments.getCurrentItem());
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .detach(fragmentFocus)
                            .attach(fragmentFocus)
                            .commit();
                    if (vpFragments.getCurrentItem() + 1 < fragmentAdapter.getCount()) {
                        Fragment fragmentNext = fragmentAdapter.getItem(vpFragments.getCurrentItem() + 1);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .detach(fragmentNext)
                                .attach(fragmentNext)
                                .commit();
                    }
                }
            }
        });
        btnNext.setText(viewModel.getNextButtonName());
        btnPrevious.setText(viewModel.getPreviousButtonName());
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        return view;
    }

    @Override
    public void onPreviousClicked() {
        if (vpFragments.getCurrentItem() == 0)
            return;
        vpFragments.setCurrentItem(vpFragments.getCurrentItem() - 1);
    }

    @Override
    public void onNextClicked() {
        if (vpFragments.getCurrentItem() == Objects.requireNonNull(vpFragments.getAdapter()).getCount() - 1)
            return;
        vpFragments.setCurrentItem(vpFragments.getCurrentItem() + 1);
    }

    @Override
    public void onSkipClicked() {

    }

    @Override
    public void onClick(View view) {
        FragmentAdapter adapter = (FragmentAdapter) vpFragments.getAdapter();
        ValidateListener validateListener = (ValidateListener) Objects.requireNonNull(adapter).getItem(vpFragments.getCurrentItem());
        if (validateListener.isValid()) {
            if (btnNext.getId() == view.getId())
                onNextClicked();
            else if (btnPrevious.getId() == view.getId())
                onPreviousClicked();
        }
    }
}
