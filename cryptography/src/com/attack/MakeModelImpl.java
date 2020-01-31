package com.attack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.cipher.Cipher;
import com.cipher.IntegeralDistinguisher;
import com.cipher.KeySchedule;


/**
 * @author Allen-zhu
 * 对密码进行建模
 */
public class MakeModelImpl implements MakeModel {
	
	@Override
	public Cipher makeCipherModel(String filePath, String fileName) {
		
	/*
         * r：环移，s：s盒，p：p置换
         * */
    	
    	Cipher cipher = null;
    	KeySchedule ks = null;
    	
        String structure = "";		
        int blockSize = 0;
    	String order = "";
    	List<Integer> permution = new ArrayList<>();
    	List<Integer> rotate = new ArrayList<>();

		
    	//对密码进行建模，不涉及密钥编排
        try(BufferedReader br = new BufferedReader(new InputStreamReader
        		(new FileInputStream(new File(filePath+fileName)),"UTF-8"));) {
                        
            String read = br.readLine();
            structure = read;
            read = br.readLine();
            blockSize = Integer.parseInt(read);
            //Feistel结构对一侧进行分析
            if(structure.equals("Feistel"))
            	blockSize /= 2;
            
        	while(!(read = br.readLine()).equals("end")) {
        		
        		order += read.charAt(0);
        		
        		switch(read.charAt(0)) {
        		case 'r':
        			read = br.readLine();
    				String[] rotateBits = read.split(",");
    				for(int i=0;i<rotateBits.length;i++)
    					rotate.add(Integer.parseInt(rotateBits[i]));
        			break;
        		case 's':
        			break;
        		case 'p':
        			read = br.readLine();
        			String[] nums = read.split(",");
        			for(int i=0;i<nums.length;i++)
        				permution.add(Integer.parseInt(nums[i]));
        		}
        	}
        	
        	cipher = new Cipher(structure, blockSize, order, permution, rotate);	//构造密码算法
        	br.readLine();
        	read = br.readLine();
        	
        	//如果有密钥编排，则对其进行建模
        	if(read.equals("KeySchedule")) {
        		
        		int keySize = 0;
        		String order2 = "";
        		List<Integer> sbox = new ArrayList<>();
        		int rotate2 = 0;
        		
        		read = br.readLine();
        		keySize = Integer.parseInt(read);
        		
        		while(!(read = br.readLine()).equals("end")) {
            		order2 += read.charAt(0);
            		switch(read.charAt(0)) {
            		case 'r':
            			read = br.readLine();
            			rotate2 = Integer.parseInt(read);
            			break;
            		case 's':
            			read = br.readLine();
            			String[] nums = read.split(",");
            			for(int i=0;i<nums.length;i++)
            				sbox.add(Integer.parseInt(nums[i]));
            		}
            	}
        		
        		ks = new KeySchedule(keySize, order2, sbox, rotate2);
        		cipher.setKeySchedule(ks);
        	}
        	
        } catch (Exception e) {
            System.err.println("read errors :" + e);
        }
        
        return cipher;
	}

	/**
	 *构建积分区分器
	 *
	 */
	@Override
	public List<IntegeralDistinguisher> makeIntegeralDistinguisher(String filePath, String fileName) {
		
		List<IntegeralDistinguisher> ids = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader
        		(new FileInputStream(new File(filePath+fileName)),"UTF-8"));) {
                        
            String read = "";
            
        	while(!(read = br.readLine()).equals("end")) {
        		
        		int round = Integer.parseInt(read);		    	 	//轮数
        		List<Integer> balancedBits = new ArrayList<>();			//平衡比特位置
        		
    			read = br.readLine();
    			String[] nums = read.split(",");
    			for(int i=0;i<nums.length;i++)
    				balancedBits.add(Integer.parseInt(nums[i]));
    			
    			IntegeralDistinguisher id = new IntegeralDistinguisher(round, balancedBits);
    			ids.add(id);
        	}
        } catch (Exception e) {
            System.err.println("read errors :" + e);
        }
		
		return ids;
	}
}

