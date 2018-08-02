import java.io.*;
import java.net.*;
import java.sql.*;

public class ServerThread extends Thread {
	private Socket s;
    private String uppath = "E:/uploadpath/";
    private FileInputStream fis;
    private DataOutputStream dos;
    private ObjectInputStream objin;

	public ServerThread(Socket s) {
		this.s = s;
		
		
	}
	public void downloadfun(int id) throws Exception {
		
	 Doc doc=DataProcessing.searchDoc(id);
      File file =new File(uppath+doc.getFilename());
      fis =new FileInputStream(file);
      dos =new DataOutputStream(s.getOutputStream());
      dos.writeUTF(file.getName());
   
      dos.flush();
      dos.writeLong(file.length());
      dos.flush();
      byte[] sendBytes =new byte[1024];
      int length =0;
      try {
			while((length = fis.read(sendBytes)) !=-1){
			    dos.write(sendBytes,0, length);
			    dos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      finally{
          if(fis !=null)
              fis.close();
          if(dos !=null)
              dos.close();
          
      }
	}
	
	public void uploadfun(String fileName,String desciption,Timestamp time, long filelength) throws Exception {
		DataInputStream	 in =new DataInputStream(s.getInputStream());
			String ID=null;
		
		   FileOutputStream   fos =new FileOutputStream(new File(uppath +fileName));
		   byte[] sendBytes =new byte[1024];
		   int transLen =0;
		   System.out.println("----开始接收文件<" + fileName +">,文件大小为<" + filelength +">----");
		   while(true){
		       int read =0;
		       read = in.read(sendBytes);
		       if(read <=0)
		           break;
		       transLen += read;
		       System.out.println("正在上传，请稍候....");
		       fos.write(sendBytes,0, read);
		       fos.flush();
		   }
		   System.out.println("----接收文件<" +fileName +">成功-------");
		   if(in !=null)
		      in.close();
		   if(fos !=null)
		       fos.close();
		   
		   DataProcessing.insertDoc(ID, "jack", time,  desciption,fileName);

	
	
	
	
	}
	
	
	public void run() {
	
		while(true) {
			
			
				try {
				
					objin=new ObjectInputStream(s.getInputStream());
					NetTransfer ntin=(NetTransfer) objin.readObject();
					switch(ntin.action) {
					case "download":
					downloadfun(ntin.id);
					 
					break;	
					case "upload":
					 uploadfun(ntin.fileName,ntin.description,ntin.time,ntin.filelength);
						break;
					
					}	
					if(ntin.action.equals("shutdown"))
					{  
						break;
					}		
					
					
				
				} catch (Exception  e) {
					try {
						s.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				
				}
				
				}
		
			try {
				System.out.println("本次连接已关闭");
				s.close();
			} catch (IOException e1) {
			
				e1.printStackTrace();
			}
		}
		

	
		
	
	
	
	
	
	
	
	
	
}
		
		


