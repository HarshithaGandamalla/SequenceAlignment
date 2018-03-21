import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class hw1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		   List<Result> outputK = new ArrayList<Result>();

			
			int alignment_type = Integer.parseInt(args[0]);
			
			if(alignment_type==1){
				
				GlobalAlignment gb = new GlobalAlignment();

				outputK = 	gb.PerformGlobalAlignment(args[1],args[2],args[3],args[4],Integer.parseInt(args[5]),Integer.parseInt(args[6]));
				
                 
				
				for(int i=0;i<outputK.size();i++){
					Result r = outputK.get(i);
					
					System.out.println(r.score);
                 	System.out.println("id1 "+r.id1+" "+r.startpos1+" "+r.seq1);
					System.out.println("id2 "+r.id2+" "+r.startpos2+" "+r.seq2);
//					

				}
				
			}else if(alignment_type==2){
				
				LocalAlignment loc = new LocalAlignment();
				outputK = 	loc.PerformLocalAlignment(args[1],args[2],args[3],args[4],Integer.parseInt(args[5]),Integer.parseInt(args[6]));
				
                 
				
				for(int i=0;i<outputK.size();i++){
					Result r = outputK.get(i);

					System.out.println(r.score);
                    System.out.println("id1 "+r.id1+" "+r.startpos1+" "+r.seq1);
					System.out.println("id2 "+r.id2+" "+r.startpos2+" "+r.seq2);


				}
				

			}else if(alignment_type==3){
				
		     	DoveTail dt = new DoveTail();		
				outputK = 	dt.PerformDoveTailAlignment(args[1],args[2],args[3],args[4],Integer.parseInt(args[5]),Integer.parseInt(args[6]));
				
                 
				
				for(int i=0;i<outputK.size();i++){
					Result r = outputK.get(i);

					System.out.println(r.score);
                    System.out.println("id1 "+r.id1+" "+r.startpos1+" "+r.seq1);
					System.out.println("id2 "+r.id2+" "+r.startpos2+" "+r.seq2);

				}
				

			}else{
				System.out.println("Enter 1,2 or 3");
			}
		
	
	}

}