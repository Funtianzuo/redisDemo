package redisDemo01;

import java.io.*;
import java.net.*;

public class socket implements Runnable {
	String cmd;// 命令
	redis r0 = new redis();// 数据库
	String returnText = "success";// 给客户看的信息

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
				cmd = cmd.trim();// 去掉多余空格，这个bug调了半天
				System.out.println("redis  >  " + cmd);

				returnText = r0.command(cmd);//操作数据库
				os.write(returnText.getBytes());// 给客户打印的数据

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
