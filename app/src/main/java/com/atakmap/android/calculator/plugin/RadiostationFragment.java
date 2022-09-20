package com.atakmap.android.calculator.plugin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.atak.plugins.impl.PluginLayoutInflater;
import com.atakmap.android.maps.MapView;
import com.atakmap.android.plugintemplate.plugin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadiostationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadiostationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  Button markButton;
    private  TextView textView;
    private MapView mapView;
    //private final View calculateView;

    private double longitude;
    private double latitude;


    public RadiostationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RadiostationFragment newInstance(String param1, String param2) {
        RadiostationFragment fragment = new RadiostationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        calculateView = PluginLayoutInflater.inflate(context, R.layout.calculator_layout, null);
//        textView = calculateView.findViewById(R.id.longLatTextView);
//
//        latitude = mapView.getSelfMarker().getPoint().getLatitude();
//        longitude = mapView.getSelfMarker().getPoint().getLongitude();


        return inflater.inflate(R.layout.radiostation_layout, container, false);
    }
}