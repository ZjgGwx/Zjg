package test28;
/**
 * ����һ����̬���� void comp(int[] arr),��������������ֵ����Сֵ����main�����ж���һ��int�����飬
 * ����Ԫ����{2,3,-5,1,9,2}�����������鴫��comp�����У����ò��ԡ� 
ʾ�����£�
      ���ֵ:20	��Сֵ:-6
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
		System.out.println("���ֵ�ǣ�"+max+"��Сֵ�ǣ�"+min);
		
	}

}
