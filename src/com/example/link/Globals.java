package com.example.link;

import java.util.List;

import com.memetix.mst.language.Language;

public class Globals {
	// List of static variables
	// Static variables that need to be saved:

	// Number of translations the user has left
	public static volatile int initialCredits = 10;
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

	// Languages Available
	public static volatile Language defaultLanguage1;
	public static volatile Language defaultLanguage2;

	//Languages supported
	public static volatile List<String> supportedLanguages;

	//2D array of language information. First row is language name, second row is abbreviation, third is whether it is valid or not.
	public static volatile String[] languages = {"Afrikaans", "Albanian", "Arabic", "Azerbaijani", "Basque", "Bengali",
		"Belarusian", "Bulgarian", "Catalan", "Chinese Simplified", "Chinese Traditional", "Croatian", "Czech",
		"Danish", "Dutch", "English", "Esperanto", "Estonian", "Filipino", "Finnish", "French", "Galician", "Georgian",
		"German", "Greek", "Gujarati", "Haitian Creole", "Hebrew", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Irish",
		"Italian", "Japanese", "Kannada", "Korean", "Latin", "Latvian", "Lithuanian", "Macedonian", "Malay", "Maltese",
		"Norwegian", "Persian", "Polish", "Portuguese", "Romanian", "Russian", "Serbian", "Slovak", "Slovenian",
		"Spanish", "Swahili", "Swedish", "Tamil", "Telugu", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese", "Welsh", 
		"Yiddish"};

	public static volatile String[] language_abbreviations = {"af", "sq", "ar", "az", "eu",
		"bn", "be", "bg", "ca", "zh-CN", "zh-TW", "hr", "cs", "da", "nl", "en", "eo", "et",
		"tl", "fi", "fr", "gl", "ka", "de", "el", "gu", "ht", "iw", "hi", "hu", "is", "id",
		"ga", "it", "ja", "kn", "ko", "la", "lv", "lt", "mk", "ms", "mt", "no", "fa", "pl",
		"pt", "ro", "ru", "sr", "sk", "sl", "es", "sw", "sv", "ta", "te", "th", "tr", "uk",
		"ur", "vi", "cy", "yi"};

	public static volatile int numLanguages = 64;
	public static volatile String[] language_speech  = new String[numLanguages];
	public static volatile boolean[] language_available = new boolean[numLanguages];
	
	// Flags
	public static volatile boolean hasTextToSpeech;
	public static volatile boolean hasSpeechRecognizer;
	
	
}
