package tools;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowImage {
	public static void run(File image_file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image_file);

        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        JFrame jFrame = new JFrame();
        
        jFrame.setLayout(new FlowLayout());

        jFrame.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        JLabel jLabel = new JLabel();

        jLabel.setIcon(imageIcon);
        jFrame.add(jLabel);
        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

