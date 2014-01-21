package lv.lu.locationsharing.requests.authentication;

import java.util.ArrayList;

import lv.lu.locationsharing.model.ReturnMessageList;


public class GetRequestsResponse {
	ReturnMessageList open_requests;

	public ReturnMessageList getOpen_requests() {
		return open_requests;
	}

	public void setOpen_requests(ReturnMessageList open_requests) {
		this.open_requests = open_requests;
	}

	ArrayList<Integer> closed_requests;

	public ArrayList<Integer> getClosed_requests() {
		return closed_requests;
	}

	public void setClosed_requests(ArrayList<Integer> closed_requests) {
		this.closed_requests = closed_requests;
	}

	public GetRequestsResponse() {
	}

}