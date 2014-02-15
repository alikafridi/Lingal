package com.example.link;

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
}
