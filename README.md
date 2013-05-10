-------------------------------------------------------------------------------
hosprice
-------------------------------------------------------------------------------
Version 0.0.1
Release date: N/A
-------------------------------------------------------------------------------
Project state: prototype 
-------------------------------------------------------------------------------
Credits
	Stephen Ohimor stephen.ohimor@gmail.com

Project description	
-------------------------------------------------------------------------------
Generic web application with javascript front end


Dependencies
-------------------------------------------------------------------------------

Hibernate
Hsqldb


Documentation
-------------------------------------------------------------------------------

Servlet context Simple Endpoint: http://localhost:8080/hosprice/rest/user/{yourMessage}

Installation instructions
-------------------------------------------------------------------------------

Install JDK 1.6+
Install Maven 3

git clone https://github.com/cerberusaeon/hosprice.git

build:
  mvn clean package


-------------------------------------------------------------------------------
Additional Notes

hosprice-common - contains domain/model objects
hosprice-services - contains REST services/endpoints for app
hosprice-web - js/image



-------------------------------------------------------------------------------
Changes

-.project
-top level folder names *common, *services, * web
-top level pom
-common pom.xml
-common .project
-services .pom.xml
-services .project
-web pom.xml
-web .project