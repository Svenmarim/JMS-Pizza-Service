import message_gateways.MessageReceiverGateway;
import message_gateways.MessageSenderGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceTest {
    @Test
    void performance100Test() {
        performTestWithAmount(100);
    }

    @Test
    void performance1000Test() {
        performTestWithAmount(1000);
    }

    @Test
    void performance10000Test() {
        performTestWithAmount(10000);
    }

    @Test
    void performance100000Test() {
        performTestWithAmount(100000);
    }

    private void performTestWithAmount(int amount) {
        // Start receiving messages
        MessageReceiverGateway messageReceiverGateway = new MessageReceiverGateway("topic", "requestTopic");
        messageReceiverGateway.receiveRequestPerformanceTest();

        // Start sending messages
        MessageSenderGateway messageSenderGateway = new MessageSenderGateway("topic", "requestTopic");
        messageSenderGateway.sendRequestPerformanceTest(amount);

        assertEquals(amount, messageReceiverGateway.getReceivedMessages().size());
    }
}