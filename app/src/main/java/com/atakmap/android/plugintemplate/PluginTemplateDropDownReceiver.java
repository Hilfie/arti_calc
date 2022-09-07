
package com.atakmap.android.plugintemplate;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atak.plugins.impl.PluginLayoutInflater;
import com.atakmap.android.cot.CotMapComponent;
import com.atakmap.android.maps.MapView;
import com.atakmap.android.plugintemplate.plugin.R;
import com.atakmap.android.dropdown.DropDown.OnStateListener;
import com.atakmap.android.dropdown.DropDownReceiver;

import com.atakmap.coremap.cot.event.CotEvent;
import com.atakmap.coremap.cot.event.CotPoint;
import com.atakmap.coremap.log.Log;
import com.atakmap.coremap.maps.time.CoordinatedTime;

import java.io.IOException;

public class PluginTemplateDropDownReceiver extends DropDownReceiver implements
        OnStateListener {

    public static final String TAG = PluginTemplateDropDownReceiver.class
            .getSimpleName();

    public static final String SHOW_PLUGIN = "com.atakmap.android.plugintemplate.SHOW_PLUGIN";
    private final Context pluginContext;
//    private final View myFragmentView;
//    private final Button markButton;
    private final TextView textView;
//    private final EditText latitudeEditText;
//    private final EditText longitudeEditText;

    private final View calculateView;
    private final EditText azimuthEditText;
    private final EditText distanceEditText;
    private final Button calculate_xys;
    //private final Button refresh_cords;
    double Xb;
    double Yb;

    /**************************** CONSTRUCTOR *****************************/

    public PluginTemplateDropDownReceiver(final MapView mapView,
            final Context context) {
        super(mapView);
        this.pluginContext = context;

        // Remember to use the PluginLayoutInflator if you are actually inflating a custom view
        // In this case, using it is not necessary - but I am putting it here to remind
        // developers to look at this Inflator

//        myFragmentView = PluginLayoutInflater.inflate(pluginContext, R.layout.radiostation_layout, null);
//
//        textView = myFragmentView.findViewById(R.id.longLatTextView);
//        markButton = myFragmentView.findViewById(R.id.markButton);
//
//        latitudeEditText = myFragmentView.findViewById(R.id.latitude_editText);
//        longitudeEditText = myFragmentView.findViewById(R.id.longitude_editText);
//
//
//        /**************************** USTALANIE POŁOŻENIA *****************************/
//
//        Double latitude = mapView.getSelfMarker().getPoint().getLatitude();
//        Double longitude = mapView.getSelfMarker().getPoint().getLongitude();
//        String longAndLat;
//
//        longAndLat = latitude.toString() + ' ' + longitude.toString();
//        textView.setText(longAndLat);
//
//
//        markButton.setOnClickListener(view -> {
//            String lat = latitudeEditText.getText().toString();
//            String lon = longitudeEditText.getText().toString();
//
//            CotPoint cotPoint = new CotPoint(Double.parseDouble(lat), Double.parseDouble(lon), 0.0, 2.0, 2.0);
//            CotEvent cotEvent = new CotEvent();
//            CoordinatedTime time = new CoordinatedTime();
//
//            cotEvent.setUID("TEST ladziak");
//            cotEvent.setTime(time);
//            cotEvent.setStart(time);
//            cotEvent.setHow("h-e");
//            cotEvent.setType("enemy");
//            //cotEvent.setDetail();
//            cotEvent.setStale(time.addMinutes(10));
//            cotEvent.setPoint(cotPoint);
//
//            CotMapComponent.getInternalDispatcher().dispatch(cotEvent);
//
//        });


        /*********************** CALCULATOR ***************************/

        calculateView = PluginLayoutInflater.inflate(pluginContext, R.layout.calculator_layout, null);
        textView = calculateView.findViewById(R.id.longLatTextView);

        azimuthEditText = calculateView.findViewById(R.id.target_azimuth);
        distanceEditText = calculateView.findViewById(R.id.target_distance);

        calculate_xys = calculateView.findViewById(R.id.calculate_xys);
        //refresh_cords = calculateView.findViewById(R.id.refresh_cords);


        double latitude = mapView.getSelfMarker().getPoint().getLatitude();

        double longitude = mapView.getSelfMarker().getPoint().getLongitude();
        String longAndLat;

        longAndLat = Double.toString(latitude) + ' ' + longitude;
        textView.setText(longAndLat);


        calculate_xys.setOnClickListener(view -> {

            String azim = azimuthEditText.getText().toString();
            double azimuth = Double.parseDouble(azim);

            String dist = distanceEditText.getText().toString();
            double distance = Double.parseDouble(dist)/100000;

            azimuth = azimuth * ((float)3/50);
            Xb = latitude + (distance * Math.sin(azimuth));
            Yb = longitude + (distance * Math.cos(azimuth));

            CotPoint cotPoint = new CotPoint(Xb, Yb, 0.0, 1.0, 1.0);
            CotEvent cotEvent = new CotEvent();
            CoordinatedTime time = new CoordinatedTime();

            cotEvent.setUID("artylerzysta");
            cotEvent.setTime(time);
            cotEvent.setStart(time);
            cotEvent.setHow("h-e");
            cotEvent.setType("enemy");
            //cotEvent.setDetail();
            cotEvent.setStale(time.addMinutes(10));
            cotEvent.setPoint(cotPoint);

            CotMapComponent.getInternalDispatcher().dispatch(cotEvent);
        });

//        refresh_cords.setOnClickListener(view -> {
//          textView.setText(longAndLat);
//        });

    }

    /**************************** PUBLIC METHODS *****************************/
//
//

    public void disposeImpl() {
    }

    /**************************** INHERITED METHODS *****************************/

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        if (action == null)
            return;

        if (action.equals(SHOW_PLUGIN)) {

            Log.d(TAG, "showing plugin drop down");
            showDropDown(calculateView, HALF_WIDTH, FULL_HEIGHT, FULL_WIDTH,
                    HALF_HEIGHT, false, this);
        }
    }

    @Override
    public void onDropDownSelectionRemoved() {
    }

    @Override
    public void onDropDownVisible(boolean v) {
    }

    @Override
    public void onDropDownSizeChanged(double width, double height) {
    }

    @Override
    public void onDropDownClose() {
    }

}
