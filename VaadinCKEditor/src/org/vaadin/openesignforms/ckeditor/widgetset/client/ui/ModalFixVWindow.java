// Copyright (C) 2012-2013 Yozons, Inc.
// CKEditor for Vaadin - Fix for broken VWindow implementation as it relates to modal windows 
// containing CKEditor that then opens its own modal dialogs that no longer work.
//
// Based on code provided by Tien Tran on 6/20/2012 in the Vaadin forums:
// https://vaadin.com/forum/-/message_boards/view_message/1515632
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
package org.vaadin.openesignforms.ckeditor.widgetset.client.ui;

import com.google.gwt.user.client.Event;
import com.vaadin.client.ui.VWindow;

/**
 * <p>
 * A placement of VWindow implementation to fix issue with CKEditor component in a modal window.
 * 
 * Setup your own widgetset and have this somewhere in the module.xml:
 *   <code>
 *   <replace-with class="org.vaadin.openesignforms.ckeditor.widgetset.client.ui.ModalFixVWindow">
 *       <when-type-is class="com.vaadin.client.ui.VWindow"/>
 *   </replace-with>
 *   </code>
 * </p>
 * 
 * @see http://code.google.com/p/vaadin-ckeditor/issues/detail?id=10
 * @see https://vaadin.com/forum/-/message_boards/view_message/238571
 * @author ttran, Yozons
 */
public class ModalFixVWindow extends VWindow {
	
    @Override
    public boolean onEventPreview(final Event event)
    {
        if (vaadinModality)
            return true; // why would they block click to other elements?
        return super.onEventPreview(event);
    }
}
