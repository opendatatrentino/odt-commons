
## Odt Commons Release Notes


### 1.1.0

November 6th, 2015

* moved checks from OdtUtils to validation.Preconditions class
* added Preconditions.checkScore
* shortened method names in LocalizedString (str, loc) end Dict (get, some, str)
* added APeriodOfTime
* added OdtParseException
* added OdtUtils.format, parseIso8061, parseUrlParams, putKey
* added odt-eclipse-format-style.xml


### 1.0.3

6 May 2015

* Now BuilderStyle and SimpleStyle accept both A* and Abstract*
* added BuilderStylePublic
* upgraded to odt-super-pom-1.2.0

### 1.0.2

4 May 2015

* Adopted new josman-maven-plugin for generating website
* Upgraded Immutables to 2.0.9
* Upgraded to odt-super-pom-1.0.17

### 1.0.1

* Adopted new parent odt-super-pom

### 1.0.0

Implemented basic utilities for

* logging
* building info
* versioning
* immutability
* multilingual stringd like classes Dict and LocalizedString classes

Adopted also jedoc documentation guidelines

NOTE: this release has code extracted from TraceProv 0.2.0, thus it is incompatible with it.


