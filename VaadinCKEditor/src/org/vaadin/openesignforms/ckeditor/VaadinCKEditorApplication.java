// Vaadin CKEditor - Widget linkage for using CKEditor within a Vaadin application.
// Copyright (C) 2010 Yozons, Inc.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class VaadinCKEditorApplication extends Application {
	private static final long serialVersionUID = 5644886178209875499L;

	@Override
	public void init() {
		Window mainWindow = new Window("Vaadin CkEditor Application", new CssLayout());
		setMainWindow(mainWindow);
		
		mainWindow.addComponent(new Button("Hit server"));


		/* This is how the default Full toolbar is defined, showing you all the options available for defining your toolbar.
[
  ['Source','-','Save','NewPage','Preview','-','Templates'],
  ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
  ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
  ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
  '/',
  ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
  ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
  ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
  ['Link','Unlink','Anchor'],
  ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
  '/',
  ['Styles','Format','Font','FontSize'],
  ['TextColor','BGColor'],
  ['Maximize', 'ShowBlocks','-','About']
]
		 */
		
		final CKEditorTextField ckEditorTextField1 = new CKEditorTextField();
		
		final String editor1InitialValue = 
			"<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";

		// The following sets to the built-in Basic toolbar. 'Full' is the default toolbar.
		//ckEditorTextField1.setInPageConfig("{ toolbar : 'Basic' }");
		
		// The following defines the toolbar to be a custom toolbar, and defines the custom toolbar.
		ckEditorTextField1.setInPageConfig(
			"{ " +
				"extraPlugins: 'vaadinsave'," + // add this if using the editor's VaadinSave button
				"toolbar : 'Custom'," +
				"toolbar_Custom : [" +
					"['Styles','Format','Bold','Italic','TextColor','BGColor','-','Font','FontSize','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Image','Link']," +
					"'/'," +
					"['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo','-','NumberedList','BulletedList','-','Outdent','Indent','-','Table','HorizontalRule','-','Maximize','-','Source','ShowBlocks','-','VaadinSave']" +
								 "]" +
			" }" 
				     					);

		ckEditorTextField1.useCompactTags();
		//ckEditorTextField.setImmediate(true);
		mainWindow.addComponent(ckEditorTextField1);
		
		ckEditorTextField1.setValue(editor1InitialValue);
		
		ckEditorTextField1.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 54555014513845952L;

			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification("CKEditor #1 contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
			}
		});
		
		Button resetTextButton1 = new Button("Reset editor #1");
		resetTextButton1.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -5568890922280949248L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField1.setValue(editor1InitialValue);
			}
		});
		mainWindow.addComponent(resetTextButton1);
		
		
		// Now add in a second editor....
		final String editor2InitialValue = 
			"<p>Here is editor #2.</p><h1>Hope you find this useful in your Vaadin projects.</h1>";


		final CKEditorTextField ckEditorTextField2 = new CKEditorTextField();
		ckEditorTextField2.setInPageConfig(
				"{ " +
				    "extraPlugins: 'vaadinsave'," + // add this if using the editor's VaadinSave button
					"toolbar : 'Custom'," +
					"toolbar_Custom : [" +
						"['Styles','-','VaadinSave']" +
									 "]" +
				" }" 
				     					);

		mainWindow.addComponent(ckEditorTextField2);
		
		ckEditorTextField2.setValue(editor2InitialValue);
		
		ckEditorTextField2.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 5261774097251439369L;

			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification("CKEditor #2 contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
			}
		});
		
		Button resetTextButton2 = new Button("Reset editor #2");
		resetTextButton2.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 918660321808687898L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField2.setValue(editor2InitialValue);
			}
		});
		mainWindow.addComponent(resetTextButton2);

	}
}
