// Copyright (C) 2010-2015 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wrapper around CKEDITOR.editor js object
 */
public class CKEditor extends JavaScriptObject {

	protected CKEditor() {
	}

	public final native boolean checkDirty()
	/*-{
		return this.checkDirty();
	}-*/;

	public final native void resetDirty()
	/*-{
		this.resetDirty();
	}-*/;

	public final native String getData()
	/*-{
		return this.getData();
	}-*/;

	public final native void setData(String htmlData)
	/*-{
		return this.setData(htmlData);
	}-*/;
	
	public final native boolean isReadOnly()
	/*-{
		return this.readOnly;
	}-*/;

	public final native void setReadOnly(boolean isReadOnly)
	/*-{
		this.setReadOnly(isReadOnly);
	}-*/;
	
	public final native void setWriterRules(String tagName, String jsRule)
	/*-{
	 	var rule = @org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(jsRule);
		this.dataProcessor.writer.setRules(tagName, rule);
	}-*/;
	
	public final native void setKeystroke(int keystroke, String command)
	/*-{
		this.setKeystroke(keystroke, command);
	}-*/;
	
	public final native void pushProtectedSource(String regexString)
	/*-{
	    var regex = @org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(regexString);
		this.config.protectedSource.push( regex );
	}-*/;
	
	public final native void setWriterIndentationChars(String indentationChars)
	/*-{
		this.dataProcessor.writer.indentationChars = indentationChars;
	}-*/;
	
	public final native void instanceReady(CKEditorService.CKEditorListener listener)
	/*-{
	 	// The 'listener' passed to us is used as 'listenerData' for the callback.
	 	this.on( 'blur', function( ev ) {
 			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onBlur()();
    	}, null, listener);
    	
	 	this.on( 'focus', function( ev ) {
 			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onFocus()();
    	}, null, listener);
    	
     	this.on( 'vaadinsave', function( ev ) {
	 		ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onSave()();
    	}, null, listener);
    	
    	// hook into the change events for ckEditor
		this.on('change', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onChange()(); 
		}, null, listener);

		this.on('selectionChange', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onSelectionChange()(); 
		}, null, listener);
		this.on('contentDom', function(ev) {
			this.document.on('keyup', function(ev2) {
				ev2.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onSelectionChange()(); 
			}, null, ev.listenerData);
			this.document.on('mouseup', function(ev2) {
				ev2.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onSelectionChange()(); 
			}, null, ev.listenerData);
		}, null, listener);
		
		this.on('mode', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onModeChange(Ljava/lang/String;)(ev.editor.mode); 
		}, null, listener);
		this.on('dataReady', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onDataReady()(); 
		}, null, listener);

	}-*/;
	
	public final native void execCommand(String cmd)
	/*-{
		this.execCommand(cmd);
	}-*/;
	
	public final native void updateElement()
	/*-{
		this.updateElement();
	}-*/;
	
	public final native void destroy(boolean noUpdate)
	/*-{
		this.destroy(noUpdate);
	}-*/;
	
	public final native void destroy()
	/*-{
		this.destroy();
	}-*/;
	
	public final native void resize(int width, int height)
	/*-{
	 	this.resize(width, height);
	}-*/;
	
	public final native String getId()
	/*-{
	 	return this.id;
	}-*/;
	
	public final native void focus()
	/*-{
	 	this.focus();
	}-*/;
	
	public final native int getTabIndex()
	/*-{
	 	return this.tabIndex;
	}-*/;
	
	public final native void setTabIndex(int tabIndex)
	/*-{
	 	this.tabIndex = tabIndex;
	}-*/;
	
	public final native void insertHtml(String data)
	/*-{
	 	this.insertHtml(data);
	}-*/;
	
	public final native void insertText(String data)
	/*-{
	 	this.insertText(data);
	}-*/;
	
	public final native void protectBody(boolean protectBody)
	/*-{
	 	if (this.document) {
	 		if (this.document.getBody()) {
	 			this.document.getBody().$.contentEditable = !protectBody;
	 		}
	 	}
	}-*/;

	public final native String getSelectedHtml()
	/*-{
	    return this.getSelectedHtml(true);
	}-*/;

}
