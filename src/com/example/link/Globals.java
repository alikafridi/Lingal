package com.example.link;

import java.util.List;

import com.memetix.mst.language.Language;

public class Globals {
	// List of static variables
	// Static variables that need to be saved:

	// Number of translations the user has left
	public static volatile int initialCredits = 5;
	public static volatile int creditsRemaining = initialCredits; 

	// Number of translations used
	public static volatile int creditsUsed = 0;

	// Whether or not a coupon has been used
	public static volatile boolean couponUsed = false;

	// Whether this is the first time the app is being opened
	public static volatile boolean firstOpen = true;

	// Whether or not the account has been upgraded to premium
	public static volatile boolean premiumAccount;

	// Whether or not the user has shared or rated the app
	public static volatile boolean appSharedFb = false;
	public static volatile boolean appTweeted = false;
	public static volatile boolean appRated = false;
	public static volatile int appShareReward = 5;

	public static volatile Language defaultLanguage1;
	public static volatile Language defaultLanguage2;

	//Languages supported
	public static volatile List<String> supportedLanguages;

	//2D array of language information. First row is language name, second row is abbreviation, third is whether it is valid or not.
	public static volatile String[] languages = {"Arabic", "Armenian", "Bulgarian", "Chinese (Simplified)", "Chinese (Traditional)", "Croatian", "Czech", "Danish", "Dutch", "English-US", "English-UK",
		"Finnish", "French", "German", "Greek", "Hebrew", "Hindi", "Hungarian", "Indonesian", "Italian", "Japanese", "Korean", "Latvian",
		"Lithuanian", "Norwegian", "Polish", "Portuguese", "Romanian", "Russian", "Serbian", "Slovak", "Slovenian", "Spanish", "Swedish",
		"Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese"};
	
	public static volatile String[] language_abbreviations = {"ar", "hy", "bg", "zh-CN", "zh-TW", "hr", "cs", "da", "nl", "en", "en-GB",
		"fi", "fr", "de", "el", "he", "hi", "hu", "id", "it", "ja", "ko", "lv", 
		"lt", "no", "pl", "pt", "ro", "ru", "sr", "sk", "sl", "es", "sv",
		"th", "tr", "uk", "ur", "vi"};
	
	public static volatile String [][] languageInfo  = new String[39][3];

	public static void setUpLanguage() {
		for (int i = 0; i < languages.length; i++) {
			languageInfo[i][0] = languages[i];
		}
		
		for (int i = 0; i < languages.length; i++) {
			languageInfo[i][1] = language_abbreviations[i];
		}
		
		for (int i = 0; i < languages.length; i++) {
			languageInfo[i][2] = "false";
		}
	}
}
