package lv.lu.locationsharing.listview.adapter;

import java.util.List;

import lv.lu.locationsharing.R;
import lv.lu.locationsharing.model.Friend;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RequestAdapter extends BaseAdapter {
	private static final String TAG = RequestAdapter.class.getSimpleName();
	private List<Friend> mRequestList;
	private LayoutInflater mInflater;
	private Context c;

	public RequestAdapter(Context c) {
		mInflater = LayoutInflater.from(c);
		this.c = c;
	}

	public void setData(List<Friend> list) {
		mRequestList = list;
		Log.v(TAG, "End setting data");

	}

	@Override
	public int getCount() {
		return mRequestList.size();
	}

	@Override
	public Object getItem(int position) {
		return mRequestList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.listview_request_row, null);

			holder = new ViewHolder();

			holder.mName = (TextView) convertView
					.findViewById(R.id.listview_friend_name);
			holder.mEmail = (TextView) convertView
					.findViewById(R.id.listview_friend_email);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Friend friend = mRequestList.get(position);

		holder.mName.setText(friend.getName());
		holder.mEmail.setText(friend.getEmail());
		

		return convertView;
	}

	static class ViewHolder {
		TextView mName;
		TextView mEmail;
	}
}
