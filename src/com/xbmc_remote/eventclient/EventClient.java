package com.xbmc_remote.eventclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xbmc.eventclient.PacketBYE;
import org.xbmc.eventclient.PacketHELO;
import org.xbmc.eventclient.XBMCClient;

import android.os.AsyncTask;

public class EventClient {
	// var decs
	XBMCClient eventClient;
	String inputAddress = "";
	int hostPort = 9777;
	String deviceName = "Android Device";
	PacketHELO helo;
	InetAddress hostAddress = null;
	static boolean connected;

	public EventClient(String inputAddress) {
		this.inputAddress = inputAddress;
		this.helo = new PacketHELO(this.deviceName);
	}

	public EventClient(String inputAddress, int hostPort) {
		this.inputAddress = inputAddress;
		this.hostPort = hostPort;
		this.helo = new PacketHELO(this.deviceName);
	}

	public EventClient(String inputAddress, int hostPort, String deviceName) {
		this.inputAddress = inputAddress;
		this.hostPort = hostPort;
		this.deviceName = deviceName;
		this.helo = new PacketHELO(deviceName);
	}

	public EventClient(String inputAddress, String deviceName) {
		this.inputAddress = inputAddress;
		this.deviceName = deviceName;
		this.helo = new PacketHELO(deviceName);
	}

	/*
	 * Dan and Kev:
	 * 
	 * Firstly will try to evaluate the host address input. Success leads to
	 * subsequent connection attempt. A successful connection will send a HELO
	 * packet to the XBMC client, letting it know which device has connected.
	 */
	public String connectToXBMC() throws IOException {
		boolean hostnameCorrect = true;

		try {
			hostAddress = InetAddress.getByName(this.inputAddress);
		} catch (UnknownHostException e) {
			// should never be reached due to regex but you never know
			hostnameCorrect = false;
			return "Invalid IP address entered.";
		}

		if (hostnameCorrect == true && inputAddress != null) {
			try {
				eventClient = new XBMCClient(hostAddress, hostPort, deviceName);
				connected = true;
			} catch (IOException e) {
				connected = false;
			}

			if (connected) {
				try {
					helo.send(hostAddress, hostPort);
					eventClient.sendNotification(deviceName, "has connected.");
				} catch (IOException e) {
					return "Failed to send HELO."
							+ "\nCheck firewall and try again.";
				}
			} else if (!connected) {
				return "Failed to connect."
						+ "\nCheck your settings and try again.";
			}
		}

		return "Connecting...";
	}

	public void disconnectFromXBMC() {
		try {
			eventClient.sendNotification("Disconnected", deviceName
					+ " has disconnected.");
			eventClient.stopClient();
		} catch (IOException e) {
			
		} catch (NullPointerException ne) {
			
		}
	}
}
