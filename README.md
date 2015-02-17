Requirements
------------

- Java 8 http://www.java.com/ru/
- Ant http://ant.apache.org/
- Firefox https://www.mozilla.org/ru/firefox

Run tests
---------
From root folder run Ant
$ ant
Buildfile: /home/dkishenk/workspace/yandex/build.xml
clean:
   [delete] Deleting directory /home/dkishenk/workspace/yandex/bin
   [delete] Deleting directory /home/dkishenk/workspace/yandex/reports
prepare:
    [mkdir] Created dir: /home/dkishenk/workspace/yandex/bin
    [mkdir] Created dir: /home/dkishenk/workspace/yandex/reports
resources:
     [copy] Copying 3 files to /home/dkishenk/workspace/yandex/bin
compile:
    [javac] Compiling 39 source files to /home/dkishenk/workspace/yandex/bin
test:

 
Reports
-------
HTML JUnit report will be generated automatically to folder "reports" 


Structure
---------
.settings - Eclipse settings (optional)
bin - folder with compiled project classes
lib - libraries required to compile project
reports - output folder for HTML JUnt report
resources - extra resources useful for project, e.g. forecast.xsd, cities.xml
src/main/java/
	ru/yandex/
		account/wi 	- web wrappers for account management, e.g. switch language
		common 		- common staff
			matches - additional hamcrest matchers
			wi 		- base web wrappers
		pogoda
			common	- 
			
	
src/test/java - test sources
.classpath - Eclipse project staff
.project - Eclipse project staff 



- Поддержка нескольких языков
- Поддержка нескольких браузеров
- Погода меняется появляются новые данные не учтенные тестами 
- надо чаще обновлять web service data
TODO
- Filter JARs
- Направление ветра имеет два значения
- forecast сгенерирован, но было бы хоро иметь более удобную версию
- нуюно большее раздение между логическим и физическим уровнями

http://export.yandex.ru/weather-ng/forecasts/27612.xml

Источник достоверных данных
============================
http://export.yandex.ru/weather-ng/forecasts/27612.xml

https://devutilsonline.com/xsd-xml/generate-xsd-from-xml
Russian Doll style content type string
xjc.exe -d src/main/java/ -p ru.yandex.pogoda.ws resources/a.xsd

Selinide не рассчитан для работы с несколькими браузерами одновременно bи даже с несколькими экземплярами одной и тойже страницы

мобильная версия

много форматирования, хорошобы иметь собственный фортаттер
нужно больше специфичных матчеров


