package test28;

import java.util.Scanner;

/**
 * 提示用户键盘录入一个长度大于3的字符串,若用户输入数据合法，
 * 则截取出字符串的后三位，并将截取出来的字符串进行反转打印到控制台上。
 * 否则给出错误提示并结束程序。演示如下
 */

public class test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个长度大于3的字符串");
		String s=sc.nextLine();
		if(s.length()<=3){
			System.out.println("输入的字符不合法");
		}else {
			String news=s.substring(s.length()-3);
			StringBuilder sb = new StringBuilder();
			sb.append(news);
			sb.reverse();
			System.out.println(sb.toString());
		}
		
	}

}
