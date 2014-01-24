package lv.lu.locationsharing.requests.registration;

import java.util.List;

import lv.lu.locationsharing.model.Registration;
import lv.lu.locationsharing.utils.Url;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class RegistrationRequest extends SpringAndroidSpiceRequest<Registration> {
	
	private static String resourceName = "users";
	private Context c;
	private String email;
	private String password;
	private String password_confirmation;
	private String name;
	
	public RegistrationRequest(Context c, String email, String password, String password_confirmation, String name) {
		super(Registration.class);
		this.setRetryPolicy(null);
		this.c = c;
		this.email = email;
		this.password = password;
		this.password_confirmation = password_confirmation;
		this.name = name;
	}

	@Override
	public Registration loadDataFromNetwork() throws Exception {
		Message message = new Message();
		message.setEmail(this.email);
		message.setPassword(this.password);
		message.setPassword_confirmation(this.password_confirmation);
		message.setName(this.name);
		Container container=new Container();
		container.setUser(message);
		String url = Url.apiUrl + resourceName;
		
		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		Registration thing = restTemplate.postForObject(url, container,
				Registration.class);

		return thing;
	}
	
	public class Message {
		private String email;
		private String password;
		private String password_confirmation;
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
		public String getPassword_confirmation() {
			return password_confirmation;
		}
		public void setPassword_confirmation(String password_confirmation) {
			this.password_confirmation = password_confirmation;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		private String name;


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
