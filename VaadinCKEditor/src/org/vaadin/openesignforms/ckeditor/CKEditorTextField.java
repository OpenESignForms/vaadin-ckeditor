// Copyright (C) 2010-2017 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
// It was later converted to extend AbstractField instead of com.vaadin.ui.TextField, at which time some of the code from TextField was used here.
//
package org.vaadin.openesignforms.ckeditor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.vaadin.openesignforms.ckeditor.widgetset.client.ui.VCKEditorTextField;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.LegacyComponent;
import com.vaadin.util.ReflectTools;

/**
 * Server side component for the VCKEditorTextField widget.  
 */
public class CKEditorTextField extends AbstractField<String> 
	implements FieldEvents.BlurNotifier, FieldEvents.FocusNotifier, Component.Focusable, LegacyComponent  {
	private static final long serialVersionUID = -4416787199757304854L;

	private CKEditorConfig config;
	private String version = "unknown";
	private String insertText = null;
	private String insertHtml = null;
	private boolean protectedBody = false;
	private boolean viewWithoutEditor = false;
	private boolean focusRequested = false;
	protected LinkedList<VaadinSaveListener> vaadinSaveListenerList;

	private boolean textIsDirty;

	public CKEditorTextField() {
		super.setValue("");
		setWidth("100%");
		setHeight("300px");
	}
	
	public CKEditorTextField(CKEditorConfig config) {
		this();
		setConfig(config);
	}
	
	public CKEditorTextField(CKEditorConfig config, String initialValue) {
		this();
		setValue(initialValue);
		setConfig(config);
	}
	
	public void setConfig(CKEditorConfig config) {
		this.config = config;
		if ( config.isReadOnly() )
			setReadOnly(true);
	}
	
	public String getVersion() {
		return version;
	}
	
	@Override
    public void setValue(String newValue) throws Property.ReadOnlyException, Converter.ConversionException {
    	if ( newValue == null )
    		newValue = "";
    	super.setValue(newValue, false);  // will call setInternalValue
    	requestRepaint();
    }
	
	@Override
	protected void setInternalValue(String newValue) {
		super.setInternalValue(newValue==null?"":newValue);
    	textIsDirty = true;
    }
	
 	@Override
 	public void setPropertyDataSource(Property newDataSource) {
 		super.setPropertyDataSource(newDataSource);
 		markAsDirty();
     	textIsDirty = true;
 	}
 
 	@Override
 	protected void fireValueChange(boolean repaintIsNotNeeded) {
 		super.fireValueChange(repaintIsNotNeeded);
 		textIsDirty = true;
 	}	
 	
 	@Override
	public void beforeClientResponse(boolean initial) {
		if (initial) {
			textIsDirty = true;
		}
		super.beforeClientResponse(initial);
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		//super.paintContent(target);
		
		if (textIsDirty) {
			Object currValueObject = getValue();
			String currValue = currValueObject == null ? "" : currValueObject.toString();
			target.addVariable(this, VCKEditorTextField.VAR_TEXT, currValue);
			textIsDirty = false;
		}
		
		target.addAttribute(VCKEditorTextField.ATTR_READONLY, isReadOnly());
		target.addAttribute(VCKEditorTextField.ATTR_VIEW_WITHOUT_EDITOR, isViewWithoutEditor());
		//System.out.println("*** TRACE FROM SERVER paintContent() - sending value to browser (" + currValue.length() + ") >>>" + currValue + "<<< " + System.currentTimeMillis());
		
		if (config != null) {
			target.addAttribute(VCKEditorTextField.ATTR_INPAGECONFIG, config.getInPageConfig());
			
			if ( config.hasWriterRules() ) {
				int i = 0;
				Set<String> tagNameSet = config.getWriterRulesTagNames();
				for( String tagName : tagNameSet ) {
					target.addAttribute(VCKEditorTextField.ATTR_WRITERRULES_TAGNAME+i, tagName);
					target.addAttribute(VCKEditorTextField.ATTR_WRITERRULES_JSRULE+i, config.getWriterRuleByTagName(tagName));
					++i;
				}
			}
			
			if ( config.hasWriterIndentationChars() ) {
				target.addAttribute(VCKEditorTextField.ATTR_WRITER_INDENTATIONCHARS, config.getWriterIndentationChars());
			}
			
			if ( config.hasKeystrokeMappings() ) {
				int i = 0;
				Set<Integer> keystrokeSet = config.getKeystrokes();
				for( Integer keystroke : keystrokeSet ) {
					target.addAttribute(VCKEditorTextField.ATTR_KEYSTROKES_KEYSTROKE+i, keystroke);
					target.addAttribute(VCKEditorTextField.ATTR_KEYSTROKES_COMMAND+i, config.getKeystrokeCommandByKeystroke(keystroke));
					++i;
				}
			}
			
			if ( config.hasProtectedSource() ) {
				int i = 0;
				for( String protectedSourceRegex : config.getProtectedSource() ) {
					target.addAttribute(VCKEditorTextField.ATTR_PROTECTED_SOURCE+i, protectedSourceRegex);
					++i;
				}
			}
		}
		
		target.addAttribute(VCKEditorTextField.ATTR_PROTECTED_BODY, protectedBody);
		
		if (insertHtml != null) {
			target.addAttribute(VCKEditorTextField.ATTR_INSERT_HTML, insertHtml);
			insertHtml = null;
		}
		if (insertText != null) {
			target.addAttribute(VCKEditorTextField.ATTR_INSERT_TEXT, insertText);
			insertText = null;
		}	
		
		if ( focusRequested ) {
			target.addAttribute(VCKEditorTextField.ATTR_FOCUS, true);
			focusRequested = false;
		}
	}
	
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        //super.changeVariables(source, variables);

        // Sets the CKEditor version
        if (variables.containsKey(VCKEditorTextField.VAR_VERSION)) {
        	version = (String)variables.get(VCKEditorTextField.VAR_VERSION);
        }
        
        // Sets the text
        if (variables.containsKey(VCKEditorTextField.VAR_TEXT) && ! isReadOnly()) {
            // Only do the setting if the string representation of the value has been updated
        	Object newVarTextObject = variables.get(VCKEditorTextField.VAR_TEXT);
            String newValue = newVarTextObject == null ? "" : newVarTextObject.toString();

            Object currValueObject = getValue();
            final String oldValue = currValueObject == null ? "" : currValueObject.toString();
            if ( ! newValue.equals(oldValue) ) {
        		//System.out.println("*** TRACE FROM CLIENT changeVariables() - new value (" + newValue.length() + ") >>>" + newValue + "<<< " + System.currentTimeMillis());
                setValue(newValue, true);
            }
        }
        
        if (variables.containsKey(FocusEvent.EVENT_ID)) {
			//System.out.println("------------------------------");
    		//System.out.println("*** TRACE FROM CLIENT changeVariables() - FOCUS - " + System.currentTimeMillis());
            fireEvent(new FocusEvent(this));
        }
        if (variables.containsKey(BlurEvent.EVENT_ID)) {
    		//System.out.println("*** TRACE FROM CLIENT changeVariables() - BLUR - " + System.currentTimeMillis());
            fireEvent(new BlurEvent(this));
        }
        
        if (variables.containsKey(SelectionChangeEvent.EVENT_ID)) {
        	Object selectedHtmlObject = variables.get(SelectionChangeEvent.EVENT_ID);
            if ( selectedHtmlObject != null ) {
            	String selectedHtml = selectedHtmlObject.toString();
            	fireEvent(new SelectionChangeEvent(this,selectedHtml));
            }
        }

        // See if the vaadinsave button was pressed
        if (variables.containsKey(VCKEditorTextField.VAR_VAADIN_SAVE_BUTTON_PRESSED) && ! isReadOnly()) {
        	notifyVaadinSaveListeners();
        }
    }


	@Override
	public Class<String> getType() {
		return String.class;
	}
	
	@Override
	public void addListener(BlurListener listener) {
        addListener(BlurEvent.EVENT_ID, BlurEvent.class, listener, BlurListener.blurMethod);
	}
	@Override
	public void addBlurListener(BlurListener listener) {
		addListener(BlurEvent.EVENT_ID, BlurEvent.class, listener, BlurListener.blurMethod);
	}
	
	@Override
	public void removeListener(BlurListener listener) {
        removeListener(BlurEvent.EVENT_ID, BlurEvent.class, listener);
	}
	@Override
	public void removeBlurListener(BlurListener listener) {
		removeListener(BlurEvent.EVENT_ID, BlurEvent.class, listener);
	}
	
	@Override
	public void addListener(FocusListener listener) {
        addListener(FocusEvent.EVENT_ID, FocusEvent.class, listener, FocusListener.focusMethod);
	}
	@Override
	public void addFocusListener(FocusListener listener) {
		addListener(FocusEvent.EVENT_ID, FocusEvent.class, listener, FocusListener.focusMethod);
	}

	@Override
	public void removeListener(FocusListener listener) {
        removeListener(FocusEvent.EVENT_ID, FocusEvent.class, listener);
	}
	@Override
	public void removeFocusListener(FocusListener listener) {
		removeListener(FocusEvent.EVENT_ID, FocusEvent.class, listener);
	}
	
	/**
	 * @param listener
	 */
	public void addSelectionChangeListener(SelectionChangeListener listener) {
		addListener(SelectionChangeEvent.EVENT_ID, SelectionChangeEvent.class, listener, SelectionChangeListener.selectionChangeMethod);
	}
	public void removeSelectionChangeListener(SelectionChangeListener listener) {
		removeListener(SelectionChangeEvent.EVENT_ID, SelectionChangeEvent.class, listener);
	}
	
	@Override
    public void setHeight(String height) {
		super.setHeight(height);
	}

	@Override
	public void attach() {
		//System.out.println("** CKEDITOR DEBUG: attach()");
		super.attach();
	}
	
	@Override
	public void detach() {
		//System.out.println("** CKEDITOR DEBUG: detach()");
		super.detach();
	    textIsDirty = true;
	}
	
	// Part of Focusable
	@Override
    public void focus() {
		super.focus();
		focusRequested = true;
		requestRepaint();
    }
	
	public boolean isViewWithoutEditor() {
		return viewWithoutEditor;
	}
	public void setViewWithoutEditor(boolean v) {
		viewWithoutEditor = v;
		requestRepaint();
	}
	
	public void insertHtml(String html) {
		if (insertHtml == null) 
			insertHtml = html;
		else 
			insertHtml += html;
		requestRepaint();
	}
	
	public void insertText(String text) {
		if (insertText == null) 
			insertText = text;
		else 
			insertText += text;
		requestRepaint();
	}

	public void setProtectedBody(boolean protectedBody) {
		this.protectedBody = protectedBody;
		requestRepaint();
	}

	public boolean isProtectedBody() {
		return protectedBody;
	}
	
	
	public synchronized void addVaadinSaveListener(VaadinSaveListener listener) {
		if ( vaadinSaveListenerList == null )
			vaadinSaveListenerList = new LinkedList<VaadinSaveListener>();
		vaadinSaveListenerList.add(listener);
	}
	public synchronized void removeVaadinSaveListener(VaadinSaveListener listener) {
		if ( vaadinSaveListenerList != null )
			vaadinSaveListenerList.remove(listener);
	}
	synchronized void notifyVaadinSaveListeners() {
		if ( vaadinSaveListenerList != null ) {
			for( VaadinSaveListener listener : vaadinSaveListenerList )
				listener.vaadinSave(this);
		}
	}
	
	public interface VaadinSaveListener extends Serializable {
		/**
	     * Notifies this listener that the vaadinsave button in the editor was pressed.
	     * 
	     * @param editor the CKEditorTextField that was saved
	     */
	    public void vaadinSave(CKEditorTextField editor);
	}
	
	
    @SuppressWarnings("serial")
    public static class SelectionChangeEvent extends Component.Event {
        public static final String EVENT_ID = VCKEditorTextField.EVENT_SELECTION_CHANGE;

        private String selectedHtml;
        
        public SelectionChangeEvent(Component source, String selectedHtml) {
            super(source);
            this.selectedHtml = selectedHtml;
        }
        
        public String getSelectedHtml() {
        	return selectedHtml;
        }
        
        public boolean hasSelectedHtml() {
        	return ! "".equals(selectedHtml);
        }
    }

    public interface SelectionChangeListener extends ConnectorEventListener {
        public static final Method selectionChangeMethod = ReflectTools.findMethod(
                SelectionChangeListener.class, "selectionChange", SelectionChangeEvent.class);
        
        public void selectionChange(SelectionChangeEvent event);
    }

}
