
import java.io.*;
import java.net.*;


public class MyServer {
	
	public static void main(String[] args) throws Exception {
		ServerSocket ss=new ServerSocket(1234);
		System.out.println("Waiting connection");
		String driverName = "com.mysql.jdbc.Driver"; // �������ݿ�������
		String url = "jdbc:mysql://localhost:3306/data"; // �������ݿ��URL
		String user = "root"; // ���ݿ��û�
		String password = "hu15927548577";// ���ݿ�����
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
