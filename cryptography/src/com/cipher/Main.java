package com.cipher;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.attack.AnalysisImpl;
import com.attack.MakeModelImpl;

/**
 * @author Allen-zhu
 *
 */
public class Main {

	public static void main(String[] args) {
		
		String path = "C:/Users/Shinelon/Desktop/";							//密码结构文本路径
		String cipherName = "gift.txt";									//密码结构的文本名
		
		MakeModelImpl makeModel = new MakeModelImpl();		
		Cipher cipher = makeModel.makeCipherModel(path, cipherName);					//建模
		
		AnalysisImpl analysis = new AnalysisImpl(cipher);
		
		//单个积分区分器
//		int round = 10;											//轮数
//		int[] balancedBits = {0,1,4,6,8,9,12,13,14,15,16,18,24,25};					//平衡比特位置
//		
//		List<Integer> bits = new ArrayList<>();
//		for(int bit:balancedBits)
//			bits.add(bit);
//		IntegeralDistinguisher singleId = new IntegeralDistinguisher(round, bits);
//		analysis.analysis(singleId, null);
		
		
		//多个积分区分器，使用文本输入
		String integeralDistinguisherName = "distinguisher.txt";					//积分区分器的文本名
		String outFileName = "result.txt";								//输出文件名
		List<IntegeralDistinguisher> ids = makeModel.makeIntegeralDistinguisher(path, integeralDistinguisherName);
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(path + outFileName),"UTF-8"));) {
	
			for(IntegeralDistinguisher id:ids)
				analysis.analysis(id, bw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("finished");
	}
}

