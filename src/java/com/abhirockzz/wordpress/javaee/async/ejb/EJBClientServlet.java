package com.abhirockzz.wordpress.javaee.async.ejb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EJBClientServlet", urlPatterns = {"/EJBClientServlet"})
public class EJBClientServlet extends HttpServlet {
    
    @Inject
    private MyAsyncEJB ejb;
  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try(PrintWriter writer = response.getWriter();) {
             writer.println("Entered EJBClientServlet/doGet()");
        writer.println("Executing in thread: "+ Thread.currentThread().getName());

        writer.println("invoking asyncEJB1()");
            ejb.asyncEJB1();
        
            writer.println("invoking asyncEJB2()");
           Future<String> future = ejb.asyncEJB2();
           
           String asyncEJB2Result = null;
        try {
            
            //this method blocks!!!!
            
             asyncEJB2Result = future.get();
             
        } catch (InterruptedException ex) {
            Logger.getLogger(EJBClientServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(EJBClientServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          writer.println("asyncEJB2Result --> "+ asyncEJB2Result);
             writer.println("Exiting EJBClientServlet/doGet()");
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
             
    }
    
    


}
