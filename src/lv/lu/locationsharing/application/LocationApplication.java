package lv.lu.locationsharing.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

import lv.lu.locationsharing.config.Config;
import android.app.AlarmManager;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class LocationApplication extends Application {

	final public String TAG = "LocationSharingApplication";
	private String lastTagId = null;
	private static String configFile = "config.locations";
	private File fileDir;
	private Config config;

	public boolean configFileExists() {
		File config = new File(fileDir.toString() + configFile);
		// Log.v(TAG,"Config file size: "+Utils.readableFileSize(config.length()));
		// Log.v(TAG,
		// "Exists: " + config.exists() + ", canRead: " + config.canRead()
		// + ", canWrite: " + config.canWrite());

		return (config.exists() && config.canRead() && config.canWrite());
	}

	public Config getConfig() {
		return config;

	}

	public Config getConfigFromFile() {
		boolean configExists = configFileExists();
		if (configExists) {
			Log.v(TAG, "Config from file");
			Config config = ((Config) loadClassFile(new File(fileDir.toString()
					+ configFile)));
			Log.v(TAG, config + "");

			return config;

		}
		return null;

	}

	public String getLastTagId() {
		return lastTagId;
	}

//	public void getMessages() {
//		sendMessage();
//		Intent intent = new Intent(getApplicationContext(), DataService.class);
//		intent.putExtra("action", "get");
//		startService(intent);
//	}

	private Object loadClassFile(File f) {
		try {
			FileInputStream fis = new FileInputStream(f);

			ObjectInputStream ois = new ObjectInputStream(fis);
			Object o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception ex) {
			// Log.v("Address Book", ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		fileDir = getFilesDir();
		//setConfig(new Config());
		// sendMessage();
		// deleteDatabase(DBHelper.DATABASE_NAME);
		/*
		 * try { SimpleDateFormat dateFormat = new
		 * SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); Date date = new Date(); File
		 * dir=new
		 * File(Environment.getExternalStorageDirectory().getAbsolutePath
		 * ()+"/signLogs/"); boolean created=false; if(!dir.exists()){
		 * created=dir.mkdirs(); } File filename = new
		 * File(Environment.getExternalStorageDirectory
		 * ()+"/signLogs/logfile"+dateFormat.format(date)+".log");
		 * filename.createNewFile();
		 * 
		 * String cmd = "logcat -f "+filename.getAbsolutePath()+
		 * " -v time ListView:V Prefs:V Splash:V System.err:V MyApplication:V AndroidRuntime:V *:S"
		 * ; Runtime.getRuntime().exec(cmd); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		// cleanConfig();Constants.java
		LocationLibrary.showDebugOutput(true);
		
        try {
            // in most cases the following initialising code using defaults is probably sufficient:
            //
            // LocationLibrary.initialiseLibrary(getBaseContext(), "com.your.package.name");
            //
            // however for the purposes of the test app, we will request unrealistically frequent location broadcasts
            // every 1 minute, and force a location update if there hasn't been one for 2 minutes.
            LocationLibrary.initialiseLibrary(getBaseContext(), 60*1000, 60*2000, "mobi.littlefluffytoys.littlefluffytestclient");
        }
        catch (UnsupportedOperationException ex) {
            Log.d("TestApplication", "UnsupportedOperationException thrown - the device doesn't have any location providers");
        }
		Log.v(TAG, fileDir.toString());
		Log.v(TAG, configFileExists() + "");
		if (configFileExists()) {
			config = getConfigFromFile();
		}
		else{
			setConfig(new Config());
		}
		//sendData();
	}

	public void resetConfig() {
		this.config = getConfigFromFile();
	}

	public void resetLastTagId() {
		this.lastTagId = null;
	}

	public boolean saveConfig(Object config) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(
					fileDir.toString() + configFile));

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(config);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} // write the class as an 'object'
		Log.v(TAG, "config saved");
		return true;
	}
	public LocationApplication sendUpdate() {
		Intent in = new Intent();
		in.setAction("lv.lu.locationsharing");
		sendBroadcast(in);
		return this;
	}

	public LocationApplication setConfig(Config config) {
		this.config = config;
		saveConfig(config);
		return this;

	}

}
