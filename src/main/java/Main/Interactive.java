package main.java.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Interactive {
    public static JFrame introFrame;

    public static void geetingsImage()  {

        try {
            String path = MyProperties.getCompanyBigLogoAddress();
            File file = new File(path);
            BufferedImage image = ImageIO.read(file);
            JLabel label = new JLabel(new ImageIcon(image));
            introFrame = new JFrame();
            introFrame.setUndecorated(true);
            introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            introFrame.getContentPane().add(label);
            introFrame.pack();
            introFrame.setLocation(200,200);
            introFrame.setLocationRelativeTo(null);
            introFrame.setVisible(true);
            Thread.sleep(3000);
            introFrame.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wellDoneReport() {

        ImageIcon imageIcon = new ImageIcon(MyProperties.getCompanySmallLogoAddress());
        JOptionPane.showMessageDialog(null,"Отчет сформирован", "info",JOptionPane.NO_OPTION,imageIcon);

    }
}
