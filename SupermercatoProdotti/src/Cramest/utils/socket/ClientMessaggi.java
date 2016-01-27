package Cramest.utils.socket;

import java.net.Socket;

import Cramest.utils.GestioneTastiera;

public class ClientMessaggi {

	private static Socket sock;
	private static inputMessaggi im;
	private static outputMessaggi om;
	
	public static void main(String[] args) {
		System.out.println("--CLIENT--");
		try {
			try {
				sock = new Socket("127.0.0.1",7777);
				System.out.println("--CONNESSO--");
				im = new inputMessaggi(sock);
				im.addMsgListener(new MsgListener() {
					@Override
					public void MioEventoESuccesso(MsgEvent mevt) {
						System.out.println(im.getMessaggio());
					}
				});
				om = new outputMessaggi(sock);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String sel = "";
			System.out.println("Sono in attesa di messaggi, 'chiudi' per uscire");
			
			while(!sel.equals("chiudi")){
				sel = GestioneTastiera.leggiStringa();
				if(!sel.equals("chiudi")){
					om.mandaMessaggio(sel);
				}else{
					chiudi();
				}
			}
		} catch (Exception e) {
			System.out.println("ERRORE DI CONNESSIONE, PROBABILMENTE NESSUN SERVER TROVATO");
		}
	}
	

	public static void chiudi() {
		im.chiudi();
	}
}
