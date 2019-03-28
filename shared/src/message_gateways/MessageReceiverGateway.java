package message_gateways;

import interfaces.ICallbackReplyFrame;
import interfaces.ICallbackRequestFrame;
import models.JsonSerializer;
import models.Reply;
import models.Request;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MessageReceiverGateway extends MessageGateway{
    private JsonSerializer jsonSerializer;
    private List<String> receivedMessages;

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }

    public MessageReceiverGateway(String queueOrTopic, String lookupName) {
        jsonSerializer = new JsonSerializer();
        receivedMessages = new ArrayList<>();

        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        props.put((queueOrTopic + "." + lookupName), lookupName);
        try {
            initConnection(props, lookupName);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveRequest(ICallbackRequestFrame callbackFrame) {
        try {
            MessageConsumer consumer = getSession().createConsumer(getDestination());

            getConnection().start(); // this is needed to start receiving messages

            consumer.setMessageListener(msg -> {
                TextMessage textMessage = (TextMessage) msg;
                try {
                    Request request = jsonSerializer.jsonToObject(textMessage.getText(), Request.class);
                    request.setDestination(msg.getJMSReplyTo());

                    // Go back to frame
                    callbackFrame.onRequestReceived(request);
                } catch (IOException | JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveReply(ICallbackReplyFrame callbackFrame) {
        try {
            MessageConsumer consumer = getSession().createConsumer(getDestination());

            getConnection().start(); // this is needed to start receiving messages

            consumer.setMessageListener(msg -> {
                TextMessage textMessage = (TextMessage) msg;
                try {
                    Reply reply = jsonSerializer.jsonToObject(textMessage.getText(), Reply.class);
                    reply.setDestination(msg.getJMSReplyTo());

                    // Go back to frame
                    callbackFrame.onReplyReceived(reply);
                } catch (IOException | JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveRequestPerformanceTest() {
        try {
            MessageConsumer consumer = getSession().createConsumer(getDestination());

            getConnection().start(); // this is needed to start receiving messages

            consumer.setMessageListener(msg -> {
                TextMessage textMessage = (TextMessage) msg;
                try {
                    receivedMessages.add(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
