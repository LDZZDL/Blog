package com.github.ldzzdl;

public class DamonDemo {

	public static class DaemoT extends Thread{
		
		@Override
		public void run(){
			while(true){
				System.out.println("I am alive");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new DaemoT();
		thread.setDaemon(true);
		thread.start();
		Thread.sleep(2000);
	}

}
