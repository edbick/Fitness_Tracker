import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LogPage extends JFrame {
    Container pane;
    JTextField userField;
    JButton logInButton;
    JLabel titleLabel, userLabel, passLabel, messageLabel;
    JPanel titlePanel, logInPanel, enterPanel, bottomPanel;
    JPasswordField passField;

    public LogPage(Container pane){
        this.pane = pane;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(new GridLayout(4, 1));
        pane.revalidate();

        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 1));

        titleLabel = new JLabel("Fitness Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titlePanel.add(titleLabel);
        pane.add(titlePanel);

        logInPanel = new JPanel();
        logInPanel.setLayout(new GridLayout(2, 2));
        logInPanel.setBorder(new EmptyBorder(0,20,0,20));

        userLabel = new JLabel("Username", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        logInPanel.add(userLabel);

        userField = new JTextField();
        userField.setFont(new Font("Tahoma", Font.PLAIN, 22));
        logInPanel.add(userField);

        passLabel = new JLabel("Password", SwingConstants.RIGHT);
        passLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        logInPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setFont(new Font("Tahoma", Font.PLAIN, 22));
        logInPanel.add(passField);
        pane.add(logInPanel);

        enterPanel = new JPanel();
        enterPanel.setLayout(new GridLayout(1, 1));
        enterPanel.setBorder(new EmptyBorder(5,10,5,10));

        logInButton = new JButton("Log in");
        //this.getRootPane().setDefaultButton(logInButton);
        logInButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
        enterPanel.add(logInButton);
        pane.add(enterPanel);

        logIn li = new logIn();
        logInButton.addActionListener(li);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 1));

        messageLabel = new JLabel("Please enter your username and password to login into your account", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bottomPanel.add(messageLabel);
        pane.add(bottomPanel);
    }

    public class logIn implements ActionListener {
        public void actionPerformed(ActionEvent li) {
            String username = userField.getText();
            String password = passField.getText();
            int id = 0;
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2", "test2");
                PreparedStatement st1 = conn.prepareStatement("SELECT username, password FROM users WHERE username=? and password=?");
                st1.setString(1, username);
                st1.setString(2, password);
                ResultSet rs1 = st1.executeQuery();
                if (rs1.next()) {
                    PreparedStatement st2 = conn.prepareStatement("SELECT id FROM users WHERE username=?");
                    st2.setString(1,username);
                    ResultSet rs2 = st2.executeQuery();
                    if(rs2.next()){
                        id = rs2.getInt("id");
                    }
                    new HomePage(pane,id);
                } else {
                    messageLabel.setText("<html><center>Incorrect username and password entered!<br>Make sure you have entered your username " +
                            "and password correctly<br>Don't have an account? Sign up by clicking register at the top of the screen</center></html>");
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                messageLabel.setText("Sorry, our servers and offline please try again later");
            }
        }
    }
}
