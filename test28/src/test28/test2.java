package test28;
/**
 * 定义一个静态方法 void comp(int[] arr),输出该数组中最大值和最小值，在main方法中定义一个int型数组，
 * 数组元素有{2,3,-5,1,9,2}，并将该数组传入comp方法中，调用测试。 
示例如下：
      最大值:20	最小值:-6
 */
public class test2 {

	public static void main(String[] args) {
		int [] arr={2,3,-5,1,9,2};
		comp(arr);

	}

	public static void comp(int[] arr) {
		int max=arr[0];
		int min=arr[0];
		for (int i = 0; i < arr.length; i++) {
			if(min>arr[i]){
				min=arr[i];
			}
			if(max<arr[i]){
				max=arr[i];
			}
		}
		System.out.println("最大值是："+max+"最小值是："+min);
		
	}

}
