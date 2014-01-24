package lv.lu.locationsharing;

import java.util.ArrayList;

import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.listview.adapter.RequestAdapter;
import lv.lu.locationsharing.model.Friend;
import lv.lu.locationsharing.model.GetFriends;
import lv.lu.locationsharing.model.InviteFriends;
import lv.lu.locationsharing.requests.friends.ConfirmFriendRequest;
import lv.lu.locationsharing.requests.friends.GetFriendsRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class Fragment5 extends SherlockFragment {
	private ListView fList;
	private RequestAdapter adapter;
	protected SpiceManager spiceManager;
	protected LocationApplication mApp;
	private View mLoginStatusView;
	private View mLoginFormView;
	private ArrayList<Friend> list;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment2, container, false);
		mLoginFormView = fList = (ListView) rootView
				.findViewById(R.id.friend_list);
		mLoginStatusView = rootView.findViewById(R.id.login_status);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		mApp = (LocationApplication) getActivity().getApplication();
		adapter = new RequestAdapter(getActivity());
		adapter.setData(new ArrayList<Friend>());
		adapter.notifyDataSetChanged();
		fList.setAdapter(adapter);
		fList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getActivity().getApplicationContext(),
						position + "", Toast.LENGTH_SHORT).show();
				confirmFriend(mApp.getConfig().getUserToken(),list.get(position).getId());
				showProgress(true);
			}
		});

		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

	}

	@Override
	public void onStart() {
		super.onStart();
		spiceManager.start(getActivity());
		getFriends(mApp.getConfig().getUserToken());
		showProgress(true);
		LocationLibrary.forceLocationUpdate(getActivity()
				.getApplicationContext());
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
			showProgress(false);
		}

		@Override
		public void onRequestSuccess(GetFriends authentication) {
			Log.v("Tag", "success");

			adapter.setData(list = authentication.getInviting_friends());
			adapter.notifyDataSetChanged();
			showProgress(false);

			// Intent i = new Intent(getBaseContext(), MainActivity.class);
			//
			// startActivity(i);
			// finish();
			// Toast.makeText(getApplicationContext(), "WORKED!",
			// Toast.LENGTH_LONG).show();
		}
	}

	public void confirmFriend(String userToken, int userId) {
		spiceManager.execute(new ConfirmFriendRequest(getActivity(), userId,
				userToken), MainActivity.GET_FRIENDS_CACHE_KEY,
				DurationInMillis.ONE_MINUTE, new ConfirmationListener());
	}

	// inner class of your spiced Activity
	private class ConfirmationListener implements
			RequestListener<InviteFriends> {

		private ArrayList<Friend> list;

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

			}
			Toast.makeText(getActivity(),
					"Neizdvâs apstiprinât draudzîbu.", Toast.LENGTH_LONG)
					.show();
			showProgress(false);
		}

		@Override
		public void onRequestSuccess(InviteFriends authentication) {
			Log.v("Tag", "success");
			Toast.makeText(getActivity(),
					"Draudzîba veiksmîgi apstiprinâta.", Toast.LENGTH_LONG)
					.show();
			showProgress(false);
			getFriends(mApp.getConfig().getUserToken());

		}
	}

	@Override
	public void onStop() {
		if (spiceManager.isStarted()) {
			spiceManager.shouldStop();
		}
		super.onStop();
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}
