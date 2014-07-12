package com.github.ZXSkelobrine.stock.shop.listeners;

import com.github.ZXSkelobrine.stock.shop.misc.PriceObject;
import com.github.ZXSkelobrine.stock.shop.windows.ShopWindow;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialListener {

	private static SerialPort serialPort;
	private static boolean open = false;

	public static void prepareConection(int port) {
		serialPort = new SerialPort("COM" + port);
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR;
			serialPort.setEventsMask(mask);
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	public static void startListener() throws SerialPortException {
		if (!open) {
			serialPort.addEventListener(new SerialPortReader());
			open = true;
		}
	}

	static class SerialPortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {
				if (event.getEventValue() > 0) {
					try {
						byte buffer[] = serialPort.readBytes();
						String product = new String(buffer).replace("\n", "");
						String[] parts = product.split("::");
						if (parts.length == 2) {
							ShopWindow.addBoughtItem(new PriceObject(parts[0], parts[1], ShopWindow.LIST_SIZE, ShopWindow.LIST_FONT));
						}else{
							System.out.println(product);
						}
					} catch (SerialPortException ex) {
						System.out.println(ex);
					}
				}
			}
		}
	}

}
