package com.api.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.api.reporting.SlackUtil;
import com.api.utilities.ExcelUtil;

public class ScratchPad {

	public static void main(String[] args) throws IOException {

		System.out.println("In Scratch Pad!!");
		
//		File file=new File("src/test/resources/Test_Status_Text.txt"); 
//		FileReader fr=new FileReader(file);  
//		BufferedReader br=new BufferedReader(fr);
//		String line=br.readLine();
//		fr.close(); 
//		//<${{ needs.presigned-url.outputs.s3_url }}|Entent Report Link>
//		
//		System.setProperty("testStatus1", line);
//		System.setProperty("ReportLink", "www.angelone.in");
//		String testStatus = System.getProperty("testStatus");
//		String reportLink = System.getProperty("ReportLink");
//		ExcelUtil blocksXl = new ExcelUtil("src/test/resources/Test_Status.xlsx");
//		blocksXl.setAvtiveSheet("Blocks");
//		int rowCount = blocksXl.getNumberOfDataRows()+1; 
//		String channelId = blocksXl.getParam(1, 0);
//		for(String channel: channelId.split(",")) {	
//			SlackUtil slack = new SlackUtil(channel);
//			if(testStatus!=null) {
//				List<String>blocks = Arrays.asList(testStatus.split(";"));
//				for(String mssg:blocks) {
//					List<String>messageLines = Arrays.asList(mssg.split(","));
//					String newMssg ="";
//					for(String s:messageLines) {
//						newMssg+=(s+"\n");
//					}
//					
//					mssg=newMssg.replace("_See Next Bot Message_", "<"+reportLink+"|Entent Report Link>");
//					slack.postFormattedMessageWithThread(mssg);
//				}
//			}else {
//				for(int i=0;i<rowCount;i++) {
//					String mssg = blocksXl.getParam(0, i);
//					mssg=mssg.replace("_See Next Bot Message_", "<"+reportLink+"|Entent Report Link>");
//					slack.postFormattedMessageWithThread(mssg);
//				}		
//			}
//		}
		
		Integer i = 4;
		Integer j = 2;
				
		System.out.println(i>j);
		System.out.println(i<j);
		System.out.println(i++);
		System.out.println(j++);
		System.out.println(++i);
		System.out.println(++j);
	}

}
