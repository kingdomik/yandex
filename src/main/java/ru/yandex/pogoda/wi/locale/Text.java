package ru.yandex.pogoda.wi.locale;

import ru.yandex.pogoda.common.Utils;

/**
 * Text used by tests
 * 
 * Each enum value must have corresponding entries localize property files  
 *
 */
public enum Text {

	TEMPERATURE,
	HUMIDITY,
	OBSERVATION_TIME,
	
	JANUARY,
	FEBRUARY,
	MARCH,
	APRIL,
	MAY,
	JUNE,
	JULY,
	AUGUST,
	SEPTEMBER,
	OCTOBER,
	NOVEMBER,
	DECEMBER,
	
	MALE,
	FEMALE,
	
	LABEL_USERNAME,
	LABEL_PASSWORD,
	
	BTN_LOGIN,
	BTN_SAVE,
	BTN_CANCEL,
	BTN_CLOSE,
	
	MENU_ABOUT,
	
	LINK_EDIT_PROFILE,
	
	TITLE_CHANGE_PERSONAL_INFO,
	
	FIELD_NAME,
	FIELD_SURNAME,
	FIELD_BIRTH_DATE,
	
	FIELD_CITY,
	FIELD_BIRTH_CITY,
	
	MSG_PERSONAL_INFO_CHANGED,
	
	ERR_SPECIFY_NAME,
	ERR_SPECIFY_SURNAME,
	ERR_USE_ALPHA_ONLY,
	ERR_SPECIFY_CITY,
	ERR_CHOOSE_CITY_FROM_LIST	
	;
	
	public String getValue(Object... params) {
		return Utils.format(LocaleManager.getCurrent().getProperty(name()), params);
	}
	
	
	public String getName() {
		// Use one of available locales comfortable for QA team as property names 
		return LocaleManager.getDefault().getProperty(name());
	}
}
