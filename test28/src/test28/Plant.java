package test28;
/**
 * 1.����"Plant"��, �������³�Ա: 
��Ա����:  
name (����)��String���ͣ� 
price(�۸�) ��int���ͣ� 
����ʹ��private���Ρ� 
��Ա����: 
1).get/set���� 
2).�޲��޷���ֵ�ķǾ�̬showInfo()����, ��ӡ��������ֺͼ۸�
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
//	2).�޲��޷���ֵ�ķǾ�̬showInfo()����, ��ӡ��������ֺͼ۸�
	public void showInfo(){
		System.out.println("����"+name+",�۸�"+price);
	}
	
}
