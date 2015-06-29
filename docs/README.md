<p class="jedoc-to-strip">
WARNING: THIS IS ONLY A TEMPLATE FOR THE DOCUMENTATION. <br/>
RELEASE DOCS ARE ON THE <a href="http://opendatatrentino.github.io/odt-commons/" target="_blank">PROJECT WEBSITE</a>
</p>

Odt Commons implements basic utilities for

* logging
* building info
* versioning
* immutability
* multilingual strings such as `LocalizedString` and `Dict`
* validation


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

### Building objects

Most objects in odt-commons are immutable, and make heavy use of <a href="https://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained" target="_blank"> Guava immutable collections </a>. In odt-commons, wherever you see an abstract class called `ASomething`, there will always be an immutable class `Something` implementing it.



Immutable classes don't have public constructors, they only have factory methods starting with _of()_:

```
    LocalizedString myLocalizedString = LocalizedString.of(Locale.ITALIAN, "ciao");
```

Default language is always `Locale.ROOT`:

```
    LocalizedString localizedString = LocalizedString.of("string with unknwon language");

    assert Locale.ROOT.equals(localizedString.getLocale());
```

Generally, we are null hostile:

```
    try {
        LocalizedString.of(null);
    } catch(NullPointerException ex){

    }
```


Dicts are immutable multimaps with builders. Factory method for a `Dict`:

```
    Dict.of(Locale.ENGLISH, "hello", "my friend");
```

Building a `Dict`:

```
    Dict myDict = Dict.builder().put(Locale.ENGLISH, "hello")
                                .put(Locale.ENGLISH, "hello again")
                                .put(Locale.ITALIAN, "ciao")
                                .build();

    assert "hello".equals(myDict.string(Locale.ENGLISH));
    assert "hello again".equals(myDict.strings(Locale.ENGLISH).get(1)); 
    assert "ciao".equals(myDict.string(Locale.ITALIAN)); 

```


### Logging

Odt-Commons uses native Java logging system (JUL). If you also use JUL in your application and want to see Odt-Commons logs, you can take inspiration from [odt-commons test logging properties](src/test/resources/odt.commons.logging.properties).  If you have an application which uses SLF4J logging system, you can route logging with <a href="http://mvnrepository.com/artifact/org.slf4j/jul-to-slf4j" target="_blank">JUL to SLF4J bridge</a>, just remember <a href="http://stackoverflow.com/questions/9117030/jul-to-slf4j-bridge" target="_blank"> to programmatically install it first. </a>


