
import java.util.*;
import java.lang.String;
import java.io.*;
import java.util.*;
import java.io.File;
import java.math.BigInteger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.WritableRaster;
import java.awt.Image;
import java.awt.*;

import java.awt.image.BufferedImage;

public class Slice{
	MacroBlock [] MacroBlockData;
	sps sps0;
	pps pps0;
	nal nal0;
	int[] scalingList ={6,13,13,20,20,20,28,28,28,28,32,32,32,37,37,42};
	boolean no_output_of_prior_pics_flag; //u1
	boolean long_term_reference_flag;//u1
	boolean adaptive_ref_pic_marking_mode_flag;//u1
	int memory_management_control_operation;//uev
	int difference_of_pic_nums_minus1;//uev
	int long_term_pic_num;//uev
	int long_term_frame_idx;//uev
	int max_long_term_frame_idx_plus1;//uev
	boolean IdrPicFlag;
	parser p;
	int first_mb_in_slice; //uev
	int slice_type; //uev
	int pic_parameter_set_id;//uev
	int colour_plane_id; //u2
	int frame_num; //uv
	boolean field_pic_flag; //u1
	boolean bottom_field_flag; //u1
	int idr_pic_id; //uev
	int pic_order_cnt_lsb; //uv
	int delta_pic_order_cnt_bottom;//sev
	int delta_pic_order_cnt[];
	int redundant_pic_cnt; //uev
	boolean direct_spatial_mv_pred_flag;
	boolean num_ref_idx_active_override_flag;
	int num_ref_idx_l0_active_minus1;//uev
	int num_ref_idx_l1_active_minus1; //uev
	int cabac_init_idc; //uev
	int slice_qp_delta;//sev
	boolean sp_for_switch_flag;//u1

	int slice_qs_delta; //sev
	int disable_deblocking_filter_idc; //sev
	int slice_alpha_c0_offset_div2; //sev
	int slice_beta_offset_div2;//sev
	int slice_group_change_cycle; //uv
	int cabac_alignment_one_bit;
	boolean MbaffFrameFlag; 
	int CurrMbAddr;      
	boolean moreDataFlag; //  
	boolean prevMbSkipped; //
	int mb_skip_run;
	boolean mb_field_decoding_flag;
	String mb_type; //uev|aev
	int mbRow; // row in lookuptable for mb type
	int pcm_alignment_zero_bit;//f1
	int [] pcm_sample_luma; //uv [256]
	int [] pcm_sample_chroma; //2 * MbWidthC * MbHeightC // uv
	Scanner scan= new Scanner(System.in);

	boolean transform_size_8x8_flag=false;//if not in bit stream //u1|aev
	int coded_block_pattern; //aev mev
	int mb_qp_delta; //sev|aev
	boolean noSubMbPartSizeLessThan8x8Flag;

	// mb_pred
	boolean[] prev_intra4x4_pred_mode_flag;//u1
	int[] rem_intra4x4_pred_mode;//u3
	boolean[] prev_intra8x8_pred_mode_flag;//u1
	int[] rem_intra8x8_pred_mode;//u3
	int intra_chroma_pred_mode; //uev
	int []ref_idx_l0;//tev
	int []ref_idx_l1;//tev
	int [][][]mvd_l0;//sev
	int[][][] mvd_l1; //sev
	int luma4x4BlkIdx;
	int cr4x4BlkIdx;
	int cb4x4BlkIdx;
	int blkA;
	int blkB;
	int CodedBlockPatternLuma,CodedBlockPatternChroma;
	int xS,yS,x,y,xC,yC,iCbCr,mbAddrA,mbAddrB,mbAddrN,luma4x4BlkIdxB,luma4x4BlkIdxA,mbAddrC,mbAddrD,xW,yW,xA,yA,xB,yB,nA,nB,nC,chroma4x4BlkIdx,chroma4x4BlkIdxA,chroma4x4BlkIdxB;
	int maxW=16;
	int maxH=16;
	boolean availableFlagA,availableFlagB,availableFlagC,availableFlagD,availableFlagN;
	int[] Intra16x16DCLevel;
	int[][] Intra16x16ACLevel;
	int[][] LumaLevel4x4;
	int[][] LumaLevel8x8;

	int NumC8x8;
	int[][] ChromaDCLevel;
	int [][][] ChromaACLevel;


	int[] CbIntra16x16DCLevel;
	int[][] CbIntra16x16ACLevel;
	int[][] CbLevel4x4;
	int[][] CbLevel8x8;
	String mb_type_table;



	int[] CrIntra16x16DCLevel;
	int[][] CrIntra16x16ACLevel;
	int[][] CrLevel4x4;
	int[][] CrLevel8x8;
	// int PicHeightInMbs;
	int BitDepthY;
	int BitDepthC;
	int ChromaArrayType;
	boolean sMbFlag;
	int BitDepth;
	int [][] SL;
	int qp;
	int QPY=0;
	int QpBdOffsetY;
	boolean TransformBypassModeFlag;
	boolean mbIsInterFlag;
	int PicWidthInSamplesL;
	int PicHeightInSamplesL;
	int PicWidthInMbs;
	int PicHeightInMbs;
	int FrameHeightInMbs;
	int [][] predL,predPartL;
	int MbWidthC;
	int MbHeightC;
	int [] Intra4x4PredMode;
	boolean dcPredModePredictedFlag;
	MacroBlock mbData;
	int intraMxMPredModeA;
	int intraMxMPredModeB;
	String N;
	// inter varibles 
	int MvCnt,partWidth,partHeight,partWidthC,partHeightC,SubWidthC,SubHeightC;
	int[] mvL0 = new int[2];
	int refIdxL0;
	boolean predFlagL0;
	int[] mvL1 = new int[2];
	int refIdxL1;
	boolean refIdxL1Flag;
	boolean refIdxL0Flag;
	boolean mvL1Flag;
	boolean mvL0Flag;
	boolean availableFlagMbPartA, availableFlagMbPartB, availableFlagMbPartC, availableFlagMbPartD;
	boolean predFlagL1;
	int subMvCnt;
	int mbPartIdx;
	int subMbPartIdx;
	int predPartWidth;
	String currSubMbType;
	boolean listSuffixFlag;
	int[] mvL0A = new int[2];
	int[] mvL0B = new int[2];
	int[] mvL0D = new int[2];
	int[] mvL0C = new int[2];
	
	int[] mvL1A = new int[2];
	int[] mvL1B = new int[2];
	int[] mvL1D = new int[2];
	int[] mvL1C = new int[2];

	int refIdxL1D,refIdxL1C,refIdxL1A,refIdxL1B,refIdxL0D,refIdxL0C,refIdxL0A,refIdxL0B,mbPartIdxC,mbPartIdxN,mbPartIdxD,mbPartIdxA,mbPartIdxB,subMbPartIdxC,subMbPartIdxN,subMbPartIdxD,subMbPartIdxA,subMbPartIdxB; 
	String mbTypeN;
	String[] sub_mb_type=new String[4];
	String[] subMbTypeN = new String[4];
	int [][][] MvL0,MvL1;
	int [] RefIdxL0,RefIdxL1;
	boolean [] PredFlagL1,PredFlagL0;
	int LogWDL,W0L,W1L,O0L,O1L;
	int [][] predPartL1L,predPartL0L;
	int [][] refPicLXL;
	int refPicL0L,refPicL1L;

	int []RefPicList0=new int[10000];
	int []RefPicList1=new int[10000];
	int [][] predPartLXL;



	// inter variables 

	// Referene picture list modification variables 
	boolean ref_pic_list_modification_flag_l0;
	int modification_of_pic_nums_idc;
	int abs_diff_pic_num_minus1;
	// int long_term_pic_num;
	boolean ref_pic_list_modification_flag_l1;
	// int modification_of_pic_nums_idc;
	// int abs_diff_pic_num_minus1;
	int ong_term_pic_num;
	Slice(byte[] rbsp,sps sps_,pps pps_,nal nal_0){
		sps0=sps_;
		pps0=pps_;
		nal0=nal_0;
		for (int i=0;i<10000 ;i++ ) {
			RefPicList0[i]=i;
			RefPicList1[i]=i;
		}
		p=new parser(rbsp);
		if(sps0.mb_adaptive_frame_field_flag&&!field_pic_flag){
			MbaffFrameFlag=true;
		}else{
			MbaffFrameFlag=false;
		}
		if(sps0.separate_colour_plane_flag==false){
			ChromaArrayType=sps0.chroma_format_idc;
		}else{
			ChromaArrayType=0;
		}
		for(int i=0;i<4;i++){
			sub_mb_type[i]="na";
		}
		BitDepthY=8+sps0.bit_depth_luma_minus8;
		PicWidthInMbs=sps0.pic_width_in_mbs_minus_1+1;
		FrameHeightInMbs = (2-(sps0.frame_mbs_only_flag ? 1:0))*(sps0.pic_height_in_map_units_minus_1+1);
		PicHeightInMbs = FrameHeightInMbs / (1+(field_pic_flag ? 1:0));
		PicWidthInSamplesL=PicWidthInMbs*16;
		PicHeightInSamplesL=PicHeightInMbs*16;
		MacroBlockData =new MacroBlock[PicHeightInMbs*PicWidthInMbs];
		SL=new int [PicWidthInSamplesL][PicHeightInSamplesL];
		predL=new int[16][16];
		slice_layer_without_partitioning_rbsp();
	}
	public void slice_layer_without_partitioning_rbsp(){
		slice_header();	
		slice_data();
	}

	public void ref_pic_list_modification(){
		if((slice_type % 5 != 2) &&  (slice_type % 5 != 4)) {
			ref_pic_list_modification_flag_l0 = p.getBit();
			if(ref_pic_list_modification_flag_l0) {
				modification_of_pic_nums_idc = p.uev();
				while (modification_of_pic_nums_idc != 3) {
					if(modification_of_pic_nums_idc == 0 || modification_of_pic_nums_idc == 1) {
						abs_diff_pic_num_minus1 = p.uev();
					} else if(modification_of_pic_nums_idc == 2) {
						long_term_pic_num = p.uev();
					}
					modification_of_pic_nums_idc = p.uev();
				}
			}
		}
		if(slice_type % 5 == 1) {
			ref_pic_list_modification_flag_l1 = p.getBit();
			if (ref_pic_list_modification_flag_l1) {	
				modification_of_pic_nums_idc = p.uev();
				while (modification_of_pic_nums_idc != 3) {
					if(modification_of_pic_nums_idc == 0 || modification_of_pic_nums_idc == 1) {
						abs_diff_pic_num_minus1 = p.uev();
					} else if(modification_of_pic_nums_idc == 2) {
						long_term_pic_num = p.uev();
					}
					modification_of_pic_nums_idc = p.uev();
				}
			}
		}
	}
	public void slice_header(){
		first_mb_in_slice = p.uev();
		slice_type = p.uev();
		set_mb_type_table();
		System.out.println("slice_type "+slice_type);
		pic_parameter_set_id=p.uev();
		if(sps0.separate_colour_plane_flag){
			colour_plane_id=p.readBits(2);

		}
		int v = sps0.log2_max_frame_num_minus4;
		frame_num=p.readBits(v+4);
		if(nal0.nal_unit_type==5) {
			IdrPicFlag=true;	
		}else {
			IdrPicFlag=false;
		}
		field_pic_flag=false;
		if(!(sps0.frame_mbs_only_flag)){ 
			field_pic_flag=p.getBit();
			if(field_pic_flag){
				bottom_field_flag=p.getBit();
			}
		}
		if(IdrPicFlag){
			idr_pic_id=p.uev();
		}
		if(sps0.pic_order_cnt_type==0){
			int n=sps0.log2_max_pic_order_cnt_lsb_minus4+4; //
			pic_order_cnt_lsb=p.readBits(n);
			if (pps0.bottom_field_pic_order_in_frame_present_flag&&!field_pic_flag) {
				delta_pic_order_cnt_bottom=p.sev();

			}
		}
		delta_pic_order_cnt=new int[3]; // 
		if(sps0.pic_order_cnt_type==1&&sps0.delta_pic_order_always_zero_flag){
			delta_pic_order_cnt[0]=p.sev();
		}
		if(pps0.bottom_field_pic_order_in_frame_present_flag&&!field_pic_flag){
			delta_pic_order_cnt[1]=p.sev();

		}
		if(pps0.redundant_pic_cnt_present_flag){
			redundant_pic_cnt=p.uev();

		}
		if(slice_type==1||slice_type==6){
			direct_spatial_mv_pred_flag=p.getBit();
		}
			// 0	P (P slice)
			// 1	B (B slice)
			// 2	I (I slice)
			// 3	SP (SP slice)
			// 4	SI (SI slice)
			// 5	P (P slice)
			// 6	B (B slice)
			// 7	I (I slice)
			// 8	SP (SP slice)
			// 9	SI (SI slice)
		if(slice_type==0||slice_type==5||slice_type==3||slice_type==8
			||slice_type==6||slice_type==1){
			num_ref_idx_active_override_flag=p.getBit();
			if(num_ref_idx_active_override_flag){
				num_ref_idx_l0_active_minus1=p.uev();
				// System.out.println("num_ref_idx_l0_active_minus1 "+num_ref_idx_l0_active_minus1);
				if(slice_type==1||slice_type==6){
					num_ref_idx_l1_active_minus1=p.uev();

				}
			}
		}
		if(nal0.nal_unit_type==20||nal0.nal_unit_type==21){
			System.out.println("ref_pic_list_mvc_modification( ) ");
		}else{
			ref_pic_list_modification();
			// System.out.println("ref_pic_list_modification( ) to be implemented");
		}

		if(pps0.weighted_pred_flag&&(slice_type==0||slice_type==5
			||slice_type==3||slice_type==8)||(pps0.weighted_bipred_idc==1&&(slice_type==1 ||slice_type==6)
			)){
		}

		if(nal0.nal_ref_idc!=0){
			dec_ref_pic_marking();
		}
		if(pps0.entropy_coding_mode_flag&&slice_type!=7&&slice_type!=2&&slice_type!=4&&slice_type!=9){
			cabac_init_idc=p.uev();	
		}
		slice_qp_delta=p.sev();

		if(slice_type==3||slice_type==8||slice_type==4||slice_type==9){
			if (slice_type==3||slice_type==8) {
				sp_for_switch_flag=p.getBit();
			}
			slice_qs_delta=p.sev();
		}
		if (pps0.deblocking_filter_control_present_flag) {
			disable_deblocking_filter_idc=p.uev();
				slice_alpha_c0_offset_div2=p.sev();
				slice_beta_offset_div2=p.sev();
			}
		slice_group_change_cycle=0;
		if (pps0.num_slice_groups_minus1>0&&pps0.slice_group_map_type>=3&&pps0.slice_group_map_type<=5) {
			int bits = (int)Math.ceil(Math.log((pps0.pic_size_in_map_units_minus1+1)/(pps0.slice_group_change_rate_minus1+1)+1));
			slice_group_change_cycle=p.readBits(bits);
		}
		QPY = pps0.pic_init_qp_minus26+slice_qp_delta+26;
		// System.out.println("slice_qp_delta "+slice_qp_delta);
	}
	public void slice_data() {
		int counter=0;
		int x;

		if(pps0.entropy_coding_mode_flag) {
			while(! p.byte_aligned()) {
				cabac_alignment_one_bit = p.readBits(1);
			}
		}
		MbaffFrameFlag = sps0.mb_adaptive_frame_field_flag && (field_pic_flag == false);		
		CurrMbAddr = first_mb_in_slice*((MbaffFrameFlag ? 1 : 0)+1);																// int conversion 
		moreDataFlag = true;
		prevMbSkipped = false;
		while(moreDataFlag) {
			if(!(slice_type == 2 || slice_type == 7) && 
					!(slice_type == 4 || slice_type == 9)) {  					
 				if(! pps0.entropy_coding_mode_flag) {
 					int masla=p.pointer;
					mb_skip_run = p.uev();
					int endmasla=p.pointer;
					int maslasize=endmasla-masla;
					// System.out.println("size ! "+maslasize);
					prevMbSkipped = (mb_skip_run > 0);// ? true : false;
					for(int i = 0; i < mb_skip_run; i++) {
						mbData=new MacroBlock();
						mbData.mb_type_="P_Skip";
						mb_type="P_Skip";
						Inter_prediction_process();
						MacroBlockData[CurrMbAddr]=mbData;
						// System.out.println("CurrMbAddr "+CurrMbAddr);

						CurrMbAddr = NextMbAddress(CurrMbAddr);
					}
					if(mb_skip_run > 0) {
						moreDataFlag = p.more_rbsp_data();
						System.out.println(moreDataFlag +" skip mb in stream " + mb_skip_run);
					}
				} else {
					System.out.println("Cabac implemetation required ");
				}
			}
			if(moreDataFlag) {
				System.out.println("flag of mb P_Skip "+prevMbSkipped);
				if(MbaffFrameFlag && (CurrMbAddr % 2 == 0 ||
					 (CurrMbAddr % 2 == 1 && prevMbSkipped))) {
					System.out.println("came here in mb filed decoding flag");
					mb_field_decoding_flag = p.getBit(); //
					System.out.println("");
				}
				int start=p.pointer;
				System.out.println("**************************************************");
				System.out.println();
				System.out.println("mb extracted "+ CurrMbAddr);
				mbData=new MacroBlock();
				macroblock_layer();
				// for (int i=0;i<CurrMbAddr+1 ;i++ ) {
				// 	System.out.print(" "+i+" "+MacroBlockData[i].mb_type_);
					
				// }
				int end = p.pointer;
				int size = end-start;
				System.out.println("Size "+ size); 

				System.out.println("*************************"+p.pointer+"*************************");
				x=scan.nextInt();

	
			}
			if(! pps0.entropy_coding_mode_flag) {
				moreDataFlag = p.more_rbsp_data();
			} else {
				System.out.println("implemetation required for cabac only");
				 
				if(!(slice_type == 2 || slice_type == 7) && 
						!(slice_type == 4 || slice_type == 9)) {


				}
				if(MbaffFrameFlag && CurrMbAddr % 2 == 0){
					moreDataFlag = true;

				} else {
				}	
			}
			CurrMbAddr = NextMbAddress(CurrMbAddr);

		}
		ToImage();
	}
	public void set_mb_type_table() {
		if(slice_type % 5 == 2) {
			mb_type_table = "table7.11.txt";
		} else if(slice_type % 5 == 0) {
			mb_type_table = "table7.13.txt";
		}
		// Some types implementation missing
	}
	// **********
	// using table 7-13 7-14 7-17 7-18

