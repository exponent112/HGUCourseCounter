package edu.handong.analysis.utils;


public class NotEnoughArgumentException extends Exception {
	public NotEnoughArgumentException() {
		System.out.println("The file path does not exist. Please check your CLI argument!");
		System.exit(0);
	}
	public NotEnoughArgumentException(String message) {
		System.out.println("The file path does not exist. Please check your CLI argument!");
		System.exit(0);
	}


}
