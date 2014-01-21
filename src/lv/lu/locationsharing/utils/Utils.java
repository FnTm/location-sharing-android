package lv.lu.locationsharing.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

@SuppressLint("DefaultLocale")
public final class Utils {

	static final String DISPLAY_MESSAGE_ACTION = "com.mynfcteam.natuplan.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public static String bytesToHex(byte[] byteArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			stringBuilder.append(Integer
					.toString((byteArray[i] & 0xff) + 0x100, 16).substring(1)
					.toUpperCase());
		}
		return stringBuilder.toString();
	}
}
