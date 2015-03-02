
WARNING: WORK IN PROGRESS - THIS IS ONLY A TEMPLATE FOR THE DOCUMENTATION. RELEASE DOCS ARE ON THE PROJECT WEBSITE

________________


This #{version} release is just for testing docs and release system.


This is a custom image for this version: <img href="img/test-img.png"/>

This is an image for all versions: <img href="../img/test-img.png"/>
![This is an image for all versions:](../img/test-img.png?raw=true)


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

#### Get the dataset list of dati.trentino.it:

Test code can be found in <a href="#../../src/test/java/eu/trentorise/opendata/commons/test" target="_blank">in test directory</a> todo review

Test code can be found in [in test directory!](../../src/test/java/eu/trentorise/opendata/commons/test) todo review


```
    Some code

```

#### Do something

```

    Some code

```

Should give something like this:

```

    Some result

```



### Logging

Odt-Doc uses native Java logging system (JUL). If you also use JUL in your application and want to see Odt Doc logs, you can take inspiration from [odt-doc test logging properties](src/test/resources/odt.commons.logging.properties).  If you have an application which uses SLF4J logging system, you can route logging with <a href="http://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j" target="_blank">JUL to SLF4J bridge</a>, just remember <a href="http://stackoverflow.com/questions/9117030/jul-to-slf4j-bridge" target="_blank"> to programmatically install it first. </a>


### Javadoc

Javadoc can be browsed <a href="javadoc" target="_blank">here</a>.
