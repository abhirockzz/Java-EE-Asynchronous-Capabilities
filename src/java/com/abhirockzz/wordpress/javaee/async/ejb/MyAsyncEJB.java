

package com.abhirockzz.wordpress.javaee.async.ejb;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class MyAsyncEJB {

    
    @Asynchronous
    public void asyncEJB1(){
        
        System.out.println("Entered MyAsyncEJB/asyncEJB1()");
        System.out.println("MyAsyncEJB/asyncEJB1() Executing in thread: "+ Thread.currentThread().getName());
        System.out.println("Pretending as if MyAsyncEJB/asyncEJB1() is doing something !");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(MyAsyncEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Exiting MyAsyncEJB/asyncEJB1()");
    }
    
    
        @Asynchronous
        public Future<String> asyncEJB2(){
        
        System.out.println("Entered MyAsyncEJB/asyncEJB2()");
       System.out.println("MyAsyncEJB/asyncEJB2() Executing in thread: "+ Thread.currentThread().getName());
        System.out.println("Pretending as if MyAsyncEJB/asyncEJB2() is doing something !");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(MyAsyncEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Exiting MyAsyncEJB/asyncEJB2()");
        return new AsyncResult("Finished Executing on "+ new Date().toString());
        
    }
}
