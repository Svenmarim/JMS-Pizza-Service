import models.Reply;
import models.Request;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BrokerFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultListModel<Request> requestListModel = new DefaultListModel<>();
    private JList<Request> requestList = new JList<>(requestListModel);
    private DefaultListModel<Reply> replyListModel = new DefaultListModel<>();
    private JList<Reply> replyList = new JList<>(replyListModel);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BrokerFrame frame = new BrokerFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public BrokerFrame() {
        setTitle("Broker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 450, 720);
        setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 30, 30, 30, 30, 0};
        gbl_contentPane.rowHeights = new int[]{10, 300, 10, 300};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblRequestsLabel = new JLabel("Requests");
        GridBagConstraints gbc_lblRequestsLabel = new GridBagConstraints();
        gbc_lblRequestsLabel.anchor = GridBagConstraints.WEST;
        gbc_lblRequestsLabel.insets = new Insets(0, 5, 0, 5);
        gbc_lblRequestsLabel.gridx = 0;
        gbc_lblRequestsLabel.gridy = 0;
        contentPane.add(lblRequestsLabel, gbc_lblRequestsLabel);

        JScrollPane requestScrollPane = new JScrollPane();
        GridBagConstraints gbc_requestScrollPane = new GridBagConstraints();
        gbc_requestScrollPane.gridwidth = 7;
        gbc_requestScrollPane.insets = new Insets(0, 5, 20, 5);
        gbc_requestScrollPane.fill = GridBagConstraints.BOTH;
        gbc_requestScrollPane.gridx = 0;
        gbc_requestScrollPane.gridy = 1;
        contentPane.add(requestScrollPane, gbc_requestScrollPane);

        JLabel lblRepliesLabel = new JLabel("Replies");
        GridBagConstraints gbc_lblRepliesLabel = new GridBagConstraints();
        gbc_lblRepliesLabel.anchor = GridBagConstraints.WEST;
        gbc_lblRepliesLabel.insets = new Insets(0, 5, 0, 5);
        gbc_lblRepliesLabel.gridx = 0;
        gbc_lblRepliesLabel.gridy = 2;
        contentPane.add(lblRepliesLabel, gbc_lblRepliesLabel);

        JScrollPane replyScrollPane = new JScrollPane();
        GridBagConstraints gbc_replyScrollPane = new GridBagConstraints();
        gbc_replyScrollPane.gridwidth = 7;
        gbc_replyScrollPane.insets = new Insets(0, 5, 0, 5);
        gbc_replyScrollPane.fill = GridBagConstraints.BOTH;
        gbc_replyScrollPane.gridx = 0;
        gbc_replyScrollPane.gridy = 3;
        contentPane.add(replyScrollPane, gbc_replyScrollPane);

        requestScrollPane.setViewportView(requestList);
        replyScrollPane.setViewportView(replyList);

        // TODO: start listening
//        brokerClientAppGateway.receiveLoanRequest(this::onRequestReceived);
//        brokerBankAppGateway.receiveBankReply(this::onReplyReceived);
    }

    public void onRequestReceived(Request request) {
        // Adds request to list
        requestListModel.addElement(request);
        requestList.repaint();

        // TODO: Add request to topic
    }

    public void onReplyReceived(Reply reply) {
        // Adds reply to list
        replyListModel.addElement(reply);
        replyList.repaint();

        // Send reply
//        brokerClientAppGateway.sendLoanReply(reply);
    }
}
