import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login window to secure the application. (Beautified)
 */
public class LoginUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginUI() {
        // Set System Look and Feel for a more modern native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Asset Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // Header Panel
        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(41, 128, 185)); // Beautiful Blue
        panelTop.setPreferredSize(new Dimension(400, 60));
        JLabel lblTitle = new JLabel("Welcome Back");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(new EmptyBorder(10, 0, 0, 0));
        panelTop.add(lblTitle);
        add(panelTop, BorderLayout.NORTH);

        // Center Panel with Form
        JPanel panelCenter = new JPanel(new GridBagLayout());
        panelCenter.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0;
        panelCenter.add(lblUsername, gbc);

        txtUsername = new JTextField(15);
        txtUsername.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 0;
        panelCenter.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1;
        panelCenter.add(lblPassword, gbc);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 1;
        panelCenter.add(txtPassword, gbc);

        add(panelCenter, BorderLayout.CENTER);

        // Bottom Panel for Button
        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(Color.WHITE);
        panelBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        btnLogin = new JButton("Login securely");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBottom.add(btnLogin);
        add(panelBottom, BorderLayout.SOUTH);

        // Login Action
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if ("admin".equals(username) && "admin".equals(password)) {
                    // Open Main UI
                    new AssetUI().setVisible(true);
                    // Close login window
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUI().setVisible(true);
        });
    }
}
