package lv.lu.locationsharing;

import java.lang.reflect.Field;
import java.util.Iterator;

import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.model.Friend;
import lv.lu.locationsharing.model.GetFriends;
import lv.lu.locationsharing.requests.friends.GetFriendsRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
	private SupportMapFragment fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container, false);
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    FragmentManager fm = getChildFragmentManager();
	    fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
	    if (fragment == null) {
	        fragment = SupportMapFragment.newInstance();
	        fm.beginTransaction().replace(R.id.map, fragment).commit();
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	        map = fragment.getMap();

			LatLng sydney = new LatLng(56.949, 24.105);

			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
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
	
	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}

}
