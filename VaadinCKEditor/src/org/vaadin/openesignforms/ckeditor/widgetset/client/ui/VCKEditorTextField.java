// Copyright (C) 2010-2011 Yozons, Inc.
// CKEditor for Vaadin- Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.EventId;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side CKEditor widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VCKEditorTextField extends Widget implements Paintable, CKEditorService.CKEditorListener, Focusable {
	
	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-ckeditortextfield";
	
	public static final String ATTR_IMMEDIATE = "immediate";
	public static final String ATTR_READONLY = "readonly";
	public static final String ATTR_INPAGECONFIG = "inPageConfig";
	public static final String ATTR_WRITERRULES_TAGNAME = "writerRules.tagName";
	public static final String ATTR_WRITERRULES_JSRULE = "writerRules.jsRule";
	public static final String ATTR_WRITER_INDENTATIONCHARS = "writerIndentationChars";
	public static final String ATTR_INSERT_HTML = "insert_html";
	public static final String ATTR_INSERT_TEXT = "insert_text";
	public static final String VAR_TEXT = "text";
	public static final String VAR_VERSION = "version";
	
	private static boolean initializedCKEDITOR = false;
	private static String ckeditorVersion;

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection clientToServer;
	
	private String dataBeforeEdit = null;
	

	private boolean immediate;
	private boolean readOnly;
	
	private CKEditor ckEditor = null;
	private boolean ckEditorIsReady = false;
	
	private HashMap<String,String> writerRules = null;
	private String writerIndentationChars = null;
	
	private int tabIndex;
	private boolean setFocusAfterReady;
	
	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VCKEditorTextField() {
		// Any one-time library initializations go here
		if ( ! initializedCKEDITOR ) {
			CKEditorService.overrideBlurToForceBlur();
			ckeditorVersion = CKEditorService.version();
			initializedCKEDITOR = true;
		}

		// CKEditor prefers a textarea, but found too many issues trying to use createTextareaElement() instead of a simple div, 
		// which is okay in Vaadin where an HTML form won't be used to send the data back and forth.
		DivElement rootDiv = Document.get().createDivElement();
		rootDiv.getStyle().setOverflow(Overflow.AUTO);
		rootDiv.getStyle().setVisibility(Visibility.VISIBLE); // required for FF to show in popup windows repeatedly
		setElement(rootDiv);

		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		setStyleName(CLASSNAME);
	}
	
	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		clientToServer = client;
		paintableId = uidl.getId();
		boolean needsDataUpdate = false;
		
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		// If clientToServer.updateComponent returns true there have been no changes
		// and we do not need to update anything.
		if ( clientToServer.updateComponent(this, uidl, true) ) {
			return;
		}
		
		if ( uidl.hasAttribute(ATTR_IMMEDIATE) ) {
	 		immediate = uidl.getBooleanAttribute(ATTR_IMMEDIATE);
		}
		if ( uidl.hasAttribute(ATTR_READONLY) ) {
			readOnly = uidl.getBooleanAttribute(ATTR_READONLY);
		}
		if ( uidl.hasVariable(VAR_TEXT) ) {
			String data = uidl.getStringVariable(VAR_TEXT);
			needsDataUpdate = !data.equals(dataBeforeEdit);
			dataBeforeEdit = data;
		}
		
		// Save the client side identifier (paintable id) for the widget
		if ( ! paintableId.equals(getElement().getId()) ) {
			getElement().setId(paintableId);
		}
		
		if ( readOnly ) {
			if ( ckEditor != null ) {
				dataBeforeEdit = ckEditor.getData();
				ckEditor.destroy(true);
				ckEditorIsReady = false;
				ckEditor = null;
			}
			getElement().setInnerHTML(dataBeforeEdit);
		}
		else if ( ckEditor == null ) {
			getElement().setInnerHTML(""); // in case we put contents in there while in readonly mode
			
			String inPageConfig = uidl.hasAttribute(ATTR_INPAGECONFIG) ? uidl.getStringAttribute(ATTR_INPAGECONFIG) : null;
			
			writerIndentationChars = uidl.hasAttribute(ATTR_WRITER_INDENTATIONCHARS) ? uidl.getStringAttribute(ATTR_WRITER_INDENTATIONCHARS) : null;
			
			// See if we have any writer rules
			int i = 0;
			while( true ) {
				if ( ! uidl.hasAttribute(ATTR_WRITERRULES_TAGNAME+i)  ) {
					break;
				}
				// Save the rules until our instance is ready
				String tagName = uidl.getStringAttribute(ATTR_WRITERRULES_TAGNAME+i);
				String jsRule  = uidl.getStringAttribute(ATTR_WRITERRULES_JSRULE+i);
				if ( writerRules == null ) {
					writerRules = new HashMap<String,String>();
				}
				writerRules.put(tagName, jsRule);
				++i;
			}
			
			ckEditor = (CKEditor)CKEditorService.loadEditor(paintableId, this, inPageConfig);
			// editor data and some options are set when the instance is ready....
		} else if ( ckEditorIsReady ) {
			if ( needsDataUpdate ) {
				ckEditor.setData(dataBeforeEdit);
			}
			
			if (uidl.hasAttribute(ATTR_INSERT_HTML)) {
				ckEditor.insertHtml(uidl.getStringAttribute(ATTR_INSERT_HTML));
			}
			
			if (uidl.hasAttribute(ATTR_INSERT_TEXT)) {
				ckEditor.insertText(uidl.getStringAttribute(ATTR_INSERT_TEXT));
			}
		}
		
	}

	// Listener callback
	@Override
	public void onSave() {
		if ( ckEditorIsReady ) {
			// Called if the user clicks the Save button. 
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				clientToServer.updateVariable(paintableId, VAR_TEXT, data, true);
				dataBeforeEdit = data; // update our image since we sent it to the server
			}
		}
	}

	// Listener callback
	@Override
	public void onBlur() {
		if ( ckEditorIsReady ) {
			boolean sendToServer = false;
			
			if ( clientToServer.hasEventListeners(this, EventId.BLUR) ) {
				sendToServer = true;
	            clientToServer.updateVariable(paintableId, EventId.BLUR, "", false);
			}
			
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				clientToServer.updateVariable(paintableId, VAR_TEXT, data, false);
	            if (immediate) {
	            	sendToServer = true;
	            	dataBeforeEdit = data; // let's only update our image if we're going to send new data to the server
	            }
			}
			
	        if (sendToServer) {
	            clientToServer.sendPendingVariableChanges();
	        }
		}
	}

	// Listener callback
	@Override
	public void onFocus() {
		if ( ckEditorIsReady ) {
			if ( clientToServer.hasEventListeners(this, EventId.FOCUS) ) {
	            clientToServer.updateVariable(paintableId, EventId.FOCUS, "", true);
			}
		}
	}

	// Listener callback
	@Override
	public void onInstanceReady() {
		ckEditor.instanceReady(this);
		
		if ( writerRules != null ) {
			Set<String> tagNameSet = writerRules.keySet();
			for( String tagName : tagNameSet ) {
				ckEditor.setWriterRules(tagName, writerRules.get(tagName));
			}
			writerRules = null; // don't need them anymore
		}
		
		if ( writerIndentationChars != null ) {
			ckEditor.setWriterIndentationChars(writerIndentationChars);
			writerIndentationChars = null;
		}
		
		if ( dataBeforeEdit != null ) {
			ckEditor.setData(dataBeforeEdit);
		}
				
		ckEditorIsReady = true;
		
		if (setFocusAfterReady) {
			ckEditor.focus();
		}
		
		doResize();
		
		clientToServer.updateVariable(paintableId, VAR_VERSION, ckeditorVersion, true);
	}
	
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		doResize();
	}
	
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		doResize();
	}
	
	protected void doResize() {
		if (ckEditorIsReady) {
			ckEditor.resize(super.getOffsetWidth(), super.getOffsetHeight());
		}
	}

	@Override
	protected void onUnload() {
		if ( ckEditor != null ) {
			ckEditor.destroy();
			ckEditor = null;
		}
		ckEditorIsReady = false;
	}

	@Override
	public void onChange() {
		if ( ckEditor != null ) {
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				clientToServer.updateVariable(paintableId, VAR_TEXT, data, false);
	            if (immediate) {
	            	dataBeforeEdit = data; // let's only update our image if we're going to send new data to the server
	            }
			}
		}
	}

	@Override
	public int getTabIndex() {
		if (ckEditorIsReady) {
			return ckEditor.getTabIndex();
		} else {
			return tabIndex;
		}
	}

	@Override
	public void setAccessKey(char arg0) {
		return;
	}

	@Override
	public void setFocus(boolean arg0) {
		if (arg0) {
			if (ckEditorIsReady)
				ckEditor.focus();
			else
				setFocusAfterReady = true;
		}
	}

	@Override
	public void setTabIndex(int tabIndex) {
		if (ckEditorIsReady) {
			ckEditor.setTabIndex(tabIndex);
		} 
		this.tabIndex = tabIndex;
	}

}
