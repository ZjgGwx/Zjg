package estore.utils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class MessageUtils {


    //��Ʒ����:��ͨ�Ŷ���API��Ʒ,�����������滻
    static final String product = "Dysmsapi";
    //��Ʒ����,�����������滻
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO �˴���Ҫ�滻�ɿ������Լ���AK(�ڰ����Ʒ��ʿ���̨Ѱ��)
    static final String accessKeyId = "LTAIEqXVP0HFOifh";
    static final String accessKeySecret = "rhPvBpL79jInlEKK1sFyujgJP6aokY";

    public static SendSmsResponse sendSms(String phone,String code) throws ClientException {

        //������������ʱʱ��
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //��ʼ��acsClient,�ݲ�֧��region��
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //��װ�������-��������������̨-�ĵ���������
        SendSmsRequest request = new SendSmsRequest();
        //����:�������ֻ���
        request.setPhoneNumbers(phone);
        //����:����ǩ��-���ڶ��ſ���̨���ҵ�
        request.setSignName("�Ž���");
        //����:����ģ��-���ڶ��ſ���̨���ҵ�
        request.setTemplateCode("SMS_139232635");
        //��ѡ:ģ���еı����滻JSON��,��ģ������Ϊ"�װ���${name},������֤��Ϊ${code}"ʱ,�˴���ֵΪ
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        //ѡ��-���ж�����չ��(�����������û�����Դ��ֶ�)
        //request.setSmsUpExtendCode("90997");

        //��ѡ:outIdΪ�ṩ��ҵ����չ�ֶ�,�����ڶ��Ż�ִ��Ϣ�н���ֵ���ظ�������
        request.setOutId("yourOutId");

        //hint �˴����ܻ��׳��쳣��ע��catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }
}
