package lv.lu.locationsharing.config;

import java.io.Serializable;

public class Config implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -7858607805030527724L;
	int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserToken() {
		return userToken;
	}
	public void setUserToken(int userToken) {
		this.userToken = userToken;
	}
	int userToken;
	

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Config:[").append("userId: ").append(getUserId()).append(" ; token: ").append(getUserToken());
		buf.append("]");
		return buf.toString();
	}

}
