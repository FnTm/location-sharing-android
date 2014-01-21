package lv.lu.locationsharing.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.util.Log;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusesMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3971041586476943950L;
	/**
	 * 
	 */
	List<String> initial;
	List<String> started;
	String mainSuspend;

	List<String> suspended;

	List<String> closed;

	public List<String> getClosed() {
		return closed;
	}

	public List<String> getInitial() {
		return initial;
	}

	public String getInitialName() {
		if (getInitial().size() > 0) {
			return getInitial().get(0);
		} else {
			return "";
		}
	}

	public String getMainSuspend() {
		return mainSuspend;
	}

	public List<String> getStarted() {
		return started;
	}

	public String getStartedName() {
		if (getStarted().size() > 0) {
			return getStarted().get(0);
		} else {
			return "";
		}
	}

	public String getClosedName() {
		if (getClosed().size() > 0) {
			return getClosed().get(0);
		} else {
			return "";
		}
	}

	public List<String> getSuspended() {
		return suspended;
	}

	public void setClosed(List<String> closed) {
		this.closed = closed;
	}

	public void setInitial(List<String> initial) {
		this.initial = initial;
	}

	public void setMainSuspend(String mainSuspend) {
		this.mainSuspend = mainSuspend;
	}

	public void setStarted(List<String> started) {
		this.started = started;
	}

	public void setSuspended(List<String> suspended) {
		Log.e("Suspended", suspended.size() + "");
		if (suspended.size() > 0) {
			setMainSuspend(suspended.get(0));
			suspended.remove(0);
		}
		this.suspended = suspended;
	}

}
