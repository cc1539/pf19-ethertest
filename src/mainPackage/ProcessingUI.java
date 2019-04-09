package mainPackage;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

public class ProcessingUI extends PApplet {

	public boolean pmousePressed;
	
	public boolean mousePressing () { return mousePressed && !pmousePressed; }
	public boolean mouseReleasing() { return !mousePressed && pmousePressed; }
	
	public Frame focus = null;
	
	public Frame root = new Frame(){{
		scale[2] = 1;
		scale[3] = 1;
	}};;
	
	public PImage colorAsImage(int rgb, int w, int h) {
		PImage img = createImage(w,h,ARGB);
		for(int i=0;i<img.pixels.length;i++) {
			img.pixels[i] = rgb;
		}
		img.updatePixels();
		return img;
	}
	
	public PImage colorAsImage(int rgb, int l) {
		return colorAsImage(rgb,l,l);
	}
	
	public PImage colorAsImage(int rgb) {
		return colorAsImage(rgb,1);
	}

	public static class Button extends TextFrame {
		
		public boolean actionIsConcurrent;
		
		public boolean enabled = true;
		private boolean anticipating = false;
		
		private Runnable click_action;
		
		public Runnable getAction() { return click_action; }
		public void setAction(Runnable action) { click_action = action; }
		
		public void doAction() {
			if(click_action!=null) {
				if(actionIsConcurrent) {
					new Thread(click_action).start();
				} else {
					click_action.run();
				}
			}
		}
		
		public void draw(float x, float y, float w, float h) {
			
			MainApp app = MainApp.app;
			
			super.draw(x,y,w,h);
			
			if(enabled && pointHovering(app.mouseX,app.mouseY)) {
				if(app.mousePressing()) {
					anticipating = true;
				}
				if(app.mouseReleasing() && anticipating) {
					MainApp.app.focus = this;
				}
			}
			if(app.mouseReleasing()) {
				anticipating = false;
			}
			
		}
		
	}
	
	public static class Frame extends ArrayList<Frame> {
		
		public float[] fixed = new float[4];
		public float[] scale = new float[6];
		protected float[] absolute = new float[4];
		
		public final int X = 0;
		public final int Y = 1;
		public final int W = 2;
		public final int H = 3;
		public final int A = 4;
		public final int B = 5;
		
		public PImage[][] img = new PImage[3][3];
		public int tint_color = 0xFFFFFFFF;
		
		public boolean isWindow = false;
		public PGraphics canvas = null;
		
		public boolean selectable = false;
		public float[] scroll = new float[2];
		public float[][] scroll_range = new float[2][2];
		
		public int getTint() { return tint_color; }
		public void setTint(int value) { tint_color = value; }
		
		public void moveToFront(Frame frame) {
			if(contains(frame)) {
				remove(frame);
				add(frame);
			}
		}

		public void moveToBack(Frame frame) {
			if(contains(frame)) {
				remove(frame);
				add(0,frame);
			}
		}
		
		public void updateCanvas(float w, float h) {
			if(canvas==null || canvas.width!=(int)w || canvas.height!=(int)h) {

				MainApp app = MainApp.app;
				
				canvas = app.createGraphics((int)w,(int)h,JAVA2D);
				// copy over some properties
				canvas.beginDraw();
				canvas.textFont(app.g.textFont);
				if(app.g.stroke) { canvas.stroke(app.g.strokeColor); } else { canvas.noStroke(); }
				if(app.g.fill) { canvas.fill(app.g.fillColor); } else { canvas.noFill(); }
				canvas.endDraw();
			}
		}
		
		public void handleChildren(float x, float y, float w, float h) {
			for(int i=0;i<size();i++) {
				Frame child = get(i);
				child.handle(x,y,w,h);
			}
		}
		
		public void setEdgeColor(int rgb, int thickness) {

			MainApp app = MainApp.app;
			
			for(int i=0;i<=2;i++) {
			for(int j=0;j<=2;j++) {
				if(!(i==1&&j==1)) {
					img[i][j] = app.colorAsImage(rgb,thickness);
				}
			}
			}
		}
		
		public void setEdgeColor(int rgb) {
			setEdgeColor(rgb,1);
		}
		
		public void setFillColor(int rgb) {
			img[1][1] = MainApp.app.colorAsImage(rgb);
		}
		
		public void setCornerColor(int rgb) {

			MainApp app = MainApp.app;
			
			for(int i=0;i<=2;i+=2) {
			for(int j=0;j<=2;j+=2) {
				img[i][j] = app.colorAsImage(rgb);
			}
			}
		}
		
