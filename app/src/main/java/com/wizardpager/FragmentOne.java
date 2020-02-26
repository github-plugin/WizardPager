package com.wizardpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import widzardlib.interfaces.ActionListener;
import widzardlib.interfaces.ValidateListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment implements ValidateListener {

    public FragmentOne() {
        // Required empty public constructor
    }
    public static Fragment newInstance() {
        return new FragmentOne();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
