package mainPackage;

import java.util.ArrayList;

public class Tasks {
	
	private static final ArrayList<Runnable> tasks = new ArrayList<Runnable>();
	private static final ArrayList<Runnable> queue = new ArrayList<Runnable>();
	
	public static class Timer implements Runnable {
		
		private Runnable action;
		private int length;
		private int elapsed;
		
		public Timer(int length) {
			this.length = length;
		}
		
		public Runnable getAction() { return action; }
		public void setAction(Runnable action) { this.action = action; }
		
		public void onFinish() {}
		
		public void run() {
			if(++elapsed>=length) {
				if(action!=null) {
					action.run();
				}
				onFinish();
				Tasks.remove(this);
			}
		}
		
	}
	
	public static class Typer implements Runnable {
		
		private MainApp.TextField focus;
		
		private String text;
		private int cursor;
		
		public float speed = 1;
		private float overtime;
		
		public Typer(String text, MainApp.TextField focus) {
			this.text = text;
			this.focus = focus;
		}

		public float getSpeed() { return speed; }
		public void setSpeed(float speed) { this.speed = speed; }
		
		public void run() {
			overtime += speed;
			while(overtime>=1) {
				overtime--;
				focus.keyPressed(text.charAt(cursor++),0);
				if(cursor>=text.length()) {
					remove(this);
				}
			}
		}
		
	}
	
	public static class Float implements Runnable {
		
		public float value;
		public float target;
		public float rate;
		public float threshold;
		
		public int type = LINEAR;
		
		public static final int LINEAR = 0;
		public static final int SMOOTH = 1;
		
		public float get() { return value; }
		public void set(float value) { this.value = value; Tasks.add(this); }
		
		public float getTarget() { return target; }
		public void setTarget(float value) { target = value; Tasks.add(this); }
		
		public float getRate() { return rate; }
		public void setRate(float value) { rate = value; }
		
		public float getThreshold() { return threshold; }
		public void setThreshold(float value) { threshold = value; }
		
		public int getType() { return type; }
		public void setType(int value) { type = value; }
		
		public void onFinish() {}
		
		public void run() {
			boolean done = false;
			switch(type) {
				case LINEAR: { if((value>target)!=((value+=target>value?rate:-rate)>target)) { done = true; } } break;
				case SMOOTH: { if(Math.abs((value+=(target-value)*rate)-target)<=threshold) { done = true; } } break;
			}
			if(done) {
				value = target;
				Tasks.remove(this);
				onFinish();
			}
		}
		
	}
	
	public static void run() {
		queue.clear();
		queue.addAll(tasks);
		for(Runnable task : queue) {
			task.run();
		}
	}
	
	public static void moveToFront(Runnable task) {
		if(tasks.contains(task)) {
			remove(task);
			tasks.add(task);
		}
	}
	
	public static void moveToBack(Runnable task) {
		if(tasks.contains(task)) {
			remove(task);
			tasks.add(0,task);
		}
	}
	
	public static void add(Runnable task) {
		if(!tasks.contains(task)) {
			tasks.add(task);
		}
	}
	
	public static void remove(Runnable task) {
		tasks.remove(task);
	}
	
	public static void clear() {
		tasks.clear();
	}
	
}