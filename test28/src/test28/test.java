package test28;

import java.util.Scanner;

/**
 * ��ʾ�û�����¼��һ�����ȴ���3���ַ���,���û��������ݺϷ���
 * ���ȡ���ַ����ĺ���λ��������ȡ�������ַ������з�ת��ӡ������̨�ϡ�
 * �������������ʾ������������ʾ����
 */

public class test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("������һ�����ȴ���3���ַ���");
		String s=sc.nextLine();
		if(s.length()<=3){
			System.out.println("������ַ����Ϸ�");
		}else {
			String news=s.substring(s.length()-3);
			StringBuilder sb = new StringBuilder();
			sb.append(news);
			sb.reverse();
			System.out.println(sb.toString());
		}
		
	}

}
