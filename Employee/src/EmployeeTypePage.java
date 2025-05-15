import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeTypePage extends JFrame {
    private JLabel titleLabel;
    private Timer colorTimer;
    private float hue = 0;

    public EmployeeTypePage() {
        setTitle("Choose Employee Type");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new AnimatedGradientPanel());
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Select Employee Type", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);
        animateTitleColor();

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 25, 25));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, screenSize.width / 4, 30, screenSize.width / 4));

        String[] types = {
                "👩‍🏫 Teacher", "👨‍💼 Admin", "🧑‍🏫 Non-Teaching Staff", "📚 Librarian", "🏃‍♂️ PE Teacher"
        };

        for (String type : types) {
            JButton button = createFancyButton(type);
            button.addActionListener(e -> {
                new EmployeeDashboardUI(type).setVisible(true);
                dispose();
            });
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createFancyButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                button.setForeground(Color.YELLOW);
            }
            public void mouseExited(MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }

    private void animateTitleColor() {
        colorTimer = new Timer(100, e -> {
            hue += 0.01;
            if (hue > 1) hue = 0;
            titleLabel.setForeground(Color.getHSBColor(hue, 0.8f, 1.0f));
        });
        colorTimer.start();
    }

    static class AnimatedGradientPanel extends JPanel {
        private float hue1 = 0f;
        private float hue2 = 0.5f;
        private Timer timer;

        public AnimatedGradientPanel() {
            timer = new Timer(50, e -> {
                hue1 += 0.002f;
                hue2 += 0.002f;
                if (hue1 > 1f) hue1 = 0f;
                if (hue2 > 1f) hue2 = 0f;
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color color1 = Color.getHSBColor(hue1, 0.6f, 1.0f);
            Color color2 = Color.getHSBColor(hue2, 0.6f, 1.0f);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeTypePage::new);
    }
}
