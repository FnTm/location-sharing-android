package lv.lu.locationsharing.requests.friends;

import java.util.List;

import lv.lu.locationsharing.model.InviteFriends;
import lv.lu.locationsharing.model.Registration;
import lv.lu.locationsharing.requests.friends.InviteFriendsRequest.Container;
import lv.lu.locationsharing.requests.friends.InviteFriendsRequest.Message;
import lv.lu.locationsharing.utils.Url;

import org.json.JSONObject;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

//Create a request in its own Java file, it should not an inner class of a Context
public class InviteFriendsRequest extends SpringAndroidSpiceRequest<InviteFriends> {

	private static String resourceName = "friends";
	private Context c;
	private String email;
	private String authToken;

	public InviteFriendsRequest(Context c, String email, String authToken) {
		super(InviteFriends.class);
		this.setRetryPolicy(null);
		this.c = c;
		this.email = email;
		this.authToken = authToken;

	}
	
	
	@Override
	public InviteFriends loadDataFromNetwork() throws Exception {
		JSONObject root = new JSONObject();
		root.put("notificationId", "Hello");
		Message message = new Message();
		message.setEmail(this.email);
		message.setToken(this.authToken);
		Container container=new Container();
		container.setUser(message);
		String url = Url.apiUrl + resourceName;
		
		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		InviteFriends thing = restTemplate.postForObject(url, container,
				InviteFriends.class);

		return thing;
	}
	
	public class Message {
		private String email;
		private String authToken;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getToken() {
			return authToken;
		}
		public void setToken(String authToken) {
			this.authToken = authToken;
		}
		

	}
	public class Container {
		Message user;

		public Message getUser() {
			return user;
		}

		public void setUser(Message user) {
			this.user = user;
		}
		
	}
}