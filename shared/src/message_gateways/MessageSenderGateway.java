package message_gateways;

import com.fasterxml.jackson.core.JsonProcessingException;
import interfaces.ICallbackFrame;
import models.JsonSerializer;
import models.Request;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class MessageSenderGateway extends MessageGateway {
    private JsonSerializer jsonSerializer;

    public MessageSenderGateway() {
        jsonSerializer = new JsonSerializer();
    }

    public void sendRequest(Request request) {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        props.put(("queue.first"), "request");

        try {
            initConnection(props);

            MessageProducer producer = getSession().createProducer(getDestination());

            // use Jackson to map object to string
            String s = jsonSerializer.objectToJson(request);

            // create a text message
            Message message = getSession().createTextMessage(s);

            // send the message
            producer.send(message);
        } catch (JMSException | NamingException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
