package lv.lu.locationsharing.model;

import java.util.ArrayList;

public class GetFriends {
ArrayList<Friend> friends;
public ArrayList<Friend> getFriends() {
	return friends;
}
public void setFriends(ArrayList<Friend> friends) {
	this.friends = friends;
}
public ArrayList<Friend> getInvited_friends() {
	return invited_friends;
}
public void setInvited_friends(ArrayList<Friend> invited_friends) {
	this.invited_friends = invited_friends;
}
public ArrayList<Friend> getInviting_friends() {
	return inviting_friends;
}
public void setInviting_friends(ArrayList<Friend> inviting_friends) {
	this.inviting_friends = inviting_friends;
}
ArrayList<Friend> invited_friends;
ArrayList<Friend> inviting_friends;

}
