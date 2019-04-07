package Demo;
import java.io.*;
import java.net.*;
import java.util.*;
public class ClientThread implements Runnable {
	@Override
	public void run() {
		try {
			while(true) {
				Scanner s=new Scanner(System.in);
				String temp = s.nextLine();
				Socket socket = new Socket("127.0.0.1", 12016);
				//获取输入输出流
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				//发送
				os.write(temp.getBytes());
				os.flush();
				//接收
				byte[] cache=new byte[1024];
				is.read(cache);
				String a=new String(cache);
				if(temp.equals("show")) {
					a=a.trim();
				String[] c=a.split(" ");
				for(int i=0;i<c.length;i++) {
					c[i]=c[i].replaceAll(" ", "");
				}
				for(int i=0;i<c.length-1;i+=2)
					System.out.printf("key:%10s  value:%10s\n",c[i],c[i+1]);
				}
				else System.out.println(a);
				os.close();
				socket.close();
				if(temp.equals("end"))
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
