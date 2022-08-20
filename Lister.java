import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Graphics;

public class Lister implements ActionListener, MouseListener {

    JFrame frame;
    JPanel panelLeft, panelRight;
    JTable tb = new JTable();
    JLabel idLb, nameLb, gameLb, warninglb, imagelb, commentlb;
    DefaultTableModel model = new DefaultTableModel();
    JTextField textID, textName, textGame;
    JButton confirmBtn = new JButton("Confirm");
    JButton SaveFileButton = new JButton("Save");
    String[] options = { "Select option", "Add", "Delete", "Update", "Clear" };
    JComboBox cb = new JComboBox(options);
    Font font = new Font("", 1, 12);
    JFileChooser fileChooser = new JFileChooser(".");

    Object[] row = new Object[3];
    Object selectedCb;

    Lister() {
        ImageIcon appIcon = new ImageIcon("Icon.png");
        frame = new JFrame("Waifu Lister");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 600);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout(3, 3));
        frame.setIconImage(appIcon.getImage());

        // ------------------Image -------------------------------//
        imagelb = new JLabel();
        imagelb.setBounds(50, 400, 100, 100);
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("khesir.png"));
        } catch (IOException e) {

        }
        Image dimg = img.getScaledInstance(imagelb.getWidth(), imagelb.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon image = new ImageIcon(dimg);

        // -------------Create Table Model and Identifiers-------//
        Object[] columns = { "ID", "Name", "Game" };

        model.setColumnIdentifiers(columns);
        tb.setModel(model);

        tb.setBackground(Color.LIGHT_GRAY);
        tb.setForeground(Color.black);
        tb.setFont(font);
        tb.setRowHeight(15);

        // --------------------Panels-----------------------------//
        panelLeft = new JPanel();
        panelRight = new JPanel();

        panelLeft.setBackground(Color.DARK_GRAY);
        panelRight.setBackground(Color.GRAY);
        panelLeft.setPreferredSize(new Dimension(440, 500));
        panelRight.setPreferredSize(new Dimension(200, 500));

        // -------------Sub Panel LeftSide----------------------//
        warninglb = new JLabel("*Only save in .csv file*");
        warninglb.setBounds(140, 490, 300, 100);
        warninglb.setForeground(Color.yellow);
        panelLeft.setLayout(null);
        JScrollPane pane = new JScrollPane(tb);
        pane.setBounds(10, 10, 410, 410);

        SaveFileButton.setBounds(150, 450, 100, 25);
        SaveFileButton.addActionListener(this);

        panelLeft.add(pane);
        panelLeft.add(SaveFileButton);
        panelLeft.add(warninglb);

        // -------------Sub Panel RightSide---------------------//
        cb.setSelectedIndex(0);
        idLb = new JLabel("ID:");
        nameLb = new JLabel("Name:");
        gameLb = new JLabel("Game");
        commentlb = new JLabel("Created by Khesir");
        imagelb.setIcon(image);

        textID = new JTextField();
        textName = new JTextField();
        textGame = new JTextField();
        textID.setBounds(10, 70, 150, 25);
        textName.setBounds(10, 120, 150, 25);
        textGame.setBounds(10, 170, 150, 25);
        cb.setBounds(10, 10, 100, 25);
        cb.addActionListener(this);
        confirmBtn.setBounds(115, 10, 80, 25);
        confirmBtn.addActionListener(this);

        idLb.setBounds(10, 50, 150, 25);
        nameLb.setBounds(10, 100, 150, 25);
        gameLb.setBounds(10, 150, 150, 25);

        commentlb.setBounds(50, 470, 300, 100);

        panelRight.setLayout(null);
        panelRight.add(cb);
        panelRight.add(commentlb);
        panelRight.add(confirmBtn);
        panelRight.add(idLb);
        panelRight.add(textID);
        panelRight.add(nameLb);
        panelRight.add(textName);
        panelRight.add(gameLb);
        panelRight.add(textGame);
        panelRight.add(imagelb);

        // ----------------Frame Add------------------------------//
        frame.add(panelLeft, BorderLayout.WEST);
        frame.add(panelRight, BorderLayout.EAST);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        Lister ls = new Lister();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cb) {
            System.out.println(cb.getSelectedItem());
            selectedCb = cb.getSelectedItem();
        }

        if (e.getSource() == SaveFileButton) {
            fileChooser.setDialogTitle("Specify a file save");
            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    FileWriter fw = new FileWriter(fileToSave);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {

                            bw.write(tb.getValueAt(i, j).toString() + ",");
                        }
                        bw.newLine();

                    }

                    bw.close();
                    fw.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        }

        if (e.getSource() == confirmBtn) {
            if (selectedCb == "Add") {
                row[0] = textID.getText();
                row[1] = textName.getText();
                row[2] = textGame.getText();

                model.addRow(row);
            } else if (selectedCb == "Delete") {
                int i = tb.getSelectedRow();
                if (i >= 0) {
                    model.removeRow(i);
                } else {
                    System.out.println("Delete Error");
                }
            } else if (selectedCb == "Update") {
                int i = tb.getSelectedRow();

                if (i >= 0) {
                    model.setValueAt(textID.getText(), i, 0);
                    model.setValueAt(textName.getText(), i, 1);
                    model.setValueAt(textGame.getText(), i, 2);
                }
            } else if (selectedCb == "Clear") {
                textID.setText("");
                textName.setText("");
                textGame.setText("");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int i = tb.getSelectedRow();

        textID.setText(model.getValueAt(i, 0).toString());
        textName.setText(model.getValueAt(i, 1).toString());
        textGame.setText(model.getValueAt(i, 2).toString());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

}