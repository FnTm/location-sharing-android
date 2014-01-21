package lv.lu.locationsharing.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReturnMessage {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	int request_id;
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getDivision_name() {
		return division_name;
	}

	public void setDivision_name(String division_name) {
		this.division_name = division_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public int getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(int worker_id) {
		this.worker_id = worker_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRequest_date() {
		return request_date;
	}

	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}

	public int getWithout_nfc() {
		return without_nfc;
	}

	public void setWithout_nfc(int without_nfc) {
		this.without_nfc = without_nfc;
	}

	public ArrayList<String> getClient_tags() {
		return client_tags;
	}

	public void setClient_tags(ArrayList<String> client_tags) {
		this.client_tags = client_tags;
	}

	public ArrayList<String> getOffice_tags() {
		return office_tags;
	}

	public void setOffice_tags(ArrayList<String> office_tags) {
		this.office_tags = office_tags;
	}

	int id = 0;
	String status_name;
	int client_id;
	String client_name;
	String division_name;
	String description;
	String priority;
	int type_id;
	String type_name;
	int worker_id;
	String note;
	String request_date;
	int without_nfc;
	String office_name;

	public String getOffice_name() {
		return office_name;
	}

	public void setOffice_name(String office_name) {
		this.office_name = office_name;
	}

	ArrayList<String> client_tags;
	ArrayList<String> office_tags;
	ArrayList<Task> tasks;

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

}
