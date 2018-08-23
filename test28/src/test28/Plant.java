package test28;
/**
 * 1.定义"Plant"类, 包含以下成员: 
成员属性:  
name (名字)：String类型； 
price(价格) ：int类型； 
属性使用private修饰。 
成员方法: 
1).get/set方法 
2).无参无返回值的非静态showInfo()方法, 打印对象的名字和价格。
 */
public class Plant {
	private String name;
	private int price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Plant(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}
	public Plant() {
		super();
		// TODO Auto-generated constructor stub
	}
//	2).无参无返回值的非静态showInfo()方法, 打印对象的名字和价格
	public void showInfo(){
		System.out.println("名字"+name+",价格"+price);
	}
	
}
