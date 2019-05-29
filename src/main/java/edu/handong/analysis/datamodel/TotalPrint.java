package edu.handong.analysis.datamodel;

public class TotalPrint {
	private int Year;
	public int getYear() {
		return Year;
	}

	public int getSemester() {
		return Semester;
	}

	public String getCouseCode() {
		return CouseCode;
	}

	public String getCourseName() {
		return CourseName;
	}

	private int Semester;
	private String CouseCode;
	private String CourseName;
	private int TotalStudents;
	public int getTotalStudents() {
		return TotalStudents;
	}

	public void setTotalStudents(int totalStudents) {
		TotalStudents = totalStudents;
	}

	private int StudentsTaken;
	public int getStudentsTaken() {
		return StudentsTaken;
	}

	public void setStudentsTaken(int studentsTaken) {
		StudentsTaken = studentsTaken;
	}

	private float Rate;
	
	public float getRate() {
		return Rate;
	}

	public void setRate(float rate) {
		Rate = rate;
	}

	public TotalPrint(int Year,int Semester,String CouseCode,String CourseName,
					int TotalStudents,int StudentsTaken,float Rate) {
		 this.Year=Year;
		 this.Semester=Semester;
		 this.CouseCode=CouseCode;
		 this.CourseName=CourseName;
		 this.TotalStudents=TotalStudents;
		 this.StudentsTaken=StudentsTaken;
		 this.Rate=Rate;
	}
	
	public TotalPrint (int StudentsTaken) {
		this.TotalStudents=TotalStudents;
	}
	
	public TotalPrint (int TotalStudents,float Rate) {
		this.TotalStudents=TotalStudents;
		this.Rate=Rate;
	}

}
