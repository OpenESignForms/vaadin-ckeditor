// Copyright (C) 2010-2013 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VaadinCKEditorApplication extends Application {
	private static final long serialVersionUID = -3106462237499366300L;

	@Override
	public void init() {
		setTheme("ckexample"); // not needed, but here to show if you wanted to style the v-ckeditortextfield style defined in VAADIN/themes/ckexample/styles.css
		
		// Using CssLayout(), we found IE8 would lose its editors if you clicked any button after initial load.
		// However, if you loaded, then clicked RELOAD/REFRESH in IE8, it wouldn't happen.
		// Then we found it doesn't happen at all with VerticalLayout.
		// Window mainWindow = new Window("Vaadin CKEditor Application", new CssLayout());
		Window mainWindow = new Window("Vaadin CKEditor Application", new VerticalLayout());
		mainWindow.setSizeFull();
		setMainWindow(mainWindow);
		
		mainWindow.addComponent(new Button("Hit server"));


		/* This is the full list as we know it in CKEditor 4.x
	[
    { name: 'document', items : [ 'Source','-','NewPage','Preview','Print','-','Templates' ] },
	{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
	{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
	{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
	'/',
	{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
	{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
	{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },
	{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
	'/',
	{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
	{ name: 'colors', items : [ 'TextColor','BGColor' ] },
	{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }
	]
		 */
		
		CKEditorConfig config1 = new CKEditorConfig();
		config1.useCompactTags();
		config1.disableElementsPath();
		config1.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config1.disableSpellChecker();
		config1.setHeight("300px");
		
		final CKEditorTextField ckEditorTextField1 = new CKEditorTextField(config1);
		ckEditorTextField1.setHeight(440,Sizeable.UNITS_PIXELS); // account for 300px editor plus toolbars
		mainWindow.addComponent(ckEditorTextField1);
		
		final String editor1InitialValue = 
			"<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p><h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1><h1>And CKEditor is no slouch either.</h1>";

		ckEditorTextField1.setValue(editor1InitialValue);
		ckEditorTextField1.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 54555014513845952L;

			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification("CKEditor v" + ckEditorTextField1.getVersion() + "/" + getVersion() + " - #1 contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
				ckEditorTextField1.focus();
			}
		});
		
		Button resetTextButton1 = new Button("Reset editor #1");
		resetTextButton1.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -5568890922280949248L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! ckEditorTextField1.isReadOnly() ) {
					ckEditorTextField1.setValue(editor1InitialValue);
				}
			}
		});
		mainWindow.addComponent(resetTextButton1);
		
		Button toggleReadOnlyButton1 = new Button("Toggle read-only editor #1");
		toggleReadOnlyButton1.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -2447367323473949937L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField1.setReadOnly( ! ckEditorTextField1.isReadOnly() );
			}
		});
		mainWindow.addComponent(toggleReadOnlyButton1);

		Button toggleViewWithoutEditorButton1 = new Button("Toggle view-without-editor #1");
		toggleViewWithoutEditorButton1.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -2921679712379393004L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField1.setViewWithoutEditor( ! ckEditorTextField1.isViewWithoutEditor() );
			}
		});
		mainWindow.addComponent(toggleViewWithoutEditorButton1);

		
		// Now add in a second editor....
		final String editor2InitialValue = 
			"<p>Here is editor #2.</p><h1>Hope you find this useful in your Vaadin projects.</h1>";

		final CKEditorTextField ckEditorTextField2 = new CKEditorTextField();
		ckEditorTextField2.setWidth(600,Sizeable.UNITS_PIXELS);
		mainWindow.addComponent(ckEditorTextField2);
		
		CKEditorConfig config2 = new CKEditorConfig();
		config2.addCustomToolbarLine("{ items : ['Source','Styles','Bold','VaadinSave','-','Undo','Redo','-','NumberedList','BulletedList'] }");
		config2.enableVaadinSavePlugin();
		config2.addToRemovePlugins("scayt");
		ckEditorTextField2.setConfig(config2);
		ckEditorTextField2.setValue(editor2InitialValue);
		
		ckEditorTextField2.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 5261774097251439369L;

			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - #2 contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
			}
		});
		
		Button resetTextButton2 = new Button("Reset editor #2");
		resetTextButton2.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 8516010622125693968L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! ckEditorTextField2.isReadOnly() ) {
					ckEditorTextField2.setValue(editor2InitialValue);
				}
			}
		});
		mainWindow.addComponent(resetTextButton2);
		
		Button toggleReadOnlyButton2 = new Button("Toggle read-only editor #2");
		toggleReadOnlyButton2.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -6120226593606697001L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField2.setReadOnly( ! ckEditorTextField2.isReadOnly() );
			}
		});
		mainWindow.addComponent(toggleReadOnlyButton2);

		Button toggleViewWithoutEditorButton2 = new Button("Toggle view-without-editor #2");
		toggleViewWithoutEditorButton2.addListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 5213329273386193909L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField2.setViewWithoutEditor( ! ckEditorTextField2.isViewWithoutEditor() );
			}
		});
		mainWindow.addComponent(toggleViewWithoutEditorButton2);

		mainWindow.addComponent(new Button("Open Modal Subwindow", new ClickListener() {                      
 			private static final long serialVersionUID = 11893019141203575L;

			@Override
            public void buttonClick(ClickEvent event) {
                    Window sub = new Window("Subwindow modal");
                    
                    CKEditorConfig config = new CKEditorConfig();
                    config.useCompactTags();
                    config.disableElementsPath();
                    config.disableSpellChecker();
                    config.enableVaadinSavePlugin();
                    // set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
                    // in a window that's on top of the main two editors of this demo app
                    config.setBaseFloatZIndex(11000); 
                    config.setHeight("150px");
                    
                    final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
	                ckEditorTextField.addListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = 3363878288276305352L;

						public void valueChange(ValueChangeEvent event) {
	        				getMainWindow().showNotification("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - POPUP MODAL contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
	        			}
	        		});
	                ckEditorTextField.focus();
                    
                    sub.addComponent(ckEditorTextField);
                    
                    sub.setWidth("80%");
                    sub.setModal(true);
                    sub.center();
                    
                    event.getButton().getWindow().addWindow(sub);
            }
        }));

        mainWindow.addComponent(new Button("Open Non-Modal Subwindow with 100% Height", new ClickListener() {                      
			private static final long serialVersionUID = -5307249244055251999L;

			@Override
	        public void buttonClick(ClickEvent event) {
	                Window sub = new Window("Subwindow non-modal 100% height");
	                sub.setWidth("80%");
	                sub.setHeight(500, Sizeable.UNITS_PIXELS);

	                VerticalLayout layout = (VerticalLayout)sub.getContent();
	                layout.setSizeFull();
	                
	                CKEditorConfig config = new CKEditorConfig();
	                config.useCompactTags();
	                config.disableElementsPath();
	                config.disableSpellChecker();
	                config.enableVaadinSavePlugin();
                    // set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
                    // in a window that's on top of the main two editors of this demo app
                    config.setBaseFloatZIndex(11000); 
                    config.setStartupFocus(true);
	                
	                final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
	                ckEditorTextField.setHeight(100, Sizeable.UNITS_PERCENTAGE);
	                ckEditorTextField.addListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = 8232630568806322179L;

						public void valueChange(ValueChangeEvent event) {
	        				getMainWindow().showNotification("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().toString().replaceAll("<", "&lt;"));
	        			}
	        		});
	                layout.addComponent(ckEditorTextField);
	                layout.setExpandRatio(ckEditorTextField,10);
	                
	                final TextField textField = new TextField("TextField");
	                textField.addListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = -8819755603233116234L;

						public void valueChange(ValueChangeEvent event) {
	        				getMainWindow().showNotification("TextField - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().toString());
	        			}
	        		});
	                layout.addComponent(textField);
	                
	                sub.center();
	                
	                event.getButton().getWindow().addWindow(sub);
	        }
        }));
	}
	
	@Override
	public String getVersion() {
		return "1.8.2";
	}

}
