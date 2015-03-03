package web;

import spiders.taleo.AlstomSpider;
import spiders.taleo.RenaultSpider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.*;


/**
 * Created by Thierry on 3/2/15.
 */
public class SysContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event)
    {
        event.getServletContext().log("listener turned on");

        Runnable task1 = new Runnable() {
            public void run() {
                try {
                    new AlstomSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };
        Runnable task2 = new Runnable() {
            public void run() {
                try {
                    new RenaultSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };
        Executor executor = Executors.newScheduledThreadPool(5);
        ScheduledExecutorService scheduler = (ScheduledExecutorService) executor;
        scheduler.scheduleAtFixedRate(task1, 0, 86400, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(task2, 0, 86400, TimeUnit.SECONDS);

        event.getServletContext().log("database updated");
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        event.getServletContext().log("listener turned off");
    }




}
