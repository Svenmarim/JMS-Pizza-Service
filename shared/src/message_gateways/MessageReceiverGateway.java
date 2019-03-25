package message_gateways;

import interfaces.ICallbackFrame;
import models.JsonSerializer;
import models.Reply;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class MessageReceiverGateway extends MessageGateway{
    private JsonSerializer jsonSerializer;
    private String channel;

    public MessageReceiverGateway(String channel) {
        jsonSerializer = new JsonSerializer();
        this.channel = channel;
    }

    public void receiveReply(ICallbackFrame callbackFrame) {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        props.put(("queue.first"), channel);

        try {
            initConnection(props);

            MessageConsumer consumer = getSession().createConsumer(getDestination());

            getConnection().start(); // this is needed to start receiving messages

            consumer.setMessageListener(msg -> {
                TextMessage textMessage = (TextMessage) msg;
                try {
                    Reply reply = jsonSerializer.jsonToObject(textMessage.getText(), Reply.class);

                    // Go back to frame
                    callbackFrame.onRequestReplyReceived(reply);
                } catch (IOException | JMSException e) {
                    e.printStackTrace();
                }
            });

        } catch (JMSException | NamingException e) {
            e.printStackTrace();
        }
    }
}
