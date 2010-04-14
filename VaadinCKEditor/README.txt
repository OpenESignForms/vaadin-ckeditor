File: VaadinCKEditor/README.txt
Last updated: 14 April 2010

  USING VAADIN CKEDITOR IN YOUR APPLICATION
  =========================================

Put the pre-built JAR file and in your Vaadin application's WEB-INF/lib. 
You will need to "Compile Vaadin widgets," which is an icon in the Eclipse menubar.
Then look at the example application for the basic setup.


  LICENSE
  =======
  
This software component is licensed under Apache License 2.0. 
See http://www.apache.org/licenses/LICENSE-2.0.html for more details.

This component was written initially by Yozons, Inc. (www.yozons.com) 
for its Open eSignForms project (open.esignforms.com) -- not required to use this component -- 
which is separately licensed under the Affero GPL as well as a commercial licensed.

The code framework was initially jump-started using the Vaadin incubator
component TinyMCEEditor, replacing TinyMCE with CKEditor.  The TinyMCE code is licensed
under Apache License 2.0 and was written by Matti Tahvonen. 
No changes to TinyMCEEditor were made and this component makes no use of TinyMCE.
Special thanks to TinyMCEEditor for showing a path since it was all fuzzy to us!

CKEditor is required and is licensed separately with details at http://ckeditor.com/license.
The CKEditor code, in full as downloaded from http://ckeditor.com, is present in the 
src/org/vaadin/openesignforms/ckeditor/public folder.  No changes to CKEditor were made.

Icons are from Fat Cow Free Web Icons (http://www.fatcow.com/free-icons/) which are released 
under the Creative Commons Attribution 3.0 License.

  TODO
  ====
  
 - Nothing immediately pending until we start integrating into our forms and see all the warts
   and missing items.
 - Test on more than just Firefox 3.5 with limited testing on IE8/Chrome4/Safari4/Opera10 (all on Windows 7).


  KNOWN ISSUES
  ============
  
 - On IE8, if you load the sample application and click the 'Hit server' button immediately, the
   CKEditor disappears.  But if you load the sample application, then hit the browser refresh,
   clicking the 'Hit server' button doesn't cause the problem.

 
  IDEAS FOR FUTURE RELEASE
  ========================
  
 - Add ability to define styles.
 - Add ability to define templates.
 

  CHANGELOG
  =========
 
0.3 (14 April 2010)
 - Vaadin 6.3.0 resulted in a broken compile because of changes to TextField.  No functional changes made,
   but did a release to allow for the build to work.
 - Replaced disk (save) icon from Fat Cow Free Web Icons (http://www.fatcow.com/free-icons/) which are 
   released under the Creative Commons Attribution 3.0 License.
0.2 (8 March 2010)
 - Added save button handling that is always immediate. Added support for blur and focus event listeners 
   (defined in superclass TextField).
 - Built using CkEditor 3.2 as downloaded from http://ckeditor.com/download on 26 February 2010.
 - Added hack to change blur() to be forceBlur() to avoid 100ms delay that focusmanager.js has for its
   blur function.  This fixes a bug in which the 'Hit server' button's onclick event would fire before
   the CKEditor's blur so that it would sometimes seem like you lost an update that wouldn't be seen
   until you clicked another button.
0.1 (21 February 2010)
 - Initial take based on the TinyMCEEditor code in the Vaadin incubator SVN on 18 February 2010.
 - Built using CkEditor 3.1 as downloaded from http://ckeditor.com/download on 18 February 2010.
 - Code loaded to code.google.com and referenced in Vaadin Directory on 23 February 2010.
