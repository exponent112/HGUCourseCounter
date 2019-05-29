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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.datamodel.TotalPrint;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;


public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	HashMap<String, TotalPrint> optionC;
	String dataPath;
	String resultPath;
	boolean help;
	String analysis;
	String courseCode;
	boolean isCode;
	String startYear;
	int startInt;
	String endYear;
	int endInt;
	public boolean aResult=true;
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		

		Options options = createOptions();

		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}//if(help)
			else{this.startInt = Integer.parseInt(this.startYear);
			//System.out.println(startYear+startInt);
			
			this.endInt = Integer.parseInt(this.endYear);
			//System.out.println(endYear+endInt);
			
			if(analysis.equals("1")) {
				aResult=true;
				isCode = false;
			}
			else if(analysis.equals("2")) {
				aResult=false;
				if(!isCode) {
					printHelp(options);
					return;
				}
			}
			else {
				printHelp(options);
				return;
			}
			}
    	

		
		
		
			
			ArrayList<String> lines = Utils.getLines(dataPath, true);
			
			students = loadStudentCourseRecords(lines);
			// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
			Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
	
			// Generate result lines to be saved.
			ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
			
			// Write a file (named like the value of resultPath) with linesTobeSaved.
			//System.out.println(isCode);
			if(isCode){
				//System.out.println("1");
				optionC = makeOptionC(students);
				//System.out.println("2");
				Map<String, TotalPrint> sortOptionC = new TreeMap<String, TotalPrint>(optionC);
				//System.out.println("3");
				Utils.writeAFile(resultPath, aResult,sortOptionC);
				//System.out.println("4");
			}
			else {
				Utils.writeAFile(linesToBeSaved, resultPath, aResult);
			}
		
		}
		
	}//run
	
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
			if(Integer.parseInt(studentnum[7].trim())<this.startInt){
				continue;
			}
			if(Integer.parseInt(studentnum[7].trim())>this.endInt){
				continue;
			}
			if(loadMap.containsKey(studentnum[0])) {
				Course newRecord = new Course(lines.get(i));
				loadMap.get(studentnum[0]).addCourse(newRecord);
			}
			else {
				loadMap.put(studentnum[0], new Student(studentnum[0]));
				Course newRecord = new Course(lines.get(i));
				loadMap.get(studentnum[0]).addCourse(newRecord);
			}
			
		}
		// TODO: Implement this method
		return loadMap; // do not forget to return a proper variable.
	}//loadStudentCourseRecords

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
	}//countNumberOfCoursesTakenInEachSemester

	private HashMap<String,TotalPrint> makeOptionC (HashMap<String,Student> students){
		HashMap<String, TotalPrint> C = new HashMap<String,TotalPrint>();
		Set key = students.keySet();
		for(Iterator iterator = key.iterator(); iterator.hasNext();) {
			
			String keyName = (String) iterator.next();
			
			//System.out.println(keyName);
			
			for(int i=0;i<students.get(keyName).getCoursesTaken().size();i++) {
				String tempCourseName=students.get(keyName).getCoursesTaken().get(i).getCourseCode();
				String YandS = Integer.toString(students.get(keyName).getCoursesTaken().get(i).getYearTaken())
						+ Integer.toString(students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken());
				//System.out.println("c="+courseCode.trim());
				//System.out.println(tempCourseName);
				String tempS = YandS+keyName;
				if((courseCode.trim()).equals(tempCourseName.trim())) {
					if(C.containsKey(YandS)) {
						int temp = C.get(YandS).getStudentsTaken();
						temp+=1;
						C.get(YandS).setStudentsTaken(temp);
						
					}//if 추가 
					else {
						String[] tempString= new String[7];
						tempString[0]=new String (String.valueOf(students.get(keyName).getCoursesTaken().get(i).getYearTaken()));
						tempString[1]=new String (String.valueOf(students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken()));
						tempString[2]=new String (students.get(keyName).getCoursesTaken().get(i).getCourseCode());
						tempString[3]=new String (students.get(keyName).getCoursesTaken().get(i).getCourseName());
						tempString[4]=new String (String.valueOf(0));//totalP
						tempString[5]=new String (String.valueOf(1));
						tempString[6]=new String ("");//rate
						C.put(YandS, new TotalPrint(Integer.parseInt(tempString[0]),Integer.parseInt(tempString[1]),tempString[2],tempString[3]
								,0,1,(float) 0.00));
						//System.out.println(C.get(YandS).getCourseName());
					}//if 저장 
				}//if
				else {
					continue;
				}//else
			
			}//for
		}//for
		ArrayList<String> ID = new ArrayList<String>();
		
		
		for(Iterator iterator = key.iterator(); iterator.hasNext();) {
			
			String keyName = (String) iterator.next();
			
			//System.out.println(keyName);
			
			for(int i=0;i<students.get(keyName).getCoursesTaken().size();i++) {
				String YandS = Integer.toString(students.get(keyName).getCoursesTaken().get(i).getYearTaken())
						+ Integer.toString(students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken());
				//System.out.println("aaa"+YandS);
				String tempS = YandS+keyName;
				
				if(C.containsKey(YandS)) {
					
					if(ID.contains(tempS)  ) {
						//System.out.println("arleady contain's");
						continue;
					}
					else {
						int temp = C.get(YandS).getTotalStudents();
						temp+=1;
						int s = C.get(YandS).getStudentsTaken();
						C.get(YandS).setTotalStudents(temp);
						
						//System.out.println(temp);
						
						C.get(YandS).setRate((float)s/temp);
						
						
						ID.add(tempS);
					}
				}
				else {
					continue;
				}
			}
			
		}
		//System.out.println(C.get("20031").getTotalStudents());
		return C;
	}//makeOptionC

	
	
	
	private Options createOptions() {
		Options options = new Options();

		options.addOption(Option.builder("i").longOpt("input")
			     .desc("Set an input file path")
			     .hasArg()
			     .argName("input path")
			     .required()
			     .build());
		
		options.addOption(Option.builder("o").longOpt("output")
	     .desc("Set an output file path")
	     .hasArg()
	     .argName("output path")
	     .required()
	     .build());

		options.addOption(Option.builder("a").longOpt("analysis")
	     .desc("1: Count courses per semester, "
	     		+ "2: Count per course name and yea")
	     .hasArg()
	     .argName("Analysis option")
	     .required()
	     .build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Show a Help page")
		        .build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
			     .desc("Course code for '-a 2' option")
			     .hasArg()
			     .argName("course code")
			     .build());
		options.addOption(Option.builder("s").longOpt("startyear")
			     .desc("Set the start year for analysis e.g., -s 2002")
			     .hasArg()
			     .argName("Start year for analysis")
			     .required()
			     .build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
			     .desc("Set the end year for analysis e.g., -e 2005")
			     .hasArg()
			     .argName("End year for analysis")
			     .required()
			     .build());
		
		return options;
	}//Option
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);
			
			this.dataPath = cmd.getOptionValue("i");
			this.help = cmd.hasOption("h");
			this.resultPath = cmd.getOptionValue("o");
			this.analysis =cmd.getOptionValue("a");
			this.courseCode = cmd.getOptionValue("c");
			this.isCode = cmd.hasOption("c");
			this.startYear = cmd.getOptionValue("s");
			this.endYear =cmd.getOptionValue("e");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}//parseOptions

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}//printHelp
	
	

}//HGUCoursePatternAnalyzer
