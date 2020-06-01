# Monochrome Map style Activity

![Monochrome map](screenshot_1.png)

**activity_map_styles** [View on Github](https://github.com/baato/baato-android-demo/blob/master/app/src/main/res/layout/activity_map_styles.xml)
```
 <?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:mapbox="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.mapbox.mapboxsdk.maps.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        mapbox:mapbox_cameraTargetLat="27.7084"
        mapbox:mapbox_cameraTargetLng="85.3206"
        mapbox:mapbox_cameraZoom="13"
        />

    <TextView
        android:id="@+id/bottomInfoLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        android:background="@color/colorAccent"
        android:gravity="center"
        android:padding="@dimen/general_margin"
        android:text="@string/nav_msg"
        android:textColor="@color/colorWhite"
        android:visibility="gone"
        app:layout_anchor="@id/mapView"
        app:layout_anchorGravity="bottom" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

**MonochromeMapStyleActivity.java** [View on Github](https://github.com/baato/baato-android-demo/blob/master/app/src/main/java/com/baato/baatoandroiddemo/activities/MonochromeMapStyleActivity.java)
```
package com.baato.baatoandroiddemo.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baatoandroiddemo.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

/**
 * input the baato monochrome map style url and load with mapbox
 */
public class MonochromeMapStyleActivity extends AppCompatActivity {
    private MapView mapView;
    private TextView bottomInfoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_styles);

        mapView = findViewById(R.id.mapView);
        bottomInfoLayout = findViewById(R.id.bottomInfoLayout);

        Mapbox.getInstance(this, getString(R.string.mapbox_token));
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap ->
                //add you map style url here
                mapboxMap.setStyle("http://baato.io/api/v1/styles/monochrome?key=" + getString(R.string.baato_access_token),
                        style -> {
                            bottomInfoLayout.setText(Html.fromHtml("<b>Monochrome Map</b>" + " from Baato.io"));
                            bottomInfoLayout.setVisibility(View.VISIBLE);
                        }));

    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}

```