		public void handle(float x, float y, float w, float h) {
			
			absolute[X] = fixed[X]+x+scale[X]*w;
			absolute[Y] = fixed[Y]+y+scale[Y]*h;
			absolute[W] = fixed[W]+scale[W]*w;
			absolute[H] = fixed[H]+scale[H]*h;
			absolute[X] += scale[A]*absolute[W];
			absolute[Y] += scale[B]*absolute[H];
			
			if(isWindow) {

				MainApp app = MainApp.app;
				
				PGraphics original = app.g;
				int canvas_w = ceil(absolute[W]);
				int canvas_h = ceil(absolute[H]);
				if(canvas_w>0 && canvas_h>0) {
					updateCanvas(canvas_w,canvas_h);
					app.g = canvas;
					canvas.beginDraw();
					canvas.clear();
					draw(
						absolute[X]%1,
						absolute[Y]%1,
						canvas_w,
						canvas_h);
					handleChildren(
						-scroll[X],
						-scroll[Y],
						absolute[W],absolute[H]);
					canvas.endDraw();
					app.g = original;
					app.image(canvas,absolute[X],absolute[Y]);
				}
			} else {
				draw(
					absolute[X],absolute[Y],
					absolute[W],absolute[H]);
				handleChildren(
					absolute[X]-scroll[X],
					absolute[Y]-scroll[Y],
					absolute[W],absolute[H]);
			}
			
		}
		
		public void takeFocus() {
			MainApp.app.focus = this;
		}
		
		private int imgw(int x, int y) { return img[x][y]!=null?img[x][y].width:0; }
		private int imgh(int x, int y) { return img[x][y]!=null?img[x][y].height:0; }
		
		public void draw(float x, float y, float w, float h) {

			MainApp app = MainApp.app;
			
			app.g.tint(tint_color);
			
			// draw center
			if(img[1][1]!=null) { app.image(img[1][1],x,y,w,h); }
			
			// draw edges
			if(img[0][1]!=null) { app.image(img[0][1],x,y+imgh(0,0),imgw(0,1),h-imgh(0,0)-imgh(0,2)); }
			if(img[1][0]!=null) { app.image(img[1][0],x+imgw(0,0),y,w-imgw(0,0)-imgh(2,0),imgh(1,0)); }
			if(img[2][1]!=null) { app.image(img[2][1],x+w-imgw(2,1),y+imgh(2,0),imgw(2,1),h-imgh(2,0)-imgh(2,2)); }
			if(img[1][2]!=null) { app.image(img[1][2],x+imgw(0,2),y+h-imgh(1,2),w-imgw(0,2)-imgw(2,2),imgh(1,2)); }
			
			// draw corners
			if(img[0][0]!=null) { app.image(img[0][0],x,y); }
			if(img[2][0]!=null) { app.image(img[2][0],x+w-imgw(2,0),y); }
			if(img[0][2]!=null) { app.image(img[0][2],x,y+h-imgh(0,2)); }
			if(img[2][2]!=null) { app.image(img[2][2],x+w-imgw(2,2),y+h-imgh(2,2)); }
			
			if(selectable && app.mousePressing() && pointHovering(app.mouseX,app.mouseY)) {
				takeFocus();
			}
			
		}
		
		public boolean pointHovering(float x, float y) {
			x -= absolute[X];
			y -= absolute[Y];
			return (x>=0&&x<=absolute[W])&&(y>=0&&y<=absolute[H]);
		}
		
		public void scroll(int amount) {
			scroll[Y] += amount*12;
			// limit scrolling
			//scroll[Y] = min(max(scroll[Y],scroll_range[Y][0]),scroll_range[Y][1]);
		}
		
	}
	
	public static class Screen extends Frame {
		
		public Screen() {
			scale[W] = 1;
			scale[H] = 1;
		}
		
		public void switchTo(Screen screen) {
			switchTo(screen,LEFT);
		}
		
		public void switchTo(Screen screen, int direction) {
			
			MainApp app = MainApp.app;
			
			final Frame self = this;
			
			app.root.add(screen);
			
			final float[] targets = new float[2];
			switch(direction) {
				case LEFT:  { targets[X] =  1; } break;
				case RIGHT: { targets[X] = -1; } break;
				case UP:    { targets[Y] =  1; } break;
				case DOWN:  { targets[Y] = -1; } break;
			}
			
			Tasks.add(new Tasks.Float() {
				{
					set(0);
					setTarget(1);
					setType(SMOOTH);
					setRate(0.15f);
					setThreshold(0.001f);
					run();
				}
				public void run() {
					super.run();
					screen.scale[X] = (1-get())*targets[X];
					screen.scale[Y] = (1-get())*targets[Y];
					scale[X] = get()*-targets[X];
					scale[Y] = get()*-targets[Y];
				}
				public void onFinish() {
					app.root.remove(self);
				}
			});
			
		}
		
	}

	public static class TextField extends TextFrame {
		
		{ selectable = true; }
		
