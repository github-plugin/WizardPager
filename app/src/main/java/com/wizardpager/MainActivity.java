package com.wizardpager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import widzardlib.viewModel.WizardViewModel;
import widzardlib.views.WizardManagerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WizardViewModel viewModel = new ViewModelProvider(this).get(WizardViewModel.class);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentOne.newInstance());
        fragments.add(FragmentTwo.newInstance());
        viewModel.setFragments(fragments);
        WizardManagerFragment wizardManagerFragment = WizardManagerFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, wizardManagerFragment)
                .commit();
    }
}
