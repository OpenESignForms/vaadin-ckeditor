// Copyright (C) 2010-2017 Yozons, Inc.
// CKEditor for Vaadin- Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.LayoutManager;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.layout.ElementResizeEvent;
import com.vaadin.client.ui.layout.ElementResizeListener;
import com.vaadin.shared.EventId;

/**
 * Client side CKEditor widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VCKEditorTextField extends Widget implements Paintable, CKEditorService.CKEditorListener, Focusable {
	
	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-ckeditortextfield";
	
	public static final String ATTR_FOCUS = "focus";
	public static final String ATTR_IMMEDIATE = "immediate";
	public static final String ATTR_READONLY = "readonly";
	public static final String ATTR_VIEW_WITHOUT_EDITOR = "viewWithoutEditor";
	public static final String ATTR_INPAGECONFIG = "inPageConfig";
	public static final String ATTR_PROTECTED_SOURCE = "protectedSource";
	public static final String ATTR_WRITERRULES_TAGNAME = "writerRules.tagName";
	public static final String ATTR_WRITERRULES_JSRULE = "writerRules.jsRule";
	public static final String ATTR_WRITER_INDENTATIONCHARS = "writerIndentationChars";
	public static final String ATTR_KEYSTROKES_KEYSTROKE = "keystrokes.keystroke";
	public static final String ATTR_KEYSTROKES_COMMAND = "keystrokes.command";
	public static final String ATTR_INSERT_HTML = "insert_html";
	public static final String ATTR_INSERT_TEXT = "insert_text";
	public static final String ATTR_PROTECTED_BODY = "protected_body";
	public static final String VAR_TEXT = "text";
	public static final String VAR_VAADIN_SAVE_BUTTON_PRESSED = "vaadinsave";
	public static final String VAR_VERSION = "version";
	
	public static final String EVENT_SELECTION_CHANGE = "selectionChange";
	
	private static String ckeditorVersion;

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection clientToServer;
	
	private String inPageConfig = null;
	private String dataBeforeEdit = null;
	private boolean ignoreDataChangesUntilReady = false;
	
	private boolean immediate;
	private boolean readOnly;
	private boolean viewWithoutEditor; // Set to true and the editor will not be displayed, just the contents.
	private boolean protectedBody;
	
	private CKEditor ckEditor = null;
	private boolean ckEditorIsBeingLoaded = false;
	private boolean ckEditorIsReady = false;
	private boolean resizeListenerInPlace = false;
	private boolean notifyBlankSelection = false;
	
	private LinkedList<String> protectedSourceList = null;
	private HashMap<String,String> writerRules = null;
	private String writerIndentationChars = null;
	private HashMap<Integer,String> keystrokeMappings = null;
	
	private int tabIndex;
	private boolean setFocusAfterReady;
	private boolean setTabIndexAfterReady;
	
	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VCKEditorTextField() {
		// CKEditor prefers a textarea, but found too many issues trying to use createTextareaElement() instead of a simple div, 
		// which is okay in Vaadin where an HTML form won't be used to send the data back and forth.
		DivElement rootDiv = Document.get().createDivElement();
		rootDiv.getStyle().setOverflow(Overflow.HIDDEN);
		rootDiv.getStyle().setVisibility(Visibility.VISIBLE); // required for FF to show in popup windows repeatedly
		setElement(rootDiv);

		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		setStyleName(CLASSNAME);
	}
	
	/**
	 * Called whenever an update is received from the server
	 */
	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		clientToServer = client;
		paintableId = uidl.getId();
		
		//logState("updateFromUIDL()");
		
		boolean needsDataUpdate = false;
		boolean needsProtectedBodyUpdate = false;
		boolean readOnlyModeChanged = false;
		
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		// If clientToServer.updateComponent returns true there have been no changes
		// and we do not need to update anything.
		if ( clientToServer.updateComponent(this, uidl, true) ) {
			return;
		}
			
		if ( ! resizeListenerInPlace ) {
			//logState("updateFromUIDL() Adding resize listener to element: " + getElement());
			LayoutManager.get(client).addElementResizeListener(getElement(), new ElementResizeListener() {

				@Override
				public void onElementResize(ElementResizeEvent e) {
					doResize();
				}
			});
			resizeListenerInPlace = true;
		}
		
		if ( uidl.hasAttribute(ATTR_IMMEDIATE) ) {
	 		immediate = uidl.getBooleanAttribute(ATTR_IMMEDIATE);
		}
		if ( uidl.hasAttribute(ATTR_READONLY) ) {
			boolean newReadOnly = uidl.getBooleanAttribute(ATTR_READONLY);
			readOnlyModeChanged = newReadOnly != readOnly;
			readOnly = newReadOnly;
		}
		if ( uidl.hasAttribute(ATTR_VIEW_WITHOUT_EDITOR) ) {
			viewWithoutEditor = uidl.getBooleanAttribute(ATTR_VIEW_WITHOUT_EDITOR);
		}
		if ( uidl.hasAttribute(ATTR_PROTECTED_BODY) ) {
			boolean state = uidl.getBooleanAttribute(ATTR_PROTECTED_BODY);
			if (protectedBody != state) {
				protectedBody = state ;
				needsProtectedBodyUpdate = true;
			}
		}
		if ( uidl.hasVariable(VAR_TEXT) ) {
			String data = uidl.getStringVariable(VAR_TEXT);
			if ( ckEditor != null )
				dataBeforeEdit = ckEditor.getData();
			needsDataUpdate = ! data.equals(dataBeforeEdit);
			//logState("updateFromUIDL() includes VAR_TEXT: " + data + "; needsDataUpdate: " + needsDataUpdate);
			dataBeforeEdit = data;
		}
		
		// Save the client side identifier (paintable id) for the widget
		if ( ! paintableId.equals(getElement().getId()) ) {
			getElement().setId(paintableId);
		}
		
		if ( viewWithoutEditor ) {
			if ( ckEditor != null ) {
				// may update the data and change to viewWithoutEditor at the same time 
				if ( ! needsDataUpdate ) {
					dataBeforeEdit = ckEditor.getData();
				}
				//logState("updateFromUIDL() unloading editor for viewWithoutEditor");
				unloadEditor();
			}
			getElement().setInnerHTML(dataBeforeEdit);
		}
		else if ( ckEditor == null ) {
			//logState("updateFromUIDL() no editor, gathering UIDL attributes");
			getElement().setInnerHTML(""); // in case we put contents in there while in viewWithoutEditor mode
			
			// If we have the config, use it, otherwise attempt to use any prior config we have
			if ( uidl.hasAttribute(ATTR_INPAGECONFIG) )
				inPageConfig = uidl.getStringAttribute(ATTR_INPAGECONFIG); 
			
			if ( uidl.hasAttribute(ATTR_WRITER_INDENTATIONCHARS) )
				writerIndentationChars = uidl.getStringAttribute(ATTR_WRITER_INDENTATIONCHARS);
			
			if ( uidl.hasAttribute(ATTR_FOCUS) ) {
				setFocus(uidl.getBooleanAttribute(ATTR_FOCUS));
			}
			
			// See if we have any writer rules
			int i = 0;
			while( true ) {
				if ( ! uidl.hasAttribute(ATTR_WRITERRULES_TAGNAME+i)  ) {
					break;
				}
				if ( i == 0 && writerRules != null )
					writerRules.clear();
				// Save the rules until our instance is ready
				String tagName = uidl.getStringAttribute(ATTR_WRITERRULES_TAGNAME+i);
				String jsRule  = uidl.getStringAttribute(ATTR_WRITERRULES_JSRULE+i);
				if ( writerRules == null ) {
					writerRules = new HashMap<String,String>();
				}
				writerRules.put(tagName, jsRule);
				++i;
			}
			
			// See if we have any keystrokes
			i = 0;
			while( true ) {
				if ( ! uidl.hasAttribute(ATTR_KEYSTROKES_KEYSTROKE+i)  ) {
					break;
				}
				if ( i == 0 && keystrokeMappings != null )
					keystrokeMappings.clear();
				// Save the keystrokes until our instance is ready
				int keystroke = uidl.getIntAttribute(ATTR_KEYSTROKES_KEYSTROKE+i);
				String command  = uidl.getStringAttribute(ATTR_KEYSTROKES_COMMAND+i);
				if ( keystrokeMappings == null ) {
					keystrokeMappings = new HashMap<Integer,String>();
				}
				keystrokeMappings.put(keystroke, command);
				++i;
			}
			
			// See if we have any protected source regexs
			i = 0;
			while( true ) {
				if ( ! uidl.hasAttribute(ATTR_PROTECTED_SOURCE+i)  ) {
					break;
				}
				if ( i == 0 && protectedSourceList != null )
					protectedSourceList.clear();
				// Save the regex until our instance is ready
				String regex = uidl.getStringAttribute(ATTR_PROTECTED_SOURCE+i);
				if ( protectedSourceList == null ) {
					protectedSourceList = new LinkedList<String>();
				}
				protectedSourceList.add(regex);
				++i;
			}
			
			//logState("updateFromUIDL() creating editor");
			loadEditor();
			
			// editor data and some options are set when the instance is ready....
		} else if ( ckEditorIsReady ) {
			//logState("updateFromUIDL() editor is ready and being updated");
			if ( needsDataUpdate ) {
				setEditorData(dataBeforeEdit);
			}
			
			if ( needsProtectedBodyUpdate ) {
				ckEditor.protectBody(protectedBody);
			}
			
			if (uidl.hasAttribute(ATTR_INSERT_HTML)) {
				ckEditor.insertHtml(uidl.getStringAttribute(ATTR_INSERT_HTML));
			}
			
			if (uidl.hasAttribute(ATTR_INSERT_TEXT)) {
				ckEditor.insertText(uidl.getStringAttribute(ATTR_INSERT_TEXT));
			}

			if ( uidl.hasAttribute(ATTR_FOCUS) ) {
				setFocus(uidl.getBooleanAttribute(ATTR_FOCUS));
			}
			
			if ( readOnlyModeChanged ) {
				ckEditor.setReadOnly(readOnly);
			}			
		}
	}
	
	void setEditorData(String html) {
		if ( ckEditorIsReady ) {
			dataBeforeEdit = html;
			ignoreDataChangesUntilReady = true;
			ckEditor.setData(dataBeforeEdit); // We reset our flag above when the editor tells us the data is ready
		}
	}
	
	void unloadEditor() {
		if ( ckEditor != null ) {
			//logState("unloadEditor() with editor");
			ckEditor.destroy(true);
			ckEditor = null;
		} else {
			//logState("unloadEditor() without editor");
		}
		ignoreDataChangesUntilReady = false;
		ckEditorIsReady = false;
		ckEditorIsBeingLoaded = false;
		setFocusAfterReady = false;
		setTabIndexAfterReady = false;
	}
	
	void loadEditor() {
		if ( ckEditor == null && inPageConfig != null && ! ckEditorIsBeingLoaded ) {
			ckEditorIsBeingLoaded = true;
			//logState("loadEditor() calling CKEditorService.loadLibrary");
			CKEditorService.loadLibrary(new ScheduledCommand() {
				@Override
				public void execute() {
					//logState("loadEditor().execute() calling CKEditorService.loadEditor with inPageConfig: " + inPageConfig);
					ckEditor = (CKEditor)CKEditorService.loadEditor(
							paintableId,
							VCKEditorTextField.this,
							inPageConfig,
							VCKEditorTextField.super.getOffsetWidth(),
							VCKEditorTextField.super.getOffsetHeight());
					ckEditorIsBeingLoaded = false; // Don't need this as we have ckEditor set now.
					//logState("loadEditor().execute() CKEditorService.loadEditor done");
				}
			});
		} else {
			//logState("loadEditor() ignored, no editor, inPageConfig and/or is being loaded already");			
		}
	}

	// Listener callback
	@Override
	public void onSave() {
		if ( ckEditorIsReady && ! readOnly ) {
			// Called if the user clicks the Save button. 
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				//logState("onSave() - data has changed");
				clientToServer.updateVariable(paintableId, VAR_TEXT, data, false);
				dataBeforeEdit = data;
			} else {
				//logState("onSave() - data has NOT changed");
			}
			ignoreDataChangesUntilReady = false; // If they give us data by saving, we don't ignore whatever it is
			clientToServer.updateVariable(paintableId, VAR_VAADIN_SAVE_BUTTON_PRESSED,"",false); // inform that the button was pressed too
			clientToServer.sendPendingVariableChanges(); // ensure anything queued up goes now on SAVE
		}
	}

	// Listener callback
	@Override
	public void onBlur() {
		if ( ckEditorIsReady ) {
			boolean sendToServer = false;
			
			if ( clientToServer.hasEventListeners(this, EventId.BLUR) ) {
				//logState("onBlur() - has blur event listener, will send");
				sendToServer = true;
	            clientToServer.updateVariable(paintableId, EventId.BLUR, "", false);
			}
			else {
				//logState("onBlur() - has no blur event listener, will not send");
			}

			
			// Even though CKEditor 4.2 introduced a change event, it doesn't appear to fire if the user stays in SOURCE mode,
			// so while we do use the change event, we still are stuck with the blur listener to detect other such changes.
			if (  ! readOnly && ! ignoreDataChangesUntilReady ) {
				String data = ckEditor.getData();
				if ( ! data.equals(dataBeforeEdit) ) {
					//logState("onBlur() - data has changed");
					clientToServer.updateVariable(paintableId, VAR_TEXT, data, false);
	            	sendToServer = true;
	            	dataBeforeEdit = data;
				} else {
					//logState("onBlur() - data has NOT changed");
				}
			} else {
				//logState("onBlur() - ignoring data has changed");
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
		ckEditorIsReady = true;

		//logState("onInstanceReady()");
		ckEditor.instanceReady(this);
		
		if ( writerRules != null ) {
			//logState("onInstanceReady() - doing writerRules: " + writerRules.size());
			Set<String> tagNameSet = writerRules.keySet();
			for( String tagName : tagNameSet ) {
				ckEditor.setWriterRules(tagName, writerRules.get(tagName));
			}
		}
		
		if ( writerIndentationChars != null ) {
			//logState("onInstanceReady() - doing writerIndentationChars: " + writerIndentationChars);
			ckEditor.setWriterIndentationChars(writerIndentationChars);
		}
		
		if ( keystrokeMappings != null ) {
			Set<Integer> keystrokeSet = keystrokeMappings.keySet();
			//logState("onInstanceReady() - doing keystrokeMappings: " + keystrokeSet.size());
			for( Integer keystroke : keystrokeSet ) {
				ckEditor.setKeystroke(keystroke, keystrokeMappings.get(keystroke));
			}
		}
		
		if ( protectedSourceList != null ) {
			//logState("onInstanceReady() - doing protectedSourceList: " + protectedSourceList.size());
			for( String regex : protectedSourceList ) {
				ckEditor.pushProtectedSource(regex);
			}
		}
		
		if ( dataBeforeEdit != null ) {
			//logState("onInstanceReady() - doing setEditorData(dataBeforeEdit): " + dataBeforeEdit);
			setEditorData(dataBeforeEdit);
		}
				
		
		if (setFocusAfterReady) {
			//logState("onInstanceReady() - doing setFocusAfterReady");
			setFocus(true);
		}
		
		if ( setTabIndexAfterReady ) {
			//logState("onInstanceReady() - doing setTabIndexAfterReady tabIndex: " + tabIndex);
			setTabIndex(tabIndex);
		}
		
		//logState("onInstanceReady() - doing doResize()");
		doResize();
		
		if (protectedBody) {
			//logState("onInstanceReady() - doing protectedBody: " + protectedBody);
			ckEditor.protectBody(protectedBody);
		}

		//logState("onInstanceReady() - doing setReadOnly: " + readOnly);
		ckEditor.setReadOnly(readOnly);
		
		ckeditorVersion = CKEditorService.version();
		//logState("onInstanceReady() - retrieved ckeditorVersion: " + ckeditorVersion);
		clientToServer.updateVariable(paintableId, VAR_VERSION, ckeditorVersion, true);
	}
	
	// Listener callback
	@Override
	public void onChange() {
		if ( ckEditor != null && ! readOnly && ! ignoreDataChangesUntilReady ) {
			//logState("onChange()");
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) ) {
				//logState("onChange() - data has changed");
				clientToServer.updateVariable(paintableId, VAR_TEXT, data, immediate);
            	dataBeforeEdit = data;
			} else {
				//logState("onChange() - data has NOT changed");
			}
		} else {
			//logState("onChange() changes ignored");
		}
	}
	
	// Listener callback
	@Override
	public void onModeChange(String mode) {
		if ( ckEditor != null ) {
			//logState("onModeChange() mode: " + mode);
			if ( ! readOnly ) {
				String data = ckEditor.getData();
				if ( ! data.equals(dataBeforeEdit) ) {
					//logState("onModeChange() mode: " + mode + " - data has changed");
					clientToServer.updateVariable(paintableId, VAR_TEXT, data, immediate);
	            	dataBeforeEdit = data;
	            	ignoreDataChangesUntilReady = false; // if there's a change while doing SOURCE mode buttons, we'll accept any of those changes.
				} else {
					//logState("onModeChange() mode: " + mode + " - data has NOT changed");	
				}
			} else {
				//logState("onModeChange() is read-only so ignoring data changes in mode: " + mode);
			}
			
			if ("wysiwyg".equals(mode)) {
				ckEditor.protectBody(protectedBody);
			}
		}
	}
	
	// Listener callback
	@Override
	public void onSelectionChange() {
		if ( ckEditorIsReady ) {
			if ( clientToServer.hasEventListeners(this, EVENT_SELECTION_CHANGE) ) {
				//logState("onSelectionChange()");
				String html = ckEditor.getSelectedHtml();
				if ( html == null )
					html = "";
				// We'll send an update for nothing selected (unselected) only if we've sent out an event for a prior selected event.
				boolean isBlankSelection = "".equals(html);
				if ( ! isBlankSelection || notifyBlankSelection ) {
		            clientToServer.updateVariable(paintableId, EVENT_SELECTION_CHANGE, html, true);
		            notifyBlankSelection = ! isBlankSelection;
				}
			}
		}
	}

	// Listener callback
	@Override
	public void onDataReady() {
		if ( ckEditorIsReady ) {
			//logState("onDataReady() - data changes will be accepted after this");
			String data = ckEditor.getData();
			if ( ! data.equals(dataBeforeEdit) && ! ignoreDataChangesUntilReady ) {
				clientToServer.updateVariable(paintableId, VAR_TEXT, data, immediate);				
			}
			ignoreDataChangesUntilReady = false;
			dataBeforeEdit = data;
			ckEditor.protectBody(protectedBody);
		} else {
			//logState("onDataReady() ignored as editor not ready");
		}
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
			//logState("doResize() editor ready doing ScheduledCommand for resize");
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {				
				@Override
				public void execute() {
					//logState("doResize().execute() doing ckEditor.resize");
					ckEditor.resize(VCKEditorTextField.super.getOffsetWidth(), VCKEditorTextField.super.getOffsetHeight());
					//logState("doResize().execute() done");
				}
			});
		} else {
			//logState("doResize() ignored - editor not ready");			
		}
	}

	private void logState(String methodMessage) {
		Logger.getGlobal().info("VCKEditorTextField." + methodMessage + "; ckEditor: " + (ckEditor==null?"None":ckEditor.getId()) + 
				"; ckEditorIsReady: " + ckEditorIsReady + "; ckEditorIsLoading: " + ckEditorIsBeingLoaded + "; ignoreDataChangesUntilReady: " + ignoreDataChangesUntilReady + "; paintableId: " + paintableId);
	}
	
	@Override
	protected void onLoad() {
		//logState("onLoad()");
		if ( ! viewWithoutEditor ) {
			loadEditor();
		}
	}

	@Override
	protected void onUnload() {
		//logState("onUnload()");
		unloadEditor();
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
	public void setTabIndex(int tabIndex) {
		if (ckEditorIsReady) {
			ckEditor.setTabIndex(tabIndex);
		} else {
			setTabIndexAfterReady = true;
		}
		this.tabIndex = tabIndex;
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
		} else {
			setFocusAfterReady = false;
		}
	}

}
