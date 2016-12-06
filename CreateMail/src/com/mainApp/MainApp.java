package com.mainApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainApp {
	public static void main(String[] args) {
		//ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		List<Map<String, String>> statistics=null;
		try {
			statistics = getFilesStats();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String numOfFiles=""+statistics.size();
		StringBuilder table=new StringBuilder();
		StringBuilder stats=new StringBuilder();
		
		for (Map<String, String> map : statistics) {
			table.append("<tr><td>"+map.get("FileName").split("/")[6]+"&nbsp</td><td>"+map.get("Total Record")+"</td><td>"+"File looks good"+"&nbsp</td></tr>");
			stats.append("<b>FileName: </b>"+map.get("FileName")+"<br>"+"<b>Total Record: </b>"+map.get("Total Record")+"<br>"+"<b>Success Record: </b>"+map.get("Success Record")
					+"<br>"+"<b>Record Rejected: </b>"+map.get("Record Rejected")+"<br><br>"+"<b>Error File: </b>/home/bconnected/bc3_import/IBC_Reform/LiveVoxDailyResultLoad/error_"
					+map.get("FileName").split("/")[6]+".processed"+"<br><br>");
		}
		
		StringBuilder dailyExtractFiles=new StringBuilder();
		StringBuilder noteExtractFiles=new StringBuilder();
		StringBuilder dates=new StringBuilder();
		
		Set<String> set=new HashSet<String>();
		
		for (Map<String, String> map : statistics) {
			String fileName=map.get("FileName").split("/")[6];
			String datePortion=fileName.split("_")[3];
			set.add(datePortion);
		}
		
		int count=1;
		for (String string : set) {
			dailyExtractFiles.append(count+") "+"/home/bconnected/bc3_export/IBC_Reform/DailyTransactionFeed/IBC_AHNJ_Daily_Transaction_File_"+string+".txt"+"<br>");
			noteExtractFiles.append(count+") "+"/home/bconnected/bc3_export/IBC_Reform/NoteExtract/Note_Extract_"+string+".txt"+"<br>");
			if (count==set.size()-1) {
				dates.append(string.substring(0, 2)+"/"+string.substring(2, 4)+" and ");	
			}else if(count==set.size()){
				dates.append(string.substring(0, 2)+"/"+string.substring(2, 4));
			}else{
				dates.append(string.substring(0, 2)+"/"+string.substring(2, 4)+",");
			}
			
			count++;
		}
		
		/*System.out.println(numOfFiles);
		System.out.println(table);
		System.out.println(stats);
		System.out.println(dailyExtractFiles);
		System.out.println(noteExtractFiles);
		System.out.println(dates);
		 */
		
		BufferedReader reader=null;
		StringBuilder templateData=new StringBuilder();
		try {
			reader=new BufferedReader(new FileReader("template.txt"));
			String line=new String();
			while((line=reader.readLine())!=null){
				templateData.append(line);
				templateData.append("<br>");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String mailData=new String(templateData);
		mailData=mailData.replaceFirst("<<numOfFiles>>", numOfFiles);
		mailData=mailData.replaceFirst("<<table>>", new String(table).trim());
		mailData=mailData.replaceFirst("<<stats>>", new String(stats).trim());
		mailData=mailData.replaceFirst("<<dailyExtractFiles>>", new String(dailyExtractFiles).trim());
		mailData=mailData.replaceFirst("<<noteExtractFiles>>", new String(noteExtractFiles).trim());
		mailData=mailData.replaceAll("<<dates>>", new String(dates));
		
		System.out.println(mailData);
		BufferedWriter writer=null;
		try {
		    writer=new BufferedWriter(new FileWriter("mail.html"));
			writer.write(mailData);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
            //Close the BufferedWriter
            try {
                if (writer != null) {
                	writer.flush();
                	writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
		
		
	}

	private static List<Map<String, String>> getFilesStats() throws IOException {

		List<Map<String, String>> statistics = new LinkedList<Map<String, String>>();

		HashMap<String, String> map = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		String line = new String();
		while ((line = reader.readLine()) != null) {
			switch (line) {
			case "EOF":
				statistics.add(map);
				return statistics;

			case "\n":
				statistics.add(map);
				map = new HashMap<String, String>();
				break;
				
			default:
				String[] tokens=line.split(":");
				if (tokens.length<2) {
					statistics.add(map);
					map = new HashMap<String, String>();
					break;
				}
				map.put(tokens[0].trim(), tokens[1].trim());
				
			}

		}
		
		return statistics;
	}

}
