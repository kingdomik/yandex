Requirements
------------
- Java 8 http://www.java.com/ru/
- Maven http://maven.apache.org/
- Firefox https://www.mozilla.org/ru/firefox

Run tests
---------
```
$ mvn clean test
```
 
Reports
-------
To get HTML test report run
'''
$ mvn clean test site
'''
Report can be found in target/site/project-reports.html 


Structure
---------
```
src/main/java/
	ru/yandex/
		account/wi 			    - web wrappers for account management, e.g. switch language
		common 				    - common staff
			matches 		    - additional hamcrest matchers
			wi 				    - base web wrappers
		pogoda
			data			    - forecast web service data wrappers
			wi				    - forecast specific web wrappers
			ws				    - generated JAXB wrappers to forecast web service
		qatools/htmlelements
			matches/common	    - extra matchers
src/main/resources
    ru/yandex/pogoda/wi/lang    - language bundles 
    ru/yandex/pogoda/ws         - web service artifacts    			 
src/test/java 				    - test sources
.classpath 					    - Eclipse project staff
.project 					    - Eclipse project staff 
```
