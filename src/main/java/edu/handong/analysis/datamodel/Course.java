package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	public String getStudentId() {
		return studentId;
	}

	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	public String getCourseCode() {
		return courseCode;
	}

	private String courseName;


	public String getCourseName() {
		return courseName;
	}

	private String courseCredit;
	private int yearTaken;

	public int getYearTaken() {
		return yearTaken;
	}

	private int semesterCourseTaken;
	
	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}

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
