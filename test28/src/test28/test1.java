package test28;
/**
 * 遍历1到200之间（包含1和200）的所有数字，统计能被7整除的偶数的个数，最后将个数和数字打印在控制台
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
		System.out.println("个数有："+count);
	}

}
