package Demo;
import java.io.*;
import java.net.*;
public class ClientTest {
	public static void main(String[] args) {
		new Thread(new ClientThread()).start();
	}
}
