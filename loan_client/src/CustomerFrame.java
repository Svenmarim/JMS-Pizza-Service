import models.Reply;
import models.Request;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomerFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfAddress;
    private JTextField tfDescription;
    private JTextField tfName;
    private DefaultListModel<Reply> listModel = new DefaultListModel<>();
    private JList<Reply> list = new JList<>(listModel);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CustomerFrame frame = new CustomerFrame();

                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public CustomerFrame() {
        setTitle("Customer");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JLabel lblBody = new JLabel("name");
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

        JLabel lblNewLabel = new JLabel("address");
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

        JLabel lblNewLabel_1 = new JLabel("description");
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

        JButton btnQueue = new JButton("send request");
        btnQueue.addActionListener(arg0 -> {
            String name = tfName.getText();
            String address = tfAddress.getText();
            String description = tfDescription.getText();

            Request request = new Request(name, address, description);

            //TODO: send request
        });
        GridBagConstraints gbc_btnQueue = new GridBagConstraints();
        gbc_btnQueue.insets = new Insets(0, 0, 5, 5);
        gbc_btnQueue.gridx = 2;
        gbc_btnQueue.gridy = 2;
        contentPane.add(btnQueue, gbc_btnQueue);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 7;
        gbc_scrollPane.gridwidth = 6;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 4;
        contentPane.add(scrollPane, gbc_scrollPane);

        scrollPane.setViewportView(list);

        //TODO: start listening to replys
//        clientAppGateway.receiveLoanReply(this::onReplyReceived);
    }

    public void onReplyReceived(Reply reply) {
        listModel.addElement(reply);
        list.repaint();
    }
}
