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
    	// 创建监听器来监听任务的开始
    	
    	Timer timer = new Timer();
    	timer.schedule(new TimerTask(){

			@Override
			public void run() {
				//任务开始执行
//				System.out.println("******扫描任务开始*********");
//				logger.info("******扫描任务开始了*********");
				logger.info("******扫描任务开始了*********");
				// 调用service 层来执行扫描的具体功能代码
				IOrderService orderService = new OrderServiceImpl();
				orderService.scanOrderTimer();
				logger.info("******扫描任务结束了*********");
//				System.out.println("******扫描任务结束*********");
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
