// Copyright (C) 2013 Yozons, Inc.
// CKEditor for Vaadin - New connector for Vaadin 7 conversion.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import org.vaadin.openesignforms.ckeditor.CKEditorTextField;
import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.client.ui.SimpleManagedLayout;
import com.vaadin.shared.ui.Connect;

@Connect(CKEditorTextField.class)
public class CKEditorConnector extends LegacyConnector  implements SimpleManagedLayout {
	private static final long serialVersionUID = -3096333767592095605L;

	@Override
    public VCKEditorTextField getWidget() {
        return (VCKEditorTextField) super.getWidget();
    }

    @Override
    public void layout() {
        // TODO Auto-generated method stub
        
    } 

}
