package com.xbmc_remote.main;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;

import com.example.xbmc_remote.R;

public class MainActivity extends Activity {
	SlidingDrawer volSlider;
	SlidingDrawer libSlider;
	ImageButton btnHome, btnVolume, btnFolder, btnReturn;
	Intent serverListActivity;
	public static Boolean appConnected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Wifi Remote");
		actionBar.setTitle("XBMC");
		volSlider = (SlidingDrawer) findViewById(R.id.soundDrawer);
		libSlider = (SlidingDrawer) findViewById(R.id.libraryDrawer);
		volSlider.bringToFront();
		libSlider.bringToFront();
		setUpButtons();
	}

	void setUpButtons() {
		// Set up 'Bottom Bar'
		btnHome = (ImageButton) findViewById(R.id.btnHome);
		btnVolume = (ImageButton) findViewById(R.id.btnSound);
		btnFolder = (ImageButton) findViewById(R.id.btnLib);
		btnReturn = (ImageButton) findViewById(R.id.btnReturn);

		btnHome.setOnClickListener(bottomBarHandler);
		btnVolume.setOnClickListener(bottomBarHandler);
		btnFolder.setOnClickListener(bottomBarHandler);
		btnReturn.setOnClickListener(bottomBarHandler);

		// Set up media controls here

		// Set up navigation controls here
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			//Wifi In here!
		case R.id.action_wifi:
			
			serverListActivity = new Intent(getApplicationContext(), ServerListActivity.class);
			startActivity(serverListActivity);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * Handlers required for each section to listen for clicks
	 */
	public View.OnClickListener bottomBarHandler = new View.OnClickListener() {
		public void onClick(View v) {
			if (btnHome.getId() == ((ImageButton) v).getId()) {
				// Do Something
			} else if (btnVolume.getId() == ((ImageButton) v).getId()) {
				// Open Volume Slider
				if (libSlider.isOpened()) {
					libSlider.animateClose();
				}
				if (volSlider.isOpened()) {
					volSlider.animateClose();
				} else {
					volSlider.animateOpen();
				}

			} else if (btnFolder.getId() == ((ImageButton) v).getId()) {
				// Open Library Slider
				if (volSlider.isOpened()) {
					volSlider.animateClose();
				}
				if (libSlider.isOpened()) {
					libSlider.animateClose();
				} else {
					libSlider.animateOpen();
				}
			} else if (btnReturn.getId() == ((ImageButton) v).getId()) {
				// Do Something
			}
		}
	};
}
