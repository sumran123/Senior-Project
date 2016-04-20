public class MacroBlock{
	String mb_type_;
	String mbPredMode;
	
	int [][][] MvL0,MvL1;
	int [] RefIdxL0,RefIdxL1;
	boolean [] PredFlagL1,PredFlagL0;
	
	int [] Intra4x4PredMode_Prev = new int[16];
	String[] sub_mb_type_ = new String[4];
	  
	// int [][] SL_=new int[16][16];
	int TotalCoeff_;
	public int[] luma4x4blk=new int[16];
	public int[][] cbcr4x4blk=new int[2][16];
}