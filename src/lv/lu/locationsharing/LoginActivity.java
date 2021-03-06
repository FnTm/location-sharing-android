package lv.lu.locationsharing;



import lv.lu.locationsharing.application.LocationApplication;
import lv.lu.locationsharing.config.Config;
import lv.lu.locationsharing.model.AuthenticationStatus;
import lv.lu.locationsharing.requests.authentication.AuthenticationRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
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
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
		"aigars@aigars.com:password", "janis@janis.com:password" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	private static final Object AUTHENTICATION_CACHE_KEY2 = "AuthenticationCacheKey2";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	protected SpiceManager spiceManager;
	protected LocationApplication mApp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mApp=(LocationApplication) getApplication();
        if(mApp.getConfig().getUserToken()!=null && mApp.getConfig().getUserToken()!="" ){
        	goToLogin();
        }
		setContentView(R.layout.activity_login);
		setupActionBar();
		

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		findViewById(R.id.sign_up_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(getBaseContext(), SignUpActivity.class);
						startActivity(i);
					}
				});
		spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
		
	}

	
	@Override
	protected void onStart() {
		super.onStart();
		spiceManager.start(this);
		
	}

	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
       // LocationLibrary.forceLocationUpdate(LoginActivity.this);

	}
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	public void doAuthentication(String email,String password) {
		spiceManager.execute(new AuthenticationRequest(this,
				//"janis@peisenieks.lv","password"), AUTHENTICATION_CACHE_KEY2,
				email,password), AUTHENTICATION_CACHE_KEY2,

				DurationInMillis.ALWAYS_EXPIRED, new AuthenticationListener());
	}
	// inner class of your spiced Activity
	private class AuthenticationListener implements
			RequestListener<AuthenticationStatus> {

		@Override
		public void onRequestFailure(SpiceException spiceException) {

			
			if (spiceException.getCause() instanceof HttpClientErrorException) {
				HttpClientErrorException cause = (HttpClientErrorException) spiceException
						.getCause();

				switch (Integer.valueOf(cause.getStatusCode().toString())) {
				case 401:
					showProgress(false);
					mPasswordView
							.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
					break;

				default:
					doAuthentication("","");
					break;
				}
			}
			else if(spiceException.getCause() instanceof ResourceAccessException){
				doAuthentication("","");
			}
		}

		@Override
		public void onRequestSuccess(AuthenticationStatus authentication) {
			Log.v("Tag","success");
			
			LocationApplication app=(LocationApplication) getApplication();
			Config conf=app.getConfig();
			conf.setUserId(authentication.getId());
			conf.setUserToken(authentication.getAuthentication_token());
			app.setConfig(conf);
			goToLogin();
			// Toast.makeText(getApplicationContext(), "WORKED!",
			// Toast.LENGTH_LONG).show();
		}
	}
	
	protected void goToLogin(){
		Intent i = new Intent(getBaseContext(), MainActivity.class);
		startActivity(i);
		finish();
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

//		// Check for a valid password.
//		if (TextUtils.isEmpty(mPassword)) {
//			mPasswordView.setError(getString(R.string.error_field_required));
//			focusView = mPasswordView;
//			cancel = true;
//		} else if (mPassword.length() < 4) {
//			mPasswordView.setError(getString(R.string.error_invalid_password));
//			focusView = mPasswordView;
//			cancel = true;
//		}
//
//		// Check for a valid email address.
//		if (TextUtils.isEmpty(mEmail)) {
//			mEmailView.setError(getString(R.string.error_field_required));
//			focusView = mEmailView;
//			cancel = true;
//		} else if (!mEmail.contains("@")) {
//			mEmailView.setError(getString(R.string.error_invalid_email));
//			focusView = mEmailView;
//			cancel = true;
//		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			doAuthentication(mEmail,mPassword);

		}
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
