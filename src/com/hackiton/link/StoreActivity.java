package com.hackiton.link;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.vending.billing.IInAppBillingService;
import com.parse.Parse;
import com.parse.ParseObject;

public class StoreActivity extends Activity {

	private Spinner numOfCredits;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		
		numOfCredits = (Spinner) findViewById(R.id.inputCreditsSpinner);
		List<String> SpinnerArray1 =  new ArrayList<String>();
		SpinnerArray1.add("10 credits");
		SpinnerArray1.add("75 credits");
		SpinnerArray1.add("200 credits");
		SpinnerArray1.add("500 credits");
		SpinnerArray1.add("1500 credits");
		SpinnerArray1.add("3500 credits");
		ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, SpinnerArray1);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numOfCredits.setAdapter(dataAdapter);
		
		bindService(new 
		        Intent("com.android.vending.billing.InAppBillingService.BIND"),
		                mServiceConn, Context.BIND_AUTO_CREATE);
		Parse.initialize(this, "tInCzpvS8OnX9b6UNt6drQIezkqVi9MwnpnzrbYe", "InfysTDhLOBhLAxTKXsLMS0lkDeuNfK26mqeeNrN");
		
		ParseObject testObject = new ParseObject("Coupons");
		testObject.put("10", "10free4me");
		
		testObject.saveInBackground();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.store, menu);
		return true;
	}

	public void goToMain(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	IInAppBillingService mService;

	ServiceConnection mServiceConn = new ServiceConnection() {
	   @Override
	   public void onServiceDisconnected(ComponentName name) {
	       mService = null;
	   }

	   @Override
	   public void onServiceConnected(ComponentName name, 
	      IBinder service) {
	       mService = IInAppBillingService.Stub.asInterface(service);
	   }
	};
	
	// Need to put this in an AsyncTask TODO
	private void chargeTheCustomer() {
		ArrayList<String> skuList = new ArrayList<String> ();
		skuList.add("premiumUpgrade");
		skuList.add("gas");
		Bundle querySkus = new Bundle();
		querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
		try {
			Bundle skuDetails = mService.getSkuDetails(3, 
					   getPackageName(), "inapp", querySkus);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    if (mService != null) {
	        unbindService(mServiceConn);
	    }   
	}
	
}
