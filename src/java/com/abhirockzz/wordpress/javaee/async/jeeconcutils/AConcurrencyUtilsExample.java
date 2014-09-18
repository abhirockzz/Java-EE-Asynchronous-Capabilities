
package com.abhirockzz.wordpress.javaee.async.jeeconcutils;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AConcurrencyUtilsExample", urlPatterns = {"/AConcurrencyUtilsExample"})
public class AConcurrencyUtilsExample extends HttpServlet {

    @Resource
    ManagedExecutorService mes;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Enter AConcurrencyUtilsExample/doGet executing in thread "+ Thread.currentThread().getName());
        System.out.println("initiating task . . . ");
        mes.execute(new AManagedTask());
        System.out.println("Exit AConcurrencyUtilsExample/doGet and returning thread "+ Thread.currentThread().getName() +" back to pool");
    }

   private class AManagedTask implements Runnable, ManagedTask{

        @Override
        public void run() {
            try {
                System.out.println("Enter AManagedTask/run() executing in thread "+ Thread.currentThread().getName());
                System.out.println("Pretending as if I am doing something !");
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AConcurrencyUtilsExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public ManagedTaskListener getManagedTaskListener() {
          return  new ManagedTaskListener() {

                @Override
                public void taskSubmitted(Future<?> future, ManagedExecutorService executor, Object task) {
                    System.out.println("ManagedTaskListener --> I got submitted on "+ new Date().toString());
                }

                @Override
                public void taskAborted(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {
                    System.out.println("I got aborted on "+ new Date().toString());
                }

                @Override
                public void taskDone(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {
                        System.out.println("ManagedTaskListener --> I got completed on "+ new Date().toString());                }

                @Override
                public void taskStarting(Future<?> future, ManagedExecutorService executor, Object task) {
                    System.out.println("ManagedTaskListener --> I got started on "+ new Date().toString());
                }
            };
        }

        @Override
        public Map<String, String> getExecutionProperties() {
           return Collections.emptyMap();
        }
       
   }
}
