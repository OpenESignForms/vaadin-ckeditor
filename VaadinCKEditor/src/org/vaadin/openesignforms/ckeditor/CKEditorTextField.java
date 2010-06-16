// Vaadin CKEditor - Widget linkage for using CKEditor within a Vaadin application.
// Copyright (C) 2010 Yozons, Inc.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor;

import java.util.HashMap;
import java.util.Set;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.TextField;

/**
 * Server side component for the VCKEditorTextField widget.
 */
@com.vaadin.ui.ClientWidget(org.vaadin.openesignforms.ckeditor.widgetset.client.ui.VCKEditorTextField.class)
public class CKEditorTextField extends TextField {
	private static final long serialVersionUID = -4458661128362837752L;
	
	private String inPageConfig;
	private HashMap<String,String> writerRules = null;

	public CKEditorTextField() {
		super();
		setWidth("100%");
		setHeight("280px");
	}

	public void setInPageConfig(String js) {
		inPageConfig = js;
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
	}


	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		if (inPageConfig != null) {
			target.addAttribute("inPageConfig", inPageConfig);
		}
		if ( writerRules != null ) {
			int i = 0;
			Set<String> tagNameSet = writerRules.keySet();
			for( String tagName : tagNameSet ) {
				target.addAttribute("writerRules.tagName"+i, tagName);
				target.addAttribute("writerRules.jsRule"+i, writerRules.get(tagName));
				++i;
			}
		}
	}
}
