import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class friendsPage extends JFrame{
    Container pane;
    int id;
    JLabel titleLabel;
    JLabel friendLabel[] = new JLabel[100];
    JButton removeButton[] = new JButton[100];
    JButton viewButton[] = new JButton[100];
    JPanel topPanel,friendPanel;
    JButton backButton;
    JScrollPane scroller;
    int i = 0;
    int y = 0;

    public friendsPage(Container pane, int id){
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

        back bc = new back();
        backButton.addActionListener(bc);

        titleLabel = new JLabel("Your Friends");
        titleLabel.setFont(new Font("Serif",Font.BOLD,50));
        titleLabel.setBounds(250,10,300,100);
        topPanel.add(titleLabel);
        pane.add(topPanel);

        friendPanel = new JPanel();
        friendPanel.setLayout(null);
        friendPanel.setPreferredSize(new Dimension(450,1200));
        scroller = new JScrollPane(friendPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setSize(450,450);
        scroller.setLocation(163,130);
        getFriends();
        pane.add(scroller);
    }
    public class back implements ActionListener{
        public void actionPerformed(ActionEvent bc){
            new SocialPage(pane,id);
        }
    }

    public void getFriends(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT friend FROM UserFriends WHERE UserID =?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String username = rs.getString("friend");
                createList(username);
                i++;
                y+=80;
            }

        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void createList(String user){
        friendLabel[i] = new JLabel(user,SwingConstants.CENTER);
        friendLabel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        friendLabel[i].setBounds(0,y,285,75);
        friendPanel.add(friendLabel[i]);

        viewButton[i] = new JButton("view");
        viewButton[i].setBounds(295,y+5,50,60);
        viewButton[i].setActionCommand(Integer.toString(i));
        friendPanel.add(viewButton[i]);

        viewFriend vf = new viewFriend();
        viewButton[i].addActionListener(vf);

        removeButton[i] = new JButton("remove");
        removeButton[i].setBounds(355,y+5,65,60);
        removeButton[i].setActionCommand(Integer.toString(i));
        friendPanel.add(removeButton[i]);

        removeFriend rf = new removeFriend();
        removeButton[i].addActionListener(rf);
    }

    public class viewFriend implements ActionListener{
        public void actionPerformed(ActionEvent vf){
            int i = Integer.parseInt(vf.getActionCommand());
            int friendId = 0;
            try{
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
                PreparedStatement st1 = conn.prepareStatement("SELECT id FROM users WHERE username=?");
                st1.setString(1,friendLabel[i].getText());
                ResultSet rs1 = st1.executeQuery();
                if(rs1.next()){
                    friendId = rs1.getInt("id");
                }
                PreparedStatement st2 = conn.prepareStatement("SELECT friend FROM UserFriends WHERE UserID=?");
                st2.setInt(1,friendId);
                ResultSet rs2 = st2.executeQuery();
                if(rs2.next() && friendId != 0){
                    new FriendDataPage(pane,id,friendId,friendLabel[i].getText());
                } else{
                    JOptionPane.showMessageDialog(null,"Sorry this user has not added you yet. Try again later",
                            "Friend not found",JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    public class removeFriend implements ActionListener{
        public void actionPerformed(ActionEvent rf){
            int i = Integer.parseInt(rf.getActionCommand());
            try{
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
                PreparedStatement st = conn.prepareStatement("DELETE FROM UserFriends WHERE friend=? AND UserID=?");
                st.setString(1,friendLabel[i].getText());
                st.setInt(2,id);
                st.execute();
                removeButton[i].setEnabled(false);
                removeButton[i].setText("removed");
                viewButton[i].setEnabled(false);
            } catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

}
