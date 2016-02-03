package Cramest.utils.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.event.EventListenerList;

public class inputMessaggi {

	private DataInputStream din;
	private Socket s;
	protected EventListenerList msglistenerList = new EventListenerList();
	protected EventListenerList dellistenerList = new EventListenerList();
	private int msgnumeroListener;
	private int delnumeroListener;
	private String lastMessage = "";
	private boolean eAperto;

	public inputMessaggi(String ipServer, int porta) throws Exception {
		s = new Socket(ipServer, porta);
		main();
	}

	public inputMessaggi(Socket s) throws Exception {
		this.s = s;
		main();
	}

	public void main() {
		Thread td = new Thread() {

			public void run() {
				try {
					din = new DataInputStream(s.getInputStream());
					eAperto = true;
				} catch (IOException e2) {
					eAperto = false;
				}
				while (eAperto) {
					if (msgnumeroListener >= 1) {
						try {
							lastMessage = din.readUTF();
							if (lastMessage.equals("chiudi")) {
								eAperto = false;
							}
							eArrivatoMessaggio(new MsgEvent(this));
						} catch (IOException e) {
							e.printStackTrace(); //IL CLIENT SI E' SCOLLEGATO
							siEDisconnessoQualcuno(new DisconnectedEvent(this));
						}
					}
				}
				System.out.println("Thread: Chiudo il thread");
			}

		};
		td.start();
	}

	public Socket getSocket() {
		return s;
	}

	public String getMessaggio() {
		return lastMessage;
	}

	public void chiudi() {
		try {
			if (eAperto) {
				eAperto = false;
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addDisconnessoListener(DisconnectedEventListener del){
		dellistenerList.add(DisconnectedEventListener.class, del);
		msgnumeroListener++;
	}
	
	public void siEDisconnessoQualcuno(DisconnectedEvent de){
		Object[] listeners = dellistenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == MsgListener.class) {
				((DisconnectedEventListener) listeners[i + 1]).ClientDisconnesso(de);
			}
		}
	}

	public void addMsgListener(MsgListener msglisten) {
		msglistenerList.add(MsgListener.class, msglisten);
		msgnumeroListener++;
	}

	private void eArrivatoMessaggio(MsgEvent evt) {
		Object[] listeners = msglistenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == MsgListener.class) {
				((MsgListener) listeners[i + 1]).MioEventoESuccesso(evt);
			}
		}
	}
}
