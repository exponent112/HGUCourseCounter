package edu.handong.analysise.utils;

public class NotEnoughArgumentException extends Exception {
	public NotEnoughArgumentException() {
		
	}
	public NotEnoughArgumentException(String message) {
		System.out.println("The file path does not exist. Please check your CLI argument!");
	}
	public String getMessage() {
		String m = "No CLI argument Exception! Please put a file path.";
		// TODO Auto-generated method stub
		return m;
	}

}
