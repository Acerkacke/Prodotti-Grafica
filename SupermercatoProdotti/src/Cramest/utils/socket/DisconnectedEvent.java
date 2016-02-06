package Cramest.utils.socket;

import java.util.EventObject;

@SuppressWarnings("serial")
public class DisconnectedEvent extends EventObject {
	public DisconnectedEvent(Object source) {
		super(source);
	}
}
