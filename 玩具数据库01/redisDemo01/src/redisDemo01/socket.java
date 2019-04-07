package redisDemo01;

import java.io.*;
import java.net.*;

public class socket implements Runnable {
	String cmd;// ����
	redis r0 = new redis();// ���ݿ�
	String returnText = "success";// ���ͻ�������Ϣ

	public void run() {
		try {
			r0.read();
			ServerSocket serverSocket = new ServerSocket(12016);
			do {
				Socket socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				byte[] cache = new byte[1024];

				is.read(cache);
				cmd = new String(cache);
				cmd = cmd.trim();// ȥ������ո����bug���˰���
				System.out.println("redis  >  " + cmd);

				returnText = r0.command(cmd);//�������ݿ�
				os.write(returnText.getBytes());// ���ͻ���ӡ������

				os.flush();
				is.close();
				os.close();
			} while (!returnText.equals("end"));
			// serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
