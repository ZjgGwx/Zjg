package test28;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 1. ͨ���ַ������������Ŀ��Ŀ¼��info.txt��д���������ݣ� 
��ˮ�����ʣ� 
�����ｼԶ�� 
2. ͨ���ַ���������ȡinfo.txt�е��������ݣ�ÿ�ζ�ȡһ�У���ÿһ�еĵ�һ�����ֽ�ȡ��������ӡ�ڿ���̨�ϡ� 
 */
public class test5 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("info.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("info.txt"));
		bw.write("��ˮ�����ʣ� ");
		bw.flush();
		bw.newLine();
		bw.write("�����ｼԶ��");
		bw.flush();
		bw.newLine();
		
		String line=null;
		while((line=br.readLine())!=null){
			String s = line;
			char c = s.charAt(0);
			System.out.println(c);
		}
		
		bw.close();
		br.close();
	}
	

}
