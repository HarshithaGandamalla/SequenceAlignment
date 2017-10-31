import java.io.*;
import java.util.*;

public class GlobalAlignment {

	
	
	
	
	public GlobalAlignment(String query,String database, String alphabet, String scoringMatrix, int k, int gapPenalty) {
		// TODO Auto-generated constructor stub
		
		File queryFile = new File(query);
		File dbFile = new File(database);
		File matrixFile = new File(scoringMatrix);
		File alphaFile = new File(alphabet);

		Reader input = new Reader();
	    HashMap<Character,Integer> hm = input.hm;
		
		List<String> queryList = input.Read(queryFile); //process one query at a time via this list
		List<String> databaseList = input.Read(dbFile); //process one db sequence at a time via this list

        int[][] scoringMatrixArray = input.ReadscoringMatrix(alphaFile,matrixFile);
		
        
        for(int i=0;i<queryList.size();i++){
        	
        	String queryString = queryList.get(i);
        	
        	for(int j=0;j<databaseList.size();j++){
        		
            	String dbString = databaseList.get(j);

        		int row=queryString.length();
        		int col=dbString.length();
        		
        		int[][] globalDB=new int[row+1][col+1];
        		
        		for(int k1=0;k1<=row;k1++){
        			globalDB[k1][0]=gapPenalty*k1;
        		}
        		
        		for(int k11=0;k11<=col;k11++){
        			globalDB[0][k11]=gapPenalty*k11;
        		}
        		
        		for(int i1=1;i1<=row;i1++){
        			for(int j1=1;j1<=col;j1++){
        				
        				char c1=queryString.charAt(i1-1);
        				char c2=dbString.charAt(j1-1);
        				
        				int subsitutionPenalty =1;
        				
        				if(c1==c2){
        				 
        				//	subsitutionPenalty = scoringMatrixArray[hm.get(c1)][hm.get(c2)];
        					subsitutionPenalty=0;
        				}
        				
        				int	matchcost = globalDB[i1-1][j1-1]+subsitutionPenalty;
        				
        				int insertCost =  globalDB[i1-1][j1]+gapPenalty;
        				
        				int deleteCost =  globalDB[i1][j1-1]+gapPenalty;

        				
        				
        				globalDB[i1][j1]=Math.min(Math.min(matchcost, insertCost),deleteCost);
        				
//        	        	 System.out.println("Row:"+i1+" "+Arrays.toString(globalDB[i1]));	
//        	        	 System.out.println("subsitutionPenalty:"+i1+" "+subsitutionPenalty);	
//        	        	 System.out.println("c1 and c2:"+c1+" "+c2);	
        				
        				

        				
        			}
        		}
        		
        		
        		//backtrack for top k neighbors
        	
        		
        		backtrack(globalDB,queryString,dbString,gapPenalty);
        		
        		
        		
        		
        		for(int p=0;p<=col;p++)
        	 System.out.println(Arrays.toString(globalDB[p]));	
        		
        	}
        	
        	
        	
        }
        

	}

	private void backtrack(int[][] a, String queryString, String dbString,int gapPenalty) {
		// TODO Auto-generated method stub
		
		
		int i = a[0].length-1;
		int j = a.length-1;
		
		String seq1="";
		String seq2="";
		
		while(i>0 && j>0){
			
//			System.out.println("i:"+i+"j:"+j);
			int score = a[i][j];
			int score_diagonal = a[i-1][j-1];
			int score_up = a[i][j-1];
			int score_left = a[i-1][j];
			
//			System.out.println("score:"+score);

//			System.out.println("score_diagonal: "+score_diagonal+"score_up: "+score_up+"score_left: "+score_left);

			
			char c2=queryString.charAt(j-1);
			char c1=dbString.charAt(i-1);
			
//			System.out.println("c1:"+c1);
//			System.out.println("c2:"+c2);


			if(c1!=c2){ //change this to get values from matrix!!!
				score_diagonal =score_diagonal+1;
			}
			
//			System.out.println("scoredia update:"+score_diagonal);

			score_up += score_up+gapPenalty; //change to gappenalty!
			score_left += score_left+gapPenalty;
			
			if(score==score_diagonal){
				seq1 =dbString.charAt(i-1) + seq1;
				seq2= queryString.charAt(j-1) + seq2;
				i--;
				j--;
			}else if(score==score_up){
				seq1 = '.'+seq1;
				seq2= queryString.charAt(j-1)+seq2;
				j--;
			}else if(score==score_left){
				seq1=dbString.charAt(i-1) + seq1;;
				seq2= '.'+seq2;
				i--;
			}else{
				System.out.println("ERROR!!");
			}
			
		}
		
		while(i>0){
			
			seq1=dbString.charAt(i-1) + seq1;;
			seq2='.'+seq2;
			i--;
		}
		
		while(j>0){
			seq2=queryString.charAt(j-1) + seq2;;
			seq1='.'+seq1;
			j--;
			
		}
	
		System.out.println("S1:"+seq1);
		System.out.println("S2:"+seq2);

	}
	
	

	

	

	

}