		private StringBuilder text = new StringBuilder();
		private int cursor = 0;
		
		private String placeholder_text = null;
		private int placeholder_text_color;
		
		private int cursor_blink_timer;
		
		public String getPlaceholderText() { return placeholder_text; }
		public void setPlaceholderText(String value) { placeholder_text = value; }
		
		public int getPlaceholderTextColor() { return placeholder_text_color; }
		public void setPlaceholderTextColor(int color) { placeholder_text_color = color; }
		
		private boolean applyBounds() {
			return cursor!=(cursor=min(max(cursor,0),text.length()));
		}
		
		private void insert(char key) {
			applyBounds();
			text.insert(cursor,key);
		}
		
		private void delete() {
			applyBounds();
			text.deleteCharAt(cursor);
		}
		
		private boolean move(int amount) {
			cursor += amount;
			return !applyBounds();
		}
		
		public void setText(String text) {
			super.setText(text);
			this.text.setLength(0);
			this.text.append(text);
			cursor = text.length();
		}
		
		public void keyPressed(char key, int keyCode) {
			boolean action_performed = true;
			if(MainApp.app.key_input[MainApp.CONTROL]) {
				System.out.println(keyCode);
				switch(keyCode) {
				case 86:
					String clipboard = MainApp.getClipboard();
					if(clipboard!=null) {
						applyBounds();
						text.insert(cursor, clipboard);
						move(clipboard.length());
					}
				break;
				default:
					action_performed = false;
				break;
				}
			} else {
				switch(keyCode) {
					case ENTER:     { insert('\n'); move(1); } break;
					case DELETE:    { delete(); } break;
					case BACKSPACE: { if(move(-1)) { delete(); } } break;
					case LEFT:      { move(-1); } break;
					case RIGHT:     { move(1); } break;
					case UP:        { } break;
					case DOWN:      { } break;
					default: {
						if(key!=CODED) {
							insert(key);
							move(1);
						} else {
							action_performed = false;
						}
					} break;
				}
			}
			if(action_performed) {
				cursor_blink_timer = 0;
			}
		}
		
		public void keyReleased(char key, int keyCode) {
			
		}
		
		public void scroll(int amount) {
			scroll[1] += amount*12;
		}
		
		public void takeFocus() {
			super.takeFocus();
			cursor_blink_timer = 0;
		}
		
		public void draw(float x, float y, float w, float h) {
			
			MainApp app = MainApp.app;
			
			if(text.length()==0 && app.focus!=this && placeholder_text!=null) {
				super.setText(placeholder_text);
			} else {
				super.setText(text.toString());
			}
			super.draw(x,y,w,h);
			if(app.focus==this) {
				cursor_blink_timer++;
				if((cursor_blink_timer/30)%2==0) {
					app.stroke(getTextColor());
					String snippet = text.substring(max(0,text.lastIndexOf("\n",cursor-1)),cursor);
					float cursor_y = text_absolute[1];
					for(int i=cursor-1;i>=0;i--) {
						if(text.charAt(i)=='\n') {
							cursor_y += app.g.textLeading;
						}
					}
					float cursor_x = app.textWidth(snippet)+text_absolute[0];
					app.line(cursor_x,cursor_y,cursor_x,cursor_y+12);
				}
			}
		}
		
	}
	
	public static class TextFrame extends Frame {
			
		public float[] text_fixed = new float[2];
		public float[] text_scale = new float[2];
		protected float[] text_absolute = new float[2];
		
		private String text;
		
		private int align_x = LEFT;
		private int align_y = TOP;
		private float size = 12;
		private PFont font;
		private int text_color;
		
		public String getText() { return text; }
		public void setText(String text) { this.text = text; }
		

		public int getTextHorizontalAlign() { return align_x; }
		public int getTextVerticalAlign() { return align_y; }
		public void setTextAlign(int x, int y) {
			align_x = x;
			align_y = y;
		}

		public float getTextSize() { return size; }
		public void setTextSize(float size) { this.size = size; }

		public PFont getTextFont() { return font; }
		public void setTextFont(PFont font) { this.font = font; }

		public int getTextColor() { return text_color; }
		public void setTextColor(int rgb) { text_color = rgb; }
		
		public void draw(float x, float y, float w, float h) {
			super.draw(x,y,w,h);
			if(text!=null) {
				
				MainApp app = MainApp.app;
				
				text_absolute[0] = text_fixed[0]+x+w*text_scale[0]-scroll[0];
				text_absolute[1] = text_fixed[1]+y+h*text_scale[1]-scroll[1];
				app.g.textAlign(align_x,align_y);
				app.g.textSize(size);
				if(font!=null) {
					app.g.textFont(font);
				}
				app.g.fill(text_color);
				app.g.text(text,text_absolute[0],text_absolute[1]);
			}
		}
		
	}
	
}
