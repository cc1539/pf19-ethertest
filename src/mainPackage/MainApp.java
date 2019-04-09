package mainPackage;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import processing.core.*;
import processing.event.MouseEvent;
import screens.ChatScreen;
import screens.ContactInfoScreen;
import screens.ContactListScreen;
import screens.LoginScreen;
import screens.SplashScreen;
import smartContracts.Messenger;
import smartContracts.SimpleSMS;

import org.reactivestreams.Subscription;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import mainPackage.ProcessingUI.TextFrame;

/**
 * 
 * @author ChristopherCheng
 *
 * Some of the processing stuff lives on here.
 * Almost all of the Ethereum networking happens here.
 *
 */
public class MainApp extends ProcessingUI {
	
	public static MainApp app;
	
	public String client_address;
	
	public Web3j web3j;
	public Credentials credentials;
	public SimpleSMS contract;
	
	public EthFilter filter;
	public Disposable listener;

	public boolean[] key_input = new boolean[256];
	
	public MainApp() {
		super();
		app = this;
	}
	
	public TextFrame lost_connection_frame;
	
	public void disconnect() {

		if(web3j!=null) {
			web3j.shutdown();
		}
		web3j = null;
		
	}
	
	public void connect() {
		
		disconnect();
		
		println("Attempting to connect to the Ethereum network.");
		Web3j web3j;
		try {
			web3j = Web3j.build(new HttpService());
			println("Connected to Ethereum client version: "+
					web3j.web3ClientVersion().send().getWeb3ClientVersion());
			this.web3j = web3j;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void connectAsync() {
		
		disconnect();
		
		new Thread(new Runnable() {public void run() {
			
			while(app.web3j==null) {
				app.connect();
			}
			
		}}).start();
		
	}
	
	public void showLostConnectionFrame() {
		
		disconnect();
		
		if(lost_connection_frame==null) {
			lost_connection_frame = new TextFrame() {{
				
				scale[W] = 1;
				scale[H] = 1;
				
				img[1][1] = colorAsImage(color(0,128));
				
				setText("Connection with Ethereum network has been lost!\nAttempting to reconnect...");
				setTextColor(color(255));
				setTextSize(20);
				setTextAlign(CENTER,CENTER);
				text_scale[X] = 0.5f;
				text_scale[Y] = 0.5f;
				
			}};
		}

		Tasks.add(new Tasks.Float() {
			{
				set(0);
				setTarget(1);
				setRate(0.1f);
				setType(SMOOTH);
				run();
			}
			public void run() {
				
				root.moveToFront(lost_connection_frame);
				
				super.run();
				
				lost_connection_frame.setTint(color(255,get()*255));
				lost_connection_frame.setTextColor(color(255,get()*255));
			}
		});

		Tasks.add(new Runnable() {public void run() {
			if(web3j!=null) {

				Tasks.add(new Tasks.Float() {
					{
						set(1);
						setTarget(0);
						setRate(0.1f);
						setType(SMOOTH);
						setThreshold(0.001f);
						run();
					}
					public void run() {
						
						root.moveToFront(lost_connection_frame);
						
						super.run();
						
						lost_connection_frame.setTint(color(255,get()*255));
						lost_connection_frame.setTextColor(color(255,get()*255));
					}
					public void onFinish() {
						root.remove(lost_connection_frame);
					}
				});
				
				Tasks.remove(this);
			}
		}});
		
		root.add(lost_connection_frame);
		
	}
	
	public static String getClipboard() {
		try {
			return (String)Toolkit
					.getDefaultToolkit()
					.getSystemClipboard()
					.getData(DataFlavor.stringFlavor);
		} catch(Exception e) {}
		return null;
	}
	
	public boolean authenticate(String addr, String pass) {
		
		credentials = null;
		contract = null;
		listener = null;
		
		boolean success = true;
		
		if(addr.indexOf("0x")==0) {
			addr = addr.substring(2);
		}
		
		client_address = "0x"+addr;
		
		// load the credentials from a file
		/*
		String account = addr;
		String base_url = "C:\\Users\\ChristopherCheng\\Desktop\\ethereum_project\\";
		try {
			File file = new File(base_url+"test\\chaindata\\keystore\\");
			if(file.exists() && file.isDirectory()) {
				for(File child : file.listFiles()) {
				if(child.getName().indexOf(addr)!=-1) {
					account = child.getName();
					break;
				}
				}
			}
			credentials = WalletUtils.loadCredentials(pass,file.getAbsolutePath()+"\\"+account);
		} catch(Exception e) {
			println("Failed to authenticate user.");
			return false;
		}
		*/
		
		try {
			String account = client_address; 
					//web3j.ethAccounts().send().getAccounts().get(0);
			println("Attempting to log in with address: "+account);
			credentials = Credentials.create(account);
		
			print("We have a balance of: ");
			EthGetBalance balance = web3j
					.ethGetBalance(account,DefaultBlockParameterName.LATEST)
					.sendAsync()
					.get();
			println(balance.getBalance());
			
		} catch(Exception e) {
			println("Failed to authenticate user.");
			e.printStackTrace();
			return false;
		}
		
		println("Credentials loaded");
		
		// Obtain access to the desired Smart Contract. If it hasn't been deployed, do it ourselves.
		ContractGasProvider contractGasProvider = new DefaultGasProvider();
		String contractAddr = "0x9eb21518fa327adb5c8dbdd4cdc9e95ad71f3440";
		try {
			
			println("Attempting to find contract at known address...");
			
			ClientTransactionManager ctm = new ClientTransactionManager(web3j,client_address);
			
			contract = SimpleSMS.load(contractAddr,web3j,ctm,contractGasProvider);
			
			if(contract==null || !contract.isValid()) {
				println("Contract with address "+contractAddr+" not found!");
				contract = SimpleSMS.deploy(web3j,ctm,contractGasProvider).send();
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			println("Contract could not be loaded or deployed.");
			return false;
		}
		
		println("Contract address: "+contract.getContractAddress());
		
		filter = new EthFilter(
			DefaultBlockParameterName.EARLIEST,
			DefaultBlockParameterName.LATEST,
			contractAddr);
		
		filter.addSingleTopic(EventEncoder.encode(SimpleSMS.MESSAGESENT_EVENT));
		
		return success;
	}
	
	public boolean sendMessage(String text, String recipient) {
		try {
			contract.sendMessage(text,recipient).send();
		} catch(Exception e) {
			println("Failed to send message.");
			e.printStackTrace();
			if(e instanceof java.net.ConnectException) {
				// web3j connection was lost!
				connectAsync();
				showLostConnectionFrame();
			}
			return false;
		}
		return true;
	}
	/*
	public String getInboxMessage(BigInteger index) {
		try {
			return contract.getInboxMessage(index).send().toString();
		} catch(Exception e) {
			println("Failed to fetch inbox message #"+index);
		}
		return null;
	}
	
	public String getOutboxMessage(BigInteger index) {
		try {
			return contract.getOutboxMessage(index).send().toString();
		} catch(Exception e) {
			println("Failed to fetch outbox message #"+index);
		}
		return null;
	}
	*/
	public static void main(String[] args) {
		PApplet.main("mainPackage.MainApp");
	}
	
	/*
	public void attemptSmartContractTransaction() {
		
		connect();
		authenticate("ae33380a7ce2cde0ca9da9cb6c8fb7289d8271c3","password123");
		println("Sending message...");
		sendMessage("hey it's me ur cousin","0x17aa311237e2caf2d5e255f773ac7e44ee47854d");
		
		// the following code always fails and returns (0x) and I don't know why
		println("Viewing message in our outbox...");
		println(getOutboxMessage(BigInteger.ZERO));
		
		println("Done");

	}
	*/
	
	public void settings() {
		size(640,480);
		noSmooth();
	}
	
	public PImage icon;
	
	public Screen login_screen; // enter your own credentials (address + password)
	public Screen contact_list_screen; // view all contacts, add new contacts, and delete contacts.
	public Screen contact_info_screen; // view and modify identifying information about a contact (name, phone number, address, etc.)
	public Screen chat_screen; // actually communicate with one or more contacts. For now, only texting is supported.
	
	public Frame connection_status;
	
	// the initial message y-axis offset
	public float message_offset = 10;
	
	public void setup() {

		surface.setTitle("Ethereum Test 0.0.1");
		surface.setIcon(colorAsImage(0));
		
		background(255);
		
		icon = loadImage("resources/icon.png");
		if(icon!=null) {
			surface.setIcon(icon);
		}
		//surface.setResizable(true); // processing still crashes when resizing :(
		
		//final PFont font = createFont("source code pro",10);
		//textFont(font);
		
		root.img[1][0] = colorAsImage(color(200));
		root.img[1][1] = colorAsImage(color(255));
		
		// build the screens
		login_screen = new LoginScreen(this);
		contact_list_screen = new ContactListScreen(this);
		contact_info_screen = new ContactInfoScreen(this);
		chat_screen = new ChatScreen(this);
		
		// attempt to get a return value...
		// if the only way to retrieve values is through events,
		// why would they even let the "return" keyword be a thing?
		/*
		new Thread(new Runnable() {public void run() {
			attemptSmartContractTransaction();
		}}).start();
		*/
		
		// splash screen
		root.add(new SplashScreen(this));
		
	}
	
	// attempt to send input events to a FocalPoint
	public void keyPressed () { key_input[keyCode]=true;  if(focus!=null && focus instanceof TextField) { ((TextField)focus).keyPressed (key,keyCode); } }
	public void keyReleased() { key_input[keyCode]=false; if(focus!=null && focus instanceof TextField) { ((TextField)focus).keyReleased(key,keyCode); } }
	public void mouseWheel(MouseEvent e) { if(focus!=null) { focus.scroll(e.getCount()); } }

	public void draw() {
		if(focus!=null && !focus.selectable) {
			focus = null;
		}
		if(mousePressing()) {
			focus = null;
		}
		root.handle(0,0,width,height);
		Tasks.run();
		if(focus instanceof Button) {
			((Button)focus).doAction();
			focus = null;
		}
		pmousePressed = mousePressed; // this must come last, or else the mousePressing & mouseReleasing functions won't work.
	}
	
}
