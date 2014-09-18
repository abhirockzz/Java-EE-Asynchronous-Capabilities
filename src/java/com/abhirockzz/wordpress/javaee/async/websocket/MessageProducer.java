/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abhirockzz.wordpress.javaee.async.websocket;

import com.abhirockzz.wordpress.javaee.async.websocket.model.Stock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class MessageProducer {
    private final static List<Stock> stocks = new ArrayList(); 
    static{
        stocks.add(new Stock("GOO", "10", new Date().toString()));
        stocks.add(new Stock("FBK", "11", new Date().toString()));
        stocks.add(new Stock("TWI", "12", new Date().toString()));
    }
    
    @Inject
    Event<Stock> event;
    
    @Schedule(year = "*", month = "*", dayOfMonth = "*" , hour = "*" , minute = "*" , second = "1" )
    public void sendMsg(){
        
        //String message = new Date().toString();
        
        Random r = new Random();
        
         stocks.stream().forEach((stock) -> { 
             stock.setPrice(String.valueOf(r.nextInt(15)));
             System.out.println("Message sent by MessageProducer --> "+stock );
         event.fire(stock);}
                  );
         
        
       
    }
    
}
