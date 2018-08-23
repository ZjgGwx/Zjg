package estore.utils;

import java.util.HashMap;
import java.util.Map;

import com.github.wxpay.sdk.WXPay;

public class WXPayUtils {
	public static String wxpay(String oid){
		String code_url = "";
		
		  MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        //��Ʒ����
        data.put("body", "estore�̳�֧��");
        //������
        data.put("out_trade_no", oid);
        //
        data.put("device_info", "");
        //����  CNY��ʾ�����
        data.put("fee_type", "CNY");
        //��۽���λΪ��
        data.put("total_fee", "1");
        //
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //ָ��֧���ķ�ʽΪɨ��֧��
        data.put("trade_type", "NATIVE");  // �˴�ָ��Ϊɨ��֧��
        data.put("product_id", "12");

        try {
        	//����΢��֧��ϵͳ�ķ���ֵ
            Map<String, String> resp = wxpay.unifiedOrder(data);
            //��΢�ŷ��صĲ����У���ȡkeyΪcode_url��ֵ������΢��֧���ĵ�ַ
            code_url = resp.get("code_url");
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return code_url;
	}
	
	
	public static String getWXPayResult(String oid){
		String trade_state = "";
		
		MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", oid);

        try {
        	/**
        	 * trade_state:���׵�״̬
        	 * 	SUCCESS��֧���ɹ�
				REFUND��ת���˿�
				NOTPAY��δ֧��
				CLOSED���ѹر�
				REVOKED���ѳ�����ˢ��֧����
				USERPAYING--�û�֧����
				PAYERROR--֧��ʧ��(����ԭ�������з���ʧ��)
        	 * 
        	 */
            Map<String, String> resp = wxpay.orderQuery(data);
            //��ȡ΢�ŷ��ص�֧�����
            trade_state = resp.get("trade_state");
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return trade_state;
	}
	
	public static void main(String[] args) throws Exception {

		 	MyConfig config = new MyConfig();
	        WXPay wxpay = new WXPay(config);

	        Map<String, String> data = new HashMap<String, String>();
	        data.put("out_trade_no", "544489ed3f9d48bbaaaefd28b70bb637");

	        try {
	        	/**
	        	 * trade_state:���׵�״̬
	        	 * 	SUCCESS��֧���ɹ�
					REFUND��ת���˿�
					NOTPAY��δ֧��
					CLOSED���ѹر�
					REVOKED���ѳ�����ˢ��֧����
					USERPAYING--�û�֧����
					PAYERROR--֧��ʧ��(����ԭ�������з���ʧ��)
	        	 * 
	        	 */
	            Map<String, String> resp = wxpay.orderQuery(data);
	            System.out.println(resp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    }

}
