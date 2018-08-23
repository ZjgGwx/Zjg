package estore.listener;

import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import estore.service.IOrderService;
import estore.service.impl.OrderServiceImpl;

/**
 * Application Lifecycle Listener implementation class ScanOrderTimerListener
 *
 */
public class ScanOrderTimerListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
	private static final Logger logger = LogManager.getLogger(ScanOrderTimerListener.class);
    public ScanOrderTimerListener() {
        // TODO Auto-generated constructor stub
    	// ��������������������Ŀ�ʼ
    	
    	Timer timer = new Timer();
    	timer.schedule(new TimerTask(){

			@Override
			public void run() {
				//����ʼִ��
//				System.out.println("******ɨ������ʼ*********");
//				logger.info("******ɨ������ʼ��*********");
				logger.info("******ɨ������ʼ��*********");
				// ����service ����ִ��ɨ��ľ��幦�ܴ���
				IOrderService orderService = new OrderServiceImpl();
				orderService.scanOrderTimer();
				logger.info("******ɨ�����������*********");
//				System.out.println("******ɨ���������*********");
			}
    		
    	}, 1000, 1000*60*60*30);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }
	
}
