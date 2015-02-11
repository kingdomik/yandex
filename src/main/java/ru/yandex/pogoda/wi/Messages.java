package ru.yandex.pogoda.wi;


public enum Messages{
	SEARCH("Найти"),
	LOGIN("Войти");
	
	private String value;
	
	Messages(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}