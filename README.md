# Redis_Cache
Redis-Cache Supported Spring boot Application

How to add local repo jar.
First run the below command.
mvn install:install-file -Dfile=/home/gaian/Videos/fbAccessTokenVeification/demo/target/demo-0.0.1-SNAPSHOT.jar -DgroupId=com.fb -DartifactId=demo -Dversion=2.3.3.RELEASE -Dpackaging=jar -DgeneratePom=true


Now  add the dependncy in the pom.file 
<dependency>
			<groupId>com.fb</groupId>
			<artifactId>demo</artifactId>
			<version>2.3.3.RELEASE</version>
</dependency>

