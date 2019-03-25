import interfaces.ICallbackReplyFrame;
import message_gateways.MessageReceiverGateway;
import message_gateways.MessageSenderGateway;
import models.Reply;
import models.Request;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.charset.Charset;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomerFrame extends JFrame implements ICallbackReplyFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfAddress;
    private JTextField tfDescription;
    private JTextField tfName;
    private DefaultListModel<Reply> listModel = new DefaultListModel<>();
    private JList<Reply> list = new JList<>(listModel);
    private MessageSenderGateway messageSenderGateway;
    private String id;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CustomerFrame frame = new CustomerFrame();
                CustomerFrame frame1 = new CustomerFrame();
                frame.setVisible(true);
                frame1.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public CustomerFrame() {
        // generate random id for customer
        id = "Customer";
        int number = (int)(Math.random() * 9999 + 1);
        id = id.concat(Integer.toString(number));

        setTitle(id);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 684, 619);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 30, 30, 30, 30, 0};
        gbl_contentPane.rowHeights = new int[]{30, 30, 30, 30, 30};
        gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblBody = new JLabel("Name");
        GridBagConstraints gbc_lblBody = new GridBagConstraints();
        gbc_lblBody.insets = new Insets(0, 0, 5, 5);
        gbc_lblBody.gridx = 0;
        gbc_lblBody.gridy = 0;
        contentPane.add(lblBody, gbc_lblBody);

        tfName = new JTextField();
        GridBagConstraints gbc_tfName = new GridBagConstraints();
        gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfName.insets = new Insets(0, 0, 5, 5);
        gbc_tfName.gridx = 1;
        gbc_tfName.gridy = 0;
        contentPane.add(tfName, gbc_tfName);
        tfName.setColumns(10);

        JLabel lblNewLabel = new JLabel("Address");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        tfAddress = new JTextField();
        GridBagConstraints gbc_tfAddress = new GridBagConstraints();
        gbc_tfAddress.anchor = GridBagConstraints.NORTH;
        gbc_tfAddress.insets = new Insets(0, 0, 5, 5);
        gbc_tfAddress.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfAddress.gridx = 1;
        gbc_tfAddress.gridy = 1;
        contentPane.add(tfAddress, gbc_tfAddress);
        tfAddress.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Description");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        tfDescription = new JTextField();
        GridBagConstraints gbc_tfDescription = new GridBagConstraints();
        gbc_tfDescription.insets = new Insets(0, 0, 5, 5);
        gbc_tfDescription.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfDescription.gridx = 1;
        gbc_tfDescription.gridy = 2;
        contentPane.add(tfDescription, gbc_tfDescription);
        tfDescription.setColumns(10);

        JButton btnQueue = new JButton("Send request");
        btnQueue.addActionListener(arg0 -> {
            if (!tfName.getText().isEmpty() && !tfAddress.getText().isEmpty() && !tfDescription.getText().isEmpty()) {
                String name = tfName.getText();
                String address = tfAddress.getText();
                String description = tfDescription.getText();
                Request request = new Request(name, address, description);

                messageSenderGateway = new MessageSenderGateway("topic", "requestTopic");
                messageSenderGateway.sendRequest(request, id);
            }
        });
        GridBagConstraints gbc_btnQueue = new GridBagConstraints();
        gbc_btnQueue.insets = new Insets(0, 0, 5, 5);
        gbc_btnQueue.gridx = 2;
        gbc_btnQueue.gridy = 2;
        contentPane.add(btnQueue, gbc_btnQueue);

        JButton btnApprove = new JButton("APPROVE");
        btnApprove.addActionListener(arg0 -> {
            if (list.getSelectedValue() != null) {
                Reply reply = list.getSelectedValue();
                messageSenderGateway = new MessageSenderGateway("queue", null);
                messageSenderGateway.sendApproval(reply);
                listModel.removeElement(reply);
                list.repaint();
            }
        });
        GridBagConstraints gbc_btnApprove = new GridBagConstraints();
        gbc_btnApprove.insets = new Insets(0, 0, 5, 5);
        gbc_btnApprove.gridx = 5;
        gbc_btnApprove.gridy = 3;
        contentPane.add(btnApprove, gbc_btnApprove);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(e -> {
            if (list.getSelectedValue() != null) {
                Reply reply = list.getSelectedValue();
                listModel.removeElement(reply);
                list.repaint();
            }
        });
        GridBagConstraints gbc_btnDelete = new GridBagConstraints();
        gbc_btnDelete.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnDelete.gridx = 0;
        gbc_btnDelete.gridy = 3;
        contentPane.add(btnDelete, gbc_btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 7;
        gbc_scrollPane.gridwidth = 6;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 4;
        contentPane.add(scrollPane, gbc_scrollPane);

        scrollPane.setViewportView(list);

        MessageReceiverGateway messageReceiverGateway = new MessageReceiverGateway("queue", id);
        messageReceiverGateway.receiveReply(this);
    }

    @Override
    public void onReplyReceived(Reply reply) {
        listModel.addElement(reply);
        list.repaint();
    }
}
