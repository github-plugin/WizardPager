package com.wizardpager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import widzardlib.viewModel.WizardViewModel;
import widzardlib.views.WizardManagerFragment;

import static widzardlib.views.WizardManagerFragment.FOOTER_HIDE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WizardViewModel viewModel = new ViewModelProvider(this).get(WizardViewModel.class);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentOne.newInstance());

        Fragment fragmentTwo = FragmentTwo.newInstance();
        Bundle bundle = new Bundle();
        bundle.putBoolean(FOOTER_HIDE, true);
        fragmentTwo.setArguments(bundle);
        fragments.add(fragmentTwo);

        viewModel.setFragments(fragments);
        WizardManagerFragment wizardManagerFragment = WizardManagerFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, wizardManagerFragment)
                .commit();
    }
}
