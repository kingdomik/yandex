package ru.yandex.pogoda.wi;


public enum Messages{
	SEARCH("�����"),
	LOGIN("�����");
	
	private String value;
	
	Messages(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}