package com.github.ldzzdl;

public class Test1 {

	public static void main(String[] args) {
		
		Thread t1 = new Thread(){
			@Override
			public void run(){
				System.out.println("Hello, I am t1");
			}
		};
		
		t1.start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("runnable");
			}
		}).start();
		
	}

}
