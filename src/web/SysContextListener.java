package web;

import db.Post;
import spiders.EdfSpider;
import spiders.RteSpider;
import spiders.taleo.AlstomSpider;
import spiders.taleo.GdfsuezSpider;
import spiders.taleo.RenaultSpider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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
                    new RenaultSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };
        Runnable task2 = new Runnable() {
            public void run() {
                try {
                    new AlstomSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };
        Runnable task3 = new Runnable() {
            public void run() {
                try {
                    new EdfSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };
        Runnable task4 = new Runnable() {
            public void run() {
                try {
                    new GdfsuezSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };

        Runnable task5 = new Runnable() {
            public void run() {
                try {
                    new RteSpider().crawlData();
                } catch (Exception e) {
                }
            }
        };

        Runnable clean = new Runnable() {
            public void run() {
                try {
                    Post.removeHistory();
                } catch (Exception e) {
                }
            }
        };
        Executor executor = Executors.newScheduledThreadPool(5);
        ScheduledExecutorService scheduler = (ScheduledExecutorService) executor;
        scheduler.scheduleAtFixedRate(task1, 0, 86400, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(task2, 0, 86400, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(task3, 0, 86400, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(task4, 0, 86400, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(task5, 0, 86400, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(clean, 0, 86400, TimeUnit.SECONDS);

        event.getServletContext().log("database updated");
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        event.getServletContext().log("listener turned off");
    }




}
