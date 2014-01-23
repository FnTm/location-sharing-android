package lv.lu.locationsharing;

import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.config.Config;
import lv.lu.locationsharing.model.LocationPostStatus;
import lv.lu.locationsharing.requests.location.PostLocationUpdate;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class LocationBroadcastReceiver extends BroadcastReceiver {
	protected SpiceManager spiceManager;
	protected Context context;
	protected float latitude;
	protected float longitude;
	protected int retries = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("LocationBroadcastReceiver",
				"onReceive: received location update");
		this.context = context;
		final LocationInfo locationInfo = (LocationInfo) intent
				.getSerializableExtra(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
	
		latitude=locationInfo.lastLat;
		longitude=locationInfo.lastLong;
		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
		spiceManager.start(context);
		sendData();

	}

	protected void sendData() {
		Config cfg = ((LocationApplication) context.getApplicationContext())
				.getConfig();
		if (cfg != null && cfg.getUserId() != 0 && cfg.getUserToken() != null
				&& !cfg.getUserToken().equals("")) {
			doAuthentication(cfg.getUserId(), cfg.getUserToken(), latitude,
					longitude);
		}
		else{
			Log.e("LocationBroadcastReceiver",
					"No config or one of the parameters");
		}
	}

	public void doAuthentication(int userId, String userToken, float latitude,
			float longitude) {
		spiceManager.execute(new PostLocationUpdate(this.context, userId,
				userToken, latitude, longitude), "",
				DurationInMillis.ALWAYS_EXPIRED, new AuthenticationListener());
	}

	// inner class of your spiced Activity
	private class AuthenticationListener implements
			RequestListener<LocationPostStatus> {

		@Override
		public void onRequestFailure(SpiceException spiceException) {

			if (spiceException.getCause() instanceof HttpClientErrorException) {
				HttpClientErrorException cause = (HttpClientErrorException) spiceException
						.getCause();

				switch (Integer.valueOf(cause.getStatusCode().toString())) {
				case 401:
					Log.d("LocationSharing", "Unauthorized");
					stopSpice();
					break;

				default:
					stopSpice();
					break;
				}
			} else if (spiceException.getCause() instanceof ResourceAccessException) {
				if (retries++ > 20) {
					sendData();
				} else {
					stopSpice();
				}
			}
		}

		@Override
		public void onRequestSuccess(LocationPostStatus authentication) {
			Log.v("LocationSharing", "success: " + authentication.isSuccess());
			stopSpice();
			// Toast.makeText(getApplicationContext(), "WORKED!",
			// Toast.LENGTH_LONG).show();
		}
	}

	protected void stopSpice() {
		spiceManager.shouldStop();
	}

}