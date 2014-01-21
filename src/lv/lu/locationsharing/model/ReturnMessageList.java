package lv.lu.locationsharing.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReturnMessageList extends ArrayList<ReturnMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 432468759232871348L;
	

	  @JsonAnySetter
	  public void handleUnknown(String key, Object value) {
	    // do something: put to a Map; log a warning, whatever
	  }
	
}
