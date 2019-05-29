package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.*;

import com.opencsv.CSVReader;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.datamodel.TotalPrint;




public class Utils {

	public static ArrayList<String> getLines(String file,boolean removeHeader){
		//여기서 파일 읽어야 함.
		
		ArrayList<String> list = new ArrayList<String>();
        try {
        	
        	File f = new File(file);

    	    // 파일 존재 여부 판단
    	    if (!f.isFile()) {
    	      //System.out.println("OK 파일 있습니다.");
    	    	try {
    				throw new NotEnoughArgumentException();
    			} catch (NotEnoughArgumentException e) {
    				// TODO Auto-generated catch block
    				System.out.println(e.getMessage());
    				System.exit(0);
    			}
    	    }

        	
			CSVReader reader = new CSVReader(new FileReader(file));
        	
        	//CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("hw5data.csv"), "UTF-8"));
        	
        	// UTF-8
            List<String[]> records = reader.readAll();
           
            Iterator<String[]> iterator = records.iterator();
            int i=0;
            
            if(removeHeader) {
            String[] trash = iterator.next();
            }
           
            while (iterator.hasNext()) {
            	String[] s = iterator.next();
    			String temp = s[0]+", "+s[1]+", "+s[2]+", "+s[3]+", "+s[4]+", "+s[5]+", "
    					+s[6]+", "+s[7]+", "+s[8];
    			list.add(temp);
    		       //System.out.println(list.get(0));
    		}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return list;
	}//getLines
	
	//0001,14,1,9
	public static void writeAFile(ArrayList<String> lines, String targetFileName, boolean aResult) {
	//	String enc = new java.io.OutputStreamWriter(System.out).getEncoding();
		String cc = targetFileName;
		FileOutputStream outputStream = null;
		if(aResult){
			try {
				outputStream = new FileOutputStream(cc);
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(outputStream));
				String[] out;
				writer.write("StudentID");
				writer.write(", ");
				writer.write("TotalNumberOfSemestersRegistered");
				writer.write(", ");
				writer.write("Semester");
				writer.write(", ");
				writer.write("NumCoursesTakenInTheSemester");
				writer.write("\n");
				for(int i=0;i<lines.size();i++) {
					out = lines.get(i).trim().split(",");
					writer.write(out[0]);
					writer.write(", ");
					writer.write(out[1]);
					writer.write(", ");
					writer.write(out[2]);
					writer.write(", ");
					writer.write(out[3]);
					writer.write("\n");
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}//catch
		}//if(a = 1)
	}//write
	
	
	public static void writeAFile( String targetFileName, boolean aResult, Map<String,TotalPrint> sortOptionC) {
		String cc = targetFileName;
		FileOutputStream outputStream = null;
		try {
			
			Set key = sortOptionC.keySet();
			outputStream = new FileOutputStream(cc);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(outputStream));
			String[] out;
			writer.write("Year");
			writer.write(", ");
			writer.write("Semester");
			writer.write(", ");
			writer.write("CouseCode");
			writer.write(", ");
			writer.write("CourseName");
			writer.write(", ");
			writer.write("TotalStudents");
			writer.write(", ");
			writer.write("StudentsTaken");
			writer.write(", ");
			writer.write("Rate");
			writer.write("\n");
			for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				String keyName = (String) iterator.next();
				//System.out.println(Integer.toString(sortOptionC.get(keyName).getYear()));
				writer.write(Integer.toString(sortOptionC.get(keyName).getYear()));
				writer.write(", ");
				writer.write(Integer.toString(sortOptionC.get(keyName).getSemester()));
				writer.write(", ");
				writer.write(sortOptionC.get(keyName).getCouseCode());
				writer.write(", ");
				writer.write(sortOptionC.get(keyName).getCourseName());
				writer.write(", ");
				writer.write(Integer.toString(sortOptionC.get(keyName).getTotalStudents()));
				writer.write(", ");
				writer.write(Integer.toString(sortOptionC.get(keyName).getStudentsTaken()));
				writer.write(", ");
				writer.write(String.format("%.2f", sortOptionC.get(keyName).getRate()*100)+"%");
				writer.write("\n");
				
			 }//for
			writer.flush();
			writer.close();
			}catch (IOException e) {
				e.printStackTrace();
			}//catch//try
			
		
	}//else (a = 2)
	
	}



