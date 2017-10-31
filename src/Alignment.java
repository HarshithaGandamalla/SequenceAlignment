import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Alignment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			
			int alignment_type = Integer.parseInt(args[0]);
			
			if(alignment_type==1){
				
				GlobalAlignment gb = new GlobalAlignment(args[1],args[2],args[3],args[4],Integer.parseInt(args[5]),Integer.parseInt(args[6]));
				
			}else if(alignment_type==2){
				
			}else if(alignment_type==3){
				
			}else{
				//ERROR
			}
		
	
	}

}
