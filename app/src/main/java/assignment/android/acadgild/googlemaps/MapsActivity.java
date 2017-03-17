package assignment.android.acadgild.googlemaps;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    List<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions().position(new LatLng(33.602, -111.888)).title("Location")) ;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(33.602, -111.888));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                double latitude= latLng.latitude;
                double longitude= latLng.longitude;
                Geocoder geocoder;

                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    String show= address+" \n "+ city+"\n"+state+"\n"+country+"\n"+postalCode+"\n"+knownName;
                    Toast.makeText(MapsActivity.this, show, Toast.LENGTH_SHORT).show();



            }
        });
    }
}
