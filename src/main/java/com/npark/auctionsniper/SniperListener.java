package com.npark.auctionsniper;

import java.util.EventListener;

/**
 * Created by merritt on 5/4/2015.
 */
public interface SniperListener extends EventListener {
    void sniperLost();
    void sniperBidding();
    void sniperWinning();
    void sniperWon();
}
