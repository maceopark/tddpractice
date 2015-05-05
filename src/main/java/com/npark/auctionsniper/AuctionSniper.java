package com.npark.auctionsniper;

/**
 * Created by merritt on 5/4/2015.
 */
public class AuctionSniper implements AuctionEventListener {
    private final SniperListener sniperListener;
    private Auction auction;
    private boolean isWinning = false;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
    }

    public void auctionClosed() {
        if(isWinning)
            sniperListener.sniperWon();
        else
            sniperListener.sniperLost();
    }

    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if(isWinning) {
            sniperListener.sniperWinning();
        } else {
            auction.bid(price + increment);
            sniperListener.sniperBidding();
        }
    }
}
