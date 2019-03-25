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

public class PizzeriaFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfPrice;
    private JTextField tfTime;
    private DefaultListModel<Request> listModel = new DefaultListModel<>();
    private JList<Request> list = new JList<>(listModel);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PizzeriaFrame framePyramide = new PizzeriaFrame("PYRAMIDE");
                PizzeriaFrame framePinokkio = new PizzeriaFrame("PINOKKIO");
                PizzeriaFrame frameSphinx = new PizzeriaFrame("SPHINX");
                framePyramide.setVisible(true);
                framePinokkio.setVisible(true);
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
//        bankAppGateway = new BankAppGateway(pizzeriaName);

        setTitle("Pizzeria - " + pizzeriaName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
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

        JLabel lblNewLabel = new JLabel("price");
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

        JLabel lblNewLabel_1 = new JLabel("time");
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

        JButton btnSendReply = new JButton("send reply");
        btnSendReply.addActionListener(e -> {
            Request request = list.getSelectedValue();
            double price = Double.parseDouble(tfPrice.getText());
            int arrivalMinutes = Integer.parseInt(tfTime.getText());
            Reply reply = new Reply(request, price, arrivalMinutes);
            if (request != null) {
                listModel.removeElement(request);
                list.repaint();
                // TODO: send reply
//                bankAppGateway.sendBankReply(reply);
            }
        });
        GridBagConstraints gbc_btnSendReply = new GridBagConstraints();
        gbc_btnSendReply.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnSendReply.gridx = 4;
        gbc_btnSendReply.gridy = 1;
        contentPane.add(btnSendReply, gbc_btnSendReply);

        // TODO: start listening to requests
//        bankAppGateway.receiveBankRequest(this::onRequestReceived);
    }

    public void onRequestReceived(Request request) {
        listModel.addElement(request);
    }
}