package main.java.Main;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainWindow extends JFrame {

    private String [] buttonTextVariants = new String [] {"Запустить", "Остановить"};
    private JButton button = new JButton(buttonTextVariants[0]);
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Интервал сканирования, сек:");
    private JCheckBox check = new JCheckBox("Архивировать логи", false);


    public MainWindow() {
        super("Logs processor");
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
        }
    }

    public static void invoke() {
        MainWindow app = new MainWindow();
        app.setVisible(true);
    }

    private void nextAction(){

        boolean scannerActive = Main.logScanner.isActive();
        if (!scannerActive) {
            Main.logScanner.startScanner();
            button.setText(buttonTextVariants[1]);
        }else{
            Main.logScanner.shutDown();
            button.setText(buttonTextVariants[0]);
        }
        button.updateUI();
    }
}

