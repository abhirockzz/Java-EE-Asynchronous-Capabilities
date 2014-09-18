

package com.abhirockzz.wordpress.javaee.async.websocket.model;


public class Stock {
    
    private String symbol;
    private String price;
    private String genTStamp;

    public Stock(String symbol, String price, String genTStamp) {
        this.symbol = symbol;
        this.price = price;
        this.genTStamp = genTStamp;
    }

   

    public void setPrice(String price) {
        this.price = price;
    }
    
    

    @Override
    public String toString() {
        return "STOCK INFO: [ "+ symbol + "  $"+ price + "  @"+ genTStamp +" ]";
    }
    
    
    
    
    
}
