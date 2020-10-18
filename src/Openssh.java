 
 import com.jcraft.jsch.JSch;
 import com.jcraft.jsch.Session;

public class Openssh {

	public static void main(String[] args) {
		
		String user = "hsin";//SSH連線使用者名稱
		String host = "35.236.162.123";//SSH伺服器
		int port = 22;//SSH訪問埠
		String privateKey = "~/.ssh/id_rsa";
	
		int lport = 1433;//本地埠
		String rhost = "localhost";//遠端MySQL伺服器
		int rport = 1433;//遠端MySQL服務埠
		
		
		try {
			
			JSch jsch = new JSch();
			jsch.addIdentity(privateKey);
			Session session = jsch.getSession(user, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			System.out.println(session.getServerVersion());//這裡列印SSH伺服器版本資訊
			int assinged_port = session.setPortForwardingL(lport, rhost, rport);
			System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
