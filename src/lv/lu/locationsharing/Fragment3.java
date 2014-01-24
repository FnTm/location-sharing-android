package lv.lu.locationsharing;

import java.util.Iterator;

import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.model.Friend;
import lv.lu.locationsharing.model.GetFriends;
import lv.lu.locationsharing.model.InviteFriends;
import lv.lu.locationsharing.requests.friends.GetFriendsRequest;
import lv.lu.locationsharing.requests.friends.InviteFriendsRequest;

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

public class Fragment3 extends SherlockFragment {
	protected SpiceManager spiceManager;
	protected LocationApplication mApp = (LocationApplication) getActivity().getApplication();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment3, container, false);
		
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
		
		getView().findViewById(R.id.invite_button).setOnClickListener(
                 new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {  
                                // invateFriend();
                         }
                 });
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		spiceManager.start(getActivity());
	}

	
	public void inviteFriend(String mail, String userToken) {
		spiceManager.execute(new InviteFriendsRequest(getActivity(), mail, userToken),
				MainActivity.GET_FRIENDS_CACHE_KEY,
				DurationInMillis.ONE_MINUTE, new AuthenticationListener());
	}
	
	@Override
	public void onStop() {
		if (spiceManager.isStarted()) {
			spiceManager.shouldStop();
		}
		super.onStop();
	}
	

}
