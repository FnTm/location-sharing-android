package lv.lu.locationsharing;


import com.actionbarsherlock.app.SherlockFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Fragment2 extends SherlockFragment{
	private ListView fList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment2, container, false);
		fList = (ListView) rootView.findViewById(R.id.friend_list);
				
		  return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
			
			String[] allFriends = new String[] { "Klâvs Taube", "Aigars Maliðevs", "Jânis Pûgulis", "Jânis Peisenieks",  
			"Vladislavs Maksimèuks", "Andris Pakulis"}; 
			//String[] friendsMails = new String[] { "klavs.taube@gmail.com", "aigarsmalisevs@gmail.com", "janis.pugulis@gmail.com", "janis.peisenieks@gmail.com",  
			//		"vladislavs.maksimcuks@gmail.com", "andris.pakulis@gmail.com"};
			
			
			super.onActivityCreated(savedInstanceState);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	                getActivity(), android.R.layout.simple_list_item_1, allFriends);
	        fList.setAdapter(adapter);
			
	}

}
