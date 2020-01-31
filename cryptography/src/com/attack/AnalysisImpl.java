package com.attack;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.cipher.Cipher;
import com.cipher.IntegeralDistinguisher;
import com.cipher.KeySchedule;

/**
 * @author Allen-zhu
 * 对密码进行分析
 */
public class AnalysisImpl implements Analysis {

	private int blockSize;
	private boolean[] used;
	private String order;
	private List<Integer> rotation;
	private List<Integer> permution;
	private KeySchedule ks = null;
	private String structure = "";
	

	public AnalysisImpl(Cipher cipher) {
		super();
		this.blockSize = cipher.getBlockSize();
		this.used = new boolean[blockSize];
		this.order = cipher.getOrder();
		this.rotation = cipher.getRotate();
		this.permution = cipher.getPermution();
		this.ks = cipher.getKeySchedule();
		this.structure = cipher.getStructure();
	}



	@Override
	public void analysis(IntegeralDistinguisher id, BufferedWriter bw) {
		
		List<Integer> distinguisher = id.getBalancedBits();		//平衡位
		List<List<Integer>> keyBits = new ArrayList<>();		//需猜测的密钥位
		
		//轮密钥状态标记初始化
		for(int i=0;i<blockSize;i++) {
			used[i] = false;
		}
		//将平衡位置置为true；表示需要猜测的密钥
		for(int bit: distinguisher) {
			used[bit] = true;
		}
		
		int len = 0;							//需猜测的轮密钥位的个数
		while(len < blockSize) {
			
			List<Integer> roundKeys = new ArrayList<>();		//需猜测的轮密钥位
			
			for(int i=0;i<blockSize;i++) {
				if(used[i]) {
					roundKeys.add(i);
				}
			}
			
			keyBits.add(roundKeys);
			for(char c:order.toCharArray()) {
				
				switch(c) {
				case 'r':
					rotate();break;
				case 's':
					sbox();break;
				case 'p':
					permute();break;
				}
			}
			
			len = roundKeys.size();
		}
		
		//将结果存入StringBuilder，输出到控制台和本地文本。
		StringBuilder output = new StringBuilder();
		
		int round = id.getRound();					//轮数
		output.append(round+"轮区分器\n");
		int r = 0;
		for(List<Integer> t:keyBits) {
			output.append("\n第"+(round + r)+"轮所猜测的密钥：");
			
			for(int n:t)
				output.append(n+" ");
			
			//若有密钥编排则分析
			if(ks != null)
				output.append(keyAnalysis(ks, t, round+r));
			output.append("\n");
			
			//如果是Feistel结构，隔一轮猜测密钥。
			if(this.structure.equals("Feistel")) {
				r += 2;
			}
			else {
				r ++;
			}
		}
		output.append("\n\n\n");
		
		//输出到控制台
		System.out.println(output.toString());
		//如果可写入，则输出到本地文件
		if(bw != null) {
			try {
				bw.write(output.toString());
			}catch (Exception e) {
				    System.err.println("write errors :" + e);
			}
		}
	}
	
	//S盒
	public void sbox() {
		for(int i=0;i<blockSize;i+=4) {
			for(int j=0;j<4;j++) {
				if(used[i+j]) {
					for(int k=0;k<4;k++)
						used[i+k] = true;
					break;
				}
			}
		}
	}
	
	//循环移位
	public void rotate() {

		boolean[] temp1 = new boolean[blockSize];
		int m = rotation.get(0);
		for(int i=0;i<blockSize;i++) {
			temp1[i] = used[(i+m+blockSize)%blockSize];
		}
		
		if(rotation.size()>1) {
			int n = rotation.get(1);
			boolean[] temp2 = new boolean[blockSize];
			
			for(int i=0;i<blockSize;i++) {
				temp2[i] = used[(i+n+blockSize)%blockSize];
			}
			for(int i=0;i<blockSize;i++)
				used[i] = (temp1[i] || temp2[i]);
		}
		else {
			for(int i=0;i<blockSize;i++)
				used[i] = temp1[(i+m)%blockSize];
		}
	}
	
	//P置换
	public void permute() {

		boolean[] temp = new boolean[blockSize];
		for(int i=0;i<blockSize;i++)
			temp[i] = used[i];
		for(int i=0;i<blockSize;i++) {
			used[permution.get(i)] = temp[i];
		}
		
	}



	@Override
	public String keyAnalysis(KeySchedule ks, List<Integer> locations, int round) {
		
		int keySize = ks.getKeySize();
		boolean[] used = new boolean[keySize];
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<keySize;i++)
			used[i] = false;
		for(int n:locations)
			used[n] = true;
		
		//利用密钥编排规律，确定当前密钥位置可由后3轮哪些位置导出。
		for(int r=0;r<3;r++) {
			
			sb.append("\n可由第"+(round + r)+"轮以下位置导出：\n");
			for(char c:order.toCharArray()) {
				
				//s盒
				if(c == 's') {
					List<Integer> sbits = ks.getSbox();
					for(int i=0;i<sbits.size();i+=4) {
						for(int j=0;j<4;j++) {
							if(used[sbits.get(i+j)]) {
								for(int k=0;k<4;k++)
									used[sbits.get(i+k)] = true;
							}
						}
					}
				}
				
				//环移
				if(c == 'r'){
					boolean[] temp = new boolean[keySize];
					for(int i=0;i<keySize;i++) {
						temp[i] = used[i];
					}
					int rot = ks.getRotate();
					for(int i=0;i<keySize;i++) {
						used[i] = temp[(i+rot+keySize)%keySize];
					}
				}
			}
			
			int count = 0;						//需确定位的数量
			for(int j=0;j<keySize;j++)
				if(used[j]) {
					if(j<10)
						sb.append(" ");
					sb.append(j+" ");
					count++;
				}
			sb.append("  共"+count+"个");
		}

		return sb.toString();
	}
}

