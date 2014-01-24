package lv.lu.locationsharing.model;

import java.util.ArrayList;

public class LocationPostStatus {
	boolean success;
	ArrayList<String> errors;

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
