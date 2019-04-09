package screens;

import java.io.File;

import mainPackage.FileIO;
import mainPackage.MainApp;
import mainPackage.ProcessingUI;
import mainPackage.Tasks;
import mainPackage.ProcessingUI.Button;
import mainPackage.ProcessingUI.Frame;
import mainPackage.ProcessingUI.Screen;
import processing.core.PImage;
import processing.core.*;

public class ContactListScreen extends ProcessingUI.Screen {
	
	public Frame menubar;
		public Button add_button; // go to contact info screen for empty individual
		public Button ext_option; // logout button, at least
			public Button ext_option_overlay;
				public Frame ext_option_list;
					public Button logout_button;
	public Frame contact_list;
	
	public ContactListScreen(MainApp app) {
		
		final Screen self = this;
		
		menubar = new Frame() {{
			scale[W] = 1;
			fixed[H] = 50;
			
			img[1][2] = app.colorAsImage(app.color(230));
		}};
		
		add_button = new Button() {{
			
			fixed[W] = 50;
			fixed[H] = 50;
			
			img[1][1] = app.colorAsImage(app.color(0));
			
			setText("+");
			setTextSize(18);
			setTextColor(app.color(255));
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;
			text_fixed[X] = -1;
			text_fixed[Y] = -1;
			
			setAction(new Runnable() {public void run() {
				switchTo(app.contact_info_screen);
			}});
			
		}};
		
		ext_option = new Button() {{

			fixed[W] = 50;
			fixed[H] = 50;
			scale[X] = 1;
			scale[A] = -1;
			
			img[1][1] = app.colorAsImage(app.color(0));
			
			setText("...");
			setTextSize(18);
			setTextColor(app.color(255));
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;

			setAction(new Runnable() {public void run() {
				self.add(ext_option_overlay);
				Tasks.add(new Tasks.Float() {
					{
						set(0);
						setTarget(1);
						setRate(0.1f);
						setType(LINEAR);
						run();
					}
					public void run() {
						super.run();
						ext_option_overlay.setTint(app.color(0,128*get()));
						ext_option_list.scale[A] = -get();
					}
				});
			}});
			
		}};

		ext_option_overlay = new Button() {{
			
			scale[W] = 1;
			scale[H] = 1;
			img[1][1] = app.colorAsImage(app.color(0,128));
			
			setAction(new Runnable() {public void run() {
				Tasks.add(new Tasks.Float() {
					{
						set(1);
						setTarget(0);
						setRate(0.1f);
						setType(LINEAR);
					}
					public void run() {
						super.run();
						ext_option_overlay.setTint(app.color(0,128*get()));
						ext_option_list.scale[A] = -get();
					}
					public void onFinish() {
						self.remove(ext_option_overlay);
					}
				});
			}});
			
		}};
		
		ext_option_list = new Frame() {{
			
			scale[H] = 1;
			scale[X] = 1;
			scale[A] = -1;
			fixed[W] = 200;
			
			img[1][1] = app.colorAsImage(app.color(255));
		}};
		
		logout_button = new Button() {{
			
			scale[W] = 1;
			fixed[H] = 50;
			scale[B] = 0;
			
			img[1][2] = app.colorAsImage(app.color(230));
			
			setText("Logout");
			setTextColor(app.color(0));
			setTextSize(12);
			setTextAlign(MainApp.LEFT,MainApp.CENTER);
			text_fixed[X] = 10;
			text_scale[Y] = 0.5f;
			
			setAction(new Runnable() {public void run() {
				self.remove(ext_option_overlay);
				switchTo(app.login_screen,MainApp.DOWN);
			}});
			
		}};
		
		contact_list = new Frame() {{
			scale[W] = 1;
			scale[H] = 1;
			scale[Y] = 1;
			scale[B] = -1;
			fixed[Y] = 50;
		}};
		
		menubar.add(add_button);
		menubar.add(ext_option);
		ext_option_overlay.add(ext_option_list);
		ext_option_list.add(logout_button);
		add(menubar);
		add(contact_list);
	}
	
	public void updateContacts() {
		
		MainApp app = MainApp.app;
		
		contact_list.clear();
		
		File contact_dir = new File(app.sketchPath()+"/data/contacts/"+app.client_address);
		System.out.println("Searching for contacts at: "+contact_dir.getAbsolutePath());
		if(!contact_dir.exists()) {
			contact_dir.mkdir();
		}
		
		// test
		for(File file : contact_dir.listFiles()) {
			String[] contact_info = FileIO.read(file.getAbsolutePath()).split("\n");
			addContactButton(contact_info[0],file.getName(),null);
		}
		
	}
	
	public static class ContactEntry extends Button {
		
		private String name;
		private String address;
		private PImage icon;
		
		public ContactEntry(String name, String address, PImage img) {
			
			setName(name);
			setAddress(address);
			setIcon(img);
			
			scale[W] = 1;
			fixed[H] = 50;
			
		}
		
		public String getName() { return name; }
		public void setName(String value) { name=value; }
		
		public String getAddress() { return address; }
		public void setAddress(String value) { address=value; }
		
		public PImage getIcon() { return icon; }
		public void setIcon(PImage value) { icon=value; }
		
	}
	
	public void addContact(String name, String address, PImage img) {
		
		String contact_info = FileIO.read(MainApp.app.sketchPath()+"/data/contacts/"+address);
		
		if(contact_info!=null) {
			
			// a contact with the given address already exists!
			// do we replace the existing information?
			
		} else {
			
			addContactButton(name,address,img);
			FileIO.write(MainApp.app.sketchPath()+"/data/contacts/"+MainApp.app.client_address+"/"+address, name);
			
		}
		
	}
	
	public void addContactButton(String name, final String address, PImage img) {
		
		contact_list.add(new Button() {{
			
			scale[W] = 1;
			fixed[H] = 50;
			scale[B] = contact_list.size();
			
			img[1][2] = MainApp.app.colorAsImage(MainApp.app.color(200));
			
			setText(name);
			setTextColor(MainApp.app.color(0));
			setTextAlign(MainApp.LEFT,MainApp.CENTER);
			setTextSize(14);
			text_scale[Y] = 0.5f;
			text_fixed[X] = 14;
			
			setAction(new Runnable() {public void run() {
				((ChatScreen)MainApp.app.chat_screen).setRecipient(address);
				switchTo(MainApp.app.chat_screen,MainApp.LEFT);
				System.out.println("Chatting with: "+address);
			}});
			
		}});
		
	}
	
}
