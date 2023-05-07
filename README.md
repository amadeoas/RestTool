# REST APIs Tool
This UI provides access to rend any of the available REST requests to the supported APIs.

The UI provides a list of the supported APIs on the menu and the home page.

### Usage ###
To see the home page of the UI go to the corresponding URL to see the Java libraries available and their corresponding Javadocs, i.e. for URL http://localhost:8080/. If the UI is running in your computer open the web browser and go to *http://localhost:8080*.

### Development ###
To add a new fictitious supported API add a new JSON file with the definition of the environments, and services provided by that API. Rebuild the UI to include the new additions.

This UI is a Spring Boot application that may be run by itself if the host computer have JRE installed and can be run with the below command.

<div class="warning" style='padding:0.1em; background-color:lightgrey; color:darkblue; border:1px solid #555; display: block; margin-left: auto; margin-right: auto;'>
<span>
	<p style='margin-top:1em; text-align:left'><b>Command</b>:</p>
	<hr />
	<p style='margin-top:1em; text-align:center'>java -jar RestTool-&lt;version&gt;.jar</p>
</span>
</div>
<p></p>
<div class="warning" style='padding:0.1em; background-color:lightgrey; color:darkblue; border:1px solid #555; display: block; margin-left: auto; margin-right: auto;'>
<span>
	<p style='margin-top:1em; text-align:left'><b>Example</b>:</p>
	<hr />
	<p style='margin-left:1em; text-align:center'>java -jar RestTool-1.0.001.jar</p>
</span>
</div>

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.10/gradle-plugin/reference/html/)
* [Apache Freemarker](https://docs.spring.io/spring-boot/docs/2.7.10/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.10/reference/htmlsingle/#web)
* [Java Docs Tool](https://github.com/amadeoas/JDocs)
* [Rest APIs Tool](https://github.com/amadeoas/RestTool)
