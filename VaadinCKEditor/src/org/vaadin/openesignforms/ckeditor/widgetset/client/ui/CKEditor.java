// Copyright (C) 2010-2011 Yozons, Inc.
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
	
	public final native void setWriterRules(String tagName, String jsRule)
	/*-{
	 	var rule = @org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(jsRule);
		this.dataProcessor.writer.setRules(tagName, rule);
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
		this.on('saveSnapshot', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onChange()(); 
		}, null, listener);
		this.on('mode', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onModeChange(Ljava/lang/String;)(ev.editor.mode); 
		}, null, listener);
		this.on('dataReady', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onDataReady()(); 
		}, null, listener);
		this.getCommand('undo').on( 'afterUndo', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onChange()(); 
		}, null, listener);
		this.getCommand('redo').on( 'afterRedo', function(ev) { 
			ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onChange()(); 
		}, null, listener);
		this.on('afterCommandExec', function( ev ) { 
			if ( ev.data.command.canUndo !== false ) { 
				ev.listenerData.@org.vaadin.openesignforms.ckeditor.widgetset.client.ui.CKEditorService.CKEditorListener::onChange()(); 
			} 
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
}
