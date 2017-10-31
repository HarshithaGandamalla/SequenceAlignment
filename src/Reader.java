import java.io.*;
import java.util.*;

public class Reader {

	HashMap<Character,Integer> hm = new HashMap<Character,Integer>();
	
	public List<String> Read(File f){
		
		
		List<String> queryList = new ArrayList<String>();
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line=reader.readLine().trim();
	
				int i=0;
				StringBuilder sb = new StringBuilder();
	            while(line!=null){
	            		// StringBuilder sb = new StringBuilder();
	            		// line=reader.readLine().trim();
	        			// System.out.println("line "+line);
	            	 	if(line.startsWith(">")){
	            		//sb.append(line);
	                	//line=reader.readLine();
	                	 if(sb.length() > 0) {
	                			// System.out.println("i"+i++);
	                			 String s = sb.toString();
	                			 queryList.add(s);
	                			 sb = new StringBuilder();
	                		 }
	            		 }
	                	 else {
	                		 int size=queryList.size();
	                		 sb.append(line);
                         
	                	 }
	            	 	line = reader.readLine();
	            }
	            
   			 queryList.add(sb.toString());

	            
	            
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return queryList;
	}



	public int[][] ReadscoringMatrix(File alphafile, File f) {
		// TODO Auto-generated method stub

		int[][] matrix = null;
		
		try {
			BufferedReader reader1 = new BufferedReader(new FileReader(alphafile));

			String alphabetLine = reader1.readLine().trim();
			int length = alphabetLine.length();
			matrix = new int[length][length];
			
			//building hashmap of chars and index
			
			for(int i=0;i<length;i++){
				hm.put(alphabetLine.charAt(i), i);
			}

			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			String line = reader.readLine();
			int row=0;
			while(line!=null)
			{

				String[] rowvalues=line.trim().split("\\s+");

				for(int i=0;i<rowvalues.length;i++){
					matrix[row][i]=Integer.parseInt(rowvalues[i]);
				}
				row++;
				line = reader.readLine();

				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return matrix;
	}
	
	
}
