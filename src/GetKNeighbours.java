import java.util.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GetKNeighbours {

	PriorityQueue<Result> pQueue;
	List<Result> outputKResult = new ArrayList<Result>();
	int k;
	
	GetKNeighbours(int k){
		
		this.k=k;
		
		pQueue = new PriorityQueue<Result>(k,new Comparator<Result>(){

		    public int compare(Result r1, Result r2) {
		       return r2.score-r1.score;
		    }
		
		});
	}
	void insert(Result r){
		pQueue.add(r);
	}
	
	 List<Result> getkMaxResults(){
		
		while(outputKResult.size()!=k){
			outputKResult.add(pQueue.poll());
		}
		
		return outputKResult;
		
		
	}
	
	
	
	
	
	
}
