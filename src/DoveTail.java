import java.io.*;
import java.util.*;

public class  DoveTail {

	
	
	
	
	public List<Result> PerformDoveTailAlignment(String query,String database, String alphabet, String scoringMatrix, int k, int gapPenalty) {
		// TODO Auto-generated constructor stub
		
		File queryFile = new File(query);
		File dbFile = new File(database);
		File matrixFile = new File(scoringMatrix);
		File alphaFile = new File(alphabet);

		Reader input = new Reader();
		
		GetKNeighbours neigh = new GetKNeighbours(k);

		
		List<String> queryList = input.Read(queryFile); //process one query at a time via this list
		List<String> databaseList = input.Read(dbFile); //process one db sequence at a time via this list

        int[][] scoringMatrixArray = input.ReadscoringMatrix(alphaFile,matrixFile);
        
	    HashMap<Character,Integer> hm = input.hm;
		HashMap<String,Integer>  seq_id = input.seq_id;
		
	
		
		int max_Score=Integer.MIN_VALUE;
		

        
        for(int i=0;i<queryList.size();i++){
        	
        	String queryString = queryList.get(i);
        	
    		long startTime = java.lang.System.nanoTime();

        	
        	
        	for(int j=0;j<databaseList.size();j++){
        		
        		int X_index=0;
        		int Y_index=0;
        		
            	String dbString = databaseList.get(j);

        		int row=queryString.length();
        		int col=dbString.length();
        		
        		int[][] globalDB=new int[row+1][col+1];
        		
        		for(int k1=0;k1<=row;k1++){
        			globalDB[k1][0]=0*k1;
        		}
        		
        		for(int k11=0;k11<=col;k11++){
        			globalDB[0][k11]=0*k11;
        		}
        		
        		for(int i1=1;i1<=row;i1++){
        			for(int j1=1;j1<=col;j1++){
        				
        				char c1=Character.toLowerCase(queryString.charAt(i1-1));
        				char c2=Character.toLowerCase(dbString.charAt(j1-1));
        				
        			
        				int	subsitutionPenalty = scoringMatrixArray[hm.get(c1)][hm.get(c2)];        				
        				
        				int	matchcost = globalDB[i1-1][j1-1]+subsitutionPenalty;
        				
        				int insertCost =  globalDB[i1-1][j1]+gapPenalty;
        				
        				int deleteCost =  globalDB[i1][j1-1]+gapPenalty;

//        				
//        				System.out.println("matchcost:"+matchcost);
//        				System.out.println("insertCost:"+insertCost);
//
//        				System.out.println("deleteCost:"+deleteCost);

        				globalDB[i1][j1]=Math.max(insertCost,Math.max(matchcost,deleteCost));
        				
        			       			
        			}
        		}
        		
        		
        		int max_x1=0;
        		int max_y1=0;
        		int max_x2=0;
        		int max_y2=0;
        		
        		int max1=Integer.MIN_VALUE;
        		int max2=Integer.MIN_VALUE;
        		
        		
        		for(int x=0;x<=row;x++){
        			for(int y=0;y<=col;y++){
        				
        				
        				if(y==col){
        					if(max1<globalDB[x][y]){
        					
        						max1=globalDB[x][y];
        						max_x1=x;
        						max_y1=y;
        						
        					}
        				}
        				
        				if(x==row){
        					
        					if(max2<globalDB[x][y]){
        					
        						max2=globalDB[x][y];
        						max_x2=x;
        						max_y2=y;
        					}
        				}
        			}
        		}
        		
//        		System.out.println("max1: "+max1);
//        		
//        		System.out.println("max2: "+max2);
//        		
//        		System.out.println("max_x1: "+max_x1+ "max_y1"+max_y1);
//        		System.out.println("max_x2: "+max_x2+ "max_y2"+max_y2);

                 if(max1>max2){
                	 X_index=max_x1;
                	 Y_index=max_y1;
                	 
                 }else{
                	 X_index=max_x2;
                	 Y_index=max_y2; 
                 }
 
        		
         		Result result = new Result();

           		
          		backtrack(globalDB,queryString,dbString,gapPenalty,hm,scoringMatrixArray,seq_id,X_index,Y_index,result);
           		
        		neigh.insert(result);




       		
        	}
        	long endTime = java.lang.System.nanoTime();
    		System.out.println(queryString.length()+"            "+(float)(endTime-startTime)/1000000000);
        	
        	
        	
        }
        
        return neigh.getkMaxResults();

	}

	private  void backtrack(int[][] a, String queryString, String dbString,int gapPenalty, HashMap<Character, Integer> hm, int[][] scoringMatrixArray, HashMap<String, Integer> seq_id, int x_index, int y_index, Result result) {
		// TODO Auto-generated method stub
		
		
		int i = x_index;
		int j = y_index;
		
		result.setScore(a[i][j]);

		
		//System.out.println("i: "+x_index+"j: "+y_index);
		
		String seq1="";
		String seq2="";
		
		while(i>0 && j>0 && a[i][j]!=0){
			
			int score = a[i][j];
			int score_diagonal = a[i-1][j-1];
			int score_left = a[i][j-1];
			int score_up = a[i-1][j];

			char c1=Character.toLowerCase(queryString.charAt(i-1));
			char c2=Character.toLowerCase(dbString.charAt(j-1));
		
			score_diagonal = score_diagonal+scoringMatrixArray[hm.get(c1)][hm.get(c2)];
			score_up = score_up + gapPenalty; 
			score_left = score_left + gapPenalty;
			
//			if(score==0 || score_diagonal==0 || score_left==0 || score_up==0 )
//			{
//			  break;
//			}
			
		//	System.out.println("c1: "+c1+"c2: "+c2);

			
//			System.out.println("score_diagonal: "+score_diagonal);
//			System.out.println("score_left: "+score_left);
//
//			System.out.println("score_up: "+score_up);

			if(score==score_diagonal){
				
				
				//System.out.println("picking diag: ----------------------");

				seq1 =dbString.charAt(j-1) + seq1;
				seq2= queryString.charAt(i-1) + seq2;
				i--;
				j--;
				
				//System.out.println(seq2+"       "+seq1);
				

			}else if(score==score_left){
				
				//System.out.println("picking left:-------- ");
//
				seq1=dbString.charAt(j-1) + seq1;;
				seq2= '.'+seq2;
				j--;
				
				//System.out.println(seq2+"    "+seq1);

				

			}else if(score==score_up){
				
			//	System.out.println("picking up: ---------------");

				seq1 = '.'+seq1;
				seq2= queryString.charAt(i-1)+seq2;
				i--;
				
			//	System.out.println(seq2+"       "+seq1);


			}else{
		//		System.out.println("ERROR!!");
				break;
			}
			
		}
		
//		while(j>0){
//			
//			seq1=dbString.charAt(j-1) + seq1;;
//			seq2='.'+seq2;
//			j--;
//		}
//		
//		while(i>0){
//			seq2=queryString.charAt(i-1) + seq2;;
//			seq1='.'+seq1;
//			i--;
//			
//		}
		int id1 = seq_id.get(queryString);
		int id2 = seq_id.get(dbString);
		
//		System.out.println("id: "+id1+"S1:"+seq1);
//		System.out.println("id: "+id2+"S2: "+seq2);
		
		result.setSeq1(id1,seq1,i);
		result.setSeq2(id2,seq2,j);

		
//		if(seq1.isEmpty() && seq2.isEmpty()){
//			return true;
//		}
//		
//		return false;
		
		
//		System.out.println("id1:"+id1);
//		System.out.println("id2:"+id2);



	}
	
	

	

	

	

}
