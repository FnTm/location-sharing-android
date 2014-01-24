package lv.lu.locationsharing;

import java.util.ArrayList;

import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.model.Friend;
import lv.lu.locationsharing.model.InviteFriends;
import lv.lu.locationsharing.requests.friends.InviteFriendsRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class Fragment3 extends SherlockFragment {
	protected SpiceManager spiceManager;
	protected LocationApplication mApp;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment3, container, false);
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mApp = (LocationApplication) getActivity().getApplication();
		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
		
		getView().findViewById(R.id.invite_button).setOnClickListener(
                 new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {  
                                 inviteFriend(((EditText)getView().findViewById(R.id.email)).getText().toString(),mApp.getConfig().getUserToken());
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
	
	// inner class of your spiced Activity
	private class AuthenticationListener implements RequestListener<InviteFriends> {

		private ArrayList<Friend> list;

		@Override
		public void onRequestFailure(SpiceException spiceException) {

			if (spiceException.getCause() instanceof HttpClientErrorException) {
				HttpClientErrorException cause = (HttpClientErrorException) spiceException
						.getCause();

				switch (Integer.valueOf(cause.getStatusCode().toString())) {
				case 401:
					Toast.makeText(getActivity(), "Neizdevâs uzaicinât draugu", Toast.LENGTH_LONG).show();
					break;

				default:
					break;
				}
			} else if (spiceException.getCause() instanceof ResourceAccessException) {
                inviteFriend(((EditText)getView().findViewById(R.id.email)).getText().toString(),mApp.getConfig().getUserToken());
			}
			//showProgress(false);
		}

		@Override
		public void onRequestSuccess(InviteFriends authentication) {
			Log.v("Tag", "success");

			Toast.makeText(getActivity(), "Uzaicinâjums veiksmîgs", Toast.LENGTH_LONG).show();


		}
	}

	

}
