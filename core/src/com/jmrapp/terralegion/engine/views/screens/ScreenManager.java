package com.jmrapp.terralegion.engine.views.screens;


public class ScreenManager {

	private static Screen current, last;
	
	public static void setScreen(Screen s) {
		if (current != null) {
			current.dispose();
		}
		last = current;
		current = s;
		current.create();
	}
	
	public static void goBack() {
		if (current != null) {
			current.dispose();
		}
		Screen temp = last;
		last = current;
		current = temp;
		current.resume();
	}
	
	public static Screen getCurrent() {
		return current;
	}
	
}
