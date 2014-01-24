package lv.lu.locationsharing;

import lv.lu.locationsharing.model.Registration;
import lv.lu.locationsharing.requests.registration.RegistrationRequest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
                
                
                mEmail = "d188233@drdrb.com";
                mPassword = "password";
                mPasswordConfirm = "password";
                mName = "test";

                findViewById(R.id.sign_up_button).setOnClickListener(
                                new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                               // mEmail = mEmailView.getText().toString();
                                               //mPassword = mPasswordView.getText().toString();
                                               // mPasswordConfirm = mPasswordConfirmView.getText().toString();
                                               // mName = mNameView.getText().toString();
                                                
                                                doRegistration(mEmail, mPassword, mPasswordConfirm,
                                                                mName);
                                        }
                                });
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

        public void doRegistration(String Email, String Password,
                        String PasswordConfirm, String Name) {
                //spiceManager.execute(new RegistrationRequest(this.context, Email,
                //                Password, PasswordConfirm, Name), "",
                                spiceManager.execute(new RegistrationRequest(this.context, "d188825@drdrb.com",
                                        "Password", "Password", "Name"), "",
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
                                        break;

                                default:
                                        break;
                                }
                        } else if (spiceException.getCause() instanceof ResourceAccessException) {

                        }
                }

                @Override
                public void onRequestSuccess(Registration arg0) {
                        // TODO Auto-generated method stub
                }
        }

}