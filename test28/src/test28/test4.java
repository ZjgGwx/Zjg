package test28;

import java.util.ArrayList;

/**

2.����Test�ࣺ��main()�����У�������Ҫ���д����: 
    1���ֱ�ʵ��������Plant������������ֱ�Ϊ��"����"��20��"�ٺ�"��30��"õ��"��50; 
    2������һ��ArrayList���ϣ������������洢����Plant���ͣ��ֱ����������Plant������ӵ������� 
3������������ϣ���������nameΪ"õ��"�Ķ��󣬼۸�����50Ԫ��Ȼ�������������showInfo()����չʾ�����������ֺͼ۸�. 
 */
public class test4 {

	public static void main(String[] args) {
		Plant p1 = new Plant("����",20);
		Plant p2 = new Plant("�ٺ�",30);
		Plant p3 = new Plant("õ��",50);
		ArrayList<Plant> list = new ArrayList<Plant>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		for (int i = 0; i <list.size(); i++) {
			Plant p = list.get(i);
			if(p.getName().equals("õ��")){
				p.setPrice(p.getPrice()+50);
				p.showInfo();
			}
		}
		
	}

}
