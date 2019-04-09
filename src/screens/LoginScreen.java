package screens;

import mainPackage.MainApp;
import mainPackage.ProcessingUI;
import mainPackage.ProcessingUI.Button;
import mainPackage.ProcessingUI.TextField;
import mainPackage.ProcessingUI.TextFrame;

public class LoginScreen extends ProcessingUI.Screen {

	private TextFrame instructions;
	
	private TextField addr_field;
	private TextField pass_field;
	
	private Button enter_button;
	
	public LoginScreen(MainApp app) {
		
		instructions = new TextFrame() {{
			scale[W] = 1;
			scale[H] = 1;
			
			setText("Please enter your credentials below.");
			setTextColor(app.color(0));
			setTextSize(16);
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;
			text_fixed[Y] = -100;
		}};
		
		addr_field = new TextField() {{
			
			isWindow = true;
			
			fixed[W] = 400;
			fixed[H] = 30;
			scale[X] = 0.5f;
			scale[Y] = 0.5f;
			scale[A] = -0.5f;
			scale[B] = -0.5f;
			
			setEdgeColor(app.color(0));
			setCornerColor(app.color(255));
			
			setTextColor(app.color(0));
			setTextSize(12);
			text_fixed[X] = 8;
			text_fixed[Y] = 8;
			setPlaceholderText("Address");
			setPlaceholderTextColor(app.color(200));
			
			//0xae33380a7ce2cde0ca9da9cb6c8fb7289d8271c3
			setText("0xC55EF66f621F05e0bdF69A8E8F33fb83b6Bae9dB");
		}};

		pass_field = new TextField() {{

			isWindow = true;
			
			fixed[W] = 400;
			fixed[H] = 30;
			scale[X] = 0.5f;
			scale[Y] = 0.5f;
			scale[A] = -0.5f;
			scale[B] = -0.5f;
			fixed[Y] = 50;
			
			setEdgeColor(app.color(0));
			setCornerColor(app.color(255));
			
			setTextColor(app.color(0));
			setTextSize(12);
			text_fixed[X] = 8;
			text_fixed[Y] = 8;
			setPlaceholderText("Password");
			setPlaceholderTextColor(app.color(200));
			
			setText("password123");
		}};

		enter_button = new Button() {{
			
			actionIsConcurrent = true;
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
				enabled = false;
				addr_field.selectable = false;
				pass_field.selectable = false;
				setTextColor(app.color(200));
				// perform verification
				if(app.authenticate(addr_field.getText().trim(), pass_field.getText().trim())) {
					((ContactListScreen)app.contact_list_screen).updateContacts();
					switchTo(app.contact_list_screen,MainApp.UP);
				}
				setTextColor(app.color(0));
				enabled = true;
				addr_field.selectable = true;
				pass_field.selectable = true;
			}});
			
		}};
		
		add(instructions);
		add(addr_field);
		//add(pass_field); // bruh
		add(enter_button);
	}
	
}
