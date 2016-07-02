package com.jmr.terraria.engine.utils;

import java.util.HashMap;

public class Settings {

	/** Holds all settings in a key-value pair hierarchy. */
	private static final HashMap<String, Object> settings = new HashMap<String, Object>();

	/**
	 * Adds a setting.
	 *
	 * @param id The setting ID
	 * @param o The value
	 */
	public static void addSetting(String id, Object o) {
		settings.put(id, o);
	}

	/**
	 * Gets a setting by it's ID.
	 *
	 * @param id The setting ID
	 * @return The value
	 */
	public static Object getSetting(String id) {
		return settings.get(id);
	}

	/**
	 * Set or add the value of a setting. It will add the setting if it doesn't exist.
	 * Otherwise it will set the existing setting's value.
	 *
	 * @param id The setting ID
	 * @param o The value
	 */
	public static void setSetting(String id, Object o) {
		if (settings.containsKey(id)) {
			settings.remove(id);
			settings.put(id, o);
		}
	}

	/**
	 * Static method to return the width because of it's common use.
	 *
	 * @return The width setting.
	 */
	public static int getWidth() {
		return (Integer)getSetting("width");
	}

	/**
	 * Static method to return the height because of it's common use.
	 *
	 * @return The height setting.
	 */
	public static int getHeight() {
		return (Integer)getSetting("height");
	}
	
}
