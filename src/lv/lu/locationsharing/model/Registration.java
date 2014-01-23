package lv.lu.locationsharing.model;

import java.util.ArrayList;

public class Registration {
	Boolean success;
	ArrayList<String> errors;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public ArrayList<String> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	
}
