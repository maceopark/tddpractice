package com.npark.auctionsniper;

import com.npark.auctionsniper.ui.MainWindow;
import com.npark.auctionsniper.xmpp.AuctionMessageTranslator;
import org.jetbrains.annotations.NotNull;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by merritt on 5/3/2015.
 */
public class Main implements AuctionEventListener {
    @NotNull
    public static final String JOIN_COMMAND_FORMAT = "";
    @NotNull
    public static final String BID_COMMAND_FORMAT = "%d";
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;
    @NotNull
    private static final String AUCTION_RESOURCE = "Auction";
    @NotNull
    private static final String ITEM_ID_AS_LOGIN = "auction-%s";
    @NotNull
    private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
    private MainWindow ui;
    @SuppressWarnings("unused")
    private Chat notToBeGCd;

    private Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.joinAuction(
            connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]),
            args[ARG_ITEM_ID]
        );
    }

    @NotNull
    private static XMPPConnection connection(@NotNull String hostname, @NotNull String username, @NotNull String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private static String auctionId(@NotNull String itemId, @NotNull XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    private void joinAuction(@NotNull XMPPConnection connection, @NotNull String itemId) throws XMPPException {
        disconnectWhenUICloses(connection);
        final Chat chat = connection.getChatManager().createChat(
            auctionId(itemId, connection),
            new AuctionMessageTranslator(this)
        );

        this.notToBeGCd = chat;
        chat.sendMessage(JOIN_COMMAND_FORMAT);
    }

    private void disconnectWhenUICloses(@NotNull final XMPPConnection connection) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                connection.disconnect();
            }
        });
    }

    public void auctionClosed() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.showStatus(MainWindow.STATUS_LOST);
            }
        });
    }

    public void currentPrice(int price, int increment) {

    }
}
