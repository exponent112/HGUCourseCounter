package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;


	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String line) {
		String[] cour;
		cour = line.trim().split(",");
		studentId = cour[0];
		yearMonthGraduated= cour[1];
		firstMajor= cour[2];
		secondMajor= cour[3];
		courseCode= cour[4];
		courseName= cour[5];
		courseCredit= cour[6];
		yearTaken= Integer.parseInt(cour[7].trim());
		semesterCourseTaken= Integer.parseInt(cour[8].trim());
		
	}


}
