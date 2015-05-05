package com.npark.auctionsniper.xmpp;

import com.npark.auctionsniper.AuctionEventListener.*;
import com.npark.auctionsniper.AuctionEventListener;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by merritt on 5/3/2015.
 */
public class AuctionMessageTranslator implements MessageListener {
    private AuctionEventListener listener = null;
    private String sniperId = null;

    public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
        this.listener = listener;
        this.sniperId = sniperId;
    }

    public void processMessage(Chat chat, Message message) {
        AuctionEvent event = AuctionEvent.from(message.getBody());

        String eventType = event.type();
        if ("CLOSE".equals(eventType))
            listener.auctionClosed();
        else if ("PRICE".equals(eventType))
            listener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));

    }

    private static class AuctionEvent {
        private final Map<String, String> fields = new HashMap<String, String>();

        public PriceSource isFrom(String sniperId) {
            return sniperId.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
        }

        private String bidder() {
            return get("Bidder");
        }

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for(String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }

        static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        public String type() { return get("Event"); }
        public int currentPrice() { return getInt("CurrentPrice"); }
        public int increment() { return getInt("Increment"); }

        private int getInt(String fieldName) {
            return Integer.parseInt(get(fieldName));
        }

        private String get(String fieldName) {
            return fields.get(fieldName);
        }


    }

}
