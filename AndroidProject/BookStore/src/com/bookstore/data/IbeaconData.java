package com.bookstore.data;

import android.bluetooth.BluetoothDevice;

public class IbeaconData {

	public static class Ibeacon {
		public String name;
		public String bluetoothAddress;
		public String uuid;
		public int major;
		public int minor;
		public int txPower;
		public int rssi;
		public Boolean isAvailable;
	}

	public static Ibeacon getIbeaconData(BluetoothDevice device, int rssi,
			byte[] scanData) {

		int startByte = 5;
		Ibeacon ibeacon = new Ibeacon();
		Boolean flag = false;
		if (((int) scanData[startByte + 2] & 0xff) == 0x02
				&& ((int) scanData[startByte + 3] & 0xff) == 0x15) {
			flag = true;
		}
		if (!flag) {
			ibeacon.isAvailable = false;
		} else {
			ibeacon.isAvailable = true;
			ibeacon.major = (scanData[startByte + 20] & 0xff) * 0x100
					+ (scanData[startByte + 21] & 0xff);
			ibeacon.minor = (scanData[startByte + 22] & 0xff) * 0x100
					+ (scanData[startByte + 23] & 0xff);
			ibeacon.txPower = (int) scanData[startByte + 24];
			ibeacon.rssi = rssi;
			ibeacon.name = device.getName();
			ibeacon.bluetoothAddress = device.getAddress();
			byte[] proximityUuidBytes = new byte[16];
			System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16);
			String hexString = bytesToHexString(proximityUuidBytes);
			StringBuilder sb = new StringBuilder();
			sb.append(hexString.substring(0, 8));
			sb.append("-");
			sb.append(hexString.substring(8, 12));
			sb.append("-");
			sb.append(hexString.substring(12, 16));
			sb.append("-");
			sb.append(hexString.substring(16, 20));
			sb.append("-");
			sb.append(hexString.substring(20, 32));
			ibeacon.uuid = sb.toString();
		}
		return ibeacon;
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

}
