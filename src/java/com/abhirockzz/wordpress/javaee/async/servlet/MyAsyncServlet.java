package com.abhirockzz.wordpress.javaee.async.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/MyAsyncServlet", asyncSupported = true)
public class MyAsyncServlet extends HttpServlet {

    @Override

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {

        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(MyAsyncServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("entered doGet()");
        writer.println("ENTERING ... " + MyAsyncServlet.class.getSimpleName() + "/doGet()");
        writer.println("Executing in Thread: " + Thread.currentThread().getName());
        //step 1
        final AsyncContext asyncContext = req.startAsync();

        //step 2
        asyncContext.addListener(new CustomAsyncHandler(asyncContext));

        //step 3
        asyncContext.start(
                () -> {
                    PrintWriter logger = null;
                    try {
                        logger = asyncContext.getResponse().getWriter();
                    } catch (IOException ex) {
                        Logger.getLogger(MyAsyncServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    logger.println("Long running Aync task execution started : " + new Date().toString());

                    logger.println("Executing in Thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Logger.getLogger(MyAsyncServlet.class.getName()).log(Level.SEVERE, null, e);
                    }

                    logger.println("Long task execution complete : " + new Date().toString());

                    logger.println("Calling complete() on AsyncContext");

                    //step 4
                    asyncContext.complete();
                }
        );

        writer.println("EXITING ... " + MyAsyncServlet.class.getSimpleName() + "/doGet() and returning initial thread back to the thread pool");

    }

    private class CustomAsyncHandler implements AsyncListener {

        private AsyncContext ac;
        private PrintWriter logger = null;

        

        public CustomAsyncHandler(final AsyncContext ac) {

            
            this.ac = ac;

            try {
                logger = this.ac.getResponse().getWriter();
            } catch (IOException ex) {
                Logger.getLogger(MyAsyncServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            logger.println("EXECUTED .... CustomAsyncHandler/constructor");
        }

        @Override
        public void onStartAsync(AsyncEvent ae) throws IOException {
            logger.println("ENTERING ... " + CustomAsyncHandler.class.getSimpleName() + "/onStartAsync");
            
        }

        @Override
        public void onComplete(AsyncEvent ae) throws IOException {
            logger.println("ENTERING ... " + CustomAsyncHandler.class.getSimpleName() + "/onComplete");
        }

        @Override
        public void onTimeout(AsyncEvent ae) throws IOException {
            logger.println("ENTERING ... " + CustomAsyncHandler.class.getSimpleName() + "/onTimeout");

        }

        @Override
        public void onError(AsyncEvent ae) throws IOException {
            logger.println("ENTERING ... " + CustomAsyncHandler.class.getSimpleName() + "/onError");
        }

    }
}
