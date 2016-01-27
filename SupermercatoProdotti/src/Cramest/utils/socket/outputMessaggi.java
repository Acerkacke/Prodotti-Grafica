package Cramest.utils.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class outputMessaggi {

	private static DataOutputStream dout;
	private Socket s;
	
	public outputMessaggi(String ipServer, int porta) throws Exception {
		s = new Socket(ipServer, porta);
		dout = new DataOutputStream(s.getOutputStream());
	}
	public outputMessaggi(Socket s) throws Exception {
		this.s = s;
		dout = new DataOutputStream(s.getOutputStream());
	}
	
	public void mandaMessaggio(String msg) {
		try {
			dout.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return s;
	}
	
	public static void mandaMessaggio(String msg,Socket sock){
		try {
			dout = new DataOutputStream(sock.getOutputStream());
			dout.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void mandaMessaggio(String msg,String ipServer, int porta){
		try {
			Socket sock = new Socket(ipServer, porta);
			dout = new DataOutputStream(sock.getOutputStream());
			dout.writeUTF(msg);
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
