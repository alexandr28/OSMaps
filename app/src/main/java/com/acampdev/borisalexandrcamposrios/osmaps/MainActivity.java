package com.acampdev.borisalexandrcamposrios.osmaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView myOpenMapView;
    private MapController myMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeoPoint lugar= new GeoPoint(1.2233320917344201,-77.28313787934216);
        GeoPoint narino= new GeoPoint(1.223296144679992,-77.282288108354);
        GeoPoint carnavales= new GeoPoint(1.2246323194975304,-77.28315568298414);
        myOpenMapView=(MapView) findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController=(MapController) myOpenMapView.getController();
        myMapController.setCenter(lugar);
        myMapController.setZoom(6);

        myOpenMapView.setMultiTouchControls(true);


        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationoverlay); //No añadir si no quieres una marca
        myLocationoverlay.enableMyLocation();
        myLocationoverlay.runOnFirstFix(new Runnable() {
            public void run() {
                myMapController.animateTo(myLocationoverlay.getMyLocation());
            }
        });


        ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
        puntos.add(new OverlayItem("Auditorio", "Universidad Mariana", lugar));
        puntos.add(new OverlayItem("Parque Nariño", "Universidad Mariana", narino));
        puntos.add(new OverlayItem("Parque Carnavales", "Universidad Mariana", carnavales));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }
        };

        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(this, puntos, tap);
        capa.setFocusItemsOnTap(true);
        myOpenMapView.getOverlays().add(capa);
    }
}
