package com.npark.auctionsniper;

/**
 * Created by merritt on 5/4/2015.
 */
public interface Auction {
    void bid(int newBidPrice);
    void join();
}
