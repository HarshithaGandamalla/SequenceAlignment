
public class Result {

	
	   int score;
	   int id1;
	   int id2;
	   String seq1;
	   String seq2;
	   int startpos1;
	   int startpos2;
	   
	   
	public  void setScore(int i) {
		// TODO Auto-generated method stub
		
		this.score=i;
	}
	public void setSeq1(int id1, String seq1,int startpos1) {
		// TODO Auto-generated method stub
		
		this.id1=id1;
		this.seq1=seq1;
		this.startpos1=startpos1;
		
	}
	public void setSeq2(int id2, String seq2,int startpos2) {
		// TODO Auto-generated method stub
		this.id2=id2;
		this.seq2=seq2;
		this.startpos2=startpos2;

	}

}
