package com.example.link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.memetix.mst.language.Language;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener{

	public static final String LOG_TAG = "Main Activity";
	protected static final int REQUEST_OK = 1;

	public static String input = "";
	public static String output = "";

	public static Language input_lang = Language.ENGLISH;
	public static Language output_lang = Language.FRENCH;

	private TextToSpeech mTts;

	public TextView creditsRemainingArea;
	public TextView inputArea;
	public TextView outputArea;
	
	 private Spinner spinner1, spinner2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		creditsRemainingArea = ((TextView)findViewById(R.id.creditsRemaining));
		inputArea = ((TextView)findViewById(R.id.inputText));
		outputArea = ((TextView)findViewById(R.id.translatedText));

		mTts = new TextToSpeech(this,this); //TextToSpeech.OnInitListeners

		// Set up some variables initially
		if (Globals.firstOpen) {
			initialize();
		}
		updateUI();
		populateSpinners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/* ************************************************************** */
	/* ********************** Update Information ******************** */
	/* ************************************************************** */

	public void updateCredits() {
		Globals.creditsRemaining--;
		Globals.creditsUsed++;
	}
	
	
	/**
	 * Updates the UI elements of the app
	 */
	public void updateUI() {
		creditsRemainingArea.setText(Globals.creditsRemaining + " Credits Remaining");
		if (!internetConnected())
			Toast.makeText(this, "You are not connected to the internet", Toast.LENGTH_LONG).show();
	}
	
	/* ************************************************************** */
	/* ********************* Speech Recognitions ******************** */
	/* ************************************************************** */
	
	/**
	 * Method executes when the mic button is pressed
	 * @param v
	 */
	public void startListening(View v) {
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		try {
			startActivityForResult(i, REQUEST_OK);
		} catch (Exception e) {
			Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Callback for when startListening finished
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
			ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			inputArea.setText(thingsYouSaid.get(0));
			input = thingsYouSaid.get(0);
			if (internetConnected()) {
				TranslateTextTask task = new TranslateTextTask();
				task.execute(new String[] { thingsYouSaid.get(0) });
			}
			else {
				new AlertDialog.Builder(this).setTitle("Alert").setMessage("You are not connected to the internet").setNeutralButton("OK", null).show();
			}
		}
	}
	
	/* ************************************************************** */
	/* ************************ Translations ************************ */
	/* ************************************************************** */

	/**
	 * AsyncTask Background Thread to execute call to Bing Language converter
	 */
	private class TranslateTextTask extends AsyncTask<String, Void, String> {
		String response = "no response";
		@Override
		protected String doInBackground(String... urls) {
			try {
				String googleApiKey = "AIzaSyB1fwathoLC04eSlvY8p5CmLxHBJbSyrMk";
				String URL = "https://www.googleapis.com/language/translate/v2?key=MY_API_KEY&source=INITIAL_LANGUAGE_AB&target=OUTPUT_LANGUAGE_AB&q=" + input.replaceAll(" ", "+");  
				URL = URL.replace("MY_API_KEY", googleApiKey);
				URL = URL.replace("INITIAL_LANGUAGE_AB", "en");
				URL = URL.replace("OUTPUT_LANGUAGE_AB", "fr");
				Log.e("test", URL);
				HttpClient httpclient = new DefaultHttpClient();  
		        HttpGet request = new HttpGet(URL);  
		        //request.addHeader("deviceId", deviceId);  
		        ResponseHandler<String> handler = new BasicResponseHandler();  
		        try {  
		            response = httpclient.execute(request, handler);
		            while (response.equals("no response")){
		            	
		            }
		            int indexOfColon = response.indexOf("\": \"");
		            int endOfWantedResult = response.indexOf("\"", indexOfColon+4);
		            String results = response.substring(indexOfColon+4, endOfWantedResult);

					response = results;
		        } catch (ClientProtocolException e) {  
		            e.printStackTrace();  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }  
		        httpclient.getConnectionManager().shutdown();  
		        Log.i("AsyncTask: ", response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}
		@Override
		protected void onPostExecute(String result) {
			outputArea.setText(response);
			sayHello();
		}
	}

	/* ************************************************************** */
	/* *********************** Text to Speech *********************** */
	/* ************************************************************** */
	
	@Override
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			int result = mTts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Lanuage data is missing or the language is not supported.
				Log.e(LOG_TAG, "Language is not available.");
			} else {
				// The TTS engine has been successfully initialized.
				sayHello();
			}
		} else {
			// Initialization failed.
			Log.e(LOG_TAG, "Could not initialize TextToSpeech.");
		}
	}

	private void sayHello() {
		mTts.setLanguage(Locale.US);
		mTts.speak(input,TextToSpeech.QUEUE_FLUSH,null);
	}

	/* ************************************************************** */
	/* ************************ Nav. Buttons ************************ */
	/* ************************************************************** */

	public void goToStore(View v) {
		Intent intent = new Intent(this, StoreActivity.class);
		startActivity(intent);
	}
	
	public void goToSettings(View v) {
		Intent intent = new Intent(this, StoreActivity.class);
		startActivity(intent);
	}
	
	public void goToFeedback(View v) {
		Intent intent = new Intent(this, StoreActivity.class);
		startActivity(intent);
	}
	
	/* ************************************************************** */
	/* *********************** Error Checking *********************** */
	/* ************************************************************** */

	/**
	 * @return	True if device is connected to the internet, False otherwise
	 */
	private boolean internetConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/* ************************************************************** */
	/* ********************** Set Up / Clean Up ********************* */
	/* ************************************************************** */

	/**
	 * Sets up some Globals variables. Should only be called the first time the app is opened on a device. 
	 */
	private void initialize() {
		if (Globals.firstOpen) {
			Globals.creditsRemaining = Globals.initialCredits;
			Globals.creditsUsed = 0;
			Globals.firstOpen = false;
			checkAvailableLanguages();
			checkVoiceRecognition();
		}
	}
	
	public void checkAvailableLanguages() {
		Locale loc = new Locale("en");
		String availableLangs = Arrays.toString(loc.getAvailableLocales());
		Log.e("-------------", availableLangs);
		for (int i = 0; i < Globals.numLanguages; i++) {
			String check = Globals.language_abbreviations[i];
			check += ",";
			if (availableLangs.contains(check))
				Globals.language_available[i] = true;
			else
				Globals.language_available[i] = false;
		}
		for (int i = 0; i < Globals.numLanguages; i++) {
			if (Globals.language_available[i])
				Log.e("-------------", Globals.language_abbreviations[i]);
		}
	}

	/**
	 * Checks whether the device has speech recognition
	 */
	private void checkVoiceRecognition() {
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			Toast.makeText(this, "Speech to Text service not available on this phone",
					Toast.LENGTH_LONG).show();
			Globals.hasSpeechRecognizer = false;
		}
		else 
			Globals.hasSpeechRecognizer = true;
	}
	
	private void populateSpinners() {
		spinner1 = (Spinner) findViewById(R.id.inputLanguageSpinner);
		spinner2 = (Spinner) findViewById(R.id.outputLanguageSpinner);
		List<String> SpinnerArray =  new ArrayList<String>();
	    for (int i = 0; i < Globals.numLanguages; i++) {
	    	if (Globals.language_available[i])
	    		SpinnerArray.add(Globals.languages[i]);
	    }
	    ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, SpinnerArray);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner1.setAdapter(dataAdapter);
	    spinner2.setAdapter(dataAdapter);
	}

	/**
	 * Need to clean up text to speech stuff when app is destroyed
	 */
	@Override
	public void onDestroy() {
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}
		super.onDestroy();
	}

}
