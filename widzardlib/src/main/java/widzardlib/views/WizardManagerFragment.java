package widzardlib.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import practice.com.widzardlib.databinding.FragmentWizardManagerBinding;
import widzardlib.interfaces.ActionListener;
import widzardlib.interfaces.ValidateListener;
import widzardlib.viewModel.FragmentAdapter;
import widzardlib.viewModel.WizardViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class WizardManagerFragment extends Fragment implements View.OnClickListener, ActionListener {

    private FragmentWizardManagerBinding binding;

    public WizardManagerFragment() {
        // Required empty public constructor
    }


    public static WizardManagerFragment newInstance() {
        return new WizardManagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WizardViewModel viewModel = new ViewModelProvider(requireActivity()).get(WizardViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWizardManagerBinding.inflate(inflater, container, false);
        WizardViewModel viewModel = new ViewModelProvider(requireActivity()).get(WizardViewModel.class);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(requireActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.POSITION_NONE);
        fragmentAdapter.setFragments(viewModel.getFragments());
        binding.vpFragments.disableSwipe(viewModel.isSwipeDisable());
        binding.vpFragments.setAdapter(fragmentAdapter);
        binding.vpFragments.post(new Runnable() {
            @Override
            public void run() {
                if (binding.vpFragments.getAdapter() != null) {

                    FragmentAdapter fragmentAdapter = (FragmentAdapter) binding.vpFragments.getAdapter();
                    Fragment fragmentFocus = fragmentAdapter.getItem(binding.vpFragments.getCurrentItem());
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .detach(fragmentFocus)
                            .attach(fragmentFocus)
                            .commit();
                    if (binding.vpFragments.getCurrentItem() + 1 < fragmentAdapter.getCount()) {
                        Fragment fragmentNext = fragmentAdapter.getItem(binding.vpFragments.getCurrentItem() + 1);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .detach(fragmentNext)
                                .attach(fragmentNext)
                                .commit();
                    }
                }
            }
        });
        binding.btnNext.setText(viewModel.getNextButtonName());
        binding.btnPrevious.setText(viewModel.getPreviousButtonName());
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onPreviousClicked() {
        if (binding.vpFragments.getCurrentItem() == 0)
            return;
        binding.vpFragments.setCurrentItem(binding.vpFragments.getCurrentItem() - 1);
    }

    @Override
    public void onNextClicked() {
        if (binding.vpFragments.getCurrentItem() == Objects.requireNonNull(binding.vpFragments.getAdapter()).getCount() - 1)
            return;
        binding.vpFragments.setCurrentItem(binding.vpFragments.getCurrentItem() + 1);
    }

    @Override
    public void onSkipClicked() {

    }

    @Override
    public void onClick(View view) {
        FragmentAdapter adapter = (FragmentAdapter) binding.vpFragments.getAdapter();
        ValidateListener validateListener = (ValidateListener) Objects.requireNonNull(adapter).getItem(binding.vpFragments.getCurrentItem());
        if (validateListener.isValid()) {
            if (binding.btnNext.getId() == view.getId())
                onNextClicked();
            else if (binding.btnPrevious.getId() == view.getId())
                onPreviousClicked();
        }
    }
}
