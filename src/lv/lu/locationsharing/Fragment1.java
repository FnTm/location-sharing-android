package lv.lu.locationsharing;

import java.util.Iterator;

import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.model.Friend;
import lv.lu.locationsharing.model.GetFriends;
import lv.lu.locationsharing.requests.friends.GetFriendsRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class Fragment1 extends SherlockFragment {
	protected SpiceManager spiceManager;
	protected LocationApplication mApp;
	private GoogleMap map;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container, false);
		map = ((SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng sydney = new LatLng(56.949, 24.105);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));

		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApp = (LocationApplication) getActivity().getApplication();

		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
		
	}

	@Override
	public void onStart() {
		super.onStart();
		spiceManager.start(getActivity());
		getFriends(mApp.getConfig().getUserToken());
		LocationLibrary.forceLocationUpdate(getActivity().getApplicationContext());
	}

	public void getFriends(String userToken) {
		spiceManager.execute(new GetFriendsRequest(getActivity(), userToken),
				MainActivity.GET_FRIENDS_CACHE_KEY,
				DurationInMillis.ONE_MINUTE, new AuthenticationListener());
	}

	// inner class of your spiced Activity
	private class AuthenticationListener implements RequestListener<GetFriends> {

		@Override
		public void onRequestFailure(SpiceException spiceException) {

			if (spiceException.getCause() instanceof HttpClientErrorException) {
				HttpClientErrorException cause = (HttpClientErrorException) spiceException
						.getCause();

				switch (Integer.valueOf(cause.getStatusCode().toString())) {
				case 401:

					break;

				default:
					break;
				}
			} else if (spiceException.getCause() instanceof ResourceAccessException) {
				getFriends(mApp.getConfig().getUserToken());
			}
		}

		@Override
		public void onRequestSuccess(GetFriends authentication) {
			Log.v("Tag", "success");
			
			Iterator<Friend> iter = authentication.getFriends().iterator();
			map.clear();
			while(iter.hasNext()){
				Friend fr=iter.next();
				LatLng coords = new LatLng(fr.getLatitude(), fr.getLongitude());
				map.addMarker(new MarkerOptions().title(fr.getName()).position(coords));
			}

			
			
			
			
			
			// Intent i = new Intent(getBaseContext(), MainActivity.class);
			//
			// startActivity(i);
			// finish();
			// Toast.makeText(getApplicationContext(), "WORKED!",
			// Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onStop() {
		if (spiceManager.isStarted()) {
			spiceManager.shouldStop();
		}
		super.onStop();
	}

}
