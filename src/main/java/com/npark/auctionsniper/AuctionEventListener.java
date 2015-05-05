package com.npark.auctionsniper;

import java.util.EventListener;

/**
 * Created by merritt on 5/3/2015.
 */
public interface AuctionEventListener extends EventListener {
    enum PriceSource {
        FromSniper, FromOtherBidder;
    };

    void auctionClosed();
    void currentPrice(int price, int increment, PriceSource priceSource);
}
