/*
 *  Copyright (C) 2008-2013 Team XBMC
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.xbmc.eventclient;

import java.io.Serializable;

/**
 * XBMC Event Client Class
 * 
 * A PING packet tells XBMC that the client is still alive. All valid
 * packets act as ping (not just this one). A client needs to ping
 * XBMC at least once in 60 seconds or it will time
 * @author Stefan Agner
 *
 */
public class PacketPING extends Packet implements Serializable {
	/**
	 * A PING packet tells XBMC that the client is still alive.
	 */
	public PacketPING()
	{
		super(PT_PING);
	}
}
