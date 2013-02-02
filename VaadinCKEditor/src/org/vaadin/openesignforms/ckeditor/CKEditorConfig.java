// Copyright (C) 2010-2012 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
package org.vaadin.openesignforms.ckeditor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Configuration utility for using the CKEditorTextField.  You can use this "config javascript builder" object for our
 * tested/common options, or just set the options using a JavaScript/JSON string as you prefer.
 */
public class CKEditorConfig implements java.io.Serializable {
	private static final long serialVersionUID = 5966138001983585432L;

	// If this is set, we'll just use it and ignore everything else.
	private String inPageConfig;
	
	// Hack to allow a caller to add any line to the inPageConfig. Of course, if they specify it wrong or overwrite our API standard ones
	// that will just be their own issue. This is not recommended and we prefer that if you have an option to set, let us know and
	// we'll get it added to the API. We know most CKEDITOR.config options are never set by regular users, but those that are, we'd like
	// to document them as part of the API.
	private LinkedList<String> extraConfigLines = null;

	// Otherwise, we'll build the config based on settings contained here
	private HashMap<String,String> writerRules = null;
	private String writerIndentationChars = null;
	private LinkedList<String> extraPlugins = null;
	private LinkedList<String> removePlugins = null;
	private LinkedList<String> customToolbarLines = null;
	private Boolean toolbarCanCollapse = null;
	private Boolean disableNativeSpellChecker = null;
	private Boolean resizeEnabled = null;
	private String resizeDir = null;
	private Integer resizeMinWidth = null;
	private Integer resizeMaxWidth = null;
	private Integer resizeMinHeight = null;
	private Integer resizeMaxHeight = null;
	private String width = null;
	private String height = null;
	private Integer baseFloatZIndex = null;
	private Integer tabSpaces = null;
	private Boolean pasteFromWordNumberedHeadingToList = null;
	private String startupMode = null; // either "source" or "wysiwyg" (defaults to wysiwyg, so generally only used if you'd like to startup in source mode)
	private String[] contentsCssFiles = null;
	private String fontNames = null;
	private String stylesSet = null;
	private String bodyClass = null;
	private String skin = null;
	private Boolean toolbarStartupExpanded = null;
	private LinkedList<String> templates_files = null;
	private Boolean templates_replaceContent = null;
	
	private String filebrowserBrowseUrl = null;
	private String filebrowserUploadUrl = null;
	private String filebrowserWindowWidth = null; // defaults to 80% width
	private String filebrowserWindowHeight = null; // defaults to 70% height
	
	private String filebrowserImageBrowseUrl = null;
	private String filebrowserImageUploadUrl = null;
	private String filebrowserImageWindowWidth = null; // defaults to 80% width
	private String filebrowserImageWindowHeight = null; // defaults to 70% height
    private String filebrowserImageBrowseLinkUrl = null;
	
	private String filebrowserFlashBrowseUrl = null;
	private String filebrowserFlashUploadUrl = null;
	private String filebrowserFlashBrowseLinkUrl = null; // available? Not in CKEditor JS docs

	private String filebrowserLinkBrowseUrl = null; // available? Not in CKEditor JS docs
	
	private Integer enterMode = null;
	private Integer shiftEnterMode = null;
	private Boolean forceEnterMode = null;
	private Boolean forcePasteAsPlainText = null;
	
	private Boolean fullPage = null;
	
	private String language = null;
	
	// Sent separately since cannot make it work with inPageConfig
	private LinkedList<String> protectedSource = null;

	
	public CKEditorConfig() {
	}
	
