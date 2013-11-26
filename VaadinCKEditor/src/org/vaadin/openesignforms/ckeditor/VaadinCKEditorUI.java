// Copyright (C) 2010-2013 Yozons, Inc.
// CKEditor for Vaadin - Widget linkage for using CKEditor within a Vaadin application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software was originally based on the Vaadin incubator component TinyMCEEditor written by Matti Tahvonen.
//
package org.vaadin.openesignforms.ckeditor;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.UI;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This is a port of VaadinCKEditorApplication 1.8.2 built using Vaadin 6.8.10, and we're giving it version 7.8.2
 * to show it's the same basic code, just ported to Vaadin 7, but using the new VaadinCKEditorUI name.
 * It is essentially a "legacy port" and does not yet take advantage of any of the new capabilities of Vaadin 7.0.5.
 * @author Yozons, Inc.
 */

@Theme("ckexample") // not needed, but here to show if you wanted to style the v-ckeditortextfield style defined in VAADIN/themes/ckexample/styles.css
public class VaadinCKEditorUI extends UI {
	private static final long serialVersionUID = 1445049298116746927L;

	@Override
	public void init(VaadinRequest request) {
		
		getPage().setTitle("Vaadin7 CKEditor UI");
		
		VerticalLayout mainView = new VerticalLayout();
		setContent(mainView);
		
		mainView.addComponent(new Button("Hit server"));
		
		Label separator = new Label("&nbsp;");
		separator.setContentMode(ContentMode.HTML);
		mainView.addComponent(separator); 


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
		
		final String editor1InitialValue = 
				"<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p>\n\n<h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1>\n\n<h1>And CKEditor is no slouch either.</h1>\n";

		CKEditorConfig config1 = new CKEditorConfig();
		config1.useCompactTags();
		config1.disableElementsPath();
		config1.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config1.disableSpellChecker();
		config1.setHeight("300px");
		
		final CKEditorTextField ckEditorTextField1 = new CKEditorTextField(config1);
		ckEditorTextField1.setHeight("440px"); // account for 300px editor plus toolbars
		mainView.addComponent(ckEditorTextField1);
		
		ckEditorTextField1.setValue(editor1InitialValue);
		ckEditorTextField1.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -761434593559159149L;

			public void valueChange(ValueChangeEvent event) {
				Notification.show("CKEditor v" + ckEditorTextField1.getVersion() + "/" + getVersion() + " - #1 contents: " + event.getProperty().getValue().toString());
				ckEditorTextField1.focus();
			}
		});
		
		Button resetTextButton1 = new Button("Reset editor #1");
		resetTextButton1.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 2872667648717255301L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! ckEditorTextField1.isReadOnly() ) {
					ckEditorTextField1.setValue(editor1InitialValue);
				}
			}
		});
		
		Button toggleReadOnlyButton1 = new Button("Toggle read-only editor #1");
		toggleReadOnlyButton1.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 8462908141468254844L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField1.setReadOnly( ! ckEditorTextField1.isReadOnly() );
			}
		});

		Button toggleViewWithoutEditorButton1 = new Button("Toggle view-without-editor #1");
		toggleViewWithoutEditorButton1.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 8122286299515325693L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField1.setViewWithoutEditor( ! ckEditorTextField1.isViewWithoutEditor() );
			}
		});

		Button toggleVisibleButton1 = new Button("Toggle visible editor #1");
		toggleVisibleButton1.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -6715135605688427318L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField1.setVisible( ! ckEditorTextField1.isVisible() );
			}
		});
		HorizontalLayout buttonsLayout = new HorizontalLayout(resetTextButton1,toggleReadOnlyButton1,toggleViewWithoutEditorButton1,toggleVisibleButton1);
		buttonsLayout.setSpacing(true);
		mainView.addComponent( buttonsLayout );

		separator = new Label("&nbsp;");
		separator.setContentMode(ContentMode.HTML);
		mainView.addComponent(separator); 
		
		// Now add in a second editor....
		final String editor2InitialValue = 
			"<p>Here is editor #2.</p>\n\n<p>Hope you find this useful in your Vaadin projects.</p>\n";

		final CKEditorTextField ckEditorTextField2 = new CKEditorTextField();
		ckEditorTextField2.setWidth("600px");
		mainView.addComponent(ckEditorTextField2);
		
		CKEditorConfig config2 = new CKEditorConfig();
		config2.addCustomToolbarLine("{ items : ['Source','Styles','Bold','VaadinSave','-','Undo','Redo','-','NumberedList','BulletedList'] }");
		config2.enableVaadinSavePlugin();
		config2.addToRemovePlugins("scayt");
		ckEditorTextField2.setConfig(config2);
		ckEditorTextField2.setValue(editor2InitialValue);
		
		ckEditorTextField2.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1522230917891035997L;

			public void valueChange(ValueChangeEvent event) {
				Notification.show("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - #2 contents: " + event.getProperty().getValue().toString());
			}
		});

		Button resetTextButton2 = new Button("Reset editor #2");
		resetTextButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 4877506990872691752L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! ckEditorTextField2.isReadOnly() ) {
					ckEditorTextField2.setValue(editor2InitialValue);
				}
			}
		});
		
		Button toggleReadOnlyButton2 = new Button("Toggle read-only editor #2");
		toggleReadOnlyButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 7388801260896778551L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField2.setReadOnly( ! ckEditorTextField2.isReadOnly() );
			}
		});

		Button toggleViewWithoutEditorButton2 = new Button("Toggle view-without-editor #2");
		toggleViewWithoutEditorButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 6042124118599379679L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField2.setViewWithoutEditor( ! ckEditorTextField2.isViewWithoutEditor() );
			}
		});

		Button toggleVisibleButton2 = new Button("Toggle visible editor #2");
		toggleVisibleButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -3804977370320346348L;

			@Override
			public void buttonClick(ClickEvent event) {
				ckEditorTextField2.setVisible( ! ckEditorTextField2.isVisible() );
			}
		});
		
		buttonsLayout = new HorizontalLayout(resetTextButton2,toggleReadOnlyButton2,toggleViewWithoutEditorButton2,toggleVisibleButton2);
		buttonsLayout.setSpacing(true);
		mainView.addComponent( buttonsLayout );

		separator = new Label("&nbsp;");
		separator.setContentMode(ContentMode.HTML);
		mainView.addComponent(separator); 

		buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		mainView.addComponent( buttonsLayout );
		
		buttonsLayout.addComponent(new Button("Open Modal Subwindow", new ClickListener() {                      
			private static final long serialVersionUID = 7661931879334525618L;

			@Override
            public void buttonClick(ClickEvent event) {
                    Window sub = new Window("Subwindow modal");
                    VerticalLayout subLayout = new VerticalLayout();
                    sub.setContent(subLayout);
                    
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
	                ckEditorTextField.addValueChangeListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = -1308863170484877239L;

						public void valueChange(ValueChangeEvent event) {
							Notification.show("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - POPUP MODAL contents: " + event.getProperty().getValue().toString());
	        			}
	        		});
	                ckEditorTextField.focus();
                    
	                subLayout.addComponent(ckEditorTextField);
                    
                    sub.setWidth("80%");
                    sub.setModal(true);
                    sub.center();
                    
                    event.getButton().getUI().addWindow(sub);
            }
        }));

		buttonsLayout.addComponent(new Button("Open Non-Modal Subwindow with 100% Height", new ClickListener() {                      
			private static final long serialVersionUID = 8895747367120494167L;

			@Override
	        public void buttonClick(ClickEvent event) {
	                Window sub = new Window("Subwindow non-modal 100% height");
	                VerticalLayout subLayout = new VerticalLayout();
	                sub.setContent(subLayout);
	                sub.setWidth("80%");
	                sub.setHeight("500px");

	                subLayout.setSizeFull();
	                
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
	                ckEditorTextField.setHeight("100%");
	                ckEditorTextField.addValueChangeListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = 5592423527258867304L;

						public void valueChange(ValueChangeEvent event) {
							Notification.show("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().getValue().toString());
	        			}
	        		});
	                subLayout.addComponent(ckEditorTextField);
	                subLayout.setExpandRatio(ckEditorTextField,10);
	                
	                final TextField textField = new TextField("TextField");
	                textField.addValueChangeListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = 6686202497483757206L;

						public void valueChange(ValueChangeEvent event) {
							Notification.show("TextField - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().getValue().toString());
	        			}
	        		});
	                subLayout.addComponent(textField);
	                
	                sub.center();
	                
	                event.getButton().getUI().addWindow(sub);
	        }
        }));
	}
	
	public String getVersion() {
		return "7.8.8";
	}

}
