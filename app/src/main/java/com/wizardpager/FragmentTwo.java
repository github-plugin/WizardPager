package com.wizardpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import widzardlib.interfaces.ValidateListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment implements ValidateListener {

    public FragmentTwo() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new FragmentTwo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
