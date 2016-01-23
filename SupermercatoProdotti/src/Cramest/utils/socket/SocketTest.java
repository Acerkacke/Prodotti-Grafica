package Cramest.utils.socket;
import Cramest.utils.GestioneTastiera;
public class SocketTest {

	public static void main(String[] args) {
		inputMessaggi im = new inputMessaggi("127.0.0.1",7777);
		String sel = "";
		im.addMsgListener(new MsgListener(){
			@Override
			public void MioEventoESuccesso(MsgEvent mevt) {
				System.out.println(im.getMessaggio());
			}
		});
		while(!sel.equals("esci")){
			sel = GestioneTastiera.leggiStringa();
			if(sel == "esci"){
				im.chiudi();
			}
		}
		System.out.println("ciao ciao");
	}

}
