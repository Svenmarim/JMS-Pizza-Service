package message_gateways;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.JsonSerializer;
import models.Reply;
import models.Request;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

public class MessageSenderGateway extends MessageGateway {
    private JsonSerializer jsonSerializer;

    public MessageSenderGateway(String queueOrTopic, String lookupName) {
        jsonSerializer = new JsonSerializer();

        if (queueOrTopic.equals("topic")) {
            try {
                setProperties(queueOrTopic, lookupName);
            } catch (NamingException | JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRequest(Request request, String replyToName) {
        try {
            MessageProducer producer = getSession().createProducer(getDestination());

            // create and send message
            createAndSendMessage(producer, replyToName, request);
        } catch (JMSException | JsonProcessingException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                getConnection().close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendReply(Reply reply, String pizzaName) {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            initConnection(props, null);

            MessageProducer producer = getSession().createProducer(reply.getRequest().getDestination());
            reply.getRequest().setDestination(null);

            // create and send message
            createAndSendMessage(producer, pizzaName, reply);
        } catch (JMSException | JsonProcessingException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                getConnection().close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendApproval(Reply reply) {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            initConnection(props, null);

            MessageProducer producer = getSession().createProducer(reply.getDestination());
            reply.setDestination(null);

            // use Jackson to map object to string
            String s = jsonSerializer.objectToJson(reply);

            // create a text message
            Message message = getSession().createTextMessage(s);

            // send the message
            producer.send(message);
        } catch (JMSException | JsonProcessingException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                getConnection().close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void setProperties(String queueOrTopic, String lookupName) throws JMSException, NamingException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        props.put((queueOrTopic + "." + lookupName), lookupName);
        initConnection(props, lookupName);
    }

    private <T> void createAndSendMessage(MessageProducer producer, String lookupName, T requestReply) throws JMSException, JsonProcessingException, NamingException {
        // use Jackson to map object to string
        String s = jsonSerializer.objectToJson(requestReply);

        // create a text message
        Message message = getSession().createTextMessage(s);

        // set return address
        setProperties("queue", lookupName);
        message.setJMSReplyTo(getDestination());

        // send the message
        producer.send(message);
    }
}