	public String getInPageConfig() {
		if ( inPageConfig != null ) {
			return inPageConfig;
		}
		
		// Build the JSON config
		StringBuilder config = new StringBuilder(4096);
		config.append("{ "); // we assume this is 2 chars in the buffer append routines to know whether anything more has been appended yet
		
		if ( customToolbarLines != null ) {
			
			StringBuilder buf = new StringBuilder();
			ListIterator<String> iter = customToolbarLines.listIterator();
			while( iter.hasNext() ) {
				if ( buf.length() > 0 )
					buf.append(",'/',");
				String js = iter.next();
				buf.append(js);
			}

			appendJSONConfig(config, "toolbar : 'Custom'");
			appendJSONConfig(config, "toolbar_Custom : [" + buf.toString() + "]");
		}
		
		if ( toolbarCanCollapse != null ) {
			appendJSONConfig(config, "toolbarCanCollapse : " + toolbarCanCollapse);
		}
		
		if ( resizeEnabled != null ) {
			appendJSONConfig(config, "resize_enabled : " + resizeEnabled);
		}

		if ( resizeDir != null ) {
			appendJSONConfig(config, "resize_dir : '" + resizeDir + "'");
		}
		
		if ( resizeMinWidth != null ) {
			appendJSONConfig(config, "resize_minWidth : " + resizeMinWidth);
		}	
		if ( resizeMaxWidth != null ) {
			appendJSONConfig(config, "resize_maxWidth : " + resizeMaxWidth);
		}
		if ( resizeMinHeight != null ) {
			appendJSONConfig(config, "resize_minHeight : " + resizeMinHeight);
		}
		if ( resizeMaxHeight != null ) {
			appendJSONConfig(config, "resize_maxHeight : " + resizeMaxHeight);
		}
		
		if ( extraPlugins != null ) {
			StringBuilder buf = new StringBuilder();
			ListIterator<String> iter = extraPlugins.listIterator();
			while( iter.hasNext() ) {
				if ( buf.length() > 0 )
					buf.append(",");
				buf.append(iter.next());
			}
			
			appendJSONConfig(config, "extraPlugins : '" + buf.toString() + "'");
		}

		if ( removePlugins != null ) {
			StringBuilder buf = new StringBuilder();
			ListIterator<String> iter = removePlugins.listIterator();
			while( iter.hasNext() ) {
				if ( buf.length() > 0 )
					buf.append(",");
				buf.append(iter.next());
			}
			
			appendJSONConfig(config, "removePlugins : '" + buf.toString() + "'");
		}
		
		if ( width != null ) {
			appendJSONConfig(config, "width : '" + width + "'");
		}
		
		if ( height != null ) {
			appendJSONConfig(config, "height : '" + height + "'");
		}
				
		if ( baseFloatZIndex != null ) {
			appendJSONConfig(config, "baseFloatZIndex : " + baseFloatZIndex);
		}
		
		if ( tabSpaces != null ) {
			appendJSONConfig(config, "tabSpaces : " + tabSpaces);
		}
		
		if ( pasteFromWordNumberedHeadingToList != null ) {
			appendJSONConfig(config, "pasteFromWordNumberedHeadingToList : " + pasteFromWordNumberedHeadingToList);
		}
		
		if ( startupMode != null ) {
			appendJSONConfig(config, "startupMode : '" + startupMode + "'");
		}

		if ( skin != null ) {
			appendJSONConfig(config, "skin : '" + skin + "'");
		}
                
		if ( toolbarStartupExpanded != null ) {
			appendJSONConfig(config, "toolbarStartupExpanded : " + toolbarStartupExpanded );
		}
                
		if ( contentsCssFiles != null && contentsCssFiles.length > 0 ) {
			if ( contentsCssFiles.length == 1 ) {
				appendJSONConfig(config, "contentsCss : '" + contentsCssFiles[0] + "'");
			} else {
				StringBuilder buf = new StringBuilder();
				for( String file : contentsCssFiles ) {
					if ( buf.length() > 0 )
						buf.append(",");
					buf.append("'").append(file).append("'");
				}
				appendJSONConfig(config, "contentsCss : [" + buf.toString() + "]");
			}
		}
		
		if ( bodyClass != null ) {
			appendJSONConfig(config, "bodyClass : '" + bodyClass + "'");
		}
		
		if ( disableNativeSpellChecker != null ) {
			appendJSONConfig(config, "disableNativeSpellChecker : " + disableNativeSpellChecker);
		}
		
		if ( fontNames != null ) {
			appendJSONConfig(config, "font_names : '" + fontNames + "'");
		}
		
		if ( stylesSet != null ) {
			appendJSONConfig(config, "stylesSet : '" + stylesSet + "'");
		}
		
		if ( filebrowserBrowseUrl != null ) {
			appendJSONConfig(config, "filebrowserBrowseUrl : '" + filebrowserBrowseUrl + "'");
		}	
		if ( filebrowserUploadUrl != null ) {
			appendJSONConfig(config, "filebrowserUploadUrl : '" + filebrowserUploadUrl + "'");
		}	
		if ( filebrowserWindowWidth != null ) {
			appendJSONConfig(config, "filebrowserWindowWidth : '" + filebrowserWindowWidth + "'");
		}
		if ( filebrowserWindowHeight != null ) {
			appendJSONConfig(config, "filebrowserWindowHeight : '" + filebrowserWindowHeight + "'");
		}
		if ( filebrowserLinkBrowseUrl != null ) {
			appendJSONConfig(config, "filebrowserLinkBrowseUrl : '" + filebrowserLinkBrowseUrl + "'");
		}
		
		if ( filebrowserImageBrowseUrl != null ) {
			appendJSONConfig(config, "filebrowserImageBrowseUrl : '" + filebrowserImageBrowseUrl + "'");
		}
		if ( filebrowserImageUploadUrl != null ) {
			appendJSONConfig(config, "filebrowserImageUploadUrl : '" + filebrowserImageUploadUrl + "'");
		}
		if ( filebrowserImageWindowWidth != null ) {
			appendJSONConfig(config, "filebrowserImageWindowWidth : '" + filebrowserImageWindowWidth + "'");
		}
		if ( filebrowserImageWindowHeight != null ) {
			appendJSONConfig(config, "filebrowserImageWindowHeight : '" + filebrowserImageWindowHeight + "'");
		}
		if ( filebrowserImageBrowseLinkUrl != null ) {
			appendJSONConfig(config, "filebrowserImageBrowseLinkUrl : '" + filebrowserImageBrowseLinkUrl + "'");
		}
		
		if ( filebrowserFlashBrowseUrl != null ) {
			appendJSONConfig(config, "filebrowserFlashBrowseUrl : '" + filebrowserFlashBrowseUrl + "'");
		}
		if ( filebrowserFlashUploadUrl != null ) {
			appendJSONConfig(config, "filebrowserFlashUploadUrl : '" + filebrowserFlashUploadUrl + "'");
		}
		if ( filebrowserFlashBrowseLinkUrl != null ) {
			appendJSONConfig(config, "filebrowserFlashBrowseLinkUrl : '" + filebrowserFlashBrowseLinkUrl + "'");
		}

		if ( templates_files != null ) {
			StringBuilder buf = new StringBuilder();
			ListIterator<String> iter = templates_files.listIterator();
			while( iter.hasNext() ) {
				if ( buf.length() > 0 )
					buf.append("','");
				buf.append(iter.next());
			}
			appendJSONConfig(config, "templates_files : ['" + buf.toString() + "']");
		}
		
		if (templates_replaceContent != null) {
			appendJSONConfig(config, "templates_replaceContent : "+ templates_replaceContent);
		}
		
		if (enterMode != null) {
			appendJSONConfig(config, "enterMode : " + enterMode);
		}
		
		if (shiftEnterMode != null) {
			appendJSONConfig(config, "shiftEnterMode : " + shiftEnterMode);
		}
		
		if (forceEnterMode != null) {
			appendJSONConfig(config, "forceEnterMode : " + forceEnterMode);
		}

		if (forcePasteAsPlainText != null) {
			appendJSONConfig(config, "forcePasteAsPlainText : " + forcePasteAsPlainText);
		}
		
		if (language != null) {
			appendJSONConfig(config, "language : '" + language.toLowerCase() + "'");
		}
		
		if (fullPage != null) {
			appendJSONConfig(config, "fullPage : " + fullPage);
		}
		
		// These are the hack extra options for setting options we don't support in our API
		if ( extraConfigLines != null ) {
			for( String extra : extraConfigLines ) {
				appendJSONConfig(config, extra);
			}
		}

		config.append(" }");
		return config.toString();
	}
	
