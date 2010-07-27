File: VaadinCKEditor/README.txt
Last updated: 26 June 2010

  USING CKEDITOR FOR VAADIN IN YOUR APPLICATION
  =============================================

Put the pre-built JAR file and in your Vaadin application's WEB-INF/lib. 
This has everything you need to use it, including a version of CKEditor.
You will need to "Compile Vaadin widgets," which is an icon in the Eclipse menubar.
Then look at the example application for the basic setup.

NOTE: This widget is compiled using JDK 1.6 / Java 6.  We have been using 1.6
for years now (it was released December 2006) and see no reason to use such 
an outdated JDK 1.5 per Vaadin's widget specs.

The CKEditor code, in full as downloaded from http://ckeditor.com, is present in the 
src/org/vaadin/openesignforms/ckeditor/public folder.  No changes to CKEditor were made.
If you are compiling yourself, you will need to install CKEditor code into your project
as we do not check in the CKEditor code in our source code system.
1) Download the latest ZIP file from ckeditor.com.
2) Unzip/extract the contents -- you should have a 'ckeditor' folder.
3) Copy the 'ckeditor' folder to src/org/vaadin/openesignforms/ckeditor/public.
4) If you want to use the Vaadin Save button plugin, copy ckeditor/plugins/vaadinsave
   to src/org/vaadin/openesignforms/ckeditor/public/ckeditor/plugins.
5) Recompile the widgetset.

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

Icons are from Fat Cow Free Web Icons (http://www.fatcow.com/free-icons/) which are released 
under the Creative Commons Attribution 3.0 License.

  TODO
  ====
  
 - Nothing immediately pending until we start integrating into our forms and see all the warts
   and missing items.
 - Test on more than just Firefox 3.5 with limited testing on IE8/Chrome5/Safari5/Opera10 (all on Windows 7).


  KNOWN ISSUES
  ============
  
 - Want editor to resize based on its container for % height specs.  CKEditor doesn't support it for height, only width.

 
  IDEAS FOR FUTURE RELEASE
  ========================
  
 - Add ability to define styles.
 - Add ability to define templates.
 
  CHANGELOG
  =========

0.6 (26 July 2010)
 - Use CKEDITOR.appendTo instead of CKEDITOR.replace to create the edit instance.  This resolved the bugs associated with the editor moving down
   and button clicks, as well as allowing the component to fill the space of the DIV from Vaadin's perspective.  Simplified the code as we no longer
   had to worry about that the DIV we created versus the DIV created for the editor as we switched between readonly and normal modes.  Now the editor
   places itself inside the DIV we created so we have more natural control over it.
 - Added ability to set the config contentsCss with either a single file name or an array file files: setContentCss(String file) or setContentCss(String[] files)
 - Added ability to enable/disable the native spell checker feature: setDisableNativeSpellChecker(boolean)
 - Added support to CKEditor for editor.execCommand(String cmd) 

0.5 (23 July 2010)
 - Recompiled with Vaadin 6.4.1
 - CKEditorTextField now extends AbstractField instead of TextField.
 - Added LI tag to list of compact tags.
 - Support setReadOnly(boolean) (default false).  If the editor exists when you setReadOnly(true), the editor instance is destroyed.
 - Editor is now configured with a new CKEditorConfig class that can help build the configuration, or you can still just use JSON notation.
 - Ability to add other plugins by name: CKEditorConfig.addToExtraPlugins(String pluginName)
 - Ability to add the 'vaadinsave' plugin: CKEditorConfig.enableVaadinSavePlugin()
 - Ability to remove other plugins by name: CKEditorConfig.addToRemovePlugins(String pluginName)
 - Turn off (default on) HTML nesting levels (elementspath) shown at bottom if desired: CKEditorConfig.disableElementsPath()
 - Turn off (default on) resizing editor capability if desired: CKEditorConfig.disableResizeEditor()
 - Turn off (default on) the spell checker (scayt) if desired: CKEditorConfig.disableSpellChecker()
 - Using JSON notation, build a custom toolbar line-by-line: CKEditorConfig.addCustomToolbarLine(String toolbarLineJS)
 - Build the Open eSignForms standard custom toolbar: CKEditorConfig.addOpenESignFormsCustomToolbar()
 - Set whether the toolbar (default can) can be collapsed or not: CKEditorConfig.setToolbarCanCollapse(boolean v)
 - Set resize editor ability to vertical, horizontal or both (default): CKEditorConfig.setResizeDir(RESIZE_DIR dir)
 - Set editor width: CKEditorConfig.setWidth(String cssSizeFormat)
 - Set editor height: CKEditorConfig.setHeight(String cssSizeFormat)

0.4.1 (16 June 2010)
 - Recompiled with Vaadin 6.3.4.
 - Using CKEditor 3.3.1.
 - Fixed issue tracker id #3 with a seeming hack. Resetting the contents of the editor with setData() caused the DIV to lose its "display:none;"
   styling, so we're just forcing it on when we update the editor's data.  A better fix will be to stop that from happening if we
   ever find the root cause.
 - Fixed issue track id #1 by swapping out the CssLayout for a VerticalLayout in the main window.
 - While no bad side effects, changed the editor to do more setup only after the 'instanceReady' event is fired as it could
   be a timing issue perhaps on some browser, though never saw any issue per se.
 - Added sample theme style.css to show how this can be used if desired.
 - Use two CKEditors to show that more than one can be done and they are kept separate.
 - Fixed bug in CKEditor 'VaadinSave' button/plugin handler since Chrome often would do the blur event first and so the save
   button wouldn't detect any changes and then not sent them when immediate mode was not used.
   
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
