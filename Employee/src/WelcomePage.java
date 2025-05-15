import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        setTitle("Welcome");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set JFrame size to full screen
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background panel with image
        BackgroundPanel outerPanel = new BackgroundPanel("/bg.jpg"); // Your background image path
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Inner panel with vertical layout
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setOpaque(false); // Make it transparent to show background

        // College Logo Image
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/clg_logo.png")); // Ensure this image exists
        Image logoImage = logoIcon.getImage().getScaledInstance(1000, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));

        // Title Label
        JLabel titleLabel = new JLabel("Employee Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // Continue Button
        JButton continueBtn = new JButton("➡️ Continue");
        continueBtn.setFont(new Font("Arial", Font.BOLD, 18));
        continueBtn.setBackground(new Color(70, 130, 180)); // Steel blue
        continueBtn.setForeground(Color.WHITE);
        continueBtn.setFocusPainted(false);
        continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover effect
        continueBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                continueBtn.setBackground(new Color(65, 105, 225)); // Royal blue
            }

            public void mouseExited(MouseEvent e) {
                continueBtn.setBackground(new Color(70, 130, 180)); // Steel blue
            }
        });

        // Button action
        continueBtn.addActionListener(e -> {
            new EmployeeTypePage(); // Ensure this class exists
            dispose();
        });

        // Add components
        innerPanel.add(logoLabel);
        innerPanel.add(titleLabel);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        innerPanel.add(continueBtn);

        outerPanel.add(innerPanel, gbc);

        add(outerPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomePage();
    }
}

// Custom panel to draw background image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
