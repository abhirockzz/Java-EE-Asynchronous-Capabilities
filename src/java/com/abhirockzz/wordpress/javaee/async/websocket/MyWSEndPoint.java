
package com.abhirockzz.wordpress.javaee.async.websocket;

import com.abhirockzz.wordpress.javaee.async.websocket.model.Stock;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws/async")
public class MyWSEndPoint {

    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet());

    @OnOpen
    public void onOpen(Session peer) {

        System.out.println(peer.getId()+" joined !");
       
        try {
            peer.getBasicRemote().sendText("Welcome "+ peer.getId());
        } catch (IOException ex) {
            Logger.getLogger(MyWSEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
       

       
            peers.stream()
                    .filter((aPeer) -> {
                return (!aPeer.getId().equals(peer.getId()));
            })
                    .forEach((anotherPeer) -> {

                try {
                    //System.out.println(anotherPeer.getId());
                    anotherPeer.getBasicRemote().sendText(peer.getId() + " joined !");
                } catch (IOException ex) {
                    Logger.getLogger(MyWSEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        
 peers.add(peer);
  System.out.println("peers.size() --> "+ peers.size());
    }

//    @OnMessage
//    public void onMessage(String message, Session peer) {
//
//        peers.stream().forEach((aPeer) -> {
//
//            aPeer.getAsyncRemote().sendText(message, (result) -> {
//                System.out.println("Message Sent? " + result.isOK());
//                System.out.println("Thread : " + Thread.currentThread().getName());
//            });
//
//        });
//    }

    @OnClose
    public void onClose(Session peer) { 

        peers.remove(peer);

        //if (peers.size() > 1) {
            peers.stream().forEach((aPeer) -> {
                try {
                    aPeer.getBasicRemote().sendText(peer.getId() + " disconnected !");
                } catch (IOException ex) {
                    Logger.getLogger(MyWSEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        //

    }

   public void sendMsg(@Observes Stock stock) {
        System.out.println("Message receieved by MessageObserver --> "+ stock);
        System.out.println("peers.size() --> "+ peers.size());
        peers.stream().forEach((aPeer) -> {
            //stock.setPrice();
          
                            aPeer.getAsyncRemote().sendText(stock.toString(), (result) -> {
                System.out.println("Message Sent? " + result.isOK());
                System.out.println("Thread : " + Thread.currentThread().getName());
            });
                
               

        });
    }

}