	public int MbPartWidth(String mb_type_0){
		// for p_skip only 
		int ret=16;
		if(mb_type_0.equals("P_Skip")){
			ret= 16;

		}
		return ret;
	}
	public int MbPartHeight(String mb_type_0){
		// for p_skip only
		int ret=16;
		if(mb_type_0.equals("P_Skip")){
			ret= 16;

		}
		return ret;
	}
	public int SubMbPartHeight(String mb_type_0){
		if(mb_type_0.equals("P_L0_8x8")){
			return 8;
		}else if(mb_type_0.equals("P_L0_8x4")){
			return 4;
		}else if(mb_type_0.equals("P_L0_4x8")){
			return 8;
		}else if(mb_type_0.equals("P_L0_4x4")){
			return 4;
		}
		return 0;
	}
	public int SubMbPartWidth(String mb_type_0){
		if(mb_type_0.equals("P_L0_8x8")){
			return 8;
		}else if(mb_type_0.equals("P_L0_8x4")){
			return 8;
		}else if(mb_type_0.equals("P_L0_4x8")){
			return 4;
		}else if(mb_type_0.equals("P_L0_4x4")){
			return 4;
		}
		return 0;
	}
	public int NumMbPart(String mb_type_0){
		if(mb_type_0.equals("P_L0_16x16")){
			return 1;
		}else if(mb_type_0.equals("P_L0_L0_16x8")){
			return 2;
		}else if(mb_type_0.equals("P_L0_L0_8x16")){
			return 2;
		}else if(mb_type_0.equals("P_8x8")){
			return 4;
		}else if(mb_type_0.equals("P_8x8ref0")){
			return 4;
		}else if(mb_type_0.equals("P_Skip")){
			return 1;
		}
		return -1;
	}
	// using table 7-13 7-14 7-17 7-18
	// **********
	// 6.4.2.1
	public void Inverse_macroblock_partition_scanning_process(){
		x=InverseRasterScan(mbPartIdx, MbPartWidth(mb_type),MbPartHeight(mb_type),16,0);
		y=InverseRasterScan(mbPartIdx, MbPartWidth(mb_type),MbPartHeight(mb_type),16,1);

	}
	// 6.4.11.7
	public void Derivation_process_for_neighbouring_partitions(){
		// step 1
		// call 6.4.2.1
		Inverse_macroblock_partition_scanning_process();
		// Step 2;
		// int ;
		if(mb_type.equals("P_8x8")||mb_type.equals("P_8x8ref0")||mb_type.equals("B_8x8")){
			// 6.4.2.2 to be invoked
		}else{
			xS=0;
			yS=0;
		}
		// step 3
		if(mb_type.equals("P_Skip")||mb_type.equals("B_Skip")||mb_type.equals("B_Direct_16x16")){
			predPartWidth=16;
		}else if (mb_type.equals("B_8x8")){
			// not applicable in BaseLine

		}else if(mb_type.equals("P_8x8")||mb_type.equals("P_8x8ref0")){
			predPartWidth=SubMbPartWidth(sub_mb_type[mbPartIdx]);
		}else{
			predPartWidth=MbPartWidth(mb_type);
		}
		// step 4
		int xD,yD;
		// if(N.equals("A")){
		// table 6.2
		xD=-1;
		yD=0;
		xA=x+xS+xD;
		yA=y+yS+yD;
		// }else if(N.equals("B")){
		xD=0;
		yD=-1;
		xB=x+xS+xD;
		yB=y+yS+yD;	
		// }else if(N.equals("C")){
		xD=predPartWidth;
		yD=-1;
		xC=x+xS+xD;
		yC=y+yS+yD;
		// }else if(N.equals("D")){
		xD=-1;
		yD=-1;
		xD=x+xS-1;
		yD=y+yS-1;
		// }
		// step 5
		
		
		// step 6
		Derivation_process_for_neighbouring_locations(xA,yA);
		// step 7
		if(!availableFlagN){
			availableFlagMbPartA=false;
		}else{
			// step a
			mbTypeN=MacroBlockData[mbAddrN].mb_type_;
			if(mbTypeN.equals("P_8x8") || mbTypeN.equals("P_8x8ref0") || mbTypeN.equals("B_8x8")) {
				subMbTypeN=MacroBlockData[mbAddrN].sub_mb_type_;
			} 
			// step b
			// call 6.4.13.4
			Derivation_process_for_macroblock_and_sub_macroblock_partition_indices();
			mbPartIdxA=mbPartIdxN;
			subMbPartIdxA=subMbPartIdxN;


		}
		Derivation_process_for_neighbouring_locations(xB,yB);
		// step 7
		if(!availableFlagN){
			availableFlagMbPartB=false;
		}else{
			// step a
			mbTypeN=MacroBlockData[mbAddrN].mb_type_;
			if(mbTypeN.equals("P_8x8") || mbTypeN.equals("P_8x8ref0") || mbTypeN.equals("B_8x8")) {
				subMbTypeN=MacroBlockData[mbAddrN].sub_mb_type_;
			} 
			// step b
			// call 6.4.13.4
			Derivation_process_for_macroblock_and_sub_macroblock_partition_indices();
			mbPartIdxB=mbPartIdxN;
			subMbPartIdxB=subMbPartIdxN;
		}

		Derivation_process_for_neighbouring_locations(xC,yC);
		// step 7
		if(!availableFlagN){
			availableFlagMbPartC=false;
		}else{
			// step a
			mbTypeN=MacroBlockData[mbAddrN].mb_type_;
			if(mbTypeN.equals("P_8x8") || mbTypeN.equals("P_8x8ref0") || mbTypeN.equals("B_8x8")) {
				subMbTypeN=MacroBlockData[mbAddrN].sub_mb_type_;
			} 
			// step b
			// call 6.4.13.4
			Derivation_process_for_macroblock_and_sub_macroblock_partition_indices();
			mbPartIdxC=mbPartIdxN;
			subMbPartIdxC=subMbPartIdxN;
		}

		Derivation_process_for_neighbouring_locations(xD,yD);
		// step 7
		if(!availableFlagN){
			availableFlagMbPartD=false;
		}else{
			// step a
			mbTypeN=MacroBlockData[mbAddrN].mb_type_;
			if(mbTypeN.equals("P_8x8") || mbTypeN.equals("P_8x8ref0") || mbTypeN.equals("B_8x8")) {
				subMbTypeN=MacroBlockData[mbAddrN].sub_mb_type_;
			} 
			// step b
			// call 6.4.13.4
			Derivation_process_for_macroblock_and_sub_macroblock_partition_indices();
			mbPartIdxD=mbPartIdxN;
			subMbPartIdxD=subMbPartIdxN;
		}
	}
	// 6.4.13.4
	public void Derivation_process_for_macroblock_and_sub_macroblock_partition_indices(){
		if(mbTypeN.indexOf("Intra")>=0){
			mbPartIdxN=0;
		}else{
			mbPartIdxN=(16/MbPartWidth(mbTypeN))*(yW/MbPartHeight(mbTypeN))+(xW/MbPartWidth(mbTypeN));

		}
		if(!mbTypeN.equals("P_8x8")||!mbTypeN.equals("P_8x8ref0")||!mbTypeN.equals("B_8x8")||
			!mbTypeN.equals("B_Skip")||!mbTypeN.equals("B_Direct_16x16")){
			subMbPartIdxN=0;
		}else if(mbTypeN.equals("B_Skip")||mbTypeN.equals("B_Direct_16x16")){
			subMbPartIdxN = 2*((yW%8)/4)+((xW %8)/4);
		}else if(mbTypeN.equals("P_8x8")||mbTypeN.equals("P_8x8ref0")||mbTypeN.equals("B_8x8")){
			subMbPartIdxN=(8/SubMbPartWidth(subMbTypeN[mbPartIdxN])) 
			*((yW%8)/SubMbPartHeight(subMbTypeN[mbPartIdxN]))+
			((xW%8)/SubMbPartWidth(subMbTypeN[mbPartIdxN]));
		}
	}
	// 8.4
	public void Inter_prediction_process(){

		System.out.println("Inter_prediction_process for mb_type "+mb_type);
		if(mb_type.equals("P_Skip")){
			// return;

		}else{
			System.out.println(mb_type+" Inter_prediction_process");
			// return;
		}
		for(mbPartIdx=0;mbPartIdx<NumMbPart(mb_type);mbPartIdx++){
			System.out.println("called once");
			if(!mb_type.equals("P_8x8")||!mb_type.equals("P_8x8ref0")||
				!mb_type.equals("B_Skip")||!mb_type.equals("B_Direct_16x16")||
				!mb_type.equals("B_8x8")){
				partWidth=MbPartWidth(mb_type);
				partHeight=MbPartHeight(mb_type);
			}
			partWidthC=partWidth/getSubWidthC();
			partHeightC=partHeight/getSubHeightC();


			MvCnt=0;

			// step 1
			// call 8.4.1
			Derivation_process_for_motion_vector_components_and_reference_indices();


			// step 2
			MvCnt=MvCnt+subMvCnt;

			// step 3
			if((pps0.weighted_pred_flag==true&&(slice_type%5==0||slice_type%5==3))||(pps0.weighted_bipred_idc>0&&slice_type%5==1)){
				// call 8.4.3
				System.out.println("complete the Derivation_process_for_prediction_weights");
				Derivation_process_for_prediction_weights();

			}
			// step 4
			// call 8.4.2
			Decoding_process_for_Inter_prediction_samples();
			
			// MvL0[mbPartIdx][subMbPartIdx]=mvL0;
			// MvL1[mbPartIdx][subMbPartIdx]=mvL1;
			// RefIdxL0[mbPartIdx]=refIdxL0;
			// RefIdxL1[mbPartIdx]=refIdxL1;
			// PredFlagL0[mbPartIdx]=predFlagL0;
			// PredFlagL1[mbPartIdx]=predFlagL1;

			// call 6.4.2.1
			Inverse_macroblock_partition_scanning_process();
			int xP=x;
			int yP=y;

			// call 6.4.2.2
			// int xS,yS;
			Inverse_sub_macroblock_partition_scanning_process();
			xS=x;
			yS=y;
			for(x=0;x<partWidth-1;x++){
				for(y=0;y<partHeight-1;y++){
					// predL[xP+xS+x][yP+yS+y]=predPartL[x][y];
				}
			}
		}


	}
	// 6.4.2.2
	
	public void Inverse_sub_macroblock_partition_scanning_process(){
		if(mb_type.equals("P_8x8")||mb_type.equals("P_8x8ref0")||mb_type.equals("B_8x8")){
			x=InverseRasterScan(subMbPartIdx,SubMbPartWidth(sub_mb_type[mbPartIdx]),
				SubMbPartHeight(sub_mb_type[mbPartIdx]),8,0);
			y=InverseRasterScan(subMbPartIdx,SubMbPartWidth(sub_mb_type[mbPartIdx]),
				SubMbPartHeight(sub_mb_type[mbPartIdx]),8,1);
		}else{
			x=InverseRasterScan(subMbPartIdx,4,4,8,0);
			y=InverseRasterScan(subMbPartIdx,4,4,8,1);

		}
	}

