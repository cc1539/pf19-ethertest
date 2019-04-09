package screens;

import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;

import mainPackage.MainApp;
import mainPackage.ProcessingUI;
import mainPackage.Tasks.Timer;
import smartContracts.SimpleSMS;
import mainPackage.Tasks;
import mainPackage.ProcessingUI.Button;
import mainPackage.ProcessingUI.Frame;
import mainPackage.ProcessingUI.TextField;
import mainPackage.ProcessingUI.TextFrame;

public class ChatScreen extends ProcessingUI.Screen {
	
	private Frame menubar;
		private Button back_button; // go to the contact list screen
		private Button chat_icon; // goes to the contact info screen
		private TextFrame chat_name;
	
	private Frame input_container;
		private Button send_button;
		private TextField msg_contents;
	
	private String recipient = "0x17aa311237e2caf2d5e255f773ac7e44ee47854d";
	
	private Frame output_container;

	public static final int INBOX = 0;
	public static final int OUTBOX = 1;
	
	private int outbox_sem = 0;
	
	public ChatScreen(MainApp app) {
		
		menubar = new Frame() {{
			
			fixed[H] = 40;
			scale[W] = 1;
			
			img[1][2] = app.colorAsImage(app.color(180));
		}};
		
		back_button = new Button() {{

			fixed[W] = 40;
			fixed[H] = 40;
			
			//img[1][1] = something;
			
			setText("<");
			setTextSize(18);
			setTextColor(app.color(150));
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;
			
			setAction(new Runnable() {public void run() {
				switchTo(app.contact_list_screen,MainApp.RIGHT);
			}});
			
		}};
		
		chat_icon = new Button() {{
			
		}};
		
		chat_name = new TextFrame() {{
			
		}};
		
		input_container = new Frame() {{
			
			fixed[H] = 30;
			scale[W] = 1;
			scale[Y] = 1;
			scale[B] = -1;
			fixed[Y] = -10;
			scale[X] = 0.5f;
			scale[A] = -0.5f;
			fixed[W] = -20;

			setEdgeColor(app.color(0),1);
			setFillColor(app.color(255));
			setCornerColor(app.color(255));
			
		}};
		
		send_button = new Button() {{
			
			scale[X] = 1;
			scale[A] = -1;
			scale[H] = 1;
			fixed[W] = 100;
			
			setText("Send");
			setTextColor(app.color(0));
			setTextSize(12);
			setTextAlign(MainApp.CENTER,MainApp.CENTER);
			text_scale[X] = 0.5f;
			text_scale[Y] = 0.5f;
			text_fixed[Y] = -1;
			
			setEdgeColor(app.color(0),2);
			setFillColor(app.color(255));
			setCornerColor(app.color(255));
			
			setAction(new Runnable() {public void run() {
				String msg = msg_contents.getText().trim();
				if(!msg.isEmpty()) {
					
					enabled = false;
					setText("Sending...");
					setTextColor(app.color(180));
					
					msg_contents.selectable = false;
					
					new Thread(new Runnable() {public void run() {
						
						if(app.sendMessage(msg,recipient)) {

							msg_contents.setText("");
							addOutboxMessage(msg);
							outbox_sem++;
							
						} else {
							
						}

						setTextColor(app.color(0));
						setText("Send");
						
						enabled = true;
						msg_contents.selectable = true;
					}}).start();
					
					/*
					// fake response
					Tasks.add(new Tasks.Timer(200) {public void onFinish(){ addInboxMessage("hey"); }});
					Tasks.add(new Tasks.Timer(250) {public void onFinish(){ addInboxMessage("its me ur cousin"); }});
					Tasks.add(new Tasks.Timer(300) {public void onFinish(){ addInboxMessage("gib password"); }});
					*/
					
				}
			}});
			
		}};
		
		msg_contents = new TextField() {{
			
			isWindow = true;
			
			scale[H] = 1;
			scale[W] = 1;
			fixed[W] = -100;
			
			text_fixed[X] = 8;
			text_fixed[Y] = 8;
			
		}};
		
		output_container = new Frame() {{
			
			selectable = true;
			isWindow = true;
			
			scale[W] = 1;
			scale[H] = 1;
			fixed[W] = -20;
			fixed[H] = -20-40-40;
			scale[X] = 0.5f;
			fixed[Y] = 50;
			scale[A] = -0.5f;
			
			int thickness = 2;
			float light = 130;
			float dark = 0;
			img[0][1] = app.colorAsImage(app.color(light),thickness);
			img[1][0] = app.colorAsImage(app.color(dark),thickness);
			img[2][1] = app.colorAsImage(app.color(dark),thickness);
			img[1][2] = app.colorAsImage(app.color(light),thickness);
			setFillColor(app.color(255));
			setCornerColor(app.color(255));
			
			
			
		}};
		
		menubar.add(back_button);
		add(menubar);
		input_container.add(send_button);
		input_container.add(msg_contents);
		add(input_container);
		add(output_container);
		
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public void setRecipient(String value) {

		MainApp app = MainApp.app;
		String addr = app.client_address.toLowerCase();

		app.message_offset = 10;
		
		recipient = value.toLowerCase();
		
		output_container.clear();
		
		if(app.listener!=null) {
			app.listener.dispose();
			app.listener = null;
		}
		
		System.out.println("addr: "+addr);
		System.out.println("recipient: "+recipient);
		
		/*
		try {
			EthLog ethLog = app.web3j.ethGetLogs(app.filter).send();
			for(EthLog.LogResult<?> log : ethLog.getLogs()) {
				
				EventValues params = SimpleSMS.staticExtractEventParameters(
						SimpleSMS.MESSAGESENT_EVENT,
						(Log)log);
				
				SimpleSMS.MessageSentEventResponse entry = new SimpleSMS.MessageSentEventResponse();
				entry.log = (Log)log;
				entry._from = params.getIndexedValues().get(0).toString();
				entry._to = params.getIndexedValues().get(1).toString();
				entry._text = params.getNonIndexedValues().get(0).toString();
				
				System.out.println("\nfrom: "+entry._from);
				System.out.println("to: "+entry._to);
				System.out.println("text: "+entry._text);
				System.out.print("\n");
				
				// test
				//((ChatScreen)app.chat_screen).addOutboxMessage(entry._text);
				
				//SimpleSMS.MessageSentEventResponse entry = log.getResponse();
				if(entry._to.equals(addr) && entry._from.equals(recipient)) {
					System.out.println("Identified message as inbox-type");
					addInboxMessage(entry._text);
					//((ChatScreen)app.chat_screen).addInboxMessage(entry._text);
				}
				if(entry._from.equals(addr) && entry._to.equals(recipient)) {
					System.out.println("Identified message as outbox-type");
					addOutboxMessage(entry._text);
					//((ChatScreen)app.chat_screen).addOutboxMessage(entry._text);
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to retrieve past messages.");
		}
		*/
		
		// TODO - avoid message replication
		app.listener = app.contract.messageSentEventFlowable(app.filter).subscribe(log->{

			if(log._to.equals(addr) && log._from.equals(recipient)) {
				System.out.println("Identified message as inbox-type");
				addInboxMessage(log._text);
				//((ChatScreen)app.chat_screen).addInboxMessage(entry._text);
			}
			
			if(log._from.equals(addr) && log._to.equals(recipient)) {
				if(outbox_sem>0) {
					outbox_sem--;
				} else {
					System.out.println("Identified message as outbox-type");
					addOutboxMessage(log._text);
					//((ChatScreen)app.chat_screen).addOutboxMessage(entry._text);
				}
			}
			
		});
		
	}
	
	public void addBoxMessage(String text, int type) {
		
		//System.out.println("Adding message \""+text+"\" of type: "+type);
		
		MainApp app = MainApp.app;
		
		int lines = lineCount(text);
		
		// msg_height and msg_width get messed up if I don't include this
		app.textSize(12);
		
		final float msg_height = lines*app.g.textLeading+14;
		final float msg_width = app.textWidth(text)+20;
		
		TextFrame message = new TextFrame() {{
			
			isWindow = true;
			
			if(type==OUTBOX) {
				scale[X] = 1;
				scale[A] = -1;
				fixed[X] = -10;
				fixed[H] = msg_height;
				fixed[Y] = app.message_offset;
			} else if(type==INBOX) {
				scale[X] = 0;
				fixed[X] = 10;
				fixed[H] = msg_height;
				fixed[Y] = app.message_offset;
			}
			
			setEdgeColor(app.color(0));
			setFillColor(app.color(255));
			setCornerColor(app.color(255));
			
			setText(text);
			setTextSize(12);
			setTextColor(app.color(0));
			text_fixed[X] = 10;
			text_fixed[Y] = 10;
			
			Tasks.add(new Tasks.Float() {
				{
					set(0);
					setTarget(msg_width);
					setType(SMOOTH);
					setRate(0.2f);
				}
				public void run() {
					super.run();
					fixed[W] = get();
				}
				public void onFinish() {
					isWindow = false;
				}
			});
			
		}};
		output_container.add(message);
		app.message_offset += msg_height+10;
	}
	
	public void addOutboxMessage(String text) {
		addBoxMessage(text,OUTBOX);
	}
	
	public void addInboxMessage(String text) {
		addBoxMessage(text,INBOX);
	}
	
	private static int lineCount(String text) {
		int lines = 1;
		for(int i=0;i<text.length();i++) {
			if(text.charAt(i)=='\n') {
				lines++;
			}
		}
		return lines;
	}
	
}
