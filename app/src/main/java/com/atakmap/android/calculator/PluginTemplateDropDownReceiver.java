
package com.atakmap.android.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atak.plugins.impl.PluginLayoutInflater;
import com.atakmap.android.cot.CotMapComponent;
import com.atakmap.android.maps.MapView;

import com.atakmap.android.dropdown.DropDown.OnStateListener;
import com.atakmap.android.dropdown.DropDownReceiver;

import com.atakmap.android.plugintemplate.plugin.R;
import com.atakmap.coremap.cot.event.CotEvent;
import com.atakmap.coremap.cot.event.CotPoint;
import com.atakmap.coremap.log.Log;
import com.atakmap.coremap.maps.time.CoordinatedTime;



public class PluginTemplateDropDownReceiver extends DropDownReceiver implements
        OnStateListener {

    public static final String TAG = PluginTemplateDropDownReceiver.class
            .getSimpleName();

    public static final String SHOW_PLUGIN = "com.atakmap.android.Calculator.SHOW_PLUGIN";

    private final TextView textView;
    private final TextView calculatedPos;
    private final View calculateView;
    private final EditText azimuthEditText;
    private final EditText hullEditText;
    private final EditText distanceEditText;
    private double azimuth;
    private double distance;
    private double hull_angle;
    private double latitude;
    private double longitude;
    private double sum_azimuth;
    private double thousands_azimuth;
    private static int pointCounter;
    private String uid;
    double Xb;
    double Yb;


    public CotEvent createPoint(double xb, double yb){

//        if(xb.equals("") || yb.equals("")){
//            xb = "0";
//            yb = "0";
//        }


        CotPoint cotPoint = new CotPoint((xb), (yb), 0.0, 1.0, 1.0);
        CotEvent cotEvent = new CotEvent();
        CoordinatedTime time = new CoordinatedTime();

        cotEvent.setUID("enemy_location");
        cotEvent.setTime(time);
        cotEvent.setStart(time);
        cotEvent.setHow("G-U-C-C-I");
        cotEvent.setType("enemy");
        //cotEvent.setDetail();
        cotEvent.setStale(time.addMinutes(10));
        cotEvent.setPoint(cotPoint);

//        CotMapComponent.getInternalDispatcher().dispatch(cotEvent);

    return cotEvent;
    }



    /**************************** CONSTRUCTOR *****************************/

    public PluginTemplateDropDownReceiver(final MapView mapView,
            final Context context) {
        super(mapView);

        // Remember to use the PluginLayoutInflator if you are actually inflating a custom view
        // In this case, using it is not necessary - but I am putting it here to remind
        // developers to look at this Inflator



        /*********************** CALCULATOR ***************************/

        //elementary views
        calculateView = PluginLayoutInflater.inflate(context, R.layout.calculator_layout, null);
        textView = calculateView.findViewById(R.id.longLatTextView);
        calculatedPos = calculateView.findViewById(R.id.calculated_LongAndLat);

        //images
        ImageView turret_imageView = calculateView.findViewById(R.id.turret_image);
        ImageView hull_imageView = calculateView.findViewById(R.id.hull_image);

        //edit_texts
        azimuthEditText = calculateView.findViewById(R.id.target_azimuth);
        hullEditText = calculateView.findViewById(R.id.hull_azimuth);
        distanceEditText = calculateView.findViewById(R.id.target_distance);

        //buttons
        Button calculate_xys = calculateView.findViewById(R.id.calculate_xys);
        Button refresh_cords = calculateView.findViewById(R.id.refresh_cords);
        Button add_new_point = calculateView.findViewById(R.id.add_new_point);

        //wstępne ustalanie położenia
        latitude = mapView.getSelfMarker().getPoint().getLatitude();
        longitude = mapView.getSelfMarker().getPoint().getLongitude();

        //wypisanie położenia
        String longAndLat;
        longAndLat = Double.toString(latitude) + ' ' + longitude;
        textView.setText(longAndLat);

        //calculate_xys.set

        /*******CALCULATE********/
        calculate_xys.setOnClickListener(view -> {

            String azim = azimuthEditText.getText().toString();

            String hull = hullEditText.getText().toString();

            String dist = distanceEditText.getText().toString();


          if (!dist.equals("") && !azim.equals("") && !hull.equals("")) {
              if (Double.parseDouble(dist) != 0) {

                  azimuth = Double.parseDouble(azim);
                  hull_angle = Double.parseDouble(hull);
                  distance = Double.parseDouble(dist) / 100000;

                  sum_azimuth = hull_angle + azimuth;

                  thousands_azimuth = (sum_azimuth) * ((float) 3 / 50);
                  Xb = latitude + (distance * Math.sin(thousands_azimuth));
                  Yb = longitude + (distance * Math.cos(thousands_azimuth));

                  String calculatedPosition;
                  calculatedPosition = Double.toString(Xb) + ' ' + (Yb);
                  calculatedPos.setText(calculatedPosition);

                  if(!add_new_point.isEnabled()){
                      add_new_point.setEnabled(true);
                  }
//                  CotPoint cotPoint = new CotPoint(Xb, Yb, 0.0, 1.0, 1.0);
//                  CotEvent cotEvent = new CotEvent();
//                  CoordinatedTime time = new CoordinatedTime();
//
//                  cotEvent.setUID("enemy_location");
//                  cotEvent.setTime(time);
//                  cotEvent.setStart(time);
//                  cotEvent.setHow("G-U-C-C-I");
//                  cotEvent.setType("enemy");
//                  //cotEvent.setDetail();
//                  cotEvent.setStale(time.addMinutes(10));
//                  cotEvent.setPoint(cotPoint);
//
//                  CotMapComponent.getInternalDispatcher().dispatch(cotEvent);

//                CotEvent cotEvent = createPoint(Xb, Yb);
//                cotEvent.setUID("enemy");
//
//                CotMapComponent.getInternalDispatcher().dispatch(cotEvent);

              } else {
                  Toast toast = Toast.makeText(context, "Distance must be greater than 0", Toast.LENGTH_SHORT);
                  toast.show();
              }

          }
          else{
              Toast toast = Toast.makeText(context, "Enter valid data", Toast.LENGTH_SHORT);
              toast.show();
          }
        });


        add_new_point.setOnClickListener(view -> {

            CotEvent cotEvent = createPoint(Xb, Yb);
            pointCounter++;
            uid = "enemy_location" + pointCounter;
            cotEvent.setUID(uid);
            CotMapComponent.getInternalDispatcher().dispatch(cotEvent);
            String calculatedPosition;
            calculatedPosition = Double.toString(Xb) + ' ' + (Yb);
            calculatedPos.setText(calculatedPosition);

        });


        /******REFRESH_SELF*******/
        refresh_cords.setOnClickListener(view -> {
            latitude = mapView.getSelfMarker().getPoint().getLatitude();
            longitude = mapView.getSelfMarker().getPoint().getLongitude();
            String up_longAndLat;
            up_longAndLat = Double.toString(latitude) + ' ' + longitude;
            textView.setText(up_longAndLat);

            Toast toastRefresh = Toast.makeText(context, "Position refreshed", Toast.LENGTH_SHORT);
            toastRefresh.show();
        });

    //noinspection AndroidLintClickableViewAccessibility
        turret_imageView.setOnTouchListener((view, motionEvent) -> {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    params.width = 1000;
                    params.height = 1000;
                    view.setLayoutParams(params);
                break;
                case MotionEvent.ACTION_UP:
                    params.width = 55;
                    params.height = 55;
                    view.setLayoutParams(params);
                break;
        }
        return true;
    });
        //noinspection AndroidLintClickableViewAccessibility
        hull_imageView.setOnTouchListener((view, motionEvent) -> {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    params.width = 1000;
                    params.height = 1000;
                    view.setLayoutParams(params);
                    break;
                case MotionEvent.ACTION_UP:
                    params.width = 55;
                    params.height = 55;
                    view.setLayoutParams(params);
                    break;
            }
            return true;
        });

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
