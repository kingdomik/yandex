package ru.yandex.pogoda.wi.lang;

public enum Language {
	
	RU,
	UK,
	BE;
	
	public static Language get(String name) {
		return valueOf(name.toUpperCase());
	}
	
}
