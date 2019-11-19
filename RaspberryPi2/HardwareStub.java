package model;

import java.io.InputStream;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class HardwareStub extends RaspberryPi2{
	
	public HardwareStub(int port) {
		super(port);
	}

	@Override
	public void sendNextChar(int buttonFlag) {
		
		if (buttonFlag == 1) {
			char c = this.currentChars[this.index];
			System.out.println(this.convertCharToBraille(c));
			index++;
		}
		else {
			System.out.println("Pause Button was pressed");
		}

	}
	
	public static void main(String[] args) {

		int[] input = new int[] {1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1};
		int inputIndex = 0;
		Scanner scanner = new Scanner(System.in);
		
		while (true) {

			// Create pi object with specific port
			HardwareStub pi = new HardwareStub(1002);

			// Receive the characters
			System.out.println("Receiving characters");
			String msg = pi.receiveChars();
			boolean check = pi.isProperPacket(msg);
			System.out.println(pi.getCharArray());

			
			if (check) {

				while (!pi.isLastChar()) {
					// Check for Arduino input (1 or 0)

					pi.sendNextChar(input[inputIndex]);
					inputIndex++;

				}

			}
			inputIndex = 0;

		}

	}
}