package lv.lu.locationsharing.requests.friends;

import java.util.List;

import lv.lu.locationsharing.model.InviteFriends;
import lv.lu.locationsharing.utils.Url;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

//Create a request in its own Java file, it should not an inner class of a Context
public class ConfirmFriendRequest extends SpringAndroidSpiceRequest<InviteFriends> {

	private static String resourceName = "friends";
	private Context c;
	private int userId;
	private String authToken;

	public ConfirmFriendRequest(Context c, int userId, String authToken) {
		super(InviteFriends.class);
		this.setRetryPolicy(null);
		this.c = c;
		this.userId = userId;
		this.authToken = authToken;

	}
	
	
	@Override
	public InviteFriends loadDataFromNetwork() throws Exception {
		Message message = new Message();
		message.setAuthentication_token(this.authToken);
		String url = Url.apiUrl + resourceName+"/"+this.userId+"/put_as_post";
		
		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		InviteFriends thing = restTemplate.postForObject(url, message,
				InviteFriends.class);

		return thing;
	}
	
	public class Message {
		private String authentication_token;
		public String getAuthentication_token() {
			return authentication_token;
		}
		public void setAuthentication_token(String authentication_token) {
			this.authentication_token = authentication_token;
		}
	}

}