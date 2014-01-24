package lv.lu.locationsharing.requests.friends;

import java.util.List;

import lv.lu.locationsharing.model.GetFriends;
import lv.lu.locationsharing.utils.Url;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

//Create a request in its own Java file, it should not an inner class of a Context
public class PostLogout extends SpringAndroidSpiceRequest<GetFriends> {

	private static String resourceName = "sessions";
	private Context c;
	private String authToken;

	public PostLogout(Context c, String authToken) {
		super(GetFriends.class);
		this.setRetryPolicy(null);
		this.c = c;
		this.authToken = authToken;

	}

	@Override
	public GetFriends loadDataFromNetwork() throws Exception {
		String url = Url.apiUrl + resourceName;

		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		// GetFriends thing = restTemplate.delete(url, urlVariables)(url,
		// GetFriends.class);
		return new GetFriends();
		// return thing;
	}
}
