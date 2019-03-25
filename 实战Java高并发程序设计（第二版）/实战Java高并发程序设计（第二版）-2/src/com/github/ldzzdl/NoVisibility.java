package com.github.ldzzdl;

public class NoVisibility {

	private static volatile boolean ready;
	
	private static volatile int number;
	
	private static class ReaderThread extends Thread{
		
		@Override
		public void run(){
			while(!ready);
			System.out.println(number);
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ReaderThread().start();
		Thread.sleep(1000);
		number = 42;
		ready = true;
		Thread.sleep(10000);
		System.out.println("=====================");
	}

}
