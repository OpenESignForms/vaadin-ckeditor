// Vaadin CKEditor - Widget linkage for using CKEditor within a Vaadin application.
// Copyright (C) 2010 Yozons, Inc.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.dom.client.Document;
//import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
//import com.vaadin.terminal.gwt.client.BrowserInfo;
import com.vaadin.terminal.gwt.client.EventId;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side CKEditor widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VCKEditorTextField extends Widget implements Paintable, CKEditorService.CKEditorListener {
	
	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-ckeditortextfield";
	
	private static boolean initializedCKEDITOR = false;

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;
	
	private String dataBeforeEdit = null;

	private boolean immediate;

	private CKEditor ckEditor = null;
	
	private HashMap<String,String> writerRules = null;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VCKEditorTextField() {
		
		// Any one-time library initializations go here
		if ( ! initializedCKEDITOR ) {
			CKEditorService.overrideBlurToForceBlur();
			initializedCKEDITOR = true;
		}
		
		// CKEditor prefers a textarea, but found too many issues trying to use createTextareaElement() instead of a simple div, 
		// which is okay in Vaadin where an HTML form won't be used to send the data back and forth.
		setElement(Document.get().createDivElement());

		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		setStyleName(CLASSNAME);
	}

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		this.client = client;
		paintableId = uidl.getId();

		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		// If client.updateComponent returns true there have been no changes
		// and we do not need to update anything.
		if ( client.updateComponent(this, uidl, true) ) {
			return;
		}

		immediate = uidl.getBooleanAttribute("immediate");

		dataBeforeEdit = uidl.getStringVariable("text");

		if ( ckEditor == null ) {
			// Save the client side identifier (paintable id) for the widget
			getElement().setId(paintableId);	
			//getElement().setInnerHTML("<p></p>");

			String inPageConfig = uidl.hasAttribute("inPageConfig") ? uidl.getStringAttribute("inPageConfig") : null;
			
			// See if we have any writer rules
			int i = 0;
			while( true ) {
				if ( ! uidl.hasAttribute("writerRules.tagName"+i)  ) {
					break;
				}
				// Save the rules until our instance is ready
				String tagName = uidl.getStringAttribute("writerRules.tagName"+i);
				String jsRule  = uidl.getStringAttribute("writerRules.jsRule"+i);
				if ( writerRules == null ) {
					writerRules = new HashMap<String,String>();
				}
				writerRules.put(tagName, jsRule);
				++i;
			}
			
			ckEditor = (CKEditor)CKEditorService.loadEditor(paintableId, this, inPageConfig);
		}
		
		ckEditor.setData(dataBeforeEdit);
	}

	// Listener callback
	@Override
	public void onSave() {
		if ( client != null && paintableId != null && ckEditor != null ) {
			// Called if the user clicks the Save button. 
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				client.updateVariable(paintableId, "text", data, true);
				dataBeforeEdit = data;
			}
		}
	}

	// Listener callback
	@Override
	public void onBlur() {
		if ( client != null && paintableId != null && ckEditor != null ) {
			boolean sendBlurEvent = false;
			boolean sendValueChange = false;
			
			if ( client.hasEventListeners(this, EventId.BLUR) ) {
	            sendBlurEvent = true;
	            client.updateVariable(paintableId, EventId.BLUR, "", false);
			}
			
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				sendValueChange = immediate;
				client.updateVariable(paintableId, "text", data, false);
				dataBeforeEdit = data;
			}
			
	        if (sendBlurEvent || sendValueChange) {
	            client.sendPendingVariableChanges();
	        }
		}
	}

	// Listener callback
	@Override
	public void onFocus() {
		if ( client != null && paintableId != null ) {
			if ( client.hasEventListeners(this, EventId.FOCUS) ) {
	            client.updateVariable(paintableId, EventId.FOCUS, "", true);
			}
		}
	}

	// Listener callback
	@Override
	public void onInstanceReady() {
		if ( writerRules != null ) {
			Set<String> tagNameSet = writerRules.keySet();
			for( String tagName : tagNameSet ) {
				ckEditor.setWriterRules(tagName, writerRules.get(tagName));
			}
			writerRules = null; // don't need them anymore
		}
	}
}
