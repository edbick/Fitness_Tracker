import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame{
    Container pane;
    JPanel signUpPanel,homePanel,registerPanel;
    JLabel signUpLabel,userLabel,nameLabel,passLabel,helpLabel;
    JTextField userField,nameField;
    JPasswordField passField;
    JButton signUpButton,backButton;

    public SignUp(Container pane){
        this.pane = pane;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(new GridLayout(5, 1));
        pane.revalidate();

        signUpLabel = new JLabel("New Account", SwingConstants.CENTER);
        signUpLabel.setFont(new Font("Serif",Font.BOLD,50));
        pane.add(signUpLabel);

        signUpPanel = new JPanel();
        signUpPanel.setLayout(new GridLayout(3,2));
        signUpPanel.setBorder(new EmptyBorder(0,20,0,20));

        nameLabel = new JLabel("Name",SwingConstants.RIGHT);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        signUpPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Tahoma", Font.PLAIN,22));
        signUpPanel.add(nameField);

        userLabel = new JLabel("Username", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Tahoma", Font.PLAIN,22));
        signUpPanel.add(userLabel);

        userField = new JTextField();
        userField.setFont(new Font("Tahoma", Font.PLAIN,22));
        signUpPanel.add(userField);

        passLabel = new JLabel("Password", SwingConstants.RIGHT);
        passLabel.setFont(new Font("Tahoma", Font.PLAIN,22));
        signUpPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setFont(new Font("Tahoma", Font.PLAIN,22));
        signUpPanel.add(passField);
        pane.add(signUpPanel);

        registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(1,1));
        registerPanel.setBorder(new EmptyBorder(5,10,5,10));

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Tahoma",Font.PLAIN,25));
        registerPanel.add(signUpButton);
        pane.add(registerPanel);

        register r = new register();
        signUpButton.addActionListener(r);

        helpLabel = new JLabel("Enter your name, username and password to sign up!", SwingConstants.CENTER);
        helpLabel.setFont(new Font("Tahome",Font.PLAIN,20));
        pane.add(helpLabel);

        homePanel = new JPanel();
        homePanel.setLayout(new FlowLayout());

        backButton = new JButton("Back");
        backButton.setVerticalAlignment(0);
        backButton.setFont(new Font("Tahome",Font.PLAIN,25));
        homePanel.add(backButton);
        pane.add(homePanel);

        back b = new back();
        backButton.addActionListener(b);
    }

    public class register implements ActionListener{
        public void actionPerformed(ActionEvent r){
            String name = nameField.getText();
            String username = userField.getText();
            String password = passField.getText();

            if((!name.isEmpty()) && (!username.isEmpty()) && (!password.isEmpty()) && (name.length() <= 15) && (username.length() <= 30) && (password.length() <= 30)){
                try{
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
                    //PreparedStatement st = conn.prepareStatement("INSERT INTO users (username,password,name) VALUES ('"+username+"','"+password+"','"+name+"')");
                    PreparedStatement st = conn.prepareStatement("INSERT INTO users (username,password,name) VALUES (?, ?, ?)");

                    st.setString(1, username);
                    st.setString(2, password);
                    st.setString(3, name);
                    st.executeUpdate();
                    signUpButton.setEnabled(false);
                    helpLabel.setText("<html>Success! New account <b>"+username+"</b> has been created!</html>");
                } catch(SQLException sqlException){
                    sqlException.printStackTrace();
                    helpLabel.setText("Sorry, our servers and offline please try again later");
                }
            } else{
                if((name.isEmpty()) || (name.length() > 15)){
                    helpLabel.setText("Please enter your name (max 15 characters)");
                } else if((username.isEmpty()) || (username.length() > 30)){
                    helpLabel.setText("<html><center>Please enter a username (max 30 characters)<br>Make your username memorable as you will need this to login.</center></html>");
                } else {
                    helpLabel.setText("Please enter a password (max 30 characters");
                }
            }
        }
    }

    public class back implements ActionListener{
        public void actionPerformed(ActionEvent b){
            new LogPage(pane);
        }
    }
}
