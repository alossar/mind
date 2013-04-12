package co.mind.management.maestro.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class Notification extends PopupPanel {

	public Notification(String text) {
		super(false, false);
		setWidget(new Label(text));
	}

	@Override
	public void show() {
		installCloseHandler();
		super.show();
	}

	public native void installCloseHandler() /*-{
		var tmp = this;
		$wnd.onmousemove = function() {
			// edit the com.google.gwt.sample.contacts.client package 
			// to match your own package name
			tmp.@co.mind.management.maestro.client.Notification::hide()();
			$wnd.onmousemove = null;
		}
	}-*/;
}