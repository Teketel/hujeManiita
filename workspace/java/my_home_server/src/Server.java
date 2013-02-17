import java.io.IOException;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class Server {

	public static void main(String args[]) {
		Sender sender = new Sender("AIzaSyAvibNcQ9OwiB_h-glX4SD4ZQjJGgWiJ9k");
		Message message = new Message.Builder().build();

		Result result = null;
		try {
			result = sender.send(message, "APA91bHttEIg49kyyJtbA0tQT-6Na6V5moCqqN6QJrT_hjMHZkSB85KOR6N5_rINEXpqsTJzoQtNQ-fKY52kwidhGDayyO8iqJuz9exMan2aUUklRqJ0K0Ar4f0_lvqOzYoZ4qH8SoX5FUfs-cUbqhWoz_cgt8GKMg",
							1);
		} catch (IOException e) {
			System.out.println("Error occured.");
			e.printStackTrace();

		}
		if (result.getMessageId() != null) {
			String canonicalRegId = result.getCanonicalRegistrationId();
			if (canonicalRegId != null) {
				// same device has more than on registration ID: update database
				System.out.println("same device has more than on registration ID: update database");

			}
		} else {
			String error = result.getErrorCodeName();
			if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				// application has been removed from device - unregister
				// database
				System.out.println("application has been removed from device - unregister database");

			}
		}
		System.out.println("Inforr:" + result.toString());

	}

}
