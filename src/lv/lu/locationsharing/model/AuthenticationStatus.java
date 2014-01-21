package lv.lu.locationsharing.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class AuthenticationStatus {
	boolean success;
	String authentication_token;
	int id;
	ArrayList<String> errors;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getAuthentication_token() {
		return authentication_token;
	}
	public void setAuthentication_token(String authentication_token) {
		this.authentication_token = authentication_token;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<String> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

}
