package com.xbmc_remote.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.xbmc_remote.R;
import com.xbmc_remote.eventclient.EventClient;
import com.xbmc_remote.eventclient.IPAddressValidator;

public class ServerListActivity extends Activity {

	EventClient eventClient;
	String xbmcHostAddress;
	int xbmcHostPort = 9777;
	String deviceName = "Android Device";
	IPAddressValidator IPVal = new IPAddressValidator();
	String connectionInfo;
	ToggleButton tbConnectorButton;
	final static String prefsFile = "xbmc_remotePreferences";
	EditText clientHostEditText;
	EditText clientPortEditText;
	EditText deviceNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_list);
		tbConnectorButton = (ToggleButton) findViewById(R.id.connectorButton);

		clientHostEditText = (EditText) findViewById(R.id.xbmcClientAddress);
		clientPortEditText = (EditText) findViewById(R.id.xbmcClientPort);
		deviceNameEditText = (EditText) findViewById(R.id.deviceName);

		SharedPreferences settings = getSharedPreferences(prefsFile, 0);
		xbmcHostAddress = settings.getString("xbmcHostAddress", "");
		xbmcHostPort = settings.getInt("xbmcHostPort", 9777);
		deviceName = settings.getString("deviceName", "Android Device");

		tbConnectorButton.setChecked(settings.getBoolean(
				"tbConnectorButtonIsChecked", false));
		clientHostEditText.setText(xbmcHostAddress);
		clientPortEditText.setText(String.valueOf(xbmcHostPort));
		deviceNameEditText.setText(deviceName);

		if (xbmcHostAddress != null || xbmcHostAddress != "") {
			if (IPVal.validate(xbmcHostAddress)) {
				eventClient = new EventClient(xbmcHostAddress);

				if (xbmcHostPort != 9777) {
					eventClient = new EventClient(xbmcHostAddress, xbmcHostPort);

					if (!("Android Device".equals(deviceName))) {
						eventClient = new EventClient(xbmcHostAddress,
								xbmcHostPort, deviceName);
					}
				}

				if (!("Android Device".equals(deviceName))) {
					eventClient = new EventClient(xbmcHostAddress, deviceName);
				}
			}
		}

		// set up event listener for toggle button

		tbConnectorButton
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							// The toggle is enabled
							xbmcHostAddress = clientHostEditText.getText()
									.toString();
							xbmcHostPort = Integer.parseInt(clientPortEditText
									.getText().toString());
							deviceName = deviceNameEditText.getText()
									.toString();

							if (("").equals(xbmcHostAddress)
									|| !IPVal.validate(xbmcHostAddress)) {
								Toast.makeText(ServerListActivity.this,
										"Enter a valid host address.",
										Toast.LENGTH_SHORT).show();
								tbConnectorButton.setChecked(false);
							} else if (IPVal.validate(xbmcHostAddress)) {
								eventClient = new EventClient(xbmcHostAddress);

								if (xbmcHostPort != 9777) {
									eventClient = new EventClient(
											xbmcHostAddress, xbmcHostPort);

									if (!("Android Device".equals(deviceName))) {
										eventClient = new EventClient(
												xbmcHostAddress, xbmcHostPort,
												deviceName);
									}
								}

								if (!("Android Device".equals(deviceName))) {
									eventClient = new EventClient(
											xbmcHostAddress, deviceName);
								}

								new Thread(new Runnable() {
									public void run() {
										try {
											connectionInfo = eventClient
													.connectToXBMC();
										} catch (IOException e) {
											connectionInfo = "Connection failed. Check your firewall.";
										} catch (RuntimeException re) {
											connectionInfo = "Thread exception.";
										}
									}
								}).start();

								Toast.makeText(ServerListActivity.this,
										connectionInfo, Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							// The toggle is disabled
							new Thread(new Runnable() {
								public void run() {
									try {
										eventClient.disconnectFromXBMC();
									} catch (NullPointerException e) {
										e.printStackTrace();
									}
								}
							}).start();
							Toast.makeText(ServerListActivity.this,
									"Disconnected.", Toast.LENGTH_SHORT).show();
						}
					}
				});
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
		// Wifi In here!
		case R.id.action_wifi:
			finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		SharedPreferences settings = getSharedPreferences(prefsFile, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("tbConnectorButtonIsChecked",
				tbConnectorButton.isChecked());
		editor.putString("xbmcHostAddress", xbmcHostAddress);
		editor.putInt("xbmcHostPort", xbmcHostPort);
		editor.putString("deviceName", deviceName);

		editor.apply();
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences(prefsFile, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("tbConnectorButtonIsChecked",
				tbConnectorButton.isChecked());
		editor.putString("xbmcHostAddress", xbmcHostAddress);
		editor.putInt("xbmcHostPort", xbmcHostPort);
		editor.putString("deviceName", deviceName);

		editor.apply();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences settings = getSharedPreferences(prefsFile, 0);
		clientHostEditText.setText(settings.getString("xbmcHostAddress", ""));
		clientPortEditText.setText(String.valueOf(settings.getInt(
				"xbmcHostPort", 9777)));
		deviceNameEditText.setText(settings.getString("deviceName",
				"Android Device"));
		tbConnectorButton.setChecked(settings.getBoolean(
				"tbConnectorButtonIsChecked", false));
	}

}