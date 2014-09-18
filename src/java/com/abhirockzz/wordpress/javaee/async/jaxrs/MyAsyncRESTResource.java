package com.abhirockzz.wordpress.javaee.async.jaxrs;

import com.abhirockzz.wordpress.javaee.async.jaxrs.model.Student;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

@Path("async")
public class MyAsyncRESTResource {

    @Resource
    ManagedExecutorService mes;

    @Path("/{id}")
    @GET
    @Produces("application/xml")
    public void asyncMethod(@Suspended AsyncResponse resp, @PathParam("id") String input) {
        
        System.out.println("Entered MyAsyncRESTResource/asyncMethod() executing in thread: "+ Thread.currentThread().getName());
        mes.execute(
                () -> {
                    System.out.println("Entered Async zone executing in thread: "+ Thread.currentThread().getName());
                    System.out.println("Simulating long running op via Thread sleep() started on "+ new Date().toString());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MyAsyncRESTResource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Completed Long running op on "+new Date().toString());
                    System.out.println("Exiting Async zone executing in thread: "+ Thread.currentThread().getName());
                    
                    //creating a dummy instance of our model class (Student)
                    
                    Student stud = new Student(input, "Abhishek", "Apr-08-1987");
                    resp.resume(Response.ok(stud).build());
                }
        );
        
        System.out.println("Exit MyAsyncRESTResource/asyncMethod() and returned thread "+Thread.currentThread().getName()+" back to thread pool");
    }

}
