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
        //商品描述
        data.put("body", "estore商城支付");
        //订单号
        data.put("out_trade_no", oid);
        //
        data.put("device_info", "");
        //币种  CNY表示人民币
        data.put("fee_type", "CNY");
        //标价金额，单位为分
        data.put("total_fee", "1");
        //
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //指定支付的方式为扫码支付
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");

        try {
        	//这是微信支付系统的返回值
            Map<String, String> resp = wxpay.unifiedOrder(data);
            //从微信返回的参数中，获取key为code_url的值。就是微信支付的地址
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
        	 * trade_state:交易的状态
        	 * 	SUCCESS—支付成功
				REFUND—转入退款
				NOTPAY—未支付
				CLOSED—已关闭
				REVOKED—已撤销（刷卡支付）
				USERPAYING--用户支付中
				PAYERROR--支付失败(其他原因，如银行返回失败)
        	 * 
        	 */
            Map<String, String> resp = wxpay.orderQuery(data);
            //获取微信返回的支付结果
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
	        	 * trade_state:交易的状态
	        	 * 	SUCCESS—支付成功
					REFUND—转入退款
					NOTPAY—未支付
					CLOSED—已关闭
					REVOKED—已撤销（刷卡支付）
					USERPAYING--用户支付中
					PAYERROR--支付失败(其他原因，如银行返回失败)
	        	 * 
	        	 */
	            Map<String, String> resp = wxpay.orderQuery(data);
	            System.out.println(resp);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    }

}
