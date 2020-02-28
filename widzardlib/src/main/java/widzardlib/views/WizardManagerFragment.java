package widzardlib.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

import practice.com.widzardlib.databinding.FragmentWizardManagerBinding;
import widzardlib.interfaces.ActionListener;
import widzardlib.interfaces.AsyncCallback;
import widzardlib.interfaces.ValidateListener;
import widzardlib.models.enums.CallBackType;
import widzardlib.viewModel.FragmentAdapter;
import widzardlib.viewModel.WizardViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class WizardManagerFragment extends Fragment implements View.OnClickListener, ActionListener, AsyncCallback {

    private FragmentWizardManagerBinding binding;
    public static String FOOTER_HIDE = "FOOTER_KEY";

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
        binding.vpFragments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d("onPageScrolled", String.format("position:%d positionOffset:%f positionOffsetPixels:%d", position, positionOffset, positionOffsetPixels));
            }

            @Override
            public void onPageSelected(int position) {
                showOrHideFooter();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("pageScrollStateChanged", String.format("state:%d", state));
            }
        });
        binding.vpFragments.post(new Runnable() {
            @Override
            public void run() {
                if (binding.vpFragments.getAdapter() != null) {
                    showOrHideFooter();
                   /* FragmentAdapter fragmentAdapter = (FragmentAdapter) binding.vpFragments.getAdapter();
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
                    }*/
                }
            }
        });
        if (viewModel.getButtonStyle() != 0) {
            // binding.btnPrevious.setApp
        }
        binding.btnNext.setText(viewModel.getNextButtonName());
        binding.btnPrevious.setText(viewModel.getPreviousButtonName());
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        return binding.getRoot();
    }

    private void showOrHideFooter() {
        if (binding.vpFragments.getAdapter() != null) {
            Fragment fragment = ((FragmentAdapter) binding.vpFragments.getAdapter()).getItem(binding.vpFragments.getCurrentItem());
            Bundle bundle = fragment.getArguments();
            if (bundle != null && bundle.getBoolean(FOOTER_HIDE, false))
                binding.clFooter.setVisibility(View.GONE);
            else
                binding.clFooter.setVisibility(View.VISIBLE);
        }
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
        if (binding.btnPrevious.getId() == view.getId()) {
            onPreviousClicked();
            return;
        }

        FragmentAdapter adapter = (FragmentAdapter) binding.vpFragments.getAdapter();
        ValidateListener validateListener = (ValidateListener) Objects.requireNonNull(adapter).getItem(binding.vpFragments.getCurrentItem());
        if (binding.btnNext.getId() == view.getId() && validateListener.isValid())
            onNextClicked();
    }

    @Override
    public void onCallback(CallBackType callBackType) {
        if (callBackType.getValue() == CallBackType.Next.getValue())
            onNextClicked();
        else if (callBackType.getValue() == CallBackType.Previous.getValue())
            onPreviousClicked();
    }
}
