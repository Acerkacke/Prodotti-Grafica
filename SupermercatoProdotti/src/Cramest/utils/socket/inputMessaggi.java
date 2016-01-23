package Cramest.utils.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.event.EventListenerList;

public class inputMessaggi {

	private DataInputStream din;
	private Socket s;
	protected EventListenerList listenerList = new EventListenerList();
	private int numeroListener;
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
					if (numeroListener >= 1) {
						try {
							lastMessage = din.readUTF();
							if (lastMessage.equals("chiudi")) {
								eAperto = false;
							}
							eArrivatoMessaggio(new MsgEvent(this));
						} catch (IOException e) {
							e.printStackTrace();
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

	public void addMsgListener(MsgListener msglisten) {
		listenerList.add(MsgListener.class, msglisten);
		numeroListener++;
	}

	private void eArrivatoMessaggio(MsgEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == MsgListener.class) {
				((MsgListener) listeners[i + 1]).MioEventoESuccesso(evt);
			}
		}
	}
}
