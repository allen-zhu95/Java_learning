package com.attack;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.util.List;

import com.cipher.Cipher;
import com.cipher.IntegeralDistinguisher;
import com.cipher.KeySchedule;

/**
 * @author Allen-zhu
 * 对密码进行分析
 */
public interface Analysis {

	/**
	 * 利用积分区分器对密码进行分析，看能推到几轮
	 * @param id			积分区分器
	 * @param output		输出流
	 */
	public void analysis(IntegeralDistinguisher id, BufferedWriter output);
	
	
	/**
	 * 如果有密钥编排，则尝试寻找上下两轮密钥的关系
	 * @param ks			密钥编排
	 * @param locations		区分器位置
	 * @param round			轮数
	 * @return
	 */
	public String keyAnalysis(KeySchedule ks, List<Integer> locations, int round);
}

