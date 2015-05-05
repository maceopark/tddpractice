package com.npark.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import static com.npark.auctionsniper.Main.BID_COMMAND_FORMAT;
import static com.npark.auctionsniper.Main.JOIN_COMMAND_FORMAT;

/**
 * Created by merritt on 5/4/2015.
 */
public class XMPPAuction implements Auction {
    private final Chat chat;

    public XMPPAuction(Chat chat) {
        this.chat = chat;
    }

    public void bid(int newPrice) {
        sendMessage(String.format(BID_COMMAND_FORMAT, newPrice));
    }

    public void join() {
        sendMessage(JOIN_COMMAND_FORMAT);
    }

    private void sendMessage(final String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
}
