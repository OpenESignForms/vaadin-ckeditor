// Copyright (C) 2010-2011 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;

/**
 * GWT wrapper for CKEDITOR for use by our Vaadin-based CKEditorService.
 */
public class CKEditorService {
	
	private static boolean libraryLoadInited = false;
	private static boolean libraryLoaded = false;
	private static List<ScheduledCommand> afterLoadedStack = new ArrayList<ScheduledCommand>();
	
	public static void loadLibrary(ScheduledCommand afterLoad) {
		if (!libraryLoadInited) {
			String url = GWT.getModuleBaseURL() + "ckeditor/ckeditor.js";
			ScriptElement se = Document.get().createScriptElement();
			se.setSrc(url);
			se.setType("text/javascript");
			Document.get().getElementsByTagName("head").getItem(0).appendChild(se);
			libraryLoadInited = true;
			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {				
				@Override
				public boolean execute() {
					if (libraryReady()) {
						overrideBlurToForceBlur();
						for (ScheduledCommand sc: afterLoadedStack) {
							sc.execute();
						}
						libraryLoaded = true;
						return false;
					}
					return true;
				}
			}, 50);
		}
		if (libraryLoaded) {
			afterLoad.execute();
		} else {
			afterLoadedStack.add(afterLoad);
		}
	}
	
	public static native boolean libraryReady()
	/*-{
		if($wnd.CKEDITOR) {
			return true;
		} else {
			return false;
		}
	}-*/;
	
	/**
	 * Use this method to load editor to given identifier.
	 * 
	 * @param id the string DOM <div> 'id' attribute value for the element you want to replace with CKEditor
	 * @param listener the CKEditorService.CKEditorListener will get notified when the editor instance is ready, changed, etc.
	 * @param jsInPageConfig the String possible custom "in page" configuration; note that this must be an expected JSON for the CKEDITOR in page config.
	 * sent "as is" without any real syntax or security testing, so be sure you know it's valid and not malicious, 
	 * such as: <code>{toolbar : 'Basic', language : 'en'}</code>
	 */
	public static native JavaScriptObject loadEditor(String id, CKEditorService.CKEditorListener listener, String jsInPageConfig, int compWidth, int compHeight)
	/*-{
	 	// Build our inPageConfig object based on the JSON jsInPageConfig sent to us.
	 	var inPageConfig = @org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(jsInPageConfig);
	 	
	 	var myEditor;
	 	
	 	if (inPageConfig == null) {
	 		inPageConfig = new Object;
	 		inPageConfig.width = compWidth;
	 		inPageConfig.height = compHeight;
	 	} else {
	 		if (!inPageConfig.width) inPageConfig.width = compWidth;
	 		if (!inPageConfig.height) inPageConfig.height = compHeight;
	 	}
	 	
	 	myEditor = $wnd.CKEDITOR.appendTo( id, inPageConfig );
	 		 	
	 	// The 'listener' passed to us is used as 'listenerData' for the callback.
		myEditor.on( 'instanceReady', function( ev ) {
    		ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onInstanceReady()();
		}, null, listener);
		
		return myEditor;

	}-*/;
	
	public native static String version()
	/*-{
		return $wnd.CKEDITOR.version;
	}-*/;
	
	public native static void overrideBlurToForceBlur()
	/*-{
		$wnd.CKEDITOR.focusManager.prototype['blur'] =  $wnd.CKEDITOR.focusManager.prototype['forceBlur'];
	}-*/;
	
	/**
	 * Returns a javascript CKEDITOR.editor instance for given id.
	 * 
	 * @param id the String id of the editor instance
	 * @return the overlay for CKEDITOR.editor or null in not yet initialized
	 */
	public native static CKEditor get(String id)
	/*-{
		return $wnd.CKEDITOR.instances[ id ];
	}-*/;
	
	// TODO: Never tested yet
	public native static void addStylesSet(String name, String jsStyles)
	/*-{
	 	var styles = @org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(jsStyles);
		$wnd.CKEDITOR.addStylesSet(name,styles);
	}-*/;
	
	
	// TODO: Never tested yet
	public native static void addTemplates(String name, String jsDefinition)
	/*-{
	 	var definition = @org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(jsDefinition);
		$wnd.CKEDITOR.addTemplates(name,definition);
	}-*/;

	public native static JavaScriptObject convertJavaScriptStringToObject(String jsString)
	/*-{
	    try {
	 		return eval('('+jsString+')');
	 	} catch (e) { 
	 		alert('convertJavaScriptStringToObject() INVALID JAVASCRIPT: ' + jsString); 
	 		return {}; 
	 	}
	}-*/;


	/**
	 * An interface for the VCKEditorTextField to get events from the CKEditor.
	 */
	public interface CKEditorListener {
		public void onInstanceReady();
		public void onChange();
		public void onModeChange(String mode);
		public void onDataReady();
		public void onBlur();
		public void onFocus();
		public void onSave();
	}

}
