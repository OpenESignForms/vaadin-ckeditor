// Vaadin CKEditor - Widget linkage for using CKEditor within a Vaadin application.
// Copyright (C) 2010 Yozons, Inc.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
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
		return this.dataProcessor.writer.setRules(tagName, rule);
	}-*/;
}
