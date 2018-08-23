package test28;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 1. 通过字符输出流向在项目根目录的info.txt中写入以下内容： 
黑水朝波咽， 
马上秋郊远。 
2. 通过字符输入流读取info.txt中的所有内容，每次读取一行，将每一行的第一个文字截取出来并打印在控制台上。 
 */
public class test5 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("info.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("info.txt"));
		bw.write("黑水朝波咽， ");
		bw.flush();
		bw.newLine();
		bw.write("马上秋郊远。");
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
