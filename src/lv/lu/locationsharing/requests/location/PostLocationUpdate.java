package lv.lu.locationsharing.requests.location;

import java.util.List;

import lv.lu.locationsharing.model.LocationPostStatus;
import lv.lu.locationsharing.utils.Url;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

//Create a request in its own Java file, it should not an inner class of a Context
public class PostLocationUpdate extends SpringAndroidSpiceRequest<LocationPostStatus> {

	private static String resourceName = "users";
	private Context c;
	private String authToken;
	private float latitude;
	private float longitude;
	private int userId;
	public PostLocationUpdate(Context c,int userId, String authToken,float latitude,float longitude ) {
		super(LocationPostStatus.class);
		this.setRetryPolicy(null);
		this.c = c;
		this.authToken = authToken;
		this.latitude=latitude;
		this.longitude=longitude;
		this.userId=userId;

	}

	@Override
	public LocationPostStatus loadDataFromNetwork() throws Exception {
		String url = Url.apiUrl + resourceName + "/"+this.userId+ "/location";

		Message msg=new Message();
		msg.setLatitude(this.latitude);
		msg.setLongitude(this.longitude);
		Container cnt=new Container();
		cnt.setAuthentication_token(this.authToken);
		cnt.setUser(msg);
		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		LocationPostStatus thing = restTemplate.postForObject(url,cnt, LocationPostStatus.class);

		return thing;
	}
	
	public class Message {
		private double latitude;
		private double longitude;
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
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
		String authentication_token;

		public String getAuthentication_token() {
			return authentication_token;
		}

		public void setAuthentication_token(String authentication_token) {
			this.authentication_token = authentication_token;
		}
		
	}
	
}