	private StringBuilder appendJSONConfig(StringBuilder configBuf, String oneOptions) {
		if ( configBuf.length() > 2 )
			configBuf.append(", ");
		configBuf.append(oneOptions);
		return configBuf;
	}
	
	/**
	 * You can use this to just set the JavaScript/JSON notation for setting the 'in page config' option when the editor is
	 * created.  If you don't use this, you can use all of the other builder routines that set options that are then used
	 * to generate the JSON notation to use
	 * @param js the String JSON 'config' for the new editor instance.
	 */
	public void setInPageConfig(String js) {
		inPageConfig = js;
	}
	
	/**
	 * Allows you to add any CKEDITOR.config option line to the in page configuration. This is considered a hack, but may be
	 * necessary if you are setting an option not supported by the API of this class. We recommend that you request the API
	 * be changed when you find you need to use this, but it'll keep you going while you await the API-based scheme.
	 * The resulting will be to add a JSON line to the in-page config like: <code>extraConfigName : extraConfigValue<code>
	 * @param extraConfigName the String CKEDITOR.config name to set
	 * @param extraConfigValue the String value
	 */
	public void addExtraConfig(String extraConfigName, String extraConfigValue) {
		if ( extraConfigLines == null )
			extraConfigLines = new LinkedList<String>();
		extraConfigLines.add(extraConfigName + " : " + extraConfigValue);
	}
	
