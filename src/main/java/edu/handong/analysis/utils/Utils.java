package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;


public class Utils {

	public static ArrayList<String> getLines(String file,boolean removeHeader){
		ArrayList<String> list = new ArrayList<String>();
		File files = new File(file);
		String line=null;
		int count=0;

	       try {
	    	   BufferedReader br = new BufferedReader(new FileReader(files));
	    	   if(removeHeader) br.readLine();
	    	   while((line=br.readLine())!=null) {
	    		   
	    		   list.add(line);
	    	   }
	       }catch (FileNotFoundException e) {
	    	      e.printStackTrace();
	       } catch (IOException e) {
	         e.printStackTrace();
	       }   
		return list;
	}
	
	//0001,14,1,9
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
	//	String enc = new java.io.OutputStreamWriter(System.out).getEncoding();

		try {
			String cc = targetFileName;
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(cc)));
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
		}
		
	}

}

