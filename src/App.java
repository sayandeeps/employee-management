import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel login = new JLabel("Enter Login:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(login, constraints);

        JTextField user = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(user, constraints);

        JLabel pass = new JLabel("Enter Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(pass, constraints);

        JPasswordField passField = new JPasswordField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(passField, constraints);

        JButton submit = new JButton("Submit");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submit, constraints);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                char[] passwordChars = passField.getPassword();
                String password = new String(passwordChars);

                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful");
                    // Perform actions after successful login
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean authenticate(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/employeemanage";
        String user = "root";
        String pass = "password";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM user WHERE username=? AND password=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
