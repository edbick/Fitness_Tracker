import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class findPage extends JFrame{
    Container pane;
    int id;
    JPanel topPanel,resultsPanel;
    JTextField searchField;
    JButton backButton,searchButton;
    JLabel titleLabel;
    JLabel userLabel[] = new JLabel[100];
    JButton userButton[] = new JButton[100];
    JScrollPane scroller;
    String currentUser = "";
    int i=0;

    public findPage(Container pane,int id){
        this.id = id;
        this.pane = pane;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(null);
        pane.revalidate();

        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 650, 110);

        backButton = new JButton("BACK");
        backButton.setFont(new Font("Calibre", Font.BOLD, 20));
        backButton.setBounds(10, 10, 100, 100);
        topPanel.add(backButton);

        titleLabel = new JLabel("Find a user");
        titleLabel.setFont(new Font("Serif",Font.BOLD,50));
        titleLabel.setBounds(250,10,300,100);
        topPanel.add(titleLabel);

        back bc = new back();
        backButton.addActionListener(bc);
        pane.add(topPanel);

        searchField = new JTextField();
        searchField.setBounds(200,130,300,50);
        pane.add(searchField);

        searchButton = new JButton("search");
        searchButton.setBounds(505,130,70,50);

        searchUser su = new searchUser();
        searchButton.addActionListener(su);
        pane.add(searchButton);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(null);
        resultsPanel.setPreferredSize(new Dimension(450,1200));
        scroller = new JScrollPane(resultsPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setSize(450,350);
        scroller.setLocation(163,205);
        getUsers();
        pane.add(scroller);
    }

    public class back implements ActionListener{
        public void actionPerformed(ActionEvent bc){
            new SocialPage(pane,id);
        }
    }

    public class searchUser implements ActionListener{
        public void actionPerformed(ActionEvent su){
            i = 0;
            int y = 0;
            try{
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
                PreparedStatement st = conn.prepareStatement("SELECT username FROM users WHERE username LIKE ? AND username !=?");
                st.setString(1,"%" + (searchField.getText()).toLowerCase() + "%");
                st.setString(2,currentUser);
                ResultSet rs = st.executeQuery();
                resultsPanel.removeAll();
                resultsPanel.repaint();

                while(rs.next()){
                    String username = rs.getString("username");
                    resultsPanel.repaint();
                    createList(username,i,y);
                    i+=1;
                    y+=80;
                }
            } catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    public void createList(String user,int i,int y){
        userLabel[i] = new JLabel(user,SwingConstants.CENTER);
        userLabel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userLabel[i].setBounds(0,y,350,75);
        resultsPanel.add(userLabel[i]);

        userButton[i] = new JButton("add");
        userButton[i].setBounds(365,y+5,50,60);
        userButton[i].setActionCommand(Integer.toString(i));
        resultsPanel.add(userButton[i]);
        friendChecker();

        addFriend af = new addFriend();
        userButton[i].addActionListener(af);
    }

    public void getUsers(){
        i = 0;
        int y =0;
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT username FROM users WHERE username !=? ORDER BY id ASC");
            PreparedStatement st1 = conn.prepareStatement("SELECT username FROM users WHERE id=?");
            st1.setInt(1,id);
            ResultSet rs1 = st1.executeQuery();
            if(rs1.next()){
                currentUser = rs1.getString("username");
            }
            st.setString(1,currentUser);
            ResultSet rs = st.executeQuery();
            while(rs.next() && i<14){
                String username = rs.getString("username");
                createList(username,i,y);
                i+=1;
                y+=80;
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public class addFriend implements ActionListener{
        public void actionPerformed(ActionEvent af){
            int i = Integer.parseInt(af.getActionCommand());
            userButton[i].setEnabled(false);
            userButton[i].setText("added");

            try{
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
                PreparedStatement st = conn.prepareStatement("INSERT INTO UserFriends(UserID,friend) VALUES(?,?)");
                st.setInt(1,id);
                st.setString(2,userLabel[i].getText());
                st.execute();
            } catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    public void friendChecker(){
        String friend = "";
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT friend FROM UserFriends WHERE UserID =?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                friend = rs.getString("friend");
                if(friend.equals(userLabel[i].getText())){
                    userButton[i].setEnabled(false);
                    userButton[i].setText("added");
                }
            }

        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