	// 8.4.2
	public void Decoding_process_for_Inter_prediction_samples(){
		// System.out.println("");
		System.out.println("8.4.2 implementation required");
		predPartL1L=new int[partWidth][partHeight];
		predPartL0L=new int[partWidth][partHeight];
		if(predFlagL0==true){
			// call 8.4.2.1
			Reference_picture_selection_process(0);
			// call 8.4.2.2
			Fractional_sample_interpolation_process(0);

		}
		// call 8.4.2.3



	}
	// 8.4.2.1
	public void Reference_picture_selection_process(int X){
		if(X==0){
			if(field_pic_flag){
				refPicL0L=RefPicList0[refIdxL0];
			}else if(!field_pic_flag){

				if(sps0.frame_mbs_only_flag){
					refPicL0L=RefPicList0[refIdxL0];

				}else{
					System.out.println("not frame mb do the implementation");
				}
			}
			if(!sps0.separate_colour_plane_flag){
				// refrence picture corresponds to SL


			}

		}else if(X==1){
			if(field_pic_flag){
				refPicL1L=RefPicList1[refIdxL1];
			}else if(!field_pic_flag){

				if(sps0.frame_mbs_only_flag){
					refPicL1L=RefPicList1[refIdxL1];

				}else{
					System.out.println("not frame mb do the implementation");
				}
			}
			if(!sps0.separate_colour_plane_flag){
				// refrence picture corresponds to SL


			}

		}
	}
	// 8.4.2.2
	public void Fractional_sample_interpolation_process(int X){
		int xAL = 0;
		int yAL = 0,
		int xIntL,yIntL,xFracL,yFracL;
		if(X==0){
			predPartL0L=new int[partWidth][partHeight];
			for(int xL = 0; xL < partWidth; xL++) {
				
			}
		}

	}
	public void 

	// 8.4.3
	public void	Derivation_process_for_prediction_weights(){
		// System.out.println("8.4.3 implementation required");
		boolean implicitModeFlag,explicitModeFlag;
		if(pps0.weighted_bipred_idc==2&&slice_type%5==1&&predFlagL0==true&&predFlagL1==true){
			implicitModeFlag=true;
			explicitModeFlag=false;

		}else if(pps0.weighted_bipred_idc==1&&slice_type%5==1&&(predFlagL0||predFlagL1)){
			implicitModeFlag=false;
			explicitModeFlag=true;

		}else if(pps0.weighted_pred_flag==true&&(slice_type%5==0||slice_type%5==3)&&predFlagL0==true){
			implicitModeFlag=false;
			explicitModeFlag=true;

		}else{
			implicitModeFlag=false;
			explicitModeFlag=false;
		}
		if(implicitModeFlag){
			LogWDL=5;
			O0L=0;
			O1L=0;
			// follow steps
			// 1
			
		}
	}

