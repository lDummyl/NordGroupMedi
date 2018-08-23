package main.java.Main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainWindow extends JFrame {

    private boolean inAction;
    private String [] buttonTextVariants = new String [] {"Запустить", "Остановить"};
    private JButton button = new JButton(buttonTextVariants[0]);
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Интервал сканирования, сек:");
    // TODO: 23.08.2018 delete
    //    private JRadioButton radio1 = new JRadioButton("Select this");
//    private JRadioButton radio2 = new JRadioButton("Select that");
    private JCheckBox check = new JCheckBox("Архивировать логи", false);


    public MainWindow() {
        super("Logs processer");
        this.setBounds(100,100,400,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(MyProperties.getCompanySmallLogoAddress());
        setIconImage(imageIcon.getImage());
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,2,2,2));
        container.add(label);
        input.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                try {
                    int i = Integer.parseInt(input.getText());
                    if (i<=0){
                        JOptionPane.showMessageDialog(null,
                                "Error: Please enter number bigger than 0", "Error Massage",
                                JOptionPane.ERROR_MESSAGE);
                    }else{
                        Main.logScanner.setTimePeriod(i*1000);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Please enter number bigger than 0", "Error Massage",
                            JOptionPane.ERROR_MESSAGE);


                } catch (HeadlessException e) {
                    e.printStackTrace();
                }
            }
        });
        container.add(input);
        ButtonGroup group = new ButtonGroup();
        // TODO: 23.08.2018 delete
//        group.add(radio1);
//        group.add(radio2);
//        container.add(radio1);
//        radio1.setSelected(true);
//        container.add(radio2);
        container.add(check);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileManager.setArchive(check.isSelected());
            }
        });
        button.addActionListener(new ButtonEventListener());
        container.add(button);
        setResizable(false);

    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            nextAction();
// TODO: 23.08.2018 delete
//            String message = "";
//            message += "Button was pressed\n";
//            message += "Text is " + input.getText() + "\n";
//            message += (radio1.isSelected()?"Radio #1":"Radio #2")
//                    + " is selected\n";
//            message += "CheckBox is " + ((check.isSelected())
//                    ?"checked":"unchecked");
//            JOptionPane.showMessageDialog(null,
//                    message,
//                    "Output",
//                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void invoke() {
        MainWindow app = new MainWindow();
        app.setVisible(true);
    }


    private void nextAction(){

        String text = button.getText();
        if (text.equals(this.buttonTextVariants[0])) {//start
            Main.logScanner.startScanner();
        }
        if (text.equals(this.buttonTextVariants[1])) {//stop
            Main.logScanner.shutDown();
            XmlBuilder.createXmlReport();
        }
        for (int i = 0; i < buttonTextVariants.length; i++) {
            String buttonTextVariant = buttonTextVariants[i];
            if (!buttonTextVariant.equals(text)){
                button.setText(buttonTextVariant);
                button.updateUI();
            }
        }
    }
}
