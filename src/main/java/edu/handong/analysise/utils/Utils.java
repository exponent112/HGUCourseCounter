package edu.handong.analysise.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;


public class Utils {

	public static ArrayList<String> getLines(String file,boolean removeHeader){
	//return 
		ArrayList<String> list = new ArrayList<String>();
		try {
			int j=0;
			File filef = new File(file);
			FileReader filereader = new FileReader(filef);
			BufferedReader bufReader = new BufferedReader(filereader);
			while (true) {
				String line = bufReader.readLine();
				if(line==null) break;
				String[] words = line.trim().split(",");
				list.add(j,line);
				j++;	
				//for(int i=0;i<list.size();i++) {
				//	System.out.println(list.get(1));
				//}
			}
			bufReader.close();
		}catch (FileNotFoundException e) {
			}catch (IOException e) {
				System.out.println(e);
			}
		return list;
	}
	
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
	}

}

