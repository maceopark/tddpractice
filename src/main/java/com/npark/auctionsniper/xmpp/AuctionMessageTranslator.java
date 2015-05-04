package com.npark.auctionsniper.xmpp;

import com.npark.auctionsniper.AuctionEventListener;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by merritt on 5/3/2015.
 */
public class AuctionMessageTranslator implements MessageListener {
    AuctionEventListener listener = null;

    public AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener;
    }

    public void processMessage(Chat chat, Message message) {
        listener.auctionClosed();
    }

}
