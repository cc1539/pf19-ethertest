package screens;

import mainPackage.MainApp;
import mainPackage.ProcessingUI;
import mainPackage.Tasks;
import mainPackage.ProcessingUI.TextFrame;

public class SplashScreen extends ProcessingUI.TextFrame {
	
	public SplashScreen(MainApp app) {
		
		final MainApp.Frame self = this;
		
		MainApp.Frame logo = new MainApp.Frame() {{
			img[1][1] = app.icon;
			
			scale[X] = 0.5f;
			scale[Y] = 0.5f;
			scale[A] = -0.5f;
			scale[B] = -0.5f;
			fixed[W] = app.icon.width/2;
			fixed[H] = app.icon.height/2;
			
			setTint(app.color(255,0));
		}};
		add(logo);
		
		img[1][0] = app.colorAsImage(app.color(200));
		img[1][1] = app.colorAsImage(app.color(255));
		img[1][2] = app.colorAsImage(app.color(200));
		
		scale[W] = 1;
		scale[H] = 1;
		
		setText("Ethereum Test 0.0.1");
		setTextColor(app.color(255));
		setTextSize(24);
		setTextAlign(MainApp.CENTER,MainApp.CENTER);
		text_scale[X] = 0.5f;
		text_scale[Y] = 0.5f;

		Tasks.add(new Tasks.Float() {
			{
				set(1);
				setTarget(0);
				setType(SMOOTH);
				setRate(0.03f);
			}
			public void run() {
				super.run();
				setTextColor(app.color(get()*255));
				text_fixed[Y] = (1-get())*60;
				logo.fixed[Y] = (get()-1)*60;
				logo.setTint(app.color(255,(1-get())*255));
			}
		});
		
		app.connectAsync();
		
		Tasks.add(new Tasks.Timer(150){public void onFinish(){
			Tasks.add(new Runnable() {public void run() {
				if(app.web3j!=null) {
					Tasks.add(new Tasks.Float() {
						{
							set(0);
							setTarget(1);
							setType(SMOOTH);
							setRate(0.002f);
							setThreshold(0.001f);
							app.root.add(app.login_screen);
							app.root.moveToFront(self);
						}
						public void run() {
							super.run();
							setRate(MainApp.max(0.002f,get()*0.2f));
							scale[5] = -get();
						}
						public void onFinish() {
							app.root.remove(self);
						}
					});
					Tasks.remove(this);
				}
			}});
		}});
		
		Tasks.add(new Tasks.Timer(500){public void onFinish(){
			setText("Sorry, we're having a little trouble connecting...");
			setTextColor(app.color(150));
		}});
		
	}
	
}
