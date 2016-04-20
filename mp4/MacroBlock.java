public class MacroBlock{
	String mb_type_;
	String mbPredMode;
	
	int [][][] MvL0=new int[4][16][2];
	int[][][] MvL1=new int[4][16][2];
	int [] RefIdxL0=new int[4];
	int [] RefIdxL1=new int[4];
	boolean [] PredFlagL1=new boolean[4];
	boolean [] PredFlagL0=new boolean[4];
	
	int [] Intra4x4PredMode_Prev = new int[16];
	String[] sub_mb_type_ = new String[4];
	  
	// int [][] SL_=new int[16][16];
	int TotalCoeff_;
	public int[] luma4x4blk=new int[16];
	public int[][] cbcr4x4blk=new int[2][16];
}