package lv.lu.locationsharing.requests.authentication;

import lv.lu.locationsharing.model.AuthenticationStatus;

import org.springframework.http.ResponseEntity;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AuthenticationStatusRequest extends
		SpringAndroidSpiceRequest<AuthenticationStatus> {

	private Context c;

	public AuthenticationStatusRequest(Context c) {
		super(AuthenticationStatus.class);
		this.setRetryPolicy(null);
		this.c = c;
	}

	@Override
	public AuthenticationStatus loadDataFromNetwork() throws Exception {
		ResponseEntity<AuthenticationStatus> returned = getRestTemplate()
				.getForEntity("http://www.example.com/authenticate",
						AuthenticationStatus.class);
		AuthenticationStatus toRet = returned.getBody();
//		toRet.setResponseCode(Integer.valueOf(returned.getStatusCode()
//				.toString()));
		return returned.getBody();
	}
}
