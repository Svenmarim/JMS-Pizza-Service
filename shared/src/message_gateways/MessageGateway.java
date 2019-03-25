package message_gateways;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public abstract class MessageGateway {
    private Session session;
    private Destination destination;
    private Connection connection;

    public void initConnection(Properties props, String lookupName) throws NamingException, JMSException {
        Context jndiContext = new InitialContext(props);
        ConnectionFactory connectionFactory;
        connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        if(lookupName != null){
            destination = (Destination) jndiContext.lookup(lookupName);
        }
    }

    public Session getSession() {
        return session;
    }

    public Destination getDestination() {
        return destination;
    }

    public Connection getConnection(){
        return connection;
    }
}
