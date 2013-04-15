package com.xbmc_remote.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.example.xbmc_remote.R;
import com.xbmc_remote.eventclient.EventClient;

public class MainActivity extends Activity {
	SlidingDrawer volSlider;
	SlidingDrawer libSlider;
	String prefsFile = "xbmc_remotePreferences";
	ImageButton btnHome, btnVolume, btnFolder, btnReturn, select, btnRight,
			btnMusic, btnVideo, btnTv, btnPictures, btnLeft, btnDown, btnUp,
			btnPlayPause, btnSearchForward, btnSearchBack, btnSkipForward,
			btnSkipBack;
	EventClient eventClient;
	Intent mainIntent;

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

		mainIntent = getIntent();
		eventClient = (EventClient) mainIntent
				.getSerializableExtra("eventClient");

		try {
			eventClient.isConnected();
		} catch (NullPointerException e) {
			SharedPreferences prefs = getSharedPreferences(prefsFile, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("tbConnectorButtonIsChecked", false);
			editor.apply();
		}
	}

	void setUpButtons() {
		// References
		btnHome = (ImageButton) findViewById(R.id.btnHome);
		btnVolume = (ImageButton) findViewById(R.id.btnSound);
		btnFolder = (ImageButton) findViewById(R.id.btnLib);
		btnReturn = (ImageButton) findViewById(R.id.btnReturn);
		select = (ImageButton) findViewById(R.id.select);
		btnUp = (ImageButton) findViewById(R.id.btnUp);
		btnDown = (ImageButton) findViewById(R.id.btnDown);
		btnLeft = (ImageButton) findViewById(R.id.btnLeft);
		btnRight = (ImageButton) findViewById(R.id.btnRight);
		btnMusic = (ImageButton) findViewById(R.id.btnMusic);
		btnVideo = (ImageButton) findViewById(R.id.btnVideo);
		btnTv = (ImageButton) findViewById(R.id.btnTv);
		btnPictures = (ImageButton) findViewById(R.id.btnPictures);
		btnPlayPause = (ImageButton) findViewById(R.id.playpause);
		btnSearchForward = (ImageButton) findViewById(R.id.searchForward);
		btnSearchBack = (ImageButton) findViewById(R.id.searchBack);
		btnSkipForward = (ImageButton) findViewById(R.id.skipForward);
		btnSkipBack = (ImageButton) findViewById(R.id.skipBack);

		// Bottom Bar controls
		btnHome.setOnClickListener(bottomBarHandler);
		btnVolume.setOnClickListener(bottomBarHandler);
		btnFolder.setOnClickListener(bottomBarHandler);
		btnReturn.setOnClickListener(bottomBarHandler);

		// Media controls
		btnPlayPause.setOnClickListener(mediaHandler);
		btnSearchForward.setOnClickListener(mediaHandler);
		btnSearchBack.setOnClickListener(mediaHandler);
		btnSkipForward.setOnClickListener(mediaHandler);
		btnSkipBack.setOnClickListener(mediaHandler);

		// Navigation controls
		select.setOnClickListener(navigationHandler);
		btnUp.setOnClickListener(navigationHandler);
		btnDown.setOnClickListener(navigationHandler);
		btnLeft.setOnClickListener(navigationHandler);
		btnRight.setOnClickListener(navigationHandler);
		btnMusic.setOnClickListener(navigationHandler);
		btnVideo.setOnClickListener(navigationHandler);
		btnTv.setOnClickListener(navigationHandler);
		btnPictures.setOnClickListener(navigationHandler);
	}

	/*
	 * Handlers required for each section to listen for clicks
	 */
	public View.OnClickListener bottomBarHandler = new View.OnClickListener() {
		public void onClick(View v) {
			try {
				if (btnHome.getId() == ((ImageButton) v).getId()) {
					eventClient.goToHome();
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
					eventClient.sendReturn();
				}
			} catch (NullPointerException e) {
				Toast.makeText(MainActivity.this, "Device not connected.",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	public View.OnClickListener mediaHandler = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				if (btnPlayPause.getId() == ((ImageButton) v).getId()) {
					eventClient.playPause();
				}

				if (btnSearchForward.getId() == ((ImageButton) v).getId()) {
					eventClient.searchForward();
				}

				if (btnSearchBack.getId() == ((ImageButton) v).getId()) {
					eventClient.searchBack();
				}

				if (btnSkipForward.getId() == ((ImageButton) v).getId()) {
					eventClient.skipForward();
				}

				if (btnSkipBack.getId() == ((ImageButton) v).getId()) {
					eventClient.skipBack();
				}
			} catch (NullPointerException e) {
				Toast.makeText(MainActivity.this, "Device not connected.",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	public View.OnClickListener navigationHandler = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				if (select.getId() == ((ImageButton) v).getId()) {
					eventClient.sendSelect();
				}

				if (btnUp.getId() == ((ImageButton) v).getId()) {
					eventClient.goUp();
				}

				if (btnDown.getId() == ((ImageButton) v).getId()) {
					eventClient.goDown();
				}

				if (btnLeft.getId() == ((ImageButton) v).getId()) {
					eventClient.goLeft();
				}

				if (btnRight.getId() == ((ImageButton) v).getId()) {
					eventClient.goRight();
				}

				if (btnMusic.getId() == ((ImageButton) v).getId()) {
					eventClient.goToMusic();
				}

				if (btnVideo.getId() == ((ImageButton) v).getId()) {
					eventClient.goToVideo();
				}

				if (btnTv.getId() == ((ImageButton) v).getId()) {
					eventClient.goToTv();
				}

				if (btnPictures.getId() == ((ImageButton) v).getId()) {
					eventClient.goToPictures();
				}

			} catch (NullPointerException e) {
				Toast.makeText(MainActivity.this, "Device not connected.",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Wifi In here!
		case R.id.action_wifi:

			mainIntent = new Intent(this,
					ServerListActivity.class);
			mainIntent.putExtra("eventClient", eventClient);
			startActivity(mainIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

}
