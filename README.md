# Redis_Cache
Redis-Cache Supported Spring boot Application

How to add local repo jar.
First run the below command.
<pre>
mvn install:install-file -Dfile=/home/gaian/Videos/fbAccessTokenVeification/demo/target/demo-0.0.1-SNAPSHOT.jar -DgroupId=com.fb -DartifactId=demo -Dversion=2.3.3.RELEASE -Dpackaging=jar -DgeneratePom=true
<code>

Now  add the dependncy in the pom.file 
<pre>
&lt;dependency&gt;
	&lt;groupId&gt;com.fb&lt;/groupId&gt;
	&lt;artifactId&gt;demo&lt;/artifactId&gt;
	&lt;version&gt;2.3.3.RELEASE&lt;/version&gt;
&lt;/dependency&gt;
<code>

