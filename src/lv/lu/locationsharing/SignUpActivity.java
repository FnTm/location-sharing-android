
package lv.lu.locationsharing;




import java.util.Iterator;



import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.config.Config;
import lv.lu.locationsharing.model.AuthenticationStatus;
import lv.lu.locationsharing.model.Friend;
import lv.lu.locationsharing.model.GetFriends;
import lv.lu.locationsharing.model.LocationPostStatus;
import lv.lu.locationsharing.model.Registration;
import lv.lu.locationsharing.requests.authentication.AuthenticationRequest;
import lv.lu.locationsharing.requests.friends.GetFriendsRequest;
import lv.lu.locationsharing.requests.location.PostLocationUpdate;
import lv.lu.locationsharing.requests.registration.RegistrationRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.internal.au;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


public class SignUpActivity extends Activity {
	
	private String mEmail;
	private String mPassword;
	private String mPasswordConfirm;
	private String mName;
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mPasswordConfirmView;
	private EditText mNameView;
	protected SpiceManager spiceManager;
	private static final Object REGISTRATION_CACHE_KEY = "RegistrationCacheKey";
	protected Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sign_up);
		
		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
		
		mEmailView = (EditText) findViewById(R.id.email);
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordConfirmView = (EditText) findViewById(R.id.password_conf);
		mNameView = (EditText) findViewById(R.id.name);
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPasswordConfirm = mPasswordConfirmView.getText().toString();
		mName = mNameView.getText().toString();
		
		findViewById(R.id.sign_up_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						doRegistration(mEmail, mPassword, mPasswordConfirm, mName);
					}
				});
	}
    
	public void doRegistration(String Email, String Password, String PasswordConfirm,
			String Name) {
		spiceManager.execute(new RegistrationRequest(this.context, Email,
				Password,  PasswordConfirm, Name), "",
				DurationInMillis.ALWAYS_EXPIRED, new AuthenticationListener());
	}
	
	private class AuthenticationListener implements
	RequestListener<Registration> {

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
		
			stopSpice();
		
	}
}



@Override
public void onRequestSuccess(Registration arg0) {
	// TODO Auto-generated method stub
	stopSpice();
}
}

protected void stopSpice() {
spiceManager.shouldStop();
}
	
	
}
