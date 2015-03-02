<p class="odtdoc-to-strip">
WARNING: WORK IN PROGRESS - THIS IS ONLY A TEMPLATE FOR THE DOCUMENTATION. <br/>
RELEASE DOCS ARE ON THE PROJECT WEBSITE
</p>


This #{version} release is just for testing docs and release system.


This is a custom image for this version: <img src="img/test-img.png">

This is an image for all versions: <img src="../img/odt-commons-logo-200px.png" width="150px">


Links without extension:
* link to other md file as markdown: [link](other-file)
* link to other md file as html: <a href="other-file">link</a>

Great discovery: links without extension don't work...



### Usage

Odt Commons is available on Maven Central. To use it, put this in the dependencies section of your _pom.xml_:

```
    <dependency>
        <groupId>eu.trentorise.opendata</groupId>
        <artifactId>odt-doc</artifactId>
        <version>#{version}</version>            
    </dependency>
```

In case updates are available, version numbers follows <a href="http://semver.org/" target="_blank">semantic versioning</a> rules.


Test code can be found in <a href="../../src/test/java/eu/trentorise/opendata/commons/test" target="_blank">in test directory</a> todo review

Test code can be found in [in test directory!](../../src/test/java/eu/trentorise/opendata/commons/test) todo review


#### The API

Most objects in odt-commons are immutable, and make heavy use of <a href="https://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained" target="_blank"> Guava immutable collections </a>. In odt-commons, wherever you see a class called 'AbstractSomething', there will always be an immutable class 'Something' implementing it. 

##### Building objects

Immutable classes don't have public constructors, they only have factory methods called _of()_. 

Todo put examples.


### Logging

Odt-Commons uses native Java logging system (JUL). If you also use JUL in your application and want to see Odt Doc logs, you can take inspiration from [odt-doc test logging properties](src/test/resources/odt.commons.logging.properties).  If you have an application which uses SLF4J logging system, you can route logging with <a href="http://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j" target="_blank">JUL to SLF4J bridge</a>, just remember <a href="http://stackoverflow.com/questions/9117030/jul-to-slf4j-bridge" target="_blank"> to programmatically install it first. </a>


### Javadoc

Javadoc can be browsed <a href="javadoc" target="_blank">here</a>.
