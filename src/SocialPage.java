import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SocialPage extends JFrame{
    Container pane;
    int id;
    JPanel topPanel,friendPanel,findPanel;
    JButton homeButton,friendButton,findButton;

    public SocialPage(Container pane,int id){
        this.id = id;
        this.pane = pane;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(null);
        pane.revalidate();

        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 650, 110);

        homeButton = new JButton("HOME");
        homeButton.setFont(new Font("Calibre", Font.BOLD, 20));
        homeButton.setBounds(10, 10, 100, 100);
        topPanel.add(homeButton);

        backHome bc = new backHome();
        homeButton.addActionListener(bc);
        pane.add(topPanel);

        friendPanel = new JPanel();
        friendPanel.setLayout(new GridLayout(1,1));
        friendPanel.setBounds(250,100,300,200);

        friendButton = new JButton("Your friends");
        friendButton.setFont(new Font("Calibre", Font.BOLD, 25));
        friendPanel.add(friendButton);
        pane.add(friendPanel);

        friend fr = new friend();
        friendButton.addActionListener(fr);

        findPanel = new JPanel();
        findPanel.setLayout(new GridLayout(1,1));
        findPanel.setBounds(250,350,300,200);

        findButton = new JButton("Find new user");
        findButton.setFont(new Font("Calibre", Font.BOLD, 25));
        findPanel.add(findButton);
        pane.add(findPanel);

        find fi = new find();
        findButton.addActionListener(fi);
    }

    public class backHome implements ActionListener{
        public void actionPerformed(ActionEvent bc){
            new HomePage(pane,id);
        }
    }

    public class friend implements ActionListener {
        public void actionPerformed(ActionEvent fr) {
            new friendsPage(pane,id);
        }
    }

    public class find implements ActionListener{
        public void actionPerformed(ActionEvent fi){
            new findPage(pane,id);
        }
    }

}