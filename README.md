## Tour Reservation Sample Application
This is sample application made by TERASOLUNA Global Framework([http://terasoluna.org](http://terasoluna.org "http://terasoluna.org")).

This application shows how to use the framework ,how to configure project structure and package structure.

**This sample uses Spring Data JPA.MyBatis version is not created yet.**

### Getting started

#### Download

Download source code from [here](https://github.com/terasolunaorg/terasoluna-tourreservation/releases "here").

#### Insert test data

TEST data is written in only japanese now.

	$ mvn -f terasoluna-tourreservation-initdb/pom.xml sql:execute

Change the configuration of Database in terasoluna-tourreservation-initdb/pom.xml if required.

#### Install jars


	$ mvn -f terasoluna-tourreservation-parent/pom.xml install
	$ mvn -f terasoluna-tourreservation-env/pom.xml intalll
	$ mvn -f terasoluna-tourreservation-domain/pom.xml install

#### Build war

	$ mvn -f terasoluna-tourreservation-web/pom.xml package

#### Deploy war

Deploy `terasoluna-tourreservation-web/target/terasoluna-tourreservation-web.war`

to your Application server (ex. Tomcat7)

access http://localhost:8080/terasoluna-tourreservation-web

Surely, You can import these project into Eclipse and run the application using WTP.

#### Test with selenium
Install Firefox

	$ mvn -f terasoluna-tourreservation-selenium/pom.xml test

