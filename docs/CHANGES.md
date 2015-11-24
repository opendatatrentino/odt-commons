
## Tod Commons Release Notes


### 2.0.0

todo date

BREAKING CHANGES: 

- renamed NotFoundException -> TodNotFoundException
- moved eu.trentorise.opendata.commons.exceptions to 'eu.trentorise.opendata.commons.exceptions' package

### 1.1.0

November 9th, 2015

- renamed from odt-commons to tod-commons
- changed groupid from eu.trentorise.opendata.commons to eu.trentorise.opendata
- upgraded to tod-super-pom-1.3.0
- introduced Apache commons-lang dependency (shaded)
- moved checks from TodUtils to validation.Preconditions class
- added validation.ARef
- added Preconditions.checkScore and experimental checkIso8061
- shortened method names in LocalizedString (str, loc) end Dict (get, some, str)
- added:
	* APeriodOfTime
	* TodParseException
	* TodUtils.format, parseIso8061, parseUrlParams, putKey
	* tod-eclipse-format-style.xml
	* made ASemVersion not public


### 1.0.3

6 May 2015

* Now BuilderStyle and SimpleStyle accept both A* and Abstract*
* added BuilderStylePublic
* upgraded to tod-super-pom-1.2.0

### 1.0.2

4 May 2015

* Adopted new josman-maven-plugin for generating website
* Upgraded Immutables to 2.0.9
* Upgraded to tod-super-pom-1.0.17

### 1.0.1

* Adopted new parent tod-super-pom

### 1.0.0

Implemented basic utilities for

* logging
* building info
* versioning
* immutability
* multilingual stringd like classes Dict and LocalizedString classes

Adopted also jedoc documentation guidelines

NOTE: this release has code extracted from TraceProv 0.2.0, thus it is incompatible with it.


