File: VaadinCKEditor/README.txt
Last updated: 19 May 2017

3/26/2015 - Migrated from Google Code (https://code.google.com/p/vaadin-ckeditor/) to GitHub (https://github.com/OpenESignForms/vaadin-ckeditor).


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
However, we remove the following files from the standard CKEditor distribution as they are not needed:
   ckeditor/samples
We also install our vaadinsave plugin in addition to the CKEditor plugins shipped in the full package release.
If you are compiling yourself, you will need to install CKEditor code into your project
as we do not check in the CKEditor code in our source code system.
1) Download the latest ZIP file from ckeditor.com. We include the Full Editor version.
2) Unzip/extract the contents -- you should have a 'ckeditor' folder.
3) Copy the 'ckeditor' folder to src/org/vaadin/openesignforms/ckeditor/widgetset/public.
4) If you want to use the Vaadin Save button plugin, copy ckeditor/plugins/vaadinsave
   to src/org/vaadin/openesignforms/ckeditor/widgetset/public/ckeditor/plugins.
   This is already done in the released CKEditor code.
5) Recompile the widgetset.

  LICENSE
  =======
  
This software component is licensed under Apache License 2.0. 
See http://www.apache.org/licenses/LICENSE-2.0.html for more details.

This component was written initially by Yozons, Inc. (www.yozons.com) 
for its Open eSignForms project (http://open.esignforms.com) -- not required to use this component -- 
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
  * Take advantage of Vaadin 7 features for add-on widgets.

  KNOWN ISSUES
  ============
  See: https://github.com/OpenESignForms/vaadin-ckeditor/issues
  
  CHANGELOG
  =========

7.11.2 (19 May 2017)
- Attempted patch from https://github.com/jpikl to fix issue 36 with setVisible() and multiple editors 
  (https://github.com/OpenESignForms/vaadin-ckeditor/issues/36).  
  Pull request: https://github.com/OpenESignForms/vaadin-ckeditor/pull/59.
  We found this did not work reliably and had to abandon it. For example, in the demo UI, it fails to open the editor with 100% height and
  that correct behavior is more important to us the multi-editor set visible issue (we rarely have multiple editors on the same view).
  Using the ideas behind the patch, however, we were able to make this work as the code previously had no concept of handling the widget
  being unloaded and reloaded.
- Per Issue 60 (https://github.com/OpenESignForms/vaadin-ckeditor/issues/60 from coolersport/Tien Tran), 
  we added the code like his for Vaadin 6 to set the text as dirty on detach and related updates.
- Per Issue 61 (https://github.com/OpenESignForms/vaadin-ckeditor/issues/61 from coolersport/Tien Tran),
  again could not use the code for Vaadin 6, but found a way to ignore updates on initial setting.
- Upgraded to Vaadin 7.7.9.

7.11.1 (26 January 2017)
- Upgraded to CKEditor 4.6.2.  
- Upgraded to Vaadin 7.7.6.

7.11.0 (17 November 2016)
- Upgraded to CKEditor 4.6.0.  
  The CKEditor release caused the following CKEditorConfig options to be removed as no longer supported: pasteFromWordNumberedHeadingToList and pasteFromWordRemoveStyles
  The CKEditor release also suggests the following CKEditorConfig option will be deprecated soon: pasteFromWordRemoveFontStyles
- Upgraded to Vaadin 7.7.4.

7.10.11 (21 September 2016)
- Upgraded to CKEditor 4.5.11.
- Upgraded to Vaadin 7.7.1.

7.10.10 (28 August 2016)
- Upgraded to CKEditor 4.5.10.
- Upgraded to Vaadin 7.7.0.

7.10.9 (20 May 2016)
- Upgraded to CKEditor 4.5.9.
- Upgraded to Vaadin 7.6.6.

7.10.8 (11 April 2016)
- Upgraded to CKEditor 4.5.8.
- Includes Matti Tahvonen's patch for https://github.com/OpenESignForms/vaadin-ckeditor/issues/53 to resolve
  the fail sending of changes (per the patch 48/52 added in 7.10.7) when a new item data source is set.

7.10.7 (29 March 2016)
- Upgraded to CKEditor 4.5.7.
- Upgraded to Vaadin 7.6.4.
- Includes Matti Tahvonen's patch for https://github.com/OpenESignForms/vaadin-ckeditor/issues/48 (and 52) to resolve
  updates going out when using long polling.

7.10.6 (15 December 2015)
- Upgraded to CKEditor 4.5.6.
- Upgraded to Vaadin 7.5.10.

7.10.5 (16 October 2015)
- Upgraded to CKEditor 4.5.4.
- Upgraded to Vaadin 7.5.7.

7.10.4 (21 August 2015)
- Upgraded to CKEditor 4.5.3.
- Upgraded to Vaadin 7.5.4.

7.10.3 (13 August 2015)
- Upgraded to CKEditor 4.5.2.
- Upgraded to Vaadin 7.5.3.

7.10.2 (15 July 2015)
- Upgraded to Vaadin 7.5.1.
- The 7.10.0 added 'selected html listener' (addSelectionChangeListener(SelectionChangeListener listener) is more
  reliable now, but it depends on keyup and mouseup events, so could be a bit browser heavy, but didn't notice any issues.
  Also, the event is fired when nothing is selected if we previously had sent out a selected text event (to help capture no-longer-selected).  

7.10.0 (8 July 2015)
- Upgraded to CKEditor 4.5.1
- Upgraded to Vaadin 7.5.0.
- Adding selected html listener (but currently not reliable enough so use at your own risk).
  Seems if you have selected text and then clicked on an editor button, it's usually correct.
- Project has moved to GitHub (Google Code died).

7.9.6 (2 February 2015)
- Upgraded to CKEditor 4.4.7.
- Upgraded to Vaadin 7.3.9.

7.9.5 (20 January 2015)
- Fixed issue 41 (https://code.google.com/p/vaadin-ckeditor/issues/detail?id=41) for setting the editor text contents before firing blur/focus events.  
  Thanks to kdokdo for providing the bug find and fix.

7.9.4 (2 December 2014)
- Upgraded to CKEditor 4.4.6.
- Upgraded to Vaadin 7.3.5.

7.9.3 (29 August 2014)
- Upgraded to CKEditor 4.4.4.
- Upgraded to Vaadin 7.2.6.

7.9.2 (30 July 2014)
- Upgraded to CKEditor 4.4.3.
- Upgraded to Vaadin 7.2.5.

7.9.1 (6 April 2014)
- Upgraded to CKEditor 4.3.4.
- Upgraded to Vaadin 7.1.13.

7.9.0 (2 February 2014)
- Added 'addKeystrokeMapping' to set keystroke handlers via CKEditorConfig: config.addKeystrokeMapping(int keystroke,String command)
- Added 'enableCtrlSWithVaadinSavePlugin' to CKEditorConfig to allow CTRL-S to also save via the 'vaadinsave' plugin. Note that this
  will automatically enable the vaadinsave plugin (config.enableVaadinSavePlugin()) as it's required for the keystroke mapping to take place.
- Changed the 'vaadinsave' plugin to work in both WYSIWYG and SOURCE modes.
- Added CKEditorTextField.addVaadinSaveListener() method to be notified when the vaadinsave button is pressed. Previously, this was
  only noted by a ValueChangeListener(), though that could be called if you changed the editor and then clicked 
  to remove focus from the editor. The callback listener allows you to know that it was the vaadinsave button or CTRL-S.

7.8.9 (31 January 2014)
- Upgraded to CKEditor 4.3.2.
- Upgraded to Vaadin 7.1.10.

7.8.8 (25 November 2013)
- Upgraded to CKEditor 4.3.
- Upgraded to Vaadin 7.1.8.

7.8.7 (17 October 2013)
- Upgraded to CKEditor 4.2.2.
- Upgraded to Vaadin 7.1.7.

7.8.6 (3 September 2013)
- Added 'readOnly' setting for startup read-only mode: config.setReadOnly(true).
- Fixed bug in change events so as not to lose any changes due to a conflict between the CKEditor's built-in
  'change' event and our continued to need to also use the 'blur' event to detect changes, especially
  for SOURCE mode as 'change' works great for WYSIWYG mode.
- Upgraded to Vaadin 7.1.3.

7.8.5 (29 July 2013)
- Added ElementResizeListener to VCKEditorTextField widget so react to browser resizing.

7.8.4 (23 July 2013)
- Upgraded to CKEditor 4.2. Using the new "change" event, which the documentation points out could fire
  even when no changes are done, so comparisons are still needed.
- Upgraded to Vaadin 7.1.1.

7.8.3 (10 May 2013)
- Fixed the modal window issue.

7.8.2 (8 May 2013)
- Basic legacy port from Vaadin 6.8.10 to 7.0.5. Special thanks to Samuli Penttil� for submitting a conversion patch that was based on an older 1.6 release
  but could still be applied to the 1.8.2 release we did the conversion on.
- Note that this is considered experimental as it's built on the legacy conversion and has not been tested in production Vaadin 7 environments.
- Note that the modal window bug has returned with this port, so you are recommended not to place a CKEditorTextField in a modal Vaadin Window.

1.8.2 (23 April 2013)
- Upgraded to CKEditor 4.1.1. This patch allows previous pasteFromWord* settings to work.
- Added ability to set pasteFromWordPromptCleanup: config.setPasteFromWordPromptCleanup(boolean)

1.8.1 (17 April 2013)
- Added ability to set pasteFromWordRemoveFontStyles: config.setPasteFromWordRemoveFontStyles(boolean). Use with caution since seems to cause
  CKEditor stability issues. (CKEditor fixed this in 4.1.1)
- Added ability to set pasteFromWordRemoveStyles: config.setPasteFromWordRemoveStyles(boolean). Use with caution since seems to cause
  CKEditor stability issues. (CKEditor fixed this in 4.1.1)
- Changed Open eSignForms config to turn off ACF.
- Upgraded to Vaadin 6.8.10.

1.8.0 (4 April 2013)
- Upgraded to CKEditor 4.1.0.
- Added ability to set allowedContent for advanced content filtering (ACF): config.setAllowedContent(String acfSpec).
- Added ability to disable advanced content filtering (ACF) by setting allowedContent to true: config.setAllowedContentAll().
- Added ability to set extraAllowedContent for advanced content filtering (ACF): config.setExtraAllowedContent(String acfSpec).

1.7.7 (19 March 2013)
- Added ability to set startupFocus in CKEditorConfig: config.setStartupFocus(boolean v).
- Added support for the CKEditorTextField.focus() method to be called.

1.7.6 (15 March 2013)
- Upgraded to CKEditor 4.0.2.
- Upgraded to Vaadin 6.8.9.

1.7.5 (10 February 2013)
- Reducing the blur delay from 70msecs to 20msecs to see if this works any more reliably (sometimes we don't get the latest editor contents), but
  not so quick that we get interim editor contents (like bogus span and body tags that are added during PASTE and replace text operations).
  If we go too low, and if you add a BLUR listener to the editor in Vaadin, we find that Safari users cannot easily right-click in a TABLE CELL
  because the BLUR event causes the buffer to be sent to the server, which then updates the editor and causes the editor's popup dialog to close.
  For Safari with BLUR/FOCUS listeners, we see the editor sometimes gets out of sync with a blur event fired while still in the editor, so for now
  we recommend if you need Safari support, do not add BLUR/FOCUS listeners to the widget.

1.7.4 (1 February 2013)
- Updated the sample app to set the BaseFloatZIndex to 11000 in the popup windows so that the editor
  can maximize and not show the Vaadin subwindow as a grayed out box (it's lost focus). Oddly, this
  works for that issue, but when you minimize the editor that's in a non-modal Vaadin subwindow that you do not
  reposition on the screen when it's created, it doesn't regain the focus. Interestingly, if you do move the subwindow
  even a bit before maximizing the editor, minimize works fine.  And just as interesting is that in a modal Vaadin subwindow, 
  it appears to work fine regardless of repositioning the subwindow.
- Changed from forceblur scheme to reducing the blur delay from 200msecs to just 70msecs.
- Upgraded to Vaadin 6.8.8.

1.7.3 (11 January 2013)
- Upgraded to CKEditor 4.0.1.

1.7.2 (23 December 2012)
- Fixed Open eSignForms toolbar configuration.

1.7.1 (22 December 2012)
- Changed CKEditorTextField's changeVariables() method that handles receiving changes from the client-side
  to no longer repaint as the contents received are what the editor already has. This avoids two quick
  changes on the client side from getting lost when the server sends back the first change. In particular
  we saw this on FF, Chrome and Safari browsers with a PASTE operation that caused CKEditor to insert
  temporarily a <body id="cke_pastbin...>&nbsp;</body> and one or two <span style="display:none;">&nbsp;</span>
  tags. If these are accepted and then sent back, the final change after the PASTE is lost and the
  editor then contains bad "interim" contents. IE and Opera do not seem to show this oddity on PASTE.
- Upgraded to Vaadin 6.8.7.
  
1.7.0 (29 November 2012)
- Upgraded to CKEditor 4.0, full package.
- Reworked VaadinSave plugin for 4.0.
- Reworked config.protectedSource to no longer be part of the inPageConfig.
- Because CKEditor 4.0 doesn't have the 'tableresize' plugin anymore, we removed CKEditorConfig.enableTableResizePlugin().
- Changed setReadOnly(true) to set/clear CKEditor's readOnly flag, a feature introduced in CKEditor 3.6. 
  Previously, read-only mode was not part of the editor itself, so we just displayed the HTML without the editor.
- Related, the new method setViewWithoutEditor(true) will do what setReadOnly(true) previously did, showing the HTML
  contents without the editor.

1.6.7 (16 October 2012)
- Upgraded to CKEditor 3.6.5.
- Upgraded to Vaadin 6.8.5.

1.6.6 (19 July 2012)
- Added ability to add protectedSource regular expressions: config.addProtectedSource(String regex)
- Upgraded to CKEditor 3.6.4.
- Upgraded to Vaadin 6.8.1.

1.6.5 (20 June 2012)
 - Added the following operations to CKEditorConfig:
   setResizeEnabled(boolean v)
   setResizeMinWidth(int pixels)
   setResizeMaxWidth(int pixels)
   setResizeMinHeight(int pixels)
   setResizeMaxHeight(int pixels)

1.6.4 (20 June 2012)
 - Added modal window fix. Thanks for the fix by Tien Tran.
 - Upgraded to Vaadin 6.8.0.

1.6.3 (27 April 2012)
 - Added ability to set any CKEDITOR.config option when no API is available to do it: config.addExtraConfig(String extraConfigName, String extraConfigValue )
   that will result in adding this line to the in-page config:  extraConfigName : extraConfigValue
   If you think it's a generally useful option to set, please let us know and we'll add it to the API to call it out for others to use as well.
 - Change for Open eSignForms to set the file browser window size.
 - Upgraded to Vaadin 6.7.8.

1.6.2 (21 April 2012)
 - Change for Open eSignForms to set the file browser URL.
 - Upgraded to CKEditor 3.6.3.
 - Upgraded to Vaadin 6.7.6.

1.6.1 (27 February 2012)
 - Added ability to set the font_names configuration option for the Fonts drop down list: config.setFontNames(). Also added some
   convenience methods like: getFontNameList() and addFontName(String newFontNameSpec).
 - Bug fix if the editor has its data change and readonly set together. Thanks for the bug find and fix by sascha.broich76.
 - Upgraded to Vaadin 6.7.5.

1.6.0 (8 December 2011)
 - Updated the sample application to show the 100% height fixed by Stefan.
 *** All of the following feature upgrades in this release were contributed by Stefan Mei�ner, davengo GmbH (Thanks again!) ***
 - Correct sizing of the component using component.setWidth(..., ...) and component.setHeight(..., ...). 
   Vaadin layouts do all the calculations of 100% height and widths and so on and give you the correct sizes.
 - Adds the width and the height to the inPage configuration, if it is not set yet so the component is rendered 
   immediately with the correct height and width. To get around scrollbars which can appear because of borders and margins 
   within css, the overflow of the components root element is now set to HIDDEN.
 - Added dynamic loading of the (300kb) ckeditor.js file to your component. ckeditor.js will now only get loaded when the 
   first CKEditorTextField is rendered. this saves traffic and time when the vaadin app is loaded.
 - Added support for fullPage (config)
 - Added support for protectedBody: sets contenteditable=false on the body element of the wysiwyg editor. 
   Only parts of the document can be changed. e.g. <div contenteditable="true">change me</div>.

1.5.0 (1 December 2011)
 - Upgraded to Vaadin 6.7.2.
 - Removed Config.setStylesCombo_stylesSet() method as it was previously deprecated. Use Config.setStylesSet() instead.
 *** All of the following feature upgrades in this release were contributed by Stefan Mei�ner, davengo GmbH (Thanks!) ***
 - Focus and TabIndex (implementing Focusable)
 - CKEditorField.insertHtml (insert html at the current selection)
 - CKEditorField.insertText (insert text at the current selection)
 - Detect more Content Changes (Undo, Redo, Snapshot, Source-View changes)
 - EnterMode and ShiftEnterMode (@Config)
 - Added to Config:
   * Language
   * templates_replaceContent
   * forcePasteAsPlainText
   * forceEnterMode

1.4.3 (2 October 2011)
 - Fix setValue(Object) to handle the oddball case of someone passing in a non-String value. This was
   missed in the 1.4.2 release.

1.4.2 (29 September 2011)
 - Upgraded to CKEditor 3.6.2 and Vaadin 6.6.7.
 - Fixes in case someone is abusing the widget and passing in non-String values for the HTML editor.
 - Incorporated modal dialog bug example so we have it documented until (if ever?!) it's fixed.

1.4.1 (25 June 2011)
 - Upgraded to CKEditor 3.6.1 and Vaadin 6.6.2.

1.4 (14 June 2011)
 - Changed CKEditorConfig setStylesCombo_stylesSet() to use name setStylesSet() as this was changed back in CKEditor 3.3 apparently. 
   The old name is deprecated and will be removed shortly.
 - Added config options from Andr� (google code user zorknika)
   config.setSkin(String newSkin)
   config.setFilebrowserFlashBrowseLinkUrl(String url)
   config.setFilebrowserImageBrowseLinkUrl(String url)
   config.addTemplatesFiles(String templateURL)
  
1.3.1 (3 June 2011)
 - Upgraded to Vaadin 6.6.1 with GWT 2.1.0.
 - Upgraded to CKEditor 3.6. This results in a change to the layout for defining custom toolbars. 
   You'll want to check your code if it calls config.addCustomToolbarLine(String)
 - Added ability to set the tabSpaces config option: config.setTabSpaces(int);
 
1.2 (3 May 2011)
 - Added ability to set the indentation chars (defaults to tab). The Open eSignForms default uses this to change to 4 spaces. (config.setWriterIndentationChars(String))
 - Working with the project http://sourceforge.net/projects/jckconnector/ added methods to set the following configurations:
   config.setFilebrowserUploadUrl(String)
   config.setFilebrowserImageUploadUrl(String)
   config.setFilebrowserFlashBrowseUrl(String)
   config.setFilebrowserFlashUploadUrl(String)
   config.setFilebrowserLinkBrowseUrl(String)

1.1 (12 April 2011)
 - Using CKEditor 3.5.3. This has support for IE9 and Firefox 4.
 - Recompiled with Vaadin 6.5.4, which also uses GWT 2.1.1.
 - Added ability to retrieve the version of CKEditor (i.e. "3.5.3") in use: CKEditorTextField.getVersion(). Currently this can't be done until after the editor is created
   and initialized since we don't have any way to pass it back until there's some communications between server and client.
 - Allow additional body classes to be passed in besides the default of "esf" for Open eSignForms needs. (config.setBodyClass() already allows this generally).

1.0 (17 December 2010)
 - Using CKEditor 3.5 just released today.
 - Recompiled with Vaadin 6.4.8.
 - Allow eSignForms configuration to include the document style's CSS.
 - Added ability to set a file browser URL to allow a user to select an image from the CKEditor image dialog: config.setFilebrowserImageBrowseUrl(String)
 - Added ability to set a file browser window width (CKEditor defaults to 80%): config.setFilebrowserWindowWidth(String)
 - Added ability to set a file browser window height (CKEditor defaults to 70%): config.setFilebrowserWindowHeight(String)
 - Added ability to set an image browser URL to allow a user to select an image from the CKEditor image dialog: config.setFilebrowserBrowseUrl(String)
 - Added ability to set an image browser window width (CKEditor defaults to 80%): config.setFilebrowserImageWindowWidth(String)
 - Added ability to set an image browser window height (CKEditor defaults to 70%): config.setFilebrowserImageWindowHeight(String)

0.8 (4 November 2010)
 - Using CKEditor 3.4.2.
 - Recompiled with Vaadin 6.4.7.
 - Added setting the root DIV element to be visible so that Firefox will show it on popup windows -- previously, it would only show the editor area
   once in Firefox, and then it would disappear every time after in popup windows, yet would still work in panels off the main window.
 - Added ability to set the body class CKEditor will use so it renders like how you may show the HTML on another page: config.setBodyClass(String bs)
   This is particularly useful if you are using config.setContentsCss() to specify a CSS file that should be used in the editor and when rendered.
 - Added ability to set the baseFloatZIndex: config.setBaseFloatZIndex(int zindex)
   This can be useful if your editors are opened in popup windows.  Found that the default (10000) wasn't enough and we're using 32000 for Open eSignForms.
 - Added ability to set another option: config.setPasteFromWordNumberedHeadingToList(boolean) -- testing it out ourselves.
 - Added ability to set the startupMode to "source": config.setStartupModeSource() 

0.7 (20 October 2010)
 - Using CKEditor 3.4.1.
 - Recompiled with Vaadin 6.4.6.
 - Add ability to configure an external styles definition for the Styles combobox: config.setStylesCombo_stylesSet(String styleSetSpec)
 - Added editor destroy code when the widget is unloaded to hopefully avoid leaks.
 - Root DIV for the editor includes overflow:auto so it will show scrollbars if needed (such as in read-only mode when the editor
   is not present with its scrollbars).
 - Added method CKEditorConfig.enableTableResizePlugin() to add the optional 'tableresize' plugin since it seems to be generally useful.
 - Added method CKEDitorConfig.setupForOpenESignForms(String contextPath) to set up the editor in a common way for the Open eSignForms 
   project and removed the other project-specific API: addOpenESignFormsCustomToolbar().

0.6 (26 July 2010)
 - Use CKEDITOR.appendTo instead of CKEDITOR.replace to create the edit instance.  This resolved the bugs associated with the editor moving down
   and button clicks, as well as allowing the component to fill the space of the DIV from Vaadin's perspective.  Simplified the code as we no longer
   had to worry about that the DIV we created versus the DIV created for the editor as we switched between readonly and normal modes.  Now the editor
   places itself inside the DIV we created so we have more natural control over it.
 - Added ability to set the config contentsCss with either a single file name or an array file files: setContentsCss(String file) or setContentsCss(String[] files)
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
