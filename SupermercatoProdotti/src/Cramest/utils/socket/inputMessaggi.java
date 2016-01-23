package Cramest.utils.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

public class inputMessaggi {

	private DataInputStream din;
	private Socket s1;
	protected EventListenerList listenerList = new EventListenerList();

	@SuppressWarnings("unused")
	private String lastMessage = "";

	public inputMessaggi(String ipServer, int porta) {
		
		try {
			
			s1 = new Socket(ipServer, porta);
			din = new DataInputStream(s1.getInputStream());
			
			Thread td = new Thread() {
				
				public void run() {
					while (!s1.isClosed()) {
						try {
							String msgIn = din.readUTF();
							lastMessage = msgIn;
							eArrivatoMessaggio(new MsgEvent(this));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			};
			s1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getMessaggio(){
		return lastMessage;
	}

	public void chiudi() {
		try {
			if (!s1.isClosed()) {
				s1.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addMsgListener(MsgListener msglisten) {
		listenerList.add(MsgListener.class, msglisten);
	}

	void eArrivatoMessaggio(MsgEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == MsgListener.class) {
				((MsgListener) listeners[i + 1]).MioEventoESuccesso(evt);
			}
		}
	}
}

@SuppressWarnings("serial")
class MsgEvent extends EventObject {
	public MsgEvent(Object source) {
		super(source);
	}
}

interface MsgListener extends EventListener {
	public void MioEventoESuccesso(MsgEvent mevt);
}
