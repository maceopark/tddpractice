/**
 * Created by merritt on 5/3/2015.
 */
package com.npark.auctionsniper.test;

import com.npark.auctionsniper.Main;
import com.npark.auctionsniper.ui.MainWindow;

import static com.npark.auctionsniper.test.FakeAuctionServer.XMPP_HOSTNAME;
import static com.npark.auctionsniper.ui.MainWindow.STATUS_JOINING;
import static com.npark.auctionsniper.ui.MainWindow.STATUS_LOST;

public class ApplicationRunner {
    public static final String SNIPER_XMPP_ID = "sniper@nochul/Auction";
    private static final String SNIPER_ID = "sniper";
    private static final String SNIPER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(STATUS_JOINING);
    }

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(STATUS_LOST);
    }

    public void hasShownSniperIsBidding() {
        driver.showsSniperStatus(MainWindow.STATUS_BIDDING);
    }

    public void stop() {
        if (driver != null)
            driver.dispose();
    }
}

