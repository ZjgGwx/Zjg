package test28;
/**
 * ����1��200֮�䣨����1��200�����������֣�ͳ���ܱ�7������ż���ĸ�������󽫸��������ִ�ӡ�ڿ���̨
 */
public class test1 {

	public static void main(String[] args) {
		int count=0;
		for (int i = 1; i < 201; i++) {
			if(i%7==0&&i%2==0){
				System.out.println(i);
				count++;
			}
		}
		System.out.println("�����У�"+count);
	}

}
