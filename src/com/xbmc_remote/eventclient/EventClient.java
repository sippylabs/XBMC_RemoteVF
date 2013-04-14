package com.xbmc_remote.eventclient;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xbmc.eventclient.PacketHELO;
import org.xbmc.eventclient.XBMCClient;

public class EventClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
				new Thread(new Runnable() {
					public void run() {
						try {
							helo.send(hostAddress, hostPort);
							eventClient.sendNotification(deviceName,
									"has connected.");
						} catch (IOException e) {

						}
					}
				}).start();
			} else if (!connected) {
				return "Failed to connect."
						+ "\nCheck your settings and try again.";
			}
		}

		return "Connecting...";
	}

	public void disconnectFromXBMC() {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendNotification("Disconnected", deviceName
							+ " has disconnected.");
					eventClient.stopClient();

				} catch (IOException e) {

				} catch (NullPointerException ne) {

				}
			}
		}).start();
	}

	public void sendSelect() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "enter", false, true, false,
							(short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void sendReturn() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "backspace", false, true,
							false, (short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goUp() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "up", false, true, false,
							(short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goDown() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "down", false, true, false,
							(short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goLeft() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "left", false, true, false,
							(short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goRight() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "right", false, true, false,
							(short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goToMusic() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("XBMC.ActivateWindow(MyMusic)");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goToVideo() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("XBMC.ActivateWindow(MyVideos)");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goToTv() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("Right");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goToPictures() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("XBMC.ActivateWindow(MyPictures)");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void goToHome() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("XBMC.ActivateWindow(Home)");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void playPause() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "play_pause", false, true,
							false, (short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void searchForward() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("AnalogFastForward");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void searchBack() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendAction("AnalogRewind");
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void skipForward() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "next_track", false, true,
							false, (short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public void skipBack() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.sendButton("KB", "prev_track", false, true,
							false, (short) 0, (byte) 0);
				} catch (IOException e) {

				}
			}
		}).start();
	}

	public Boolean isConnected() throws NullPointerException {
		new Thread(new Runnable() {
			public void run() {
				try {
					eventClient.ping();
					connected = true;
				} catch (NullPointerException e) {
					connected = false;
				} catch (IOException e) {
					connected = false;
				}
			}
		}).start();
		return connected;
	}
}
