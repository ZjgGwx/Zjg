package test28;

import java.util.ArrayList;

/**

2.定义Test类：在main()方法中，按以下要求编写代码: 
    1）分别实例化三个Plant对象，三个对象分别为："多肉"：20、"百合"：30、"玫瑰"：50; 
    2）创建一个ArrayList集合，这个集合里面存储的是Plant类型，分别将上面的三个Plant对象添加到集合中 
3）遍历这个集合，将集合中name为"玫瑰"的对象，价格增加50元，然后调用这个对象的showInfo()方法展示这个对象的名字和价格. 
 */
public class test4 {

	public static void main(String[] args) {
		Plant p1 = new Plant("多肉",20);
		Plant p2 = new Plant("百合",30);
		Plant p3 = new Plant("玫瑰",50);
		ArrayList<Plant> list = new ArrayList<Plant>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		for (int i = 0; i <list.size(); i++) {
			Plant p = list.get(i);
			if(p.getName().equals("玫瑰")){
				p.setPrice(p.getPrice()+50);
				p.showInfo();
			}
		}
		
	}

}
