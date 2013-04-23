// Copyright (C) 2010-2013 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
// It was later converted to extend AbstractField instead of com.vaadin.ui.TextField, at which time some of the code from TextField was used here.
//
package org.vaadin.openesignforms.ckeditor;

import java.util.Map;
import java.util.Set;

import org.vaadin.openesignforms.ckeditor.widgetset.client.ui.VCKEditorTextField;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;

/**
 * Server side component for the VCKEditorTextField widget.  
 */
@com.vaadin.ui.ClientWidget(VCKEditorTextField.class)
public class CKEditorTextField extends AbstractField 
	implements FieldEvents.BlurNotifier, FieldEvents.FocusNotifier, Component.Focusable  {
	
	private static final long serialVersionUID = -37444047694136727L;

	private CKEditorConfig config;
	private String version = "unknown";
	private String insertText = null;
	private String insertHtml = null;
	private boolean protectedBody = false;
	private boolean viewWithoutEditor = false;
	private boolean focusRequested = false;

	public CKEditorTextField() {
		super.setValue("");
		setWidth(100,UNITS_PERCENTAGE);
		setHeight(300,UNITS_PIXELS);
	}
	
	public CKEditorTextField(CKEditorConfig config) {
		this();
		setConfig(config);
	}
	
	public void setConfig(CKEditorConfig config) {
		this.config = config;
	}
	
	public String getVersion() {
		return version;
	}
	
	@Override
    public void setValue(Object newValue) throws Property.ReadOnlyException, Property.ConversionException {
    	if ( newValue == null )
    		newValue = "";
    	super.setValue(newValue.toString(), false);
    	requestRepaint();
    }
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		
		Object currValueObject = getValue();
		String currValue = currValueObject == null ? "" : currValueObject.toString();
		target.addVariable(this, VCKEditorTextField.VAR_TEXT, currValue);
		
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
        super.changeVariables(source, variables);

        // Sets the CKEditor version
        if (variables.containsKey(VCKEditorTextField.VAR_VERSION)) {
        	version = (String)variables.get(VCKEditorTextField.VAR_VERSION);
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
    }


	@Override
	public Class<?> getType() {
		return String.class;
	}
	
	@Override
	public void addListener(BlurListener listener) {
        addListener(BlurEvent.EVENT_ID, BlurEvent.class, listener,
                BlurListener.blurMethod);
	}

	@Override
	public void removeListener(BlurListener listener) {
        removeListener(BlurEvent.EVENT_ID, BlurEvent.class, listener);
	}

	@Override
	public void addListener(FocusListener listener) {
        addListener(FocusEvent.EVENT_ID, FocusEvent.class, listener,
                FocusListener.focusMethod);
	}

	@Override
	public void removeListener(FocusListener listener) {
        removeListener(FocusEvent.EVENT_ID, FocusEvent.class, listener);
	}
	
	@Override
    public void setHeight(float height, int unit) {
		super.setHeight(height,unit);
	}
	@Override
    public void setHeight(String height) {
		super.setHeight(height);
	}

	@Override
	public void detach() {
		super.detach();
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
}
