package com.npark.auctionsniper;

import com.npark.auctionsniper.ui.MainWindow;

import javax.swing.*;

/**
 * Created by merritt on 5/4/2015.
 */
public class SniperStateDisplayer implements SniperListener {
    private final MainWindow ui;

    public SniperStateDisplayer(MainWindow ui) {
        this.ui = ui;
    }

    public void sniperBidding() {
        showStatus(MainWindow.STATUS_BIDDING);
    }

    public void sniperLost() {
        showStatus(MainWindow.STATUS_LOST);
    }

    public void sniperWinning() {
        showStatus(MainWindow.STATUS_WINNING);
    }

    public void sniperWon() {
        showStatus(MainWindow.STATUS_WON);
    }

    private void showStatus(final String status) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.showStatus(status);
            }
        });
    }
}
