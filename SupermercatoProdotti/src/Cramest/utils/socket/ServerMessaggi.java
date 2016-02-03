package Cramest.utils.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Cramest.utils.GestioneTastiera;

public class ServerMessaggi {

	private static ServerSocket serverSocket;
	private static ArrayList<Socket> clientCollegati = new ArrayList<Socket>();
	private static ArrayList<inputMessaggi> ims = new ArrayList<inputMessaggi>();
	private static ArrayList<outputMessaggi> oms = new ArrayList<outputMessaggi>();
	private static Thread thClient;

	public static void main(String[] args) {
		CreaServer(7777);
		System.out.println("--SERVER--");
		thClient = new Thread() {
			public void run() {
				while (true) {
					try {
						System.out.println("Thread: In attesa di un client");
						Socket s = serverSocket.accept();
						System.out.println("Thread: Si e' aggiunto un client");
						aggiungiClient(s);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thClient.start();
		String sel = "";
		System.out.println("Sono in attesa di messaggi, 'chiudi' per uscire");

		while (!sel.equals("chiudi")) {
			sel = GestioneTastiera.leggiStringa();
			if (!sel.equals("chiudi")) {
				mandaMessaggio(sel);
			} else {
				chiudi();
			}
		}
		System.out.println("Arrivederci");
	}

	@SuppressWarnings("deprecation")
	private static void chiudi() {
		mandaMessaggio("chiudi");
		try {
			thClient.stop();
			if (!serverSocket.isClosed())
				serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void CreaServer(int porta) {
		if (!startServer(porta))
			System.err.println("Errore durante la creazione del Server");
	}

	private static boolean startServer(int porta) {
		try {
			serverSocket = new ServerSocket(porta);
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private static void aggiungiClient(Socket client) {
		clientCollegati.add(client);
		try {
			inputMessaggi newIM = new inputMessaggi(client);
			outputMessaggi newOM = new outputMessaggi(client);
			newIM.addMsgListener(new MsgListener() {
				@Override
				public void MioEventoESuccesso(MsgEvent mevt) {
					System.out.println(newIM.getMessaggio());
					mandaMessaggio(newIM.getMessaggio());
				}
			});
			newIM.addDisconnessoListener(new DisconnectedEventListener(){
				@Override
				public void ClientDisconnesso(DisconnectedEvent de){
					
				}
			});
			ims.add(newIM);
			oms.add(newOM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mandaMessaggio(String msg) {
		for (int i = 0; i < oms.size(); i++) {
			oms.get(i).mandaMessaggio(msg);
		}
	}

}
