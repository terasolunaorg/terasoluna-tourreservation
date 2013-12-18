## Tour Reservation Sample Application
This is a reference application built completely using TERASOLUNA Global Framework ([http://terasoluna.org](http://terasoluna.org "http://terasoluna.org")).

This application shows **how an IDEAL project configuration and package structure must be like.** It also shows working sample of best practices recommended in TERASOLUNA Global Framework Development Guideline.

**This sample uses Spring Data JPA. MyBatis version has not been not created yet.**

### Getting started

#### Download

Download source code from [here](https://github.com/terasolunaorg/terasoluna-tourreservation/releases "here").
Extract the zip file at any location of choice.

#### Run PostgreSQL

Install and start PostgreSQL.
select 'POstgres' as password for db user or select any password of choice. Be sure to remember the password. 
If 'POstgres' is not used, some changes will be required in configuration files. Hence be sure to remember it.

### Run PostgreSQL

Install and start PostgreSQL.

crate database 'tourreserve'.

#### Insert test data

It is assumed that maven is already installed.
Move to the directory where the downloaded source-code is unzipped.
If password of db user is set to 'POstgres' its not required to edit any file and directly execute the below command.
If it is set to any other password, then update the password in terasoluna-tourreservation-initdb/pom.xml.

Execute the below command:

	$ cd terasoluna-tourreservation-initdb
	$ mvn sql:execute
	$ cd ..

Test data is currently available in Japanese only.

#### Install jars

If db user password is not set to 'POstgres', then go to terasoluna-tourreservation-env/src/main/resources/META-INF/spring/tourreservation-infra.properties and update the password. If it is set to 'POstgres', no changes are required.

	$ mvn -f terasoluna-tourreservation-parent/pom.xml install
	$ mvn -f terasoluna-tourreservation-env/pom.xml intalll
	$ mvn -f terasoluna-tourreservation-domain/pom.xml install

#### Build war

	$ mvn -f terasoluna-tourreservation-web/pom.xml package

#### Deploy war

Deploy `terasoluna-tourreservation-web/target/terasoluna-tourreservation-web.war` to your Application server (ex. Tomcat7)

You can also use `mvn tomcat7:run` to test this application quickly with option `MAVEN_OPTS=-XX:MaxPermSize=256m` in environment variable.

access http://localhost:8080/terasoluna-tourreservation-web

Alternatively, these project can also be imported into Eclipse and application can be run using WTP.

#### Test with selenium

Install Firefox

	$ mvn -f terasoluna-tourreservation-selenium/pom.xml test
