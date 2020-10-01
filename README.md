# Redis_Cache
Redis-Cache Supported Spring boot Application
<pre>
How to add local jar files to a Maven project?

First run the below command.
<code>
<pre>
mvn install:install-file -Dfile=/home/gaian/Videos/fbAccessTokenVeification/demo/target/demo-0.0.1-SNAPSHOT.jar -DgroupId=com.fb -DartifactId=demo -Dversion=2.3.3.RELEASE -Dpackaging=jar -DgeneratePom=true
<code>
<pre>

Now  add the dependncy in the pom.file 
https://github.com/1InfinityDoesExist/Google_Facebook_Intgeration
<code>
<pre>
&lt;dependency&gt;
	&lt;groupId&gt;com.fb&lt;/groupId&gt;
	&lt;artifactId&gt;demo&lt;/artifactId&gt;
	&lt;version&gt;2.3.3.RELEASE&lt;/version&gt;
&lt;/dependency&gt;
<code>


<pre>
Issue : Consider defining a bean of type ' ** ** ** ** ' in your configuration.
the project has been broken down into different modules
Just add @ComponentScan({"com.fb.demo"}) in main application class along with @SpringBootApplication
<code>

