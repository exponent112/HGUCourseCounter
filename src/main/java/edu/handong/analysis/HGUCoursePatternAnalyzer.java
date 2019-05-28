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
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;


public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	HashMap<String,String> optionC;
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
		
		optionRun(args);

		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		optionC = makeOptionC(students);
		
		Map<String, String> sortOptionC = new TreeMap<String, String>(optionC);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 

		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		
		Utils.writeAFile(linesToBeSaved, resultPath, aResult,sortOptionC);
		
		
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
			
			//System.out.println(loadMap.get(studentnum[0]).a);
			
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
	
	private HashMap<String,String> makeOptionC (HashMap<String,Student> students){
		HashMap<String, String> C = new HashMap<String,String>();
		Set key = students.keySet();
		for(Iterator iterator = key.iterator(); iterator.hasNext();) {
			String keyName = (String) iterator.next();
			for(int i=0;i<students.get(keyName).getCoursesTaken().size();i++) {
				int totalP=0;
				int StudentsTaken=1;
			if(courseCode.equals(students.get(keyName).getCoursesTaken().get(i).getCourseCode())) {//courses와 같을 경우 
				String YandS = "students.get(keyName).getCoursesTaken().get(i).getYearTaken()"
						+ "students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken()";
				if(C.containsKey(YandS)) {
					//날짜별로 저장.
					//totalP++;
					StudentsTaken++;
					String[] inital= new String[8];
					inital[0] = String.valueOf(students.get(keyName).getCoursesTaken().get(i).getYearTaken());
					inital[1] = String.valueOf(students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken());
					inital[2] = students.get(keyName).getCoursesTaken().get(i).getCourseCode();
					inital[3] = students.get(keyName).getCoursesTaken().get(i).getCourseName();
					inital[4] = String.valueOf(0);//totalP
					inital[5] = String.valueOf(StudentsTaken);
					inital[6] = "";//rate
					inital[7] = null;
					String initial = inital[0]+","+inital[1]+","+inital[2]+","+inital[3]
							+","+inital[4]+","+inital[5]+","+inital[6];
					C.put(YandS,initial);
					
				}else {
					String[] inital= new String[8];
					inital[0] = String.valueOf(students.get(keyName).getCoursesTaken().get(i).getYearTaken());
					inital[1] = String.valueOf(students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken());
					inital[2] = students.get(keyName).getCoursesTaken().get(i).getCourseCode();
					inital[3] = students.get(keyName).getCoursesTaken().get(i).getCourseName();
					inital[4] = String.valueOf(0);//totalP
					inital[5] = String.valueOf(StudentsTaken);
					inital[6] = "";//rate
					inital[7] = null;
					String initial = inital[0]+","+inital[1]+","+inital[2]+","+inital[3]
							+","+inital[4]+","+inital[5]+","+inital[6];
					C.put(YandS,initial);
					System.out.println(C.get(YandS));
				}
			}else continue; //만약 과목 코드가 같다면 저장 아니면 continue
			}
		}//for end
			
		for(Iterator iterator = key.iterator(); iterator.hasNext();) {
			String keyName = (String) iterator.next();
			for(int i=0;i<students.get(keyName).getCoursesTaken().size();i++) {
				String YandS = "students.get(keyName).getCoursesTaken().get(i).getYearTaken()"
						+ "students.get(keyName).getCoursesTaken().get(i).getSemesterCourseTaken()";
			//만약 key=년도+학기 -> ++;
				if(YandS.equals(keyName)){
					String[] out = C.get(keyName).trim().split(",");
					int k = Integer.parseInt(out[4])+1;
					int p = Integer.parseInt(out[5]);
					out[4] = String.valueOf(k);
					out[6] = String.format("%.2f",k/p);
					String outY = out[0]+","+out[1]+","+out[2]+","+out[3]
							+","+out[4]+","+out[5]+","+out[6];
					C.put(YandS,outY);
				}

			}
		}
		
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
	
	private void optionRun(String[] args) {

		Options options = createOptions();

		if(parseOptions(options, args)){
			this.startInt = Integer.parseInt(this.startYear);
			this.endInt = Integer.parseInt(this.endYear);
			if (help){
				printHelp(options);
				return;
			}//if(help)
			if(analysis.equals("-1")) {
				aResult=true;
			}
			else if(analysis.equals("-2")) {
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
    	}//if(parseOption)
	}//optionRun
}//HGUCoursePatternAnalyzer