	public boolean hasWriterRules() {
		return writerRules != null && ! writerRules.isEmpty();
	}
	public Set<String> getWriterRulesTagNames() {
		return writerRules == null ? new HashSet<String>() : writerRules.keySet();
	}
	public String getWriterRuleByTagName(String tagName) {
		return writerRules == null ? null : writerRules.get(tagName);
	}
	public synchronized void addWriterRules(String tagName, String jsRule) {
		if ( writerRules == null ) {
			writerRules = new HashMap<String,String>();
		}
		writerRules.put( tagName, jsRule );
	}
	
	// A convenience method to set a bunch of compact HTML rules.
	public void useCompactTags() {
		addWriterRules("p",  "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("h1", "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("h2", "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("h3", "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("h4", "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("h5", "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("h6", "{indent : false, breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
		addWriterRules("li", "{indent : true,  breakBeforeOpen : true, breakAfterOpen : false, breakBeforeClose : false, breakAfterClose : true}" );
	}
	
	public String getWriterIndentationChars() {
		return writerIndentationChars;
	}
	public boolean hasWriterIndentationChars() {
		return writerIndentationChars != null;
	}
	public void setWriterIndentationChars(String v) {
		writerIndentationChars = v;
	}

	public synchronized void addToExtraPlugins(String pluginName) {
		if ( extraPlugins == null ) {
			extraPlugins = new LinkedList<String>();
		}
		if ( ! extraPlugins.contains(pluginName) ) {
			extraPlugins.add(pluginName);
		}
		// If for whatever reason this plugin name is in the remove plugins, we remove it.
		if ( removePlugins != null && removePlugins.contains(pluginName) ) {
			removePlugins.remove(pluginName);
		}
	}
	
	/**
	 * This enables the vaadinsave plugin.  You will also need a custom toolbar with the entry 'VaadinSave' included to put it 
	 * the specified position.
	 */
	public void enableVaadinSavePlugin() {
		addToExtraPlugins("vaadinsave"); 
	}

	
	/**
	 * Convenience method for the Open eSignForms project sponsors to set the plugins and configuration in a common way needed.
	 */
	public void setupForOpenESignForms(String contextPath, String ckeditorContextIdInSession, String bodyCssClass, String... extraCssFiles) {
		addCustomToolbarLine("{ items: ['Styles','Format','Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat'] }," +
							 "{ items: ['TextColor','BGColor'] }," +
	                         "{ items: ['Font','FontSize'] }," +
	             			 "{ items: ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'] }"); // all wrong icons
		addCustomToolbarLine("{ items: ['Cut','Copy','Paste','PasteText','PasteFromWord'] }," + // paste word wrong
				 			 "{ items: ['Find','Replace'] }," + 
				 			 "{ items: ['Undo','Redo'] }," +
				 			 "{ items: ['NumberedList','BulletedList'] }," + 
				 			 "{ items: ['Outdent','Indent','CreateDiv'] }," + 
				 			 "{ items: ['Table','HorizontalRule','PageBreak','SpecialChar'] }," +
				 			 "{ items: ['Image','Link','Unlink'] }," +
				 			 "{ items: ['Source','ShowBlocks'] }");

		setHeight("300px");
		setBaseFloatZIndex(32000);
		setTabSpaces(4);
		
		disableSpellChecker();
		setDisableNativeSpellChecker(false);
		disableResizeEditor();
		setPasteFromWordNumberedHeadingToList(true);
		
		useCompactTags();
		addWriterRules("script", "{indent : false, breakBeforeOpen : true, breakAfterOpen : true, breakBeforeClose : true, breakAfterClose : true}" );
		addWriterRules("style",  "{indent : false, breakBeforeOpen : true, breakAfterOpen : true, breakBeforeClose : true, breakAfterClose : true}" );
		setWriterIndentationChars("    ");

		addFontName("Calibri/Calibri, Arial, Helvetica, sans-serif");
		
		setStylesSet("esfStyleSet:" + contextPath + "/static/esf/esfStyleSet.js");
		if ( extraCssFiles == null )
			setContentsCss(contextPath + "/static/esf/esf.css");
		else
		{
			String[] cssFiles = new String[extraCssFiles.length+1];
			cssFiles[0] = contextPath + "/static/esf/esf.css";
			for(int i=0; i < extraCssFiles.length; ++i )
				cssFiles[i+1] = contextPath + extraCssFiles[i];
			setContentsCss(cssFiles);
		}
		if ( bodyCssClass == null || "".equals(bodyCssClass) )
			bodyCssClass = "esf";
		else
			bodyCssClass = "esf " + bodyCssClass;
		setBodyClass(bodyCssClass);
		setFilebrowserBrowseUrl(contextPath + "/ckeditorFileBrowser.jsp?ccid="+ckeditorContextIdInSession);
		setFilebrowserWindowWidth("500"); // 4/27/2012 believe there's a bug for any value less than 640 being ignored
		setFilebrowserWindowHeight("500");
		setFilebrowserImageBrowseUrl(contextPath + "/ckeditorImageBrowser.jsp?ccid="+ckeditorContextIdInSession);
		setFilebrowserImageWindowWidth("600");
		setFilebrowserImageWindowHeight("500");
		addProtectedSource("/<%.*%>/g"); // allow JSP code like <%=(new java.util.Date())%> or <% any java code you want %>
		addProtectedSource("/<[a-z]*:.*\\/>/g"); // allow JSP tags like <esf:out value="Test"/>
	}
	
	public synchronized void addToRemovePlugins(String pluginName) {
		if ( removePlugins == null ) {
			removePlugins = new LinkedList<String>();
		}
		if ( ! removePlugins.contains(pluginName) ) {
			removePlugins.add(pluginName);
		}
		// If for whatever reason this plugin is defined in the extraPlugins list, we remove it.
		if ( extraPlugins != null && extraPlugins.contains(pluginName) ) {
			extraPlugins.remove(pluginName);
		}
	}
	
	public void disableElementsPath() {
		addToRemovePlugins("elementspath"); 
	}

	public void disableResizeEditor() {
		addToRemovePlugins("resize"); 
	}
	
	public void disableSpellChecker() {
		addToRemovePlugins("scayt"); 
	}
	
	public void setDisableNativeSpellChecker(boolean v) {
		disableNativeSpellChecker = v;
	}


	
	/**
	 * If no custom toolbar is defined, it will use the Full toolbar by default (config.toolbar = 'Full').
	 * The format of the toolbar configuration changed in CKEditor 3.6 (our library 1.3).
	 * 
	 * Note that each line is generally one 'band' so that they all appear together.  
	 * For example:
	 * { name: 'styles', items : ['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize', 'ShowBlocks','-','About'] }
	 * @param toolbarLineJS
	 */
	public synchronized void addCustomToolbarLine(String toolbarLineJS) {
		if ( customToolbarLines == null ) {
			customToolbarLines = new LinkedList<String>();
		}
		if ( ! customToolbarLines.contains(toolbarLineJS) ) {
			customToolbarLines.add(toolbarLineJS);
		}
	}
	
	public void setToolbarCanCollapse(boolean v) {
		toolbarCanCollapse = v;
	}

    /**
     * Possible skins:
     * moono The default skin for CKEditor 4.x
     * kama The default skin for CKEditor 3.x
     * office2003
     * v2 
     * 
     * @param newSkin the skin to set
     */
    public void setSkin(String newSkin) 
    {
        skin = newSkin;
    }

    /**
     * @param newToolbarStartupExpanded the toolbarStartupExpanded status
     */
    public void setToolbarStartupExpanded(Boolean newToolbarStartupExpanded) 
    {
        toolbarStartupExpanded = newToolbarStartupExpanded;
    }

	public void setResizeEnabled(boolean v) {
		resizeEnabled = v;
	}

	public enum RESIZE_DIR { BOTH, VERTICAL, HORIZONTAL }
	
	public void setResizeDir(RESIZE_DIR dir) {
		if ( dir.equals(RESIZE_DIR.BOTH) ) {
			resizeDir = "both";
		} else if ( dir.equals(RESIZE_DIR.VERTICAL) ) {
			resizeDir = "vertical";
		} else if ( dir.equals(RESIZE_DIR.HORIZONTAL) ) {
			resizeDir = "horizontal";
		} else {
			resizeDir = null; // won't set it since it's not what we expect
		}
	}

	public void setResizeMinWidth(int pixels) {
		resizeMinWidth = pixels;
	}

	public void setResizeMaxWidth(int pixels) {
		resizeMaxWidth = pixels;
	}

	public void setResizeMinHeight(int pixels) {
		resizeMinHeight = pixels;
	}

	public void setResizeMaxHeight(int pixels) {
		resizeMaxHeight = pixels;
	}

	public void setWidth(String cssSize) {
		width = cssSize;
	}

	public void setHeight(String cssSize) {
		height = cssSize;
	}
	
	public void setBaseFloatZIndex(int zIndex) {
		baseFloatZIndex = zIndex;
	}
	
	public void setTabSpaces(int numSpaces) {
		tabSpaces = numSpaces;
	}
	
	public void setPasteFromWordNumberedHeadingToList(boolean v)
	{
		pasteFromWordNumberedHeadingToList = v;
	}

	public void setStartupModeSource()
	{
		startupMode = "source";
	}
	public void setStartupModeWysiwyg()
	{
		startupMode = "wysiwyg";
	}
	
	/**
	 * Sets the file or list of files for the contents to be applied to the editor.  Used to set the styles that will
	 * be used in the page where the HTML will actually be rendered.
	 * @param cssFiles zero or more String file URL paths -- for same system, starting with context path is recommended: /myapp/path/to/cssfile.css
	 */
	public void setContentsCss(String... cssFiles) {
		contentsCssFiles = cssFiles;
	}
	

	/**
	 * Sets the body class for the HTML editor, so if you render the results in a body with a given class, you can give it here
	 * too so that the editor will show the same styles you may have in your contents css.
	 * @param bc
	 */
	public void setBodyClass(String bc) {
		bodyClass = bc;
	}
	
	/**
	 * Sets the font_names config option, which is the registered style name 
	 * @param fontNamesSpec the new font names to use.
	 * @see #CKEDITOR_DEFAULT_FONT_NAMES for a string that shows the built-in fonts.
	 * @see #getCkeditorDefaultFontNameList() to get a list of the fonts so you can insert easily if you want and then set with #setFontNames(List<String> fontNamesSpec) or addFontName(String newFontName)
	 * 
	 */
	public static final String CKEDITOR_DEFAULT_FONT_NAMES = /* as of CKEditor 3.6.2 */
		"Arial/Arial, Helvetica, sans-serif;Comic Sans MS/Comic Sans MS, cursive;Courier New/Courier New, Courier, monospace;Georgia/Georgia, serif;Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;Tahoma/Tahoma, Geneva, sans-serif;Times New Roman/Times New Roman, Times, serif;Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;Verdana/Verdana, Geneva, sans-serif";
	public List<String> getCkeditorDefaultFontNameList()
	{
		LinkedList<String> list = new LinkedList<String>();
		for( String fontName : CKEDITOR_DEFAULT_FONT_NAMES.split(";") )
			list.add(fontName);
		return list;
	}
	public List<String> getFontNameList()
	{
		if ( fontNames == null )
			return getCkeditorDefaultFontNameList();
		LinkedList<String> list = new LinkedList<String>();
		for( String fontName : fontNames.split(";") )
			list.add(fontName);
		return list;
	}
	public void setFontNames(List<String> fontNamesSpec) {
		if ( fontNamesSpec != null && fontNamesSpec.size() > 0 ) {
			StringBuilder buf = new StringBuilder( (fontNamesSpec.size()+1) * 80);
			for( String spec : fontNamesSpec ) {
				if ( buf.length() > 0 ) {
					buf.append(';');
				}
				buf.append(spec);
			}
			setFontNames(buf.toString());
		}
	}
	public void addFontName(String newFontNameSpec) {
		LinkedList<String> newFontNames = new LinkedList<String>();
		boolean fontNeedsInsert = true;
		for( String fontName : getFontNameList() ) {
			if ( fontName.compareTo(newFontNameSpec) > 0 && fontNeedsInsert ) {
				newFontNames.add(newFontNameSpec);
				fontNeedsInsert = false;
			}
			newFontNames.add(fontName);
		}
		setFontNames(newFontNames);
	}
	public void setFontNames(String fontNamesSpec) {
		fontNames = fontNamesSpec;
	}
	
	/**
	 * Sets the stylesSet config option, which is the registered style name 
	 * @param styleSetSpec
	 */
	public void setStylesSet(String styleSetSpec) {
		stylesSet = styleSetSpec;
	}
	
	/**
	 * Sets the filebrowserBrowseUrl config option, which is an URL that will list files a user can select from
	 * @param url
	 */
	public void setFilebrowserBrowseUrl(String url) {
		filebrowserBrowseUrl = url;
	}
	
	/**
	 * Sets the filebrowserUploadUrl config option, which is an URL that will allow a file to be uploaded
	 * @param url
	 */
	public void setFilebrowserUploadUrl(String url) {
		filebrowserUploadUrl = url;
	}
	
	/**
	 * Sets the filebrowserWindowWidth config option, which is a width size spec (like "600" for 600 pixels); CKEditor defaults to 80%
	 * @param url
	 */
	public void setFilebrowserWindowWidth(String size) {
		filebrowserWindowWidth = size;
	}
	
	/**
	 * Sets the filebrowserWindowHeight config option, which is a height size spec (like "600" for 600 pixels); CKEditor defaults to 70%
	 * @param url
	 */
	public void setFilebrowserWindowHeight(String size) {
		filebrowserWindowHeight = size;
	}
	
	/**
	 * Sets the filebrowserImageBrowseUrl config option, which is an URL that will list images a user can select from
	 * @param url
	 */
	public void setFilebrowserImageBrowseUrl(String url) {
		filebrowserImageBrowseUrl = url;
	}
	
	/**
	 * Sets the filebrowserImageUploadUrl config option, which is an URL that will allow an image file to be uploaded
	 * @param url
	 */
	public void setFilebrowserImageUploadUrl(String url) {
		filebrowserImageUploadUrl = url;
	}
	
	/**
	 * Sets the filebrowserImageWindowWidth config option, which is a width size spec (like "600" for 600 pixels); CKEditor defaults to 80%
	 * @param url
	 */
	public void setFilebrowserImageWindowWidth(String size) {
		filebrowserImageWindowWidth = size;
	}
	
	/**
	 * Sets the filebrowserImageWindowHeight config option, which is a height size spec (like "600" for 600 pixels); CKEditor defaults to 70%
	 * @param url
	 */
	public void setFilebrowserImageWindowHeight(String size) {
		filebrowserImageWindowHeight = size;
	}
	
	
	/**
	 * Sets the filebrowserFlashBrowseUrl config option, which is an URL that will allow browsing for Flash
	 * @param url
	 */
	public void setFilebrowserFlashBrowseUrl(String url) {
		filebrowserFlashBrowseUrl = url;
	}
	
	/**
	 * Sets the filebrowserFlashUploadUrl config option, which is an URL that will allow a Flash file to be uploaded
	 * @param url
	 */
	public void setFilebrowserFlashUploadUrl(String url) {
		filebrowserFlashUploadUrl = url;
	}
	
	/**
	 * Sets the filebrowserLinkBrowseUrl config option, which is an URL that will allow for link browsing
	 * @param url
	 */
	public void setFilebrowserLinkBrowseUrl(String url) {
		filebrowserLinkBrowseUrl = url;
	}


    /**
     * Sets the filebrowserFlashBrowseLinkUrl config option, 
     * which is an URL that will allow for link browsing
     * in the Flash property dialog
     * 
     * @param url the filebrowserFlashBrowseLinkUrl to set
     */
    public void setFilebrowserFlashBrowseLinkUrl(String url) 
    {
        filebrowserFlashBrowseLinkUrl = url;
    }

    /**
     * Sets the filebrowserImageBrowseLinkUrl config option, 
     * which is an URL that will allow for link browsing
     * in the Image property dialog
     * 
     * @param url the filebrowserImageBrowseLinkUrl to set
     */
    public void setFilebrowserImageBrowseLinkUrl(String url) 
    {
        filebrowserImageBrowseLinkUrl = url;
    }

    /**
     * Add a new template url to the list of templates
     * 
     * @param templateURL 
     */
    public synchronized void addTemplatesFiles(String templateURL) {
    	if ( templates_files == null ) {
    		templates_files = new LinkedList<String>();
    	}
    	if ( ! templates_files.contains(templateURL) ) {
    		templates_files.add(templateURL);
    	}
    }

    /**
     * Sets 'enterMode' such that "P" will be 1, "BR" will be 2 and "DIV" will be 3. Any other value will set it to 1.
     * @param enterMode string code
     * @see #setEnterMode(int) to just set by integer value
     */
	public void setEnterMode(String enterMode) {
		if ("BR".equalsIgnoreCase(enterMode)) 
			this.enterMode = 2;
		else if ("DIV".equalsIgnoreCase(enterMode)) 
			this.enterMode = 3;
		else
			this.enterMode = 1;
	}
	public void setEnterMode(int enterMode) {
		this.enterMode = enterMode;
	}

    /**
     * Sets 'shiftEnterMode' such that "P" will be 1, "BR" will be 2 and "DIV" will be 3. Any other value will set it to 1.
     * @param shiftEnterMode string code
     * @see #setShiftEnterMode(int) to just set by integer value
     */
	public void setShiftEnterMode(String shiftEnterMode) {
		if ("BR".equalsIgnoreCase(shiftEnterMode)) 
			this.shiftEnterMode = 2;
		else if ("DIV".equalsIgnoreCase(shiftEnterMode)) 
			this.shiftEnterMode = 3;
		else
			this.shiftEnterMode = 1;
	}
	public void setShiftEnterMode(int shiftEnterMode) {
		this.shiftEnterMode = shiftEnterMode;
	}

	public void setForceEnterMode(Boolean forceEnterMode) {
		this.forceEnterMode = forceEnterMode;
	}

	public void setForcePasteAsPlainText(Boolean forcePasteAsPlainText) {
		this.forcePasteAsPlainText = forcePasteAsPlainText;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setTemplatesReplaceContent(Boolean templatesReplaceContent) {
		this.templates_replaceContent = templatesReplaceContent;
	}

	public void setFullPage(Boolean fullPage) {
		this.fullPage = fullPage;
	}
        
    /**
     * Add a protected source regular express to the list
     * 
     * Typical values are regular expressions like: /<%.*%>/g
     * 
     * If you don't start with a '/', a leading '/' and a trailing '/g' will be added automatically. 
     * 
     * @param regex the String regular expression of the protected source 
     */
    public synchronized void addProtectedSource(String regex) {
    	if ( protectedSource == null ) {
    		protectedSource = new LinkedList<String>();
    	}
    	if ( ! regex.startsWith("/") ) {
    		regex = "/" + regex + "/g";
    	}
    	if ( ! protectedSource.contains(regex) ) {
    		protectedSource.add(regex);
    	}
    }
    
    public boolean hasProtectedSource() {
    	return protectedSource != null && protectedSource.size() > 0;
    }
    
    public List<String> getProtectedSource() {
		return protectedSource;
    }

}