	// 8.4.1
	public void Derivation_process_for_motion_vector_components_and_reference_indices() {
		if(mb_type.equals("P_Skip")) {
			predFlagL0 = true;
			predFlagL1 = false;
			mvL1Flag = false;
			refIdxL1Flag = false;
			// caLL 8.4.1.1
			// System.out.println("called 8.4.1");
			// 8.4.1.1
			Derivation_process_for_luma_motion_vectors_for_skipped_macroblocks_in_P_and_SP_slices();
			subMvCnt = 1;


		}
		else if(mb_type.equals("B_Skip")||mb_type.equals("B_Direct_16x16")||sub_mb_type[mbPartIdx].equals("B_Direct_8x8")){
			System.out.println("B slice implementation required ");

		}else{
			System.out.println("complete the remaining steps to continue ");

		}
		// Only for P_Skip Macroblock
	}
	// 8.4.1.1
	public void Derivation_process_for_luma_motion_vectors_for_skipped_macroblocks_in_P_and_SP_slices(){
		refIdxL0=0;
		// step 1
		// call 8.4.1.3.2 
		mbPartIdx=0;
		subMbPartIdx=0;
		currSubMbType="na";
		listSuffixFlag=false;
		Derivation_process_for_motion_data_of_neighbouring_partitions();

		// step 2
		if(!availableFlagA || !availableFlagB) {
			// System.out.println("not availableFlagA");
			mvL0[0] = 0;
			mvL0[1] = 0;
		}else if(refIdxL0A == 0 && mvL0A[0] == 0 && mvL0A[1] == 0) {
			mvL0[0] = 0;
			mvL0[1] = 0;
		} else if(refIdxL0B == 0 && mvL0B[0] == 0 && mvL0B[1] == 0) {
			mvL0[0] = 0;
			mvL0[1] = 0;
		} else {
			mbPartIdx=0;
			subMbPartIdx=0;
			currSubMbType ="na";
			// call 8.4.1.3
			System.out.println("call 8.4.1.3");
			// Derivation_process_for_luma_motion_vector_prediction();
		}
		
	}
	// 8.4.1.3
	public void Derivation_process_for_luma_motion_vector_prediction(){
		
	}
	// 8.4.1.3.2
	public void Derivation_process_for_motion_data_of_neighbouring_partitions(){
		// step 1
		// mbAddrD\mbPartIdxD\subMbPartIdxD be variables specifying an additional neighbouring partition.
		// step 2
		// call 6.4.11.7
		Derivation_process_for_neighbouring_partitions();
		// step 3
		if(!availableFlagMbPartC){
			mbAddrC=mbAddrD;
			mbPartIdxC=mbPartIdxD;
			subMbPartIdxC=subMbPartIdxD;
		}
		// for A,B,C,D, X=0
		if(!listSuffixFlag){
		// A
			if(!availableFlagMbPartA){
				mvL0A[0]=0;
				mvL0A[1]=0;
				refIdxL0A=-1;
			}else if(MacroBlockData[mbAddrA].mb_type_.indexOf("Intra")>=0){
				mvL0A[0]=0;
				mvL0A[1]=0;
				refIdxL0A=-1;
			}else if(!predFlagL0){
				mvL0A[0]=0;
				mvL0A[1]=0;
				refIdxL0A=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL0A=MvL0[mbPartIdxA][subMbPartIdxA];
				refIdxL0A=RefIdxL0[mbPartIdxA];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
			// B
			if(!availableFlagMbPartB){
				mvL0B[0]=0;
				mvL0B[1]=0;
				refIdxL0B=-1;
			}else if(MacroBlockData[mbAddrB].mb_type_.indexOf("Intra")>=0){
				mvL0B[0]=0;
				mvL0B[1]=0;
				refIdxL0A=-1;
			}else if(!predFlagL0){
				mvL0B[0]=0;
				mvL0B[1]=0;
				refIdxL0B=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL0B=MvL0[mbPartIdxB][subMbPartIdxB];
				refIdxL0B=RefIdxL0[mbPartIdxB];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
			// C
			if(!availableFlagMbPartC){
				mvL0C[0]=0;
				mvL0C[1]=0;
				refIdxL0C=-1;
			}else if(MacroBlockData[mbAddrC].mb_type_.indexOf("Intra")>=0){
				mvL0C[0]=0;
				mvL0C[1]=0;
				refIdxL0C=-1;
			}else if(!predFlagL0){
				mvL0C[0]=0;
				mvL0C[1]=0;
				refIdxL0C=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL0C=MvL0[mbPartIdxC][subMbPartIdxC];
				refIdxL0C=RefIdxL0[mbPartIdxC];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
			// D

			if(!availableFlagMbPartD){
				mvL0D[0]=0;
				mvL0D[1]=0;
				refIdxL0D=-1;
			}else if(MacroBlockData[mbAddrD].mb_type_.indexOf("Intra")>=0){
				mvL0D[0]=0;
				mvL0D[1]=0;
				refIdxL0D=-1;
			}else if(!predFlagL0){
				mvL0D[0]=0;
				mvL0D[1]=0;
				refIdxL0D=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL0D=MvL0[mbPartIdxD][subMbPartIdxD];
				refIdxL0D=RefIdxL0[mbPartIdxD];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
		}else if(listSuffixFlag){
				if(!availableFlagMbPartA){
				mvL1A[0]=0;
				mvL1A[1]=0;
				refIdxL1A=-1;
			}else if(MacroBlockData[mbAddrA].mb_type_.indexOf("Intra")>=0){
				mvL1A[0]=0;
				mvL1A[1]=0;
				refIdxL1A=-1;
			}else if(!predFlagL0){
				mvL1A[0]=0;
				mvL1A[1]=0;
				refIdxL1A=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL1A=MvL0[mbPartIdxA][subMbPartIdxA];
				refIdxL1A=RefIdxL0[mbPartIdxA];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
			// B
			if(!availableFlagMbPartB){
				mvL1B[0]=0;
				mvL1B[1]=0;
				refIdxL1B=-1;
			}else if(MacroBlockData[mbAddrB].mb_type_.indexOf("Intra")>=0){
				mvL1B[0]=0;
				mvL1B[1]=0;
				refIdxL1A=-1;
			}else if(!predFlagL0){
				mvL1B[0]=0;
				mvL1B[1]=0;
				refIdxL1B=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL1B=MvL0[mbPartIdxB][subMbPartIdxB];
				refIdxL1B=RefIdxL0[mbPartIdxB];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
			// C
			if(!availableFlagMbPartC){
				mvL1C[0]=0;
				mvL1C[1]=0;
				refIdxL1C=-1;
			}else if(MacroBlockData[mbAddrC].mb_type_.indexOf("Intra")>=0){
				mvL1C[0]=0;
				mvL1C[1]=0;
				refIdxL1C=-1;
			}else if(!predFlagL0){
				mvL1C[0]=0;
				mvL1C[1]=0;
				refIdxL1C=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL1C=MvL0[mbPartIdxC][subMbPartIdxC];
				refIdxL1C=RefIdxL0[mbPartIdxC];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
			// D

			if(!availableFlagMbPartD){
				mvL1D[0]=0;
				mvL1D[1]=0;
				refIdxL1D=-1;
			}else if(MacroBlockData[mbAddrD].mb_type_.indexOf("Intra")>=0){
				mvL1D[0]=0;
				mvL1D[1]=0;
				refIdxL1D=-1;
			}else if(!predFlagL0){
				mvL1D[0]=0;
				mvL1D[1]=0;
				refIdxL1D=-1;
				// otherwise follow these steps
			} else{
				// step 1
				mvL1D=MvL0[mbPartIdxD][subMbPartIdxD];
				refIdxL1D=RefIdxL0[mbPartIdxD];

				// step 2
				// if curr is field and a is frame
				// mvL0A[1]=(int)(mvL0A[1]/2);
				// refIdxL0A=refIdxL0A*2;
				// if curr is frame and a is field
				// mvL0A[1]=(int)(mvL0A[1]*2);
				// refIdxL0A=(int)(refIdxL0A/2);
			}
		}
	}
	

	// 8.3.1.1
	public void Derivation_process_for_Intra4x4PredMode() {
		Derivation_process_for_neighbouring_4x4_luma_blocks(); // 6.4.11.4
		//  step 2
		if(!availableFlagA || !availableFlagB) {
			dcPredModePredictedFlag = true;
		} else if(availableFlagA) {
			if(mbAddrA == CurrMbAddr) {
				if(MbPartPredMode(mbRow,0).indexOf("Intra") == -1 && pps0.constrained_intra_pred_flag) {
					dcPredModePredictedFlag = true;
				}
			}else{
				if(MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra") == -1 && pps0.constrained_intra_pred_flag) {
					dcPredModePredictedFlag = true;
				}
			}
		}else if(availableFlagB) {
			if(mbAddrB == CurrMbAddr) {
				if(MbPartPredMode(mbRow,0).indexOf("Intra") == -1 && pps0.constrained_intra_pred_flag) {
					dcPredModePredictedFlag = true;
				}

			}else{
				if(MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra") == -1 && pps0.constrained_intra_pred_flag) {
					dcPredModePredictedFlag = true;
				}
			}
		} else {
			dcPredModePredictedFlag = false;
		}
		// step 3
		if(dcPredModePredictedFlag) {
			intraMxMPredModeA = 2;
		}else if(availableFlagA) {
			if(mbAddrA == CurrMbAddr) {
				if(MbPartPredMode(mbRow,0).indexOf("Intra_4x4") == -1 || MbPartPredMode(mbRow,0).indexOf("Intra_8x8") == -1) {
					intraMxMPredModeA = 2;
				}
			}else{
				if(MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra_4x4") == -1 || MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra_8x8") == -1) {
					intraMxMPredModeA = 2;
				}
			}
		} else if(!dcPredModePredictedFlag) {
			if(availableFlagA) {
				if(mbAddrA == CurrMbAddr) {
					if(MbPartPredMode(mbRow,0).indexOf("Intra_4x4") != -1 || MbPartPredMode(mbRow,0).indexOf("Intra_8x8") != -1) {
						if(MbPartPredMode(mbRow,0).indexOf("Intra_4x4") != -1) {
							intraMxMPredModeA = Intra4x4PredMode[luma4x4BlkIdxA]; 
						}else if(MbPartPredMode(mbRow,0).indexOf("Intra_8x8") != -1){
							//  set the value
						}
					}
				}else{
					if(MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra_4x4") != -1 || MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra_8x8") != -1) {
						if(MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra_4x4") != -1) {
							intraMxMPredModeA = MacroBlockData[mbAddrA].Intra4x4PredMode_Prev[luma4x4BlkIdxA];
						}else if(MacroBlockData[mbAddrA].mbPredMode.indexOf("Intra_8x8") != -1){
							//  set the value
						}
					}
				}
			}
		}

		// step 3 for B
		if(dcPredModePredictedFlag) {
			intraMxMPredModeB = 2;
		}else if(availableFlagB) {
			if(mbAddrB == CurrMbAddr) {
				if(MbPartPredMode(mbRow,0).indexOf("Intra_4x4") == -1 || MbPartPredMode(mbRow,0).indexOf("Intra_8x8") == -1) {
					intraMxMPredModeB = 2;
				}
			}else{
				if(MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra_4x4") == -1 || MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra_8x8") == -1) {
					intraMxMPredModeB = 2;
				}
			}
		} else if(!dcPredModePredictedFlag) {
			if(availableFlagB) {
				if(mbAddrB == CurrMbAddr) {
					if(MbPartPredMode(mbRow,0).indexOf("Intra_4x4") != -1 || MbPartPredMode(mbRow,0).indexOf("Intra_8x8") != -1) {
						if(MbPartPredMode(mbRow,0).indexOf("Intra_4x4") != -1) {
							intraMxMPredModeB = Intra4x4PredMode[luma4x4BlkIdxB]; 
						}else if(MbPartPredMode(mbRow,0).indexOf("Intra_8x8") != -1){
							//  set the value
						}
					}
				}else{
					if(MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra_4x4") != -1 || MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra_8x8") != -1) {
						if(MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra_4x4") != -1) {
							intraMxMPredModeB = MacroBlockData[mbAddrB].Intra4x4PredMode_Prev[luma4x4BlkIdxA];
						}else if(MacroBlockData[mbAddrB].mbPredMode.indexOf("Intra_8x8") != -1){
							//  set the value
						}
					}
				}
			}
		}
		// Step 4
		int predIntra4x4PredMode;  
		predIntra4x4PredMode = Math.min(intraMxMPredModeA, intraMxMPredModeB);
		if(prev_intra4x4_pred_mode_flag[luma4x4BlkIdx]) {
			Intra4x4PredMode[luma4x4BlkIdx] = predIntra4x4PredMode;
		} else {
			if(rem_intra4x4_pred_mode[luma4x4BlkIdx] < predIntra4x4PredMode) {
				Intra4x4PredMode[luma4x4BlkIdx] = rem_intra4x4_pred_mode[luma4x4BlkIdx];
			} else {
				Intra4x4PredMode[luma4x4BlkIdx] = rem_intra4x4_pred_mode[luma4x4BlkIdx] + 1;
			} 
		}
	}
	// 8.3.1.2
	public void Intra_4x4_sample_prediction(){

		int []ret=Inverse_4x4_luma_block_scanning_process(luma4x4BlkIdx);
		int xO,yO;
		xO=ret[0];
		yO=ret[1];
		int [] p_xminus_yminus=new int[5];
		int [] p_xplus_yminus=new int[8];
		boolean[] p_xminus_yminus_available_flag=new boolean[5];
		boolean[] p_xplus_yminus_available_flag=new boolean[8];
		int xN,yN;
		int x=-1;
		int y=0;
		for(y=0;y<5;y++){
			// Step 1
			xN=xO+x;
			yN=yO+y;
			// step 2
			Derivation_process_for_neighbouring_locations(xN,yN);
			// step 3

			
		}

		y=-1;
		for(x=0;x<8;x++){

		}
	}
	// 8.3.1
	public void Intra_4x4_prediction_process_for_luma_samples() {
		// call 8.3.1.1
		for (luma4x4BlkIdx=0;luma4x4BlkIdx<16;luma4x4BlkIdx++){
			Derivation_process_for_Intra4x4PredMode();
		}
		for (luma4x4BlkIdx=0;luma4x4BlkIdx<16;luma4x4BlkIdx++){
			// 8.3.1.2

		}
		// for()
	}
	public void transform_decoding_process_for_4x4_luma_residual_blocks(){
		if(!transform_size_8x8_flag){
			for(int luma4x4BlkIdx=0;luma4x4BlkIdx<16;luma4x4BlkIdx++){
				// System.out.println("********* "+luma4x4BlkIdx+1+ " *************");
				int [][]c=new int[4][4];
				c=Inverse_zigzag_process(LumaLevel4x4[luma4x4BlkIdx]);
				int [][]r=Scaling_and_transformation_process(c);
				// for(int i=0;i<4;i++){
					// System.out.println();
					// for (int j=0;j<16 ;j++ ){
						// System.out.print(" "+LumaLevel4x4[j]);						
					// }
				// }
				// System.out.println();
				// System.out.println();
				// for(int i=0;i<4;i++){
				// 	System.out.println();
				// 	for (int j=0;j<4 ;j++ ){
				// 		System.out.print(" "+r[i][j]);						
				// 	}
				// }
				
				// System.out.println();
				
				// step 3
				if(TransformBypassModeFlag&&MbPartPredMode(mbRow,0).equals("Intra_4x4")){
					// &&(Intra4x4PredMode[luma4x4BlkIdx]==0||Intra4x4PredMode[luma4x4BlkIdx]==1)){
					// invoke process 8.5.15
					System.out.println("implemetation required for 8.5.15");

				}

			}
		}
	}

	public void macroblock_layer() {
		mbData=new MacroBlock();
		Intra4x4PredMode =new int[16];
		if(sps0.chroma_format_idc==0||sps0.separate_colour_plane_flag){
			MbWidthC=0;
			MbHeightC=0;

		}else{
			MbWidthC=16/getSubWidthC();
			MbHeightC=16/getSubHeightC();
		}
		// System.out.println("MbWidthC "+MbWidthC+" MbHeightC "+MbHeightC);

		mbRow=p.uev();
		// System.out.println("mbRow "+mbRow);
		if(mbRow>4&&slice_type%5==0){
			mbRow=mbRow-5;
			mb_type_table="table7.11.txt";
		}else if(mbRow<4&&slice_type%5==0){
			mb_type_table="table7.13.txt";
		}
		// System.out.println("mbRow ==> "+ mbRow);
		mb_type=p.Mb_Type(mb_type_table ,mbRow,1);
		System.out.println("mb type ==> "+mb_type);
		String patLuma,patChroma;
		if(mb_type_table.equals("table7.11.txt")){
			patLuma=p.Mb_Type(mb_type_table,mbRow,6);
			patChroma=p.Mb_Type(mb_type_table,mbRow,5);
			CodedBlockPatternChroma=0;
			CodedBlockPatternLuma=0;
			if(patChroma.equals("Equation7-36")){
				CodedBlockPatternLuma = (int)(coded_block_pattern %16);
				CodedBlockPatternChroma = (int)(coded_block_pattern/16.0);
			}else{
				CodedBlockPatternChroma=Integer.parseInt(patChroma);
				CodedBlockPatternLuma=Integer.parseInt(patLuma);
			}
		}
		if(mb_type.equals("I_PCM")){
			System.out.println("I_PCM mb extracted");
			while(!p.byte_aligned()){
				pcm_alignment_zero_bit=p.readBits(1);
			}
			pcm_sample_luma=new int[256];
			BitDepthY = 8 + sps0.bit_depth_luma_minus8;

			for(int i=0;i<256;i++){
				pcm_sample_luma[i]=p.readBits(BitDepthY);
			}

			int pcm_sample_chroma_Size=2*MbWidthC*MbHeightC;
			pcm_sample_chroma=new int[pcm_sample_chroma_Size];
			
			BitDepthC = 8 + sps0.bit_depth_chroma_minus8;
			for(int i=0;i<pcm_sample_chroma_Size;i++){
				pcm_sample_chroma[i]=p.readBits(BitDepthC);
			}
		}else {
			noSubMbPartSizeLessThan8x8Flag=true;
			if(!mb_type.equals("I_NxN")&& !MbPartPredMode(mbRow,0).equals("Intra_16x16")
			&&NumMbPart(mbRow)==4){
				System.out.println("sub_mb_pred unimplemented");
				sub_mb_pred(mbRow);
				for(int mbPartIdx=0;mbPartIdx<4;mbPartIdx++){
					if(!sub_mb_type[mbPartIdx].equals("B_Direct_8x8")){
						if(NumSubMbPart(sub_mb_type[mbPartIdx])>1){
							noSubMbPartSizeLessThan8x8Flag=false;
						}
					}else if(!sps0.direct_8x8_inference_flag){
						noSubMbPartSizeLessThan8x8Flag=false;
					}
				}
			}else{
				if(pps0.transform_8x8_mode_flag&& mb_type.equals("I_NxN")){
					transform_size_8x8_flag=p.getBit();
					System.out.println("transform_size_8x8_flag ");
				}
				mb_pred(mbRow);

			}
			if(!MbPartPredMode(mbRow,0).equals("Intra_16x16")){
				// System.out.println("ChromaArrayType "+" "+ChromaArrayType);
				if(slice_type%5==0){
					if(mb_type_table.equals("table7.11.txt")){
						coded_block_pattern=p.mev(1,ChromaArrayType);
					}else{
						coded_block_pattern=p.mev(0,ChromaArrayType);
					}
				}else if(slice_type%5==2){
					coded_block_pattern=p.mev(1,ChromaArrayType);
				}
				// System.out.println("coded_block_pattern "+coded_block_pattern);
					CodedBlockPatternChroma=(int)(coded_block_pattern/16.0);
					CodedBlockPatternLuma=(int)(coded_block_pattern%16.0);
				if(CodedBlockPatternLuma>0 && pps0.transform_8x8_mode_flag
					&&!mb_type.equals("I_NxN")
					&&noSubMbPartSizeLessThan8x8Flag&&(!mb_type.equals("B_Direct_16x16")
						||sps0.direct_8x8_inference_flag)){
					transform_size_8x8_flag=p.getBit();
				}
			}
			if(CodedBlockPatternLuma>0||CodedBlockPatternChroma>0||
				MbPartPredMode(mbRow,0).equals("Intra_16x16")){
				mb_qp_delta=p.sev();
				// System.out.println("mb_qp_delta "+ mb_qp_delta);
				if(mb_qp_delta!=0){
					QPY=((QPY+mb_qp_delta+52+(2*QpBdOffsetY))%(52+QpBdOffsetY))-QpBdOffsetY;
				}
				// System.out.println(" mb_qp_delta "+mb_qp_delta+"qpy "+QPY);
				residual(0,15);
				// System.out.println("");
				// System.out.println(MbPartPredMode(mbRow,0).equals("Intra_4x4"));
				
				// transform_decoding_process_for_luma_samples_of_Intra_16x16();
				// transform_decoding_process_for_chroma_samples();
				mbData.mb_type_=mb_type;
				mbData.mbPredMode = MbPartPredMode(mbRow,0);
				mbData.Intra4x4PredMode_Prev = Intra4x4PredMode;
				MacroBlockData[CurrMbAddr]=mbData;
			}
		}
		if(MbPartPredMode(mbRow,0).equals("Intra_16x16")){
			transform_decoding_process_for_luma_samples_of_Intra_16x16();
		// }else if(MbPartPredMode(mbRow,0).equals("Intra_4x4")){
		}else if(MbPartPredMode(mbRow,0).equals("Intra_4x4")){
			// System.out.println("4x4 construction ");
			transform_decoding_process_for_4x4_luma_residual_blocks();
		}else{
			System.out.println("mb_type not intra  "+mb_type);
			Inter_prediction_process();
		}
		mbData.mb_type_=mb_type;

		MacroBlockData[CurrMbAddr]=mbData;
		// System.out.println("CurrMbAddr "+CurrMbAddr+" "+MacroBlockData[CurrMbAddr].mb_type_);

	}
	public void mb_pred(int r){
		if(!transform_size_8x8_flag&&r==0&&mb_type_table.equals("table7.11.txt")){
			r=-1;
		}
		// System.out.println("call to mb_pred "+MbPartPredMode(r,0));
		if(MbPartPredMode(r,0).equals("Intra_4x4")||
			MbPartPredMode(r,0).equals("Intra_8x8")||
			MbPartPredMode(r,0).equals("Intra_16x16")){
			if(MbPartPredMode(r,0).equals("Intra_4x4")){
				prev_intra4x4_pred_mode_flag=new boolean[16];
				rem_intra4x4_pred_mode=new int [16];
				for (luma4x4BlkIdx=0;luma4x4BlkIdx<16 ;luma4x4BlkIdx++ ) {
					prev_intra4x4_pred_mode_flag[luma4x4BlkIdx]=p.getBit();
					if(!prev_intra4x4_pred_mode_flag[luma4x4BlkIdx]){
						rem_intra4x4_pred_mode[luma4x4BlkIdx]=p.readBits(3);
					}else{
						rem_intra4x4_pred_mode[luma4x4BlkIdx]=0;
					}
				}

			}
			if(MbPartPredMode(r,0).equals("Intra_8x8")){
				prev_intra8x8_pred_mode_flag=new boolean[4];
				rem_intra8x8_pred_mode=new int [4];
				for (int luma8x8BlkIdx=0;luma8x8BlkIdx<4 ;luma8x8BlkIdx++ ) {
					prev_intra8x8_pred_mode_flag[luma8x8BlkIdx]=p.getBit();
					if(!prev_intra8x8_pred_mode_flag[luma8x8BlkIdx]){
						rem_intra8x8_pred_mode[luma8x8BlkIdx]=p.readBits(3);
					}else{
						rem_intra8x8_pred_mode[luma8x8BlkIdx]=0;
					}
				}
			}
			if(ChromaArrayType==1||ChromaArrayType==2){
				// System.out.println("chroma tyoe 1 or 2");
				intra_chroma_pred_mode=p.uev();
				// System.out.println(intra_chroma_pred_mode+" intra_chroma_pred_mode ");
			}
		}else if(!MbPartPredMode(r,0).equals("Direct")){
			ref_idx_l0=new int[NumMbPart(mbRow)];//tev
			ref_idx_l1=new int[NumMbPart(mbRow)];//tev
			mvd_l0=new int[NumMbPart(mbRow)][2][2];//sev
			mvd_l1=new int[NumMbPart(mbRow)][2][2]; //sev
			for(int mbPartIdx=0;mbPartIdx<NumMbPart(mbRow);mbPartIdx++){
				if((num_ref_idx_l0_active_minus1>0||mb_field_decoding_flag!=field_pic_flag)&&
				MbPartPredMode(mbRow,mbPartIdx).equals("Pred_L1")){
					System.out.println("ref_idx_l0");
					ref_idx_l0[mbPartIdx]=p.tev();	
				}
			}
			for (int mbPartIdx=0;mbPartIdx<NumMbPart(mbRow) ;mbPartIdx++ ) {
				if((num_ref_idx_l1_active_minus1>0||mb_field_decoding_flag!=field_pic_flag)&&
					MbPartPredMode(mbRow,mbPartIdx).equals("Pred_L0")){
					System.out.println("ref_idx_l1");

					ref_idx_l1[mbPartIdx]=p.tev();
				}
			}
			for (int mbPartIdx=0;mbPartIdx<NumMbPart(mbRow) ;mbPartIdx++ ) {
				if(MbPartPredMode(mbRow,mbPartIdx).equals("Pred_L1")){
					for(int compIdx=0;compIdx<2;compIdx++){
						System.out.println("mvd_l0 "+mvd_l0[mbPartIdx][0][compIdx]);
						mvd_l0[mbPartIdx][0][compIdx]=p.sev();
					}
				}
			}

			for (int mbPartIdx=0;mbPartIdx<NumMbPart(mbRow) ;mbPartIdx++ ) {
				if(MbPartPredMode(mbRow,mbPartIdx).equals("Pred_L0")){
					for(int compIdx=0;compIdx<2;compIdx++){
						mvd_l1[mbPartIdx][0][compIdx]=p.sev();
						System.out.println("mvd_l1 "+mvd_l1[mbPartIdx][0][compIdx]);
					}
				}
			}
		}
	}

	public int NextMbAddress(int n) { // used im slice data , taken from
		// 8.2.2



		//The variables PicHeightInMapUnits and PicSizeInMapUnits are derived as
		// PicHeightInMapUnits = pic_height_in_map_units_minus1 + 1 (7-16)
		// PicSizeInMapUnits = PicWidthInMbs * PicHeightInMapUnits
		int PicSizeInMapUnits= (sps0.pic_width_in_mbs_minus_1+1)*(sps0.pic_height_in_map_units_minus_1+1);
		// int FrameHeightInMbs = (2-(sps0.frame_mbs_only_flag ? 1:0))*(sps0.pic_height_in_map_units_minus_1+1);
 		// int PicHeightInMbs = FrameHeightInMbs / (1+(field_pic_flag ? 1:0));

 		int PicSizeInMbs = (sps0.pic_width_in_mbs_minus_1+1) * (PicHeightInMbs);
		// (7-34)
		int MapUnitsInSliceGroup0 = Math.min(((pps0.slice_group_change_rate_minus1 + 1) * slice_group_change_cycle), 
			(PicSizeInMapUnits));
		// System.out.println(MapUnitsInSliceGroup0);
		int sizeOfUpperLeftGroup;
		if(pps0.num_slice_groups_minus1==1&&(pps0.slice_group_map_type==4||pps0.slice_group_map_type==5)){

			sizeOfUpperLeftGroup=(pps0.slice_group_change_direction_flag ? (PicSizeInMapUnits - MapUnitsInSliceGroup0)
				: MapUnitsInSliceGroup0);
		}
		int [] mapUnitToSliceGroupMap=new int[PicSizeInMapUnits];
		if(pps0.num_slice_groups_minus1==0){
			// System.out.println("zero  ");
			for(int i=0;i<pps0.pic_size_in_map_units_minus1+1;i++){
				mapUnitToSliceGroupMap[i]=0;
			}			
		}

		else if(pps0.num_slice_groups_minus1!=0){
			if(pps0.slice_group_map_type==0){
				// 8.2.2.1
			}
			else if(pps0.slice_group_map_type==1){
				// 8.2.2.2
			}else if(pps0.slice_group_map_type==2){
				// 8.2.2.3
			}else if(pps0.slice_group_map_type==3){
				// 8.2.2.4
			}else if(pps0.slice_group_map_type==4){
				// 8.2.2.5
			}
			else if(pps0.slice_group_map_type==5){
				// 8.2.2.6
			}
			else if(pps0.slice_group_map_type==6){
				// 8.2.2.7
			}
		}


									/* 8.2.2.8 */
 		int[] MbToSliceGroupMap=new int[PicSizeInMbs];
 		for(int i=0;i<PicSizeInMbs;i++){
 			if(sps0.frame_mbs_only_flag==true||field_pic_flag==true){
 				// System.out.println(PicSizeInMbs+" "+PicSizeInMapUnits);
 				MbToSliceGroupMap[i]=mapUnitToSliceGroupMap[i];
 			} else if(MbaffFrameFlag){
 				MbToSliceGroupMap[i]=mapUnitToSliceGroupMap[(int)i/2];

 			}else if(sps0.frame_mbs_only_flag==false&&sps0.mb_adaptive_frame_field_flag==false&&field_pic_flag==false){
 				MbToSliceGroupMap[i]=mapUnitToSliceGroupMap[(int)(i/(2*sps0.pic_width_in_mbs_minus_1+1))
 				*sps0.pic_width_in_mbs_minus_1+1+(i%sps0.pic_width_in_mbs_minus_1+1)];
 			}

 		}
		// i = n + 1 
		// while( i < PicSizeInMbs && MbToSliceGroupMap[ i ] != MbToSliceGroupMap[ n ] )
		 // i++; 
		// nextMbAddress = i
		int i = n + 1;
		// int nextMbAddress = i;

		while(i<PicSizeInMbs &&( MbToSliceGroupMap[i]!=MbToSliceGroupMap[n])) {
			i++;
			// System.out.println("here   mb address");
			// nextMbAddress = i;
		}
		// System.out.println("nextMbAddress "+i);
		return i;
	}

	public void Intra_16x16_prediction_process_for_luma_samples(){
		int x,y;
		int[] p_minus_x=new int[17];
		boolean availableflagp_minus_x=false;
		boolean availableflagp_minus_y=false;
		int[] p_minus_y=new int[16];
		String Intra16x16PredMode=p.Mb_Type(mb_type_table,mbRow,4);
		
		for(y=-1;y<16;y++){
			x=-1;
			Derivation_process_for_neighbouring_locations(x,y);
			if(availableFlagA){
				availableflagp_minus_x=true;
			}else{
				availableflagp_minus_x=false;
				break;
			}
			if(pps0.constrained_intra_pred_flag){
				availableflagp_minus_x=false;
				break;
			}else{
				availableflagp_minus_x=true;
			}
			int xm,ym;
			int[] xmym=inverse_macroblock_scanning_process(mbAddrA);
			xm=xmym[0];
			ym=xmym[1];
			if(MbaffFrameFlag){
				p_minus_x[y+1]=SL[xm+xW][ym+2*yW];
			}else{
				p_minus_x[y+1]=SL[xm+xW][ym+yW];
			}
		}
		for(x=0;x<16;x++){
			y=-1;
			Derivation_process_for_neighbouring_locations(x,y);
			if(availableFlagB){
				availableflagp_minus_y=true;
			}else{
				availableflagp_minus_y=false;
				break;
			}
			if(pps0.constrained_intra_pred_flag){
				availableflagp_minus_y=false;
				break;
			}else{
				availableflagp_minus_y=true;
			}
			int xm,ym;
			int[] xmym=inverse_macroblock_scanning_process(mbAddrB);
			xm=xmym[0];
			ym=xmym[1];
			if(MbaffFrameFlag){
				p_minus_y[x]=SL[xm+xW][ym+2*yW];
			}else{
				p_minus_y[x]=SL[xm+xW][ym+yW];
			}
		}


		if(Intra16x16PredMode.equals("2")){
			// System.out.println("mode is 2");
			// 8.3.3.3
			if(availableflagp_minus_x==true && availableflagp_minus_y==true){
				// System.out.println("true true");
				for(x=0;x<16;x++){
					for(y=0;y<16;y++){
						int sum=0;
						for(int y_=0;y_<16;y_++){
							sum=sum+p_minus_x[y_+1]+p_minus_y[y_];
							// System.out.print(p_minus_x[y+1]+" p[][]");
						}
						predL[x][y]=(sum+16)>>4;
					}
				}
			}else if (availableflagp_minus_x==true && availableflagp_minus_y==false){
				// System.out.println("true false");
				for(x=0;x<16;x++){
					for(y=0;y<16;y++){
						int sum=0;
						for(int y_=0;y_<16;y_++){
							sum=sum+p_minus_x[y_+1];
							// System.out.print(p_minus_x[y+1]+" p[][]");
						}
						predL[x][y]=(sum+8)>>4;
					}
				}
			}else if (availableflagp_minus_x==false && availableflagp_minus_y==true){
				for(x=0;x<16;x++){
					for(y=0;y<16;y++){
						int sum=0;
						for(int y_=0;y_<16;y_++){
							sum=sum+p_minus_y[y_];
							// System.out.print(p_minus_x[y+1]+" p[][]");
						}
						predL[x][y]=(sum+8)>>4;
					}
				}
			}else{
				// System.out.println("false false");
				// predL[ x, y ] = ( 1 << ( BitDepthY − 1 ) ), with x, y = 0..15
				for(x=0;x<16;x++){
					for(y=0;y<16;y++){
						predL[x][y]=(1<<(BitDepthY-1));
					}
				}
			}

		}
		if(Intra16x16PredMode.equals("0")){
			// System.out.println("mode is 0");

			// 8.3.3.1
			if(availableflagp_minus_y){
				for(x=0;x<16;x++){
					for(y=0;y<16;y++){
						predL[x][y]=p_minus_y[x];
					}
				}
			}
		}
		if(Intra16x16PredMode.equals("1")){
			// System.out.println("mode is 1");

			// 8.3.3.2
			if(availableflagp_minus_x){
				for(x=0;x<16;x++){
					for(y=0;y<16;y++){
						predL[x][y]=p_minus_x[y+1];
					}
				}
			}
		}
		if(Intra16x16PredMode.equals("3")){
			System.out.println("mode is 3");


		}
	}
	public void transform_decoding_process_for_chroma_samples(){
		int MbWidthC;// = 16 / SubWidthC (6-1)
		int MbHeightC;// = 16 / SubHeightC

		if(sps0.chroma_format_idc==0||sps0.separate_colour_plane_flag){
			MbWidthC=0;
			MbHeightC=0;
		}else{
			MbWidthC=16/getSubWidthC();
			MbHeightC=16/getSubHeightC();
		}
		if(ChromaArrayType==3){
			// clause8.5.5 is invoked
			System.out.println("8.5.5 is to be invoked");
		}else{

		// 	{{1,1,1,1},
		// {1,1,-1,-1},
		// {1,-1,-1,1},
		// {1,-1,1,-1}};

			// Let the variable numChroma4x4Blks be set equal to (MbWidthC / 4) * (MbHeightC / 4).
			int numChroma4x4Blks=(MbWidthC/4)*(MbHeightC/4);
			int [][] rMb;
			if(ChromaArrayType==1){
				// c=new int[2][2];
				// int [][] c={{ChromaDCLevel[iCbCr][0], ChromaDCLevel[iCbCr][1]},{ChromaDCLevel[iCbCr][2],ChromaDCLevel[iCbCr][3]}};
			}else if (ChromaArrayType==2){
				// c=new int[2][4];
				// int [][] c={{ChromaDCLevel[iCbCr][0],ChromaDCLevel[iCbCr][2]},
				// {ChromaDCLevel[iCbCr][1],ChromaDCLevel[iCbCr][5]},
				// {ChromaDCLevel[iCbCr][3],ChromaDCLevel[iCbCr][6]},
				// {ChromaDCLevel[iCbCr][4],ChromaDCLevel[iCbCr][7]}
			// };

			}
			for(int iCbCr=0;iCbCr<2;iCbCr++){
			// scalling to be implemented skipping for now
				rMb=new int[MbWidthC][MbHeightC];
				int [] chromaList=new int[16];
				for(chroma4x4BlkIdx=0;chroma4x4BlkIdx<numChroma4x4Blks;chroma4x4BlkIdx++){
					chromaList[0]=0;
					int count=0;
					for(int k=1;k<16;k++){
						chromaList[k]=ChromaACLevel[iCbCr][chroma4x4BlkIdx][k-1];
						if(chromaList[k]!=0){
							count++;
						}
					}
					mbData.cbcr4x4blk[iCbCr][chroma4x4BlkIdx]=count;
				}
			}
		}
	}
	public int LevelScale4x4(int m,int i,int j){
		// int[] scalingList ={6,13,13,20,20,20,28,28,28,28,32,32,32,37,37,42};
		int[] scalingList ={16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16};


		int [][] v=
		{{10, 16, 13},
		{11, 18, 14},
		{13,20,16},
		{14,23,18},
		{16,25,20},
		{18,29,23}};
		if(mb_type_table.equals("table7.13.txt")){
			mbIsInterFlag=true;
		}else{
			mbIsInterFlag=false;
		}
		int iYCbCr;
		if(sps0.separate_colour_plane_flag){
			iYCbCr=colour_plane_id;
		}else {
			iYCbCr=0;
		}
		// 16*
		// normadjus
		int[][] weightScale4x4=Inverse_zigzag_process(scalingList);

		if(i%2==0&&j%2==0){
			// System.out.println(weightScale4x4[i][j]*v[m][0]+"LevelScale4x4");
			return weightScale4x4[i][j]*v[m][0];
			// return 16*v[m][0];
		}else if(i%2==1&&j%2==1){
			// return 16*v[m][1];

			return weightScale4x4[i][j]*v[m][1];
		}else{
			// return 16*v[m][2];

			return weightScale4x4[i][j]*v[m][2];
		}
	}
	int InverseRasterScan(int a,int b,int c,int d,int e){
		int ret=0;
		if(e==0){
			// System.out.println("a=="+d+"b "+b);
			ret=(a%(d/b))*b;
		}else if(e==1){
			ret=(a/(d/b))*c;
		}
		return ret;
	}
	// 8.5.12
	int [][] Scaling_and_transformation_process(int [][] c){

		BitDepth=8+sps0.bit_depth_luma_minus8;
		// sMbFlag=false;
		// System.out.println("slice_type "+slice_type);
		if(slice_type%5==4||(slice_type%5==3&&mb_type_table.equals("table7.13.txt"))){
			sMbFlag=true;
		}else{
			sMbFlag=false;
		}
		// System.out.println("sMbFlag "+sMbFlag);
		if(!sMbFlag){
			QpBdOffsetY=6*sps0.bit_depth_luma_minus8;
			// QPY=((QPY+mb_qp_delta+52+2*QpBdOffsetY)%(52+QpBdOffsetY))-QpBdOffsetY;
			qp=QPY+QpBdOffsetY;
		}else{
			// qp=qsy
			int QSY =26+pps0.pic_init_qs_minus26+slice_qs_delta;
			qp=QSY;
		}
		// System.out.println("qp "+qp);
		if(sps0.qpprime_y_zero_transform_bypass_flag&&QPY+QpBdOffsetY==0){
			TransformBypassModeFlag=true;
		}else if(!sps0.qpprime_y_zero_transform_bypass_flag||QPY+QpBdOffsetY!=0){
			TransformBypassModeFlag=false;
		}
		int [][] r=new int [4][4];
			// System.out.println("flag is TransformBypassModeFlag "+ TransformBypassModeFlag);

		if(TransformBypassModeFlag){
			for (int i=0;i<4 ;i++ ) {
				for(int j=0;j<4;j++){
					r[i][j]=c[i][j];
				}
			}
		} else if(!TransformBypassModeFlag){
			// Transformation Inveers
			// clause 8.5.12.1
			int [][] d=new int [4][4];
			for(int i=0;i<4 ;i++){
				// System.out.println();
				for(int j=0;j<4;j++){
					if(i==0&&j==0&&MbPartPredMode(mbRow,0).equals("Intra_16x16")){
						d[i][j]=c[i][j];
						// System.out.println("true Intra_16x16");
							// System.out.print(c[i][j]+" ");
						
					}else{
						if(qp>=24){
							d[i][j]=(c[i][j]*LevelScale4x4(qp%6,i,j))<<((int)(qp/6)-4);
							// System.out.print(c[i][j]+" ");

						}else{
							d[i][j]=(int)(c[i][j]*LevelScale4x4(qp%6,i,j)+Math.pow(2,(3-(int)(qp/6))))>>(int)(4-(int)(qp/6));
							// System.out.print(d[i][j]+" ");

						}
					}
					// r[i][j]=c[i][j];
				}
			}
			// for(int i=0;i<4 ;i++){
			// 	for(int j=0;j<4;j++){
			// 		System.out.print(d[i][j]+" ");
			// 	}
			// 	System.out.println();
			// }
			// clause 8.5.12.2
			// Transformation process for residual 4x4 blocks

			int [][] e=new int[4][4];
			int [][] h= new int[4][4];
			for(int i=0;i<4;i++){
				e[i][0]=d[i][0]+d[i][2];
			}
			for(int i=0;i<4;i++){
				e[i][1]=d[i][0]-d[i][2];
			}
			for(int i=0;i<4;i++){
				e[i][2]=(d[i][1]>>1) - d[i][3];
			}
			for(int i=0;i<4;i++){
				e[i][3]=d[i][1]+(d[i][3]>>1);
			}
			int[][] f=new int[4][4];
			for(int i=0;i<4;i++){
				f[i][0]=e[i][0]+e[i][3];
			}
			for(int i=0;i<4;i++){
				f[i][1]=e[i][1]+e[i][2];
			}
			for(int i=0;i<4;i++){
				f[i][2]=e[i][1]-e[i][2];
			}
			for(int i=0;i<4;i++){
				f[i][3]=e[i][0]-e[i][3];
			}
			int [][] g= new int[4][4];

			for(int i=0;i<4;i++){
				g[0][i]=f[0][i]+f[2][i];
			}
			for(int i=0;i<4;i++){
				g[1][i]=f[0][i]-f[2][i];
			}
			for(int i=0;i<4;i++){
				g[2][i]=(f[1][i]>>1)-f[3][i];
			}
			for(int i=0;i<4;i++){
				g[3][i]=f[1][i]+(f[3][i]>>1);
			}
			for(int i=0;i<4;i++){
				h[0][i]=g[0][i]+g[3][i];
			}
			
			for(int i=0;i<4;i++){
				h[1][i]=g[1][i]+g[2][i];
			}

			for(int i=0;i<4;i++){
				h[2][i]=g[1][i]-g[2][i];
			}

			for(int i=0;i<4;i++){
				h[3][i]=g[0][i]-g[3][i];
			}

			for(int i=0;i<4 ;i++){
				for(int j=0;j<4;j++){
					// System.out.println(h[i][j]+" ");
					r[i][j]=((h[i][j]+32)>>6);
					// System.out.print(" "+r[i][j]);
				}
			}
		}
		return r;
	}
	// clause 8.5.6-zigzag
	public int[][] Inverse_zigzag_process(int [] input){

		int [][]ret=new int [4][4];
		int[] zigzag={0,1,5,6
			,2,4,7,12
			,3,8,11,13
			,9,10,14,15};
		int index=0;
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				ret[i][j]=input[zigzag[index]];
				// System.out.print(ret[i][j]+" ");
				index++;
			}
		}
		return ret;
	}
	public int[][] matrixMul(int[][] a ,int[][] b){
		int [][] c=new int[4][4];
		for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                }
            }
        }
        return c;
	}
	public int[] inverse_macroblock_scanning_process(int mbAddrN){
		int xp=0,yp=0,x0=0,y0=0;
		if(MbaffFrameFlag==false){
					// System.out.println("flag is false");
			xp=InverseRasterScan(mbAddrN,16,16,PicWidthInSamplesL,0);
			yp=InverseRasterScan(mbAddrN,16,16,PicWidthInSamplesL,1);
		}else if (MbaffFrameFlag==true){
			x0=InverseRasterScan(mbAddrN/2,16,32,PicWidthInSamplesL,0);
			y0=InverseRasterScan(mbAddrN/2,16,32,PicWidthInSamplesL,1);
			xp=x0;
			yp=y0+(mbAddrN%2)*16;
		}
		int []ret=new int[2];
		ret[0]=xp;
		ret[1]=yp;
		return ret;
	}
	// 8.5.14
	public void Picture_construction_process_prior_to_deblocking_filter_process(int [][]u){
		int nE,x0=0,y0=0,xp=0,yp=0;
		// SL=new int[16][16];
			// System.out.println("qpy== "+QPY);
		
		if(MbaffFrameFlag==false){
			// System.out.println("flag is false");
			xp=InverseRasterScan(CurrMbAddr,16,16,PicWidthInSamplesL,0);
			yp=InverseRasterScan(CurrMbAddr,16,16,PicWidthInSamplesL,1);
		}else if (MbaffFrameFlag==true){
			x0=InverseRasterScan(CurrMbAddr/2,16,32,PicWidthInSamplesL,0);
			y0=InverseRasterScan(CurrMbAddr/2,16,32,PicWidthInSamplesL,1);
			xp=x0;
			yp=y0+(CurrMbAddr%2)*16;
		}

		nE=16;
		// x0=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,0)+
		// InverseRasterScan(luma4x4BlkIdx%4,4,4,8,0);

		// y0=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,1)+
		// InverseRasterScan(luma4x4BlkIdx%4,4,4,8,1);
		// System.out.println("xp "+xp);
		x0=0;
		y0=0;
		for(int i=0;i<nE;i++){
			// System.out.println();
			for(int j=0;j<nE;j++){
				// System.out.print(u[i][j]+" ");
				if(!MbaffFrameFlag){
					// System.out.print(u[i][j]+" ");
					SL[xp+x0+j][yp+y0+i]=u[i][j];
					// System.out.print(SL[xp+x0+j][yp+y0+i]+" ");
				}else{
					SL[xp+x0+j][yp+(2*(y0+i))]=u[i][j];
					// System.out.print(SL[xp+x0+j][yp+(2*(y0+i))]+" ");

				}

			}
		}
	}
	public int[] get_block_dc_location(int index){

		int[] ret = new int[2];
		if(index==0){
			ret[0]=0;
			ret[1]=0;
		}
		if(index==01){
			ret[0]=0;
			ret[1]=1;
		}
		if(index==2){
			ret[0]=1;
			ret[1]=0;
		}
		if(index==3){
			ret[0]=1;
			ret[1]=1;
		}
		if(index==4){
			ret[0]=0;
			ret[1]=2;
		}
		if(index==5){
			ret[0]=0;
			ret[1]=3;
		}
		if(index==6){
			ret[0]=1;
			ret[1]=2;
		}
		if(index==7){
			ret[0]=1;
			ret[1]=3;
		}
		if(index==8){
			ret[0]=2;
			ret[1]=0;
		}
		if(index==9){
			ret[0]=2;
			ret[1]=1;
		}
		if(index==10){
			ret[0]=3;
			ret[1]=0;
		}
		if(index==11){
			ret[0]=3;
			ret[1]=1;
		}
		if(index==12){
			ret[0]=2;
			ret[1]=2;
		}
		if(index==13){
			ret[0]=2;
			ret[1]=3;
		}
		if(index==14){
			ret[0]=3;
			ret[1]=2;
		}
		if(index==15){
			ret[0]=3;
			ret[1]=3;
		}


		return ret;

	}
	public void transform_decoding_process_for_luma_samples_of_Intra_16x16(){
		Intra_16x16_prediction_process_for_luma_samples();
		// 1
		// for(int x=0;x<16;x++){
		// 	for(int y=0;y<16;y++){
		// 		predL[x][y]=(1<<(BitDepthY-1));
		// 	}
		// }
		int [][] c,r;
		int [][]dcy;
		c=Inverse_zigzag_process(Intra16x16DCLevel);
		dcy=transformation_process_for_DC(c);
		int [][] rMB=new int[16][16];
		int [] lumaList=new int[16];
		int i=0;
		int j=0;
		// 2
		for(int luma4x4BlkIdx=0;luma4x4BlkIdx<16;luma4x4BlkIdx++){
			int [] indexes=get_block_dc_location(luma4x4BlkIdx);
			lumaList[0]=dcy[indexes[0]][indexes[1]];
			int count=0;
			for(int k=1;k<16;k++){
				if(MbPartPredMode(mbRow,0).equals("Intra_16x16")){
					lumaList[k]=Intra16x16ACLevel[luma4x4BlkIdx][k-1];
					if(lumaList[k]!=0){
						count++;
					}
				}else{
				// }else if(MbPartPredMode(mbRow,0).equals("Intra_4x4")){
					lumaList[k]=LumaLevel4x4[luma4x4BlkIdx][k-1];
					if(lumaList[k]!=0){
						count++;
					}
				}
			}
			// mbData.luma4x4blk[luma4x4BlkIdx]=count;
			c=Inverse_zigzag_process(lumaList);
			r=Scaling_and_transformation_process(c);
			int []ret = Inverse_4x4_luma_block_scanning_process(luma4x4BlkIdx);
			int xo,yo;
			xo=ret[0];
			yo=ret[1];
			// System.out.println(luma4x4BlkIdx+" == "+xo+" " +yo);

			for(int x=0;x<4;x++){
				// System.out.println();
				for(int y=0;y<4;y++){
					rMB[y+xo][x+yo]=r[x][y];
					// System.out.print(r[x][y]+" ");
				}
			}
			// System.out.println();
		}
		// return;
		// 3
		if(TransformBypassModeFlag){
			System.out.println("******** implement here ");
		}
		// 4
		int [][]u=new int [16][16];
		for(int x=0;x<16;x++){
			System.out.println();
			for(int y=0;y<16;y++){
				u[x][y]=clip1y(predL[y][x]+rMB[y][x]);
				System.out.print(" "+u[x][y]);
			}
		}
		// 5
		Picture_construction_process_prior_to_deblocking_filter_process(u);
	}
	public void Transform_coefficient_decoding(){
		Intra_16x16_prediction_process_for_luma_samples();
		int nE,x0,y0;
		int xp=0;
		int yp=0;
		int [][] c,r;
		int [][] rMB=new int[16][16];
		// int LumaLevel4x4
		int[] zigzag={0,1,5,6
			,2,4,7,12
			,3,8,1,13
			,9,10,14,15};
		int[] fieldScan={0,2,8,12,
			1,5,9,13,
			3,6,10,14,
			4,7,11,15};
		// 8.5.1
		if(!transform_size_8x8_flag){
			// System.out.println("not ******************************************************");
			for(luma4x4BlkIdx=0;luma4x4BlkIdx<16;luma4x4BlkIdx++){
					//clause 8.5.6
				// input
				// LumaLevel4x4[luma4x4BlkIdx];
				int index=0;
				c = new int[4][4];
				for(int i=0;i<4;i++){
					for (int j=0;j<4 ;j++ ) {
						c[i][j]=LumaLevel4x4[luma4x4BlkIdx][zigzag[index]];
						// System.out.println(c[i][j]);
						index++;
					}
				}
				r=Scaling_and_transformation_process(c);
				if(TransformBypassModeFlag&& MbPartPredMode(mbRow,0).equals("Intra_4x4")){
					System.out.println("implemetation of clause 8.5.15");
				}
				// clause 6.4.3
				int x=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,0)+
				InverseRasterScan(luma4x4BlkIdx%4,4,4,8,0);
				int y=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,1)+
				InverseRasterScan(luma4x4BlkIdx%4,4,4,8,1);
				int[][] u=new int [4][4];
				for(int i=0;i<4 ;i++){
					for(int j=0;j<4;j++){
						// "    "
						// u[i][j]=clip1y(predL[x+j,y+i]+r[i][j]);

						u[i][j]=clip1y(predL[x+j][i+y]+r[i][j]);
						// System.out.print(" "+u[i][j]);
					}
					// System.out.println();
				}

				// clause 6.4.1
				
				if(MbaffFrameFlag==false){
					// System.out.println("flag is false");
					xp=InverseRasterScan(CurrMbAddr,16,16,PicWidthInSamplesL,0);
					yp=InverseRasterScan(CurrMbAddr,16,16,PicWidthInSamplesL,1);
				}else if (MbaffFrameFlag==true){
					x0=InverseRasterScan(CurrMbAddr/2,16,32,PicWidthInSamplesL,0);
					y0=InverseRasterScan(CurrMbAddr/2,16,32,PicWidthInSamplesL,1);
					xp=x0;
					yp=y0+(CurrMbAddr%2)*16;
				}

				nE=4;
				x0=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,0)+
				InverseRasterScan(luma4x4BlkIdx%4,4,4,8,0);

				y0=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,1)+
				InverseRasterScan(luma4x4BlkIdx%4,4,4,8,1);
				// System.out.println("xp "+xp);
				for(int i=0;i<nE;i++){
					for(int j=0;j<nE;j++){
						if(!MbaffFrameFlag){
							// System.out.print(u[i][j]+" ");
							SL[xp+x0+j][yp+y0+i]=u[i][j];
						}
					}
				}

				// rMB

			}	
		}
		// for(int i=0;i<16;i++){
		// 	for(int j=0;j<16;j++){
		// 		System.out.print(SL[i][j]+" ");
		// 	}	
		// 	System.out.println();
		// }
		// 8.5.2
		int [][]dcy;
		int m=0;
		int n=0;
		// for(int lo=0;lo<16;lo++){
		// 	System.out.print(Intra16x16DCLevel[lo]+" ");
		// }
		// System.out.println(TransformBypassModeFlag+" TransformBypassModeFlag");
		if(MbPartPredMode(mbRow,0).equals("Intra_16x16")){
			int [] lumaList=new int[16];
			c=Inverse_zigzag_process(Intra16x16DCLevel);
			// for(int lo=0;lo<4;lo++){
			// 	for(int loo=0;loo<4;loo++){
			// 		System.out.print(c[lo][loo]);
			// 	}
			// }
			dcy=transformation_process_for_DC(c);
			// for(int lo=0;lo<4;lo++){
			// 	for(int loo=0;loo<4;loo++){
			// 		System.out.print(dcy[lo][loo]+" ");
			// 	}
			// }
			// System.out.println("dcy "+dcy[0][0]);
			for(luma4x4BlkIdx=0;luma4x4BlkIdx<16;luma4x4BlkIdx++){
				// System.out.print(dcy[][]);
				lumaList[0]=dcy[m][n];
				// System.out.print(dcy[m][n]+"  ");
				n++;
				if(n==4){
					n=0;
					m++;
				}
				for(int k=1;k<16;k++){
					lumaList[k]=Intra16x16ACLevel[luma4x4BlkIdx][k-1];

				}
				c=Inverse_zigzag_process(lumaList);
				
				r=Scaling_and_transformation_process(c);
				// for(int i=0;i<4;i++){
				// 	for(int j=0;j<4;j++){
				// 		System.out.print(r[i][j]+" ");
				// 	}	
				// 	System.out.println();
				// }
				int [] xy=Inverse_4x4_luma_block_scanning_process(luma4x4BlkIdx);
				x0=xy[0];
				y0=xy[1];
				for(int i=0;i<4;i++){
					for(int j=0;j<4;j++){
						rMB[x0+j][y0+i]=r[i][j];


					}
				}


			}
			
			int [][] u=new int [16][16];
			for(int i=0;i<16;i++){
				for(int j=0;j<16;j++){
					// uij = Clip1Y( predL[ j, i ] + rMb[ j, i ] )
					u[i][j]=clip1y(predL[j][i]+rMB[j][i]);
					// System.out.print(rMB[i][j]+" ");
				}	
				// System.out.println();
			}
			
			x0=0;
			y0=0;
			nE=16;
			for(int i=0;i<nE;i++){
				for(int j=0;j<nE;j++){
					// System.out.print(" "+u[i][j]);
					if(MbaffFrameFlag){
						SL[xp+x0+j][yp+2*(y0+i)]=u[i][j];
					}else if(!MbaffFrameFlag){
						SL[xp+x0+j][yp+y0+i]=u[i][j];
						// System.out.println();
					}
					// u[i][j]=clip1y(0+rMB[i][j]);
					// System.out.print(rMB[i][j]+" ");
				}	
				// System.out.println();
			}

			// for(int i=0;i<nE;i++){
			// 	for(int j=0;j<nE;j++){
			// 		System.out.print(SL[i][j]+" ");
			// 	}
			// 	System.out.println();
			// }
			



		}
		

		// MacroBlock mbData = new MacroBlock();
		// mbData.SL_=SL;
		// mbData.mb_type_=mb_type;
		// mbData.TotalCoeff_=p.TotalCoeff;
		// for(int i=0;i<16;i++){
		// 			for(int j=0;j<16;j++){
		// 				System.out.print(mbData.SL_[i][j]+" ");
		// 			}
		// 			System.out.println();
		// 	}
		// MacroBlockData.add(CurrMbAddr,mbData);

		// for(int i=0;i<16;i++){
		// 			for(int j=0;j<16;j++){
		// 				System.out.print(MacroBlockData.get(CurrMbAddr).SL_[i][j]+" ");
		// 			}
		// 			System.out.println();
		// 	}
	}
	// 8.5.10
	public int[][] transformation_process_for_DC(int [][]c){
		// qp'y
		int [][] dcy=new int[4][4];
		int [][] a=
		{{1,1,1,1},
		{1,1,-1,-1},
		{1,-1,-1,1},
		{1,-1,1,-1}};
		
		int [][]f;
		for (int i=0;i<4 ;i++ ) {
			for (int j=0;j<4 ;j++ ) {
				// dcy[i][j]=c[i][j];
				// System.out.print(" " +c[i][j]);
			}
			// System.out.println();
		}
		if(TransformBypassModeFlag){
			for (int i=0;i<4 ;i++ ) {
				for (int j=0;j<4 ;j++ ) {
					dcy[i][j]=c[i][j];
				}
			}
		}else if (!TransformBypassModeFlag){
			f=matrixMul(a,c);
			f=matrixMul(f,a);
			// System.out.println("qpy== "+QPY);
			// mb_qp_delta=0;

			
			// QPY=((QPY+mb_qp_delta+52+2*QpBdOffsetY)%(52+QpBdOffsetY))-QpBdOffsetY;
			qp=QPY+QpBdOffsetY;
			// System.out.println("qpy== "+QPY);
			// qp=QPY;

			// System.out.println("qp "+qp);
			if(qp>=36){
				// System.out.println(f[0][0]+"fij");
				for (int i=0;i<4 ;i++ ) {
					for (int j=0;j<4 ;j++ ) {

						dcy[i][j]=(f[i][j]*LevelScale4x4(qp%6,0,0))<<((int)(qp/6) -6);
					}
				}
			}else if(qp<36){
				for (int i=0;i<4 ;i++ ) {
					for (int j=0;j<4 ;j++ ) {
						dcy[i][j]=(f[i][j]*LevelScale4x4(qp%6,0,0)+(1<<(5-(int)(qp/6))))>>(6-(int)(qp/6));
					}
				}
			}
		}
		// for (int i=0;i<4 ;i++ ) {
		// 		for (int j=0;j<4 ;j++ ) {
		// 			System.out.print(dcy[i][j]+" ");
		// 			}
		// 			System.out.println();
		// 	}
		for (int i=0;i<4 ;i++ ) {
			for (int j=0;j<4 ;j++ ) {
			}
		}
		return dcy;
	}
	public int[] Inverse_4x4_luma_block_scanning_process(int index){
		// 6.4.3
		int x=InverseRasterScan(index/4,8,8,16,0)+
		InverseRasterScan(index%4,4,4,8,0);
		int y=InverseRasterScan(index/4,8,8,16,1)+
		InverseRasterScan(index%4,4,4,8,1);
		int[] ret = new int[2];
		ret[0]=x;
		ret[1]=y;
		return ret;
	}
	public int clip1y(int x){
		return clip3(0,(1<<BitDepthY)-1,x);
	}
	public int clip3(int x, int y,int z){
		if(z<x){
			return x;
		}else if(z>y){
			return y;
		}else{
			return z;
		}
	}

	public void dec_ref_pic_marking(){
		if(IdrPicFlag){
			no_output_of_prior_pics_flag=p.getBit();
			long_term_reference_flag=p.getBit();
		}else{
			adaptive_ref_pic_marking_mode_flag=p.getBit();
			if(adaptive_ref_pic_marking_mode_flag){

				memory_management_control_operation=p.uev();
				while(memory_management_control_operation!=0){
					if(memory_management_control_operation==1||memory_management_control_operation==3){
						difference_of_pic_nums_minus1=p.uev();
					}
					if(memory_management_control_operation==2){
						long_term_pic_num=p.uev();
					}
					if(memory_management_control_operation==3
						||memory_management_control_operation==6){
						long_term_frame_idx=p.uev();
					}
					if(memory_management_control_operation==4){
						max_long_term_frame_idx_plus1=p.uev();
					}
					memory_management_control_operation=p.uev();
				}

			}
		}
	
	}	
	public int getSubWidthC(){
		if(sps0.chroma_format_idc==1&&!sps0.separate_colour_plane_flag){
			return 2;
		}else if(sps0.chroma_format_idc==2&&!sps0.separate_colour_plane_flag){
			return 2;
		
		}else if(sps0.chroma_format_idc==3&&!sps0.separate_colour_plane_flag){
			return 1;
		}
		return 0;
	}
		
	public int getSubHeightC(){
		if(sps0.chroma_format_idc==1&&!sps0.separate_colour_plane_flag){
			return 2;
		}else if(sps0.chroma_format_idc==2&&!sps0.separate_colour_plane_flag){
			return 1;
		
		}else if(sps0.chroma_format_idc==3&&!sps0.separate_colour_plane_flag){
			return 1;
		}
		return 0;
	} 
	public String MbPartPredMode(int mbType,int n){
		String ret;
		if(n==0){
			ret =p.Mb_Type(mb_type_table,mbType,3);

		}else{
			ret =p.Mb_Type(mb_type_table,mbType,4);
		}
		return ret;
	}

	public void Derivation_process_for_neighbouring_macroblock_addresses_and_their_availability(){
		mbAddrA=CurrMbAddr-1;
		if((CurrMbAddr%PicWidthInMbs==0)){
			mbAddrA=CurrMbAddr;
			availableFlagA=false;
		}else if(mbAddrA < 0||mbAddrA>CurrMbAddr){
			mbAddrA=CurrMbAddr;
			availableFlagA=false;
		}

		mbAddrB=CurrMbAddr- PicWidthInMbs;
		if(mbAddrB<0||mbAddrB>CurrMbAddr){
			mbAddrB=CurrMbAddr;
			availableFlagB=false;
		}
		mbAddrC=CurrMbAddr - PicWidthInMbs+1;
		if((CurrMbAddr+1) %PicWidthInMbs==0){
			mbAddrC=CurrMbAddr;
			availableFlagC=false;
		}else if(mbAddrC<0||mbAddrC>CurrMbAddr){
			mbAddrC=CurrMbAddr;
			availableFlagC=false;
		}
		mbAddrD=CurrMbAddr-PicWidthInMbs-1;
		if(CurrMbAddr%PicWidthInMbs==0){
			mbAddrD=CurrMbAddr;
			availableFlagD=false;
		}else if(mbAddrD<0||mbAddrD>CurrMbAddr){
			mbAddrD=CurrMbAddr;
			availableFlagD=false;
		}

		// blkA=mbAddrA/luma4x4BlkIdxA;
		// blkB=mbAddrB/luma4x4BlkIdxB;
	}
	// 6.4.11.4
	public void Derivation_process_for_neighbouring_4x4_luma_blocks(){
		maxW= 16;
		maxH =16;
		int x=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,0)+
		InverseRasterScan(luma4x4BlkIdx%4,4,4,8,0);
		int y=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,1)+
		InverseRasterScan(luma4x4BlkIdx%4,4,4,8,1);
		xA=x-1;
		yA=y+0;
		xB=x+0;
		yB=y-1;
		Derivation_process_for_neighbouring_locations(xA,yA);
		luma4x4BlkIdxA=8*(yW/8)+4*(xW/8)+2*((yW%8)/4)+((xW %8)/4);
		Derivation_process_for_neighbouring_locations(xB,yB);
		luma4x4BlkIdxB=8*(yW/8)+4*(xW/8)+2*((yW%8)/4)+((xW %8)/4);

		Derivation_process_for_neighbouring_macroblock_addresses_and_their_availability();	
		if(xA<0&&yA<0){
			mbAddrA=mbAddrD;

		}else if(xA<0 &&(yA>=0&&yA<=maxH-1)){
			mbAddrA=mbAddrA;
		}else if((xA>=0&&xA<=maxW-1) &&yA<0){
			mbAddrA=mbAddrB;
		}else if((xA>=0&&xA<=maxW-1) && (yA>=0&&yA<=maxH-1)){
			mbAddrA=CurrMbAddr;
		}else if((xA>maxW-1&&yA<0)){
			mbAddrA=mbAddrC;
		}else if(xA>maxW-1&&(yA>=0&&yA<=maxH-1)){
			availableFlagA=false;
		}else if(yA>maxH-1){
			availableFlagA=false;
		}
		if(xB<0&&yB<0){
			mbAddrB=mbAddrD;
		}else if(xB<0 &&(yB>=0&&yB<=maxH-1)){
			mbAddrB=mbAddrA;
		}else if((xB>=0&&xB<=maxW-1) &&yB<0){
			mbAddrB=mbAddrB;
		}else if((xB>=0&&xB<=maxW-1) && (yB>=0&&yB<=maxH-1)){
			mbAddrB=CurrMbAddr;
		}else if((xB>maxW-1&&yB<0)){
			mbAddrB=mbAddrC;
		}else if(xB>maxW-1&&(yB>=0&&yB<=maxH-1)){
			availableFlagB=false;
		}else if(yB>maxH-1){
			availableFlagB=false;
		}

	}
	public void setnC(int mode){
		availableFlagA=true;
		availableFlagB=true;
		availableFlagC=true;
		availableFlagD=true;
		if(mode==0){
			
			int x=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,0)+
			InverseRasterScan(luma4x4BlkIdx%4,4,4,8,0);
			int y=InverseRasterScan(luma4x4BlkIdx/4,8,8,16,1)+
			InverseRasterScan(luma4x4BlkIdx%4,4,4,8,1);
			xA=x-1;
			yA=y+0;
			xB=x+0;
			yB=y-1;
			Derivation_process_for_neighbouring_4x4_luma_blocks();
			Derivation_process_for_neighbouring_macroblock_addresses_and_their_availability();	
			if(xA<0&&yA<0){
				mbAddrA=mbAddrD;

			}else if(xA<0 &&(yA>=0&&yA<=maxH-1)){
				mbAddrA=mbAddrA;
			}else if((xA>=0&&xA<=maxW-1) &&yA<0){
				mbAddrA=mbAddrB;
			}else if((xA>=0&&xA<=maxW-1) && (yA>=0&&yA<=maxH-1)){
				mbAddrA=CurrMbAddr;
			}else if((xA>maxW-1&&yA<0)){
				mbAddrA=mbAddrC;
			}else if(xA>maxW-1&&(yA>=0&&yA<=maxH-1)){
				availableFlagA=false;
			}else if(yA>maxH-1){
				availableFlagA=false;
			}
			if(xB<0&&yB<0){
				mbAddrB=mbAddrD;
			}else if(xB<0 &&(yB>=0&&yB<=maxH-1)){
				mbAddrB=mbAddrA;
			}else if((xB>=0&&xB<=maxW-1) &&yB<0){
				mbAddrB=mbAddrB;
			}else if((xB>=0&&xB<=maxW-1) && (yB>=0&&yB<=maxH-1)){
				mbAddrB=CurrMbAddr;
			}else if((xB>maxW-1&&yB<0)){
				mbAddrB=mbAddrC;
			}else if(xB>maxW-1&&(yB>=0&&yB<=maxH-1)){
				availableFlagB=false;
			}else if(yB>maxH-1){
				availableFlagB=false;
			}
			if(pps0.constrained_intra_pred_flag){
				availableFlagA=false;
				availableFlagB=false;
				availableFlagC=false;
				availableFlagD=false;
			}
			// if(MbPartPredMode(mbRow,0).indexOf("Intra")!=-1){
			// 	availableFlagA=false;
			// 	availableFlagB=false;
			// 	availableFlagC=false;
			// 	availableFlagD=false;
				
			// }
			// if(pps0.constrained_intra_pred_flag){

			// }
			if(availableFlagA){
				if(mbAddrA==CurrMbAddr){
					nA=mbData.luma4x4blk[luma4x4BlkIdxA];
				}else{
					if(MacroBlockData[mbAddrA].mb_type_.equals("P_Skip")||MacroBlockData[mbAddrA].mb_type_.equals("B_Skip")){
						nA=0;
						// availableFlagA=false;
					}else if(!MacroBlockData[mbAddrA].mb_type_.equals("I_PCM")&&MacroBlockData[mbAddrA].luma4x4blk[luma4x4BlkIdxA]==0){
						nA=0;
						System.out.println("nA "+nA+" luma4x4BlkIdxA "+luma4x4BlkIdxA);
					}else if(MacroBlockData[mbAddrA].mb_type_.equals("I_PCM")){
						nA=16;
					}
					else
					{
						nA=MacroBlockData[mbAddrA].luma4x4blk[luma4x4BlkIdxA];
					}
						System.out.println("nA "+nA+" luma4x4BlkIdxA "+luma4x4BlkIdxA);
				}
			}
			if(availableFlagB){
				if(mbAddrB==CurrMbAddr){
					nB=mbData.luma4x4blk[luma4x4BlkIdxB];
				}else{
				// System.out.println("mbAddrB "+mbAddrB+" "+MacroBlockData[mbAddrB].mb_type_);
				// System.out.println("availableFlagB "+MacroBlockData[mbAddrB].mb_type_+ " tyoe");
					if(MacroBlockData[mbAddrB].mb_type_.equals("P_Skip")||MacroBlockData[mbAddrB].mb_type_.equals("B_Skip")){
						// availableFlagB=false;
						nB=0;
					}else if(!MacroBlockData[mbAddrB].mb_type_.equals("I_PCM")&&MacroBlockData[mbAddrB].luma4x4blk[luma4x4BlkIdxB]==0){
						nB=0;
						// System.out.println("nB "+nB+" luma4x4BlkIdxB "+luma4x4BlkIdxB);

						
					}else if(MacroBlockData[mbAddrB].mb_type_.equals("I_PCM")){
						nB=16;
					}else{
						nB=MacroBlockData[mbAddrB].luma4x4blk[luma4x4BlkIdxB];

						}

					// System.out.println("nB "+nB+" luma4x4BlkIdxB "+luma4x4BlkIdxB);
					// nB=0;
				}
				// System.out.println("nB "+nB);
			}
			// System.out.println(availableFlagA+" flag "+availableFlagB);
			if(availableFlagA&&availableFlagB){
				// System.out.println("both true "+nA+" "+nB);
				nC=(int)((1+nA+nB)>>1);
			}else if(availableFlagA&&!availableFlagB){
				// System.out.println("nA true");
				nC=nA;
			}else if(!availableFlagA&&availableFlagB){
				nC=nB;
				// System.out.println("nB true");
			}else{
				nC=0;
			}

			p.nC=nC;
			System.out.println("nC "+p.nC);
		}else if(mode==3){
			Derivation_process_for_neighbouring_4x4_chroma_blocks();
			Derivation_process_for_neighbouring_macroblock_addresses_and_their_availability();
			if(xA<0&&yA<0){
				mbAddrA=mbAddrD;

			}else if(xA<0 &&(yA>=0&&yA<=maxH-1)){
				mbAddrA=mbAddrA;
			}else if((xA>=0&&xA<=maxW-1) &&yA<0){
				mbAddrA=mbAddrB;
			}else if((xA>=0&&xA<=maxW-1) && (yA>=0&&yA<=maxH-1)){
				mbAddrA=CurrMbAddr;
			}else if((xA>maxW-1&&yA<0)){
				mbAddrA=mbAddrC;
			}else if(xA>maxW-1&&(yA>=0&&yA<=maxH-1)){
				availableFlagA=false;
			}else if(yA>maxH-1){
				availableFlagA=false;
			}
			if(xB<0&&yB<0){
				mbAddrB=mbAddrD;
			}else if(xB<0 &&(yB>=0&&yB<=maxH-1)){
				mbAddrB=mbAddrA;
			}else if((xB>=0&&xB<=maxW-1) &&yB<0){
				mbAddrB=mbAddrB;
			}else if((xB>=0&&xB<=maxW-1) && (yB>=0&&yB<=maxH-1)){
				mbAddrB=CurrMbAddr;
			}else if((xB>maxW-1&&yB<0)){
				mbAddrB=mbAddrC;
			}else if(xB>maxW-1&&(yB>=0&&yB<=maxH-1)){
				availableFlagB=false;
			}else if(yB>maxH-1){
				availableFlagB=false;
			}
			System.out.println("maxh "+maxH+"maxW "+maxW);
			// System.out.println("luma4x4BlkIdxA "+ luma4x4BlkIdxA+" mbAddrA "+mbAddrA+""+);
			
			if(availableFlagA){
				if(mbAddrA==CurrMbAddr){
					nA=mbData.cbcr4x4blk[iCbCr][chroma4x4BlkIdxA];
				}else{
					if(MacroBlockData[mbAddrA].mb_type_.equals("P_Skip")||MacroBlockData[mbAddrA].mb_type_.equals("B_Skip")){
						nA=0;
					}else if(MacroBlockData[mbAddrA].mb_type_.equals("I_PCM")){
						nA=16;
					}else{
						nA=MacroBlockData[mbAddrA].cbcr4x4blk[iCbCr][chroma4x4BlkIdxA];
						// nA=MacroBlockData.get(mbAddrA).TotalCoeff_-1;
						// nA=0;
					}
				}
			}
			if(availableFlagB){
				if(mbAddrB==CurrMbAddr){
					nB=mbData.cbcr4x4blk[iCbCr][chroma4x4BlkIdxB];
				}else{
					if(MacroBlockData[mbAddrB].mb_type_.equals("P_Skip")||MacroBlockData[mbAddrB].mb_type_.equals("B_Skip")){
						nB=0;
					}else if(MacroBlockData[mbAddrB].mb_type_.equals("I_PCM")){
						nB=16;
					}else{
						nB=MacroBlockData[mbAddrB].cbcr4x4blk[iCbCr][chroma4x4BlkIdxB];
						// nB=0;
					}
				}
			}
			if(availableFlagA&&availableFlagB){
				nC=(1+nA+nB)>>1;
			}else if(availableFlagA&&!availableFlagB){
				nC=nA;
			}else if(!availableFlagA&&availableFlagB){
				nC=nB;
			}else{
				nC=0;
			}
			// nC=0;
			System.out.println("mode 3  ==>  "+nC);

		}
		p.nC=nC;
		// System.out.println("nC     ====>   "+nC);


	}

	public void Derivation_process_for_neighbouring_4x4_chroma_blocks(){
		int x = InverseRasterScan(chroma4x4BlkIdx,4,4,8,0);
		int y = InverseRasterScan(chroma4x4BlkIdx,4,4,8,1);
		xA=x-1;
		yA=y+0;
		xB=x+0;
		yB=y-1;
		maxW = MbWidthC;
		maxH = MbHeightC;
		Derivation_process_for_neighbouring_locations(xA,yA);
		chroma4x4BlkIdxA=2*(yW/4)+(xW/4);
		Derivation_process_for_neighbouring_locations(xB,yB);
		chroma4x4BlkIdxB=2*(yW/4)+(xW/4);
	}
	// 6.4.12
	public void Derivation_process_for_neighbouring_locations(int xA ,int yA){
		if(!MbaffFrameFlag){
			Derivation_process_for_neighbouring_macroblock_addresses_and_their_availability();
			if(xA<0&&yA<0){
				mbAddrN=mbAddrD;
				// N="D";
			}else if(xA<0 &&(yA>=0&&yA<=maxH-1)){
				mbAddrN=mbAddrA;
				// N="A";
			}else if((xA>=0&&xA<=maxW-1) &&yA<0){
				mbAddrN=mbAddrB;
				// N="B";
			}else if((xA>=0&&xA<=maxW-1) && (yA>=0&&yA<=maxH-1)){
				mbAddrN=CurrMbAddr;
				// N="CurrMbAddr";
			}else if((xA>maxW-1&&yA<0)){
				mbAddrN=mbAddrC;
				// N="C";
			}else if(xA>maxW-1&&(yA>=0&&yA<=maxH-1)){
				availableFlagN=false;
			}else if(yA>maxH-1){
				availableFlagN=false;
			}
			xW =(xA+maxW)%maxW;
			yW = (yA+maxH)%maxH;
		}
	}
	public void ChromaDCLevelnC(){
		if(ChromaArrayType==1){
			p.nC=-1;
		}else if(ChromaArrayType==2){
			p.nC=-2;
		}			
	}

	public void residual(int startIdx,int endIdx){
		int[] i16x16DClevel=new int[16];
		int[][] i16x16AClevel=new int[16][16];
		int[][] level4x4=new int[16][16];
		int[][] level8x8=new int[16][64];
		NumC8x8=4/(getSubHeightC()*getSubWidthC());
		ChromaDCLevel=new int[2][NumC8x8*4];
		ChromaACLevel=new int[2][NumC8x8*4+4][15];
		
		int countNonZero;
		if(!pps0.entropy_coding_mode_flag){
		}else{
			// int []residual_block=residual_block_cabac();
		}
		// luma4x4BlkIdx=0;
		residual_luma(i16x16DClevel,i16x16AClevel,level4x4,level8x8,startIdx,endIdx);
		Intra16x16DCLevel=i16x16DClevel;
		Intra16x16ACLevel=i16x16AClevel;
		LumaLevel4x4=level4x4;
		LumaLevel8x8=level8x8;
		if(ChromaArrayType==1||ChromaArrayType==2){
			for( iCbCr=0;iCbCr<2;iCbCr++){
				if(((CodedBlockPatternChroma & 3)!=0)&&startIdx==0){
					int[] cdc=new int[NumC8x8*4];
					ChromaDCLevelnC();
					// System.out.println(ChromaArrayType+" ChromaDCLevel called "+p.nC);
					p.residual_block_cavlc(cdc,0,((4*NumC8x8)-1),4*NumC8x8);
					// System.out.println("cdc");
					ChromaDCLevel[iCbCr]=cdc;
				}else{
					for(int i=0;i<4*NumC8x8;i++){
						ChromaDCLevel[iCbCr][i]=0;
					}
				}
			}
			for( iCbCr=0;iCbCr<2;iCbCr++){
				for(int i8x8=0;i8x8<NumC8x8;i8x8++){
					for(int i4x4=0;i4x4<4;i4x4++){
						if((CodedBlockPatternChroma&2)!=0){
							chroma4x4BlkIdx=i8x8*4+i4x4;
							setnC(3);
							System.out.println("ChromaACLevel ===> "+NumC8x8);
							p.residual_block_cavlc(ChromaACLevel[iCbCr][i8x8*4+i4x4],Math.max(0,startIdx-1),endIdx-1,15);
							countNonZero=0;
							for(int i=0;i<15;i++){
								if(ChromaACLevel[iCbCr][i8x8*4+i4x4][i]!=0){
									countNonZero++;
								}
							}
							mbData.cbcr4x4blk[iCbCr][i8x8*4+i4x4]=countNonZero;
						}else{
							for(int i=0;i<15;i++){
								chroma4x4BlkIdx=i8x8*4+i4x4;

								ChromaACLevel[iCbCr][i8x8*4+i4x4][i]=0;
							}
						}
					}	
				}
			}
		}else if(ChromaArrayType==3){
			System.out.println("\n chroma component present \n"+ChromaArrayType);
			cb4x4BlkIdx=0;
			residual_luma(i16x16DClevel,i16x16AClevel,level4x4,level8x8,startIdx,endIdx);
			CbIntra16x16DCLevel=i16x16DClevel;
			CbIntra16x16ACLevel=i16x16AClevel;
			CbLevel4x4=level4x4;
			CbLevel8x8=level8x8;
			cr4x4BlkIdx=0;
			residual_luma(i16x16DClevel,i16x16AClevel,level4x4,level8x8,startIdx,endIdx);
			CrIntra16x16DCLevel=i16x16DClevel;
			CrIntra16x16ACLevel=i16x16AClevel;
			CrLevel4x4=level4x4;
			CrLevel8x8=level8x8;	
		}
	}

	public void residual_luma(int[] i16x16DClevel,int[][] i16x16AClevel
		,int[][] level4x4,int[][] level8x8,int startIdx,int endIdx){
		System.out.println("residual_luma called ");
		if(startIdx==0&&MbPartPredMode(mbRow,0).equals("Intra_16x16")){
			luma4x4BlkIdx=0;
			setnC(0);
			System.out.println("i16x16DClevel");
			p.residual_block_cavlc(i16x16DClevel,0,15,16);
		}
		for(int i8x8=0;i8x8<4;i8x8++){
			if(!transform_size_8x8_flag||!pps0.entropy_coding_mode_flag){
				for(int i4x4=0;i4x4<4;i4x4++){
					if((CodedBlockPatternLuma & (1<<i8x8))>0){
						if(MbPartPredMode(mbRow,0).equals("Intra_16x16")){
							int [] ac=new int[15];
							luma4x4BlkIdx=i8x8*4+i4x4;
							setnC(0);
							p.residual_block_cavlc(ac,Math.max(0,startIdx-1),endIdx-1,15);
							i16x16AClevel[i8x8*4+i4x4]=ac;
							int countNonZero=0;
							for(int j=0;j<15;j++){
								if(i16x16AClevel[i8x8*4+i4x4][j]!=0){
									countNonZero++;
								}
							}
							mbData.luma4x4blk[luma4x4BlkIdx]=countNonZero;
							// System.out.println("i16x16AClevel");
						}else{
							int c=i8x8*4+i4x4;
							System.out.println();
							System.out.println("level4x4 "+c);
							luma4x4BlkIdx=i8x8*4+i4x4;
							setnC(0);
							p.residual_block_cavlc(level4x4[i8x8*4+i4x4],startIdx,endIdx,16);
							int countNonZero=0;
							for(int j=0;j<16;j++){
								if(level4x4[i8x8*4+i4x4][j]!=0){
									countNonZero++;
								}
							}
							mbData.luma4x4blk[luma4x4BlkIdx]=countNonZero;
						}

					}else if(MbPartPredMode(mbRow,0).equals("Intra_16x16")){
						for(int i=0;i<15;i++){

							i16x16AClevel[i8x8*4+i4x4][i]=0;
						}
					}else{
						for (int i=0;i<16 ;i++ ) {
							level4x4[i8x8*4+i4x4][i]=0;	
							
						}
					}
					if(!pps0.entropy_coding_mode_flag&&transform_size_8x8_flag){
						for(int i=0;i<16;i++){

							level8x8[i8x8][4*i+i4x4]=level4x4[i8x8*4+i4x4][i];
						}
					}
				}
			}

			else if((CodedBlockPatternLuma&(1<<i8x8))!=0){
				int[] l8x8=new int [64];
				setnC(0);
				System.out.println("level8x8");
				p.residual_block_cavlc(l8x8,4*startIdx,4*endIdx+3,64);
				level8x8[i8x8]=l8x8;
					// residual_block( level8x8[ i8x8 ], 4 * startIdx, 4 * endIdx + 3, 64 )
				// System.out.println("level8x8");
			}else{
				for(int i=0;i<64;i++){
					// System.out.print("line 580 implemetation required");

					level8x8[i8x8][i]=0;
				}
			}


		}
	}
	public int NumSubMbPart(String type){
		System.out.println("implemetation required line 645 NumSubMbPart");
		if(type.equals("P_L0_8x8")){
			return 1;
		}else if(type.equals("P_L0_8x4")){
			return 2;
		}else if(type.equals("P_L0_4x8")){
			return 2;
		}else if(type.equals("P_L0_4x4")){
			return 4;
		}
		return 0;
	}
	public String subMbTypeName(int idx){
		if(idx==0){
			return "P_L0_8x8";
		}else if(idx==1){
			return "P_L0_8x4";

		}else if(idx==2){
			return "P_L0_4x8";

		}else if(idx==3){
			return "P_L0_4x4";
		}
		// System.out.println("implemetation sub_mb_type");
		return"na";
	}
		
	// not for i slice
	public int NumMbPart(int row){
		if(mb_type.equals("P_Skip")){
			return 1;
		}
		String ret= p.Mb_Type(mb_type_table,row,2);
		// System.out.println("implemetation NumMbPart required at line 684");
		return Integer.parseInt(ret);
		// return ret;
	}
	public void sub_mb_pred(int mb_type){
		System.out.println("************************88sub_mb_pred implemetation required***********************************88");
		int idx;
		for(mbPartIdx=0;mbPartIdx<4;mbPartIdx++){
			idx=p.uev();
			sub_mb_type[mbPartIdx]=subMbTypeName(idx);
		}

	}// not for i slice

	public void ToImage(){
		int row=SL.length;
		int col=SL[0].length;
		int cr=0;
		int cb=0;
		// System.out.println(row+" "+col);
		int[] intArray = new int[3*row*col];
		int w=row;
	    int h=col;
	    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int pixel = 0;
		for(int x=0;x<row;x++){
			for(int y=0;y<col;y++){
				int rByte = (int)(SL[x][y]);
		        int gByte = (int)(SL[x][y]);
		        int bByte = (int)(SL[x][y]);
		        
		        // int rByte = (int)(SL[x][y]+1.402*(cr-128));
		        // int gByte = (int)(SL[x][y]-0.3414*(cb-128)-0.71414*(cr-128));
		        // int bByte = (int)(SL[x][y]+1.772*(cb-128));
		        int rgb = (rByte *65536) + (gByte * 256) + bByte;
		        image.setRGB(x,y,rgb);
	    	}
		}
	    try {
	        ImageIO.write(image, "bmp", new File("image.png"));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}