<p class="odtdoc-to-strip">
WARNING: WORK IN PROGRESS - THIS IS ONLY A TEMPLATE FOR THE DOCUMENTATION. <br/>
RELEASE DOCS ARE ON THE PROJECT WEBSITE
</p>


todo put first release description

### Maven

Odt Commons is available on Maven Central. To use it, put this in the dependencies section of your _pom.xml_:

```
    <dependency>
        <groupId>eu.trentorise.opendata</groupId>
        <artifactId>odt-commons</artifactId>
        <version>#{version}</version>            
    </dependency>
```

In case updates are available, version numbers follows <a href="http://semver.org/" target="_blank">semantic versioning</a> rules.

### The API

Most objects in odt-commons are immutable, and make heavy use of <a href="https://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained" target="_blank"> Guava immutable collections </a>. In odt-commons, wherever you see an abstract class called 'ASomething', there will always be an immutable class 'Something' implementing it. 

#### Building objects

Immutable classes don't have public constructors, they only have factory methods called _of()_. 

Todo put examples.


### Logging

Odt-Commons uses native Java logging system (JUL). If you also use JUL in your application and want to see Odt-Commons logs, you can take inspiration from [odt-commons test logging properties](src/test/resources/odt.commons.logging.properties).  If you have an application which uses SLF4J logging system, you can route logging with <a href="http://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j" target="_blank">JUL to SLF4J bridge</a>, just remember <a href="http://stackoverflow.com/questions/9117030/jul-to-slf4j-bridge" target="_blank"> to programmatically install it first. </a>


