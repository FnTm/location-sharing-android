package lv.lu.locationsharing.requests.authentication;

import java.util.List;

import lv.lu.locationsharing.model.AuthenticationStatus;
import lv.lu.locationsharing.utils.Url;

import org.json.JSONObject;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

//Create a request in its own Java file, it should not an inner class of a Context
public class AuthenticationRequest extends
		SpringAndroidSpiceRequest<AuthenticationStatus> {

	private static String resourceName = "sessions";
	private Context c;
	private String email;
	private String password;


	public AuthenticationRequest(Context c, String email, String password) {
		super(AuthenticationStatus.class);
		this.setRetryPolicy(null);
		this.c = c;
		this.email = email;
		this.password = password;

	}

	@Override
	public AuthenticationStatus loadDataFromNetwork() throws Exception {
		JSONObject root = new JSONObject();
		root.put("notificationId", "Hello");
		Message message = new Message();
		message.setEmail(this.email);
		message.setPassword(this.password);
		Container container=new Container();
		container.setUser(message);
		String url = Url.apiUrl + resourceName;// +"/?XDEBUG_SESSION_START=PHPSTORM";
		
		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		AuthenticationStatus thing = restTemplate.postForObject(url, container,
				AuthenticationStatus.class);

		return thing;
	}

	public class Message {
		private String email;
		private String password;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
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
