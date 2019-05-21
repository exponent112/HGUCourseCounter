package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;
public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 

		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		HashMap<String, Student> loadMap = new HashMap<String, Student>();
		String[] studentnum;
		for(int i=0;i<lines.size();i++) {
			studentnum = lines.get(i).split(",");
			if(loadMap.containsKey(studentnum[0])) {
				Course newRecord = new Course(lines.get(i));
				loadMap.get(studentnum[0]).addCourse(newRecord);
			}
			else {
				loadMap.put(studentnum[0], new Student(studentnum[0]));
				Course newRecord = new Course(lines.get(i));
				loadMap.get(studentnum[0]).addCourse(newRecord);
			}
			
			//System.out.println(loadMap.get(studentnum[0]).a);
			
		}
		// TODO: Implement this method
		return loadMap; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> re = new ArrayList<String> ();
		Set key = sortedStudents.keySet();
		int i=0;
		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
            String keyName = (String) iterator.next();
            Map<String, Integer> sortedC = new TreeMap<String,Integer>(sortedStudents.get(keyName).getSemestersByYearAndSemester()); 
			int s=sortedC.size();
			String id = null;
			int Cn = 0;
			int k = 0;
			id =sortedStudents.get(keyName).getCoursesTaken().get(0).getStudentId();
			//
			for(int t=1;t<s+1;t++) {
				
			Cn = sortedStudents.get(keyName).getNumCourseInNthSementer(t);
			
			k++;
			String all = id+","+s+","+k+","+Cn;
			re.add(all);
			}
		}
		// TODO: Implement this method
		
		return 	re; // do not forget to return a proper variable.
	}

}
