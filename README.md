# vaadin-ckeditor
CKEditor is a javascript-based HTML "rich text" WYSIWYG editor that works in common browsers.  
This component provides a wrapper around it for use in a Vaadin-based (Java servlet plus GWT) application. 

** GIT Versions/Branches **
GIT should have a few branches, with 'master' being the main line latest greatest release.  Branch VAADIN6 contains the version last built for Vaadin 6.  The VAADIN7-fromV6 was a temporary branch for building a Vaadin 7 version based on the Vaadin 6 version.  VAADIN7 is the branch for just Vaadin 7.  

**SVN History from Google Code**
We used two SVN branches as of May 2013 to handle the port to Vaadin 7. That created branches named VAADIN6 and VAADIN7 that each contain the VaadinCKEditor Eclipse project code.  The VAADIN7 branch was merged back to the 'trunk' in March 2015.

The older VAADIN6 branch version was developed using Vaadin 6.8.10 and CKEditor 4.1.1 in an Eclipse environment.  You simply can drop the JAR file into your Vaadin WEB-INF/lib, compile widgetsets and then you can begin using it.  No work has been done on this branch since 2013.

**Because Google has removed Downloads from Google Code in 2014 (on its way to its eventual demise!), please visit the [Vaadin Directory to download the JAR](http://vaadin.com/addon/ckeditor-wrapper-for-vaadin) as of version 7.8.9.**

The current Vaadin 7 version is developed using Vaadin 7.7.14 and CKEditor 4.11.2 in an Eclipse environment.  You simply can drop the JAR file into your Vaadin WEB-INF/lib, compile widgetsets and then you can begin using it.

NOTE: This widget is compiled using JDK 1.6 / Java 6. But we only deploy using Java 8.

It was built for use in the [Open eSignForms](https://open.esignforms.com/) open source electronic contracting and electronic signature web-based application (SaaS) where forms can be developed for [electronic signatures](https://www.yozons.com/ElectronicSignatures/), routing, etc.

**NOTE:** *All code contributed to this project will be bundled under the single copyright owner, Yozons Inc., but of course it's released under Apache 2.0 so you are free to do with the code as you please.*

**VAADIN 8**  [Another project has forked our code and taken over CKEditor maintenance for Vaadin 8.](https://github.com/alump/CKEditor)

(Automatically exported from code.google.com/p/vaadin-ckeditor on 3/19/2015)
