import interfaces.ICallbackReplyFrame;
import interfaces.ICallbackRequestFrame;
import message_gateways.MessageReceiverGateway;
import message_gateways.MessageSenderGateway;
import models.Reply;
import models.Request;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PizzeriaFrame extends JFrame implements ICallbackRequestFrame, ICallbackReplyFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfPrice;
    private JTextField tfTime;
    private DefaultListModel<Request> listModel = new DefaultListModel<>();
    private JList<Request> list = new JList<>(listModel);
    private DefaultListModel<Reply> listModelApproved = new DefaultListModel<>();
    private JList<Reply> listApproved = new JList<>(listModelApproved);
    private List<Request> latestRequests = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PizzeriaFrame framePyramide = new PizzeriaFrame("PYRAMIDE");
                PizzeriaFrame frameSphinx = new PizzeriaFrame("SPHINX");
                framePyramide.setVisible(true);
                frameSphinx.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public PizzeriaFrame(String pizzeriaName) {
        setTitle("Pizzeria - " + pizzeriaName);
        MessageSenderGateway messageSenderGateway = new MessageSenderGateway("queue", null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{46, 31, 86, 30, 89, 0};
        gbl_contentPane.rowHeights = new int[]{233, 23, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 5;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        contentPane.add(scrollPane, gbc_scrollPane);

        scrollPane.setViewportView(list);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(e -> {
            if (list.getSelectedValue() != null) {
                Request request = list.getSelectedValue();
                listModel.removeElement(request);
                list.repaint();
            }
        });
        GridBagConstraints gbc_btnDelete = new GridBagConstraints();
        gbc_btnDelete.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnDelete.gridx = 0;
        gbc_btnDelete.gridy = 1;
        contentPane.add(btnDelete, gbc_btnDelete);

        JLabel lblNewLabel = new JLabel("Price");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        tfPrice = new JTextField();
        GridBagConstraints gbc_tfPrice = new GridBagConstraints();
        gbc_tfPrice.gridwidth = 2;
        gbc_tfPrice.insets = new Insets(0, 0, 0, 5);
        gbc_tfPrice.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfPrice.gridx = 1;
        gbc_tfPrice.gridy = 1;
        contentPane.add(tfPrice, gbc_tfPrice);
        tfPrice.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Time");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        tfTime = new JTextField();
        GridBagConstraints gbc_tfTime = new GridBagConstraints();
        gbc_tfTime.gridwidth = 2;
        gbc_tfTime.insets = new Insets(0, 0, 0, 5);
        gbc_tfTime.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTime.gridx = 1;
        gbc_tfTime.gridy = 2;
        contentPane.add(tfTime, gbc_tfTime);
        tfTime.setColumns(10);

        JButton btnSendReply = new JButton("Send reply");
        btnSendReply.addActionListener(e -> {
            if (list.getSelectedValue() != null) {
                Request request = list.getSelectedValue();
                if (!tfPrice.getText().isEmpty() && !tfTime.getText().isEmpty()) {
                    try {
                        double price = Double.parseDouble(tfPrice.getText());
                        int arrivalMinutes = Integer.parseInt(tfTime.getText());
                        Reply reply = new Reply(request, price, arrivalMinutes, pizzeriaName);
                        if (request != null) {
                            listModel.removeElement(request);
                            list.repaint();
                            messageSenderGateway.sendReply(reply, pizzeriaName);
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        });
        GridBagConstraints gbc_btnSendReply = new GridBagConstraints();
        gbc_btnSendReply.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnSendReply.gridx = 4;
        gbc_btnSendReply.gridy = 1;
        contentPane.add(btnSendReply, gbc_btnSendReply);

        JLabel lblNewLabel_2 = new JLabel("Accepted requests");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 4;
        contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

        JScrollPane scrollPane2 = new JScrollPane();
        GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
        gbc_scrollPane2.gridwidth = 5;
        gbc_scrollPane2.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane2.fill = GridBagConstraints.BOTH;
        gbc_scrollPane2.gridx = 0;
        gbc_scrollPane2.gridy = 5;
        contentPane.add(scrollPane2, gbc_scrollPane2);

        scrollPane2.setViewportView(listApproved);

        JButton btnDelete2 = new JButton("DELETE");
        btnDelete2.addActionListener(e -> {
            if (listApproved.getSelectedValue() != null) {
                Reply reply = listApproved.getSelectedValue();
                listModel.removeElement(reply);
                listApproved.repaint();
            }
        });
        GridBagConstraints gbc_btnDelete2 = new GridBagConstraints();
        gbc_btnDelete2.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnDelete2.gridx = 0;
        gbc_btnDelete2.gridy = 6;
        contentPane.add(btnDelete2, gbc_btnDelete2);

        MessageReceiverGateway messageReceiverTopicGateway = new MessageReceiverGateway("topic", "requestTopic");
        messageReceiverTopicGateway.receiveRequest(this);

        MessageReceiverGateway messageReceiverQueueGateway = new MessageReceiverGateway("queue", pizzeriaName);
        messageReceiverQueueGateway.receiveReply(this);
    }

    @Override
    public void onRequestReceived(Request request) {
        for (Request r : latestRequests){
            if (r.getName().equals(request.getName()) && r.getAddress().equals(request.getAddress()) && r.getDescription().equals(request.getDescription())) {
                if (listModel.contains(r)){
                    listModel.removeElement(r);
                }
                latestRequests.remove(r);
                return;
            }
        }
        listModel.addElement(request);
        list.repaint();
        if (latestRequests.size() == 50){
            latestRequests.remove(49);
        }
        latestRequests.add(request);
    }

    @Override
    public void onReplyReceived(Reply reply) {
        listModelApproved.addElement(reply);
        listApproved.repaint();
    }
}