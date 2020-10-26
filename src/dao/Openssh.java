package dao;
 
 import com.jcraft.jsch.JSch;
 import com.jcraft.jsch.Session;

public class Openssh {

	private String user = "hsin";//SSH連線使用者名稱
	private String host = "35.236.162.123";//SSH伺服器
	private int port = 22;//SSH訪問埠
	private String privateKey = "~/.ssh/id_rsa";

	private int lport = 1433;//本地埠
	private String rhost = "localhost";//遠端MySQL伺服器
	private int rport = 1433;//遠端MySQL服務埠
	private Session session;
	
	public void loadSSH() {
		try {	
			JSch jsch = new JSch();
			jsch.addIdentity(privateKey);
			session = jsch.getSession(user, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			//System.out.println(session.getServerVersion());//這裡列印SSH伺服器版本資訊
			//int assinged_port = 
			session.setPortForwardingL(lport, rhost, rport);
			//System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);//
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeSSH() {
		session.disconnect();
		//System.out.println("成功關閉");
	}

}
