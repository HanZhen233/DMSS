
import java.io.*;
import java.net.*;


public class MyServer {
	
	public static void main(String[] args) throws Exception {
		ServerSocket ss=new ServerSocket(1234);
		System.out.println("Waiting connection");
		String driverName = "com.mysql.jdbc.Driver"; // 加载数据库驱动类
		String url = "jdbc:mysql://localhost:3306/data"; // 声明数据库的URL
		String user = "root"; // 数据库用户
		String password = "hu15927548577";// 数据库密码
		DataProcessing.connectToDatabase(driverName, url, user, password);
		int i=0;
		while(true) {	
			Socket socket=ss.accept();
	        i++;
			System.out.println("Accepting user"+i+" "+ "Connection....");
			new ServerThread(socket).start();
			
			}

		
		
		
		
		
		
		
	     
		
	}
	
	

}
