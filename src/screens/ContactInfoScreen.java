package screens;

import mainPackage.MainApp;
import mainPackage.ProcessingUI;
import mainPackage.ProcessingUI.Button;
import mainPackage.ProcessingUI.TextField;

public class ContactInfoScreen extends ProcessingUI.Screen {
	
	private TextField name_field;
	private TextField addr_field;
	private Button add_button;
	private Button back_button;
	
	public ContactInfoScreen(MainApp app) {
		
		name_field = new TextField() {{

			isWindow = true;
			
			fixed[W] = 400;
			fixed[H] = 30;
			scale[X] = 0.5f;
			scale[Y] = 0.5f;
			scale[A] = -0.5f;
			scale[B] = -0.5f;
			fixed[Y] = -30;
			
			setEdgeColor(app.color(0));
			setCornerColor(app.color(255));
			
			setTextColor(app.color(0));
			setTextSize(12);
			text_fixed[X] = 8;
			text_fixed[Y] = 8;
			setPlaceholderText("Contact's name");
			setPlaceholderTextColor(app.color(200));
			
		}};
		
		addr_field = new TextField() {{

			isWindow = true;
			
			fixed[W] = 400;
			fixed[H] = 30;
			scale[X] = 0.5f;
			scale[Y] = 0.5f;
			scale[A] = -0.5f;
			scale[B] = -0.5f;
			fixed[Y] = 30;
			
			setEdgeColor(app.color(0));
			setCornerColor(app.color(255));
			
			setTextColor(app.color(0));
			setTextSize(12);
			text_fixed[X] = 8;
			text_fixed[Y] = 8;
			setPlaceholderText("Contact's Ethereum Address");
			setPlaceholderTextColor(app.color(200));
			
		}};

		add_button = new Button() {{
			
			isWindow = true;
			
			fixed[W] = 400;
			fixed[H] = 25;
			scale[X] = 0.5f;
			scale[Y] = 0.5f;
			scale[A] = -0.5f;
			scale[B] = -0.5f;
			fixed[Y] = 100;
			
			setEdgeColor(app.color(0),2);
			setCornerColor(app.color(255));
			
			setText("Enter");
			setTextColor(app.color(0));
			setTextSize(14);
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;
			text_fixed[Y] = -2;
			
			setAction(new Runnable() {public void run() {
				((ContactListScreen)app.contact_list_screen).addContact(
					name_field.getText(),
					addr_field.getText(),
					null);
				switchTo(app.contact_list_screen,MainApp.RIGHT);
			}});
			
		}};
		
		// back button
		back_button = new Button() {{
			
			fixed[W] = 50;
			fixed[H] = 50;
			
			img[1][1] = app.colorAsImage(app.color(0));
			
			setText("<");
			setTextColor(app.color(255));
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;
			
			setAction(new Runnable() {public void run() {
				switchTo(app.contact_list_screen,MainApp.RIGHT);
			}});
			
		}};
		
		add(name_field);
		add(addr_field);
		add(add_button);
		add(back_button);
	}
	
}
