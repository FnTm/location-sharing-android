package lv.lu.locationsharing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment1 extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container, false);
		GoogleMap map = ((SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng sydney = new LatLng(56.949, 24.105);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));

		map.addMarker(new MarkerOptions().title("Kārlis").position(sydney));
		map.addMarker(new MarkerOptions().title("Kārlis").position(sydney));
		LatLng clav = new LatLng(56.959, 24.108);
		map.addMarker(new MarkerOptions().title("Klāvs").position(clav));
		LatLng poogol = new LatLng(56.937, 24.071);
		map.addMarker(new MarkerOptions().title("Jānis P").position(poogol));
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Get a handle to the Map Fragment

	}

}
