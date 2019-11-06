import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MergeJSON {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		
		String folderPath = sc.next();
		String baseFileName = folderPath+sc.next();
		String outputFileName = folderPath+sc.next();
		int fileSize = sc.nextInt();
		
		
		JSONParser jsonParser = new JSONParser();
		FileReader reader;
		JSONArray jsonList = new JSONArray();
		Object obj = null;
		JSONObject resultObj = new JSONObject();
		int fileCount=1;
		String keyName = "";
		boolean allFilesRead = false;
		while(!allFilesRead)
		{
			try {
				reader = new FileReader(baseFileName+fileCount+".json");
				obj = jsonParser.parse(reader);
				JSONObject jsonObj = (JSONObject) obj;
				keyName = jsonObj.keySet().toArray()[0].toString();
				jsonList.addAll((JSONArray) jsonObj.get(keyName));
		        fileCount++;
			} catch (IOException | ParseException e) {
				allFilesRead = true;
			}
		}
		try {
			resultObj.put(keyName, jsonList);
			FileWriter fileWriter = new FileWriter(outputFileName+fileCount+".json");
			fileWriter.write(resultObj.toJSONString());
			fileWriter.flush();
            File file = new File(outputFileName+fileCount+".json");
            if(file.length()>fileSize) {
            	System.out.println("File size exceeded the limit.");
            }
		} catch (IOException e) {
			allFilesRead = true;
		}
        System.out.println(jsonList);
	}
}
