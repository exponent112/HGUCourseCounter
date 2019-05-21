package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class Student {
	public int a=0;
	private String studentId;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>(); // 학생이 들은 수업 목록
	public ArrayList<Course> getCoursesTaken() {
		return coursesTaken;
	}
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String,Integer>(); 

	public Student(String studentId) {
		// constructor
		this.studentId = studentId;
	}
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		int se =1;
		//수강년도+학기 정보 -> 학생의 순차적인 학기정보 확인 가능 //8 9 
		HashMap<String,Integer> s = new HashMap<String,Integer>(); 
		for(int i=0;i<coursesTaken.size();i++) {
			//학기 (비교하고 저장) 
			//비교
			String t = (coursesTaken.get(i).getYearTaken()+"-"+coursesTaken.get(i).getSemesterCourseTaken());
			if(!s.containsKey(t)){
				s.putIfAbsent(t, se);
				se++;
			}//없으면 넣기 
			}
		//System.out.println(s.get(2002-1));
		return s;
	}
	public int getNumCourseInNthSementer (int semester) {
		//학기 정보 (순차적) ->수강한 과목 수 
		int answer=0;
		Set key = getSemestersByYearAndSemester().keySet();
		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
            String keyName = (String) iterator.next();
            Integer valueName = (Integer) getSemestersByYearAndSemester().get(keyName);
            //학기 수 = semester -> answer++;
            if(semester==valueName) {
            	String[] yearSem = keyName.trim().split("-");
            	for(int i=0;i<coursesTaken.size();i++) {
            		if((coursesTaken.get(i).getYearTaken())==(Integer.parseInt(yearSem[0]))
            				&&(coursesTaken.get(i).getSemesterCourseTaken())==(Integer.parseInt(yearSem[1]))){
            			answer++;
            		}
            	}
            	
            }
          
		}
		return answer;
	}

}
