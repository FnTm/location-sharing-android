package lv.lu.locationsharing.model;

public class Friend {
int id;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
String name;
String email;
double latitude;
double longitude;
String updated_at;


public String getUpdated_at() {
	return updated_at;
}
public void setUpdated_at(String updated_at) {
	this.updated_at = updated_at;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}


}
