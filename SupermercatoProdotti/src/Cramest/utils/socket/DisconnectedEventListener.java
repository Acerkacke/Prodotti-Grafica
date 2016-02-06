package Cramest.utils.socket;

import java.util.EventListener;

public interface DisconnectedEventListener extends EventListener {
	public void ClientDisconnesso(DisconnectedEvent client);
}
