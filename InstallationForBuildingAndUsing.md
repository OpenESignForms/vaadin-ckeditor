# Introduction #

You may also want to read the README.txt file for other details.


# Details #

The JAR file included has CKEditor and the widgetset all compiled and ready to use.  Just drop the JAR file in your Vaadin project's WebContent/WEB-INF/lib and you can use the example application in src/org/vaadin/openesignforms/VaadinCKEditorApplication.java (for Vaadin 6) or src/org/vaadin/openesignforms/VaadinCKEditorUI.java for Vaadin 7) for a quick tip on using it like a Vaadin textarea component.

To build your own, please note the following:

VAADIN 6: We built the project using Eclipse Helios on Vaadin 6.8.10 that also uses GWT 2.3.  These instructions assume you are doing the same.

VAADIN 7: We built the project using Eclipse Juno on Vaadin 7.0.5 with GWT built-in.  These instructions assume you are doing the same.

We have not included the CKEditor code in the source code repository.  Please download the FULL VERSION directly from http://ckeditor.com/download.  Extract the code so that the directory 'ckeditor' is located in your src/org/vaadin/openesignforms/widgetset/public folder.

Our distribution removes the following from the standard CKEditor code:
```
   ckeditor/samples
```

To use the Save button inside the editor, after you've installed ckeditor in the public folder above, you will need to copy the ckeditor/plugins/vaadinsave folder into the src/org/vaadin/openesignforms/widgetset/public/ckeditor/plugins folder.

You will then want to do a widgetset compile to generate the GWT-based code that will be put in WebContent/VAADIN/widgetsets.