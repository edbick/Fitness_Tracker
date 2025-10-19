import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame{
    Container pane;
    JButton recordButton, socialButton, leaderBoardButton, profileButton;
    JLabel titleLabel;
    JPanel titlePanel, optionPanel;
    int id;

    public HomePage(Container pane,int id){
        this.id = id;
        this.pane = pane;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(new GridLayout(2, 1));
        pane.revalidate();

        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2, 1));

        titleLabel = new JLabel("Fitness Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titlePanel.add(titleLabel);

        profileButton = new JButton("Your profile");
        profileButton.setFont(new Font("Calibre", Font.BOLD, 25));
        titlePanel.add(profileButton);
        pane.add(titlePanel);

        profilePage pp = new profilePage();
        profileButton.addActionListener(pp);

        optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(1, 3));

        recordButton = new JButton("<html>Record your latest fitness activity!</html>");
        recordButton.setFont(new Font("Calibre", Font.BOLD, 20));
        optionPanel.add(recordButton);

        recordPage rp = new recordPage();
        recordButton.addActionListener(rp);

        socialButton = new JButton("<html>Make new fitness friends and keep up to date with their progress.</html>");
        socialButton.setFont(new Font("Calibre", Font.BOLD, 20));
        optionPanel.add(socialButton);

        socialPage sp = new socialPage();
        socialButton.addActionListener(sp);

        leaderBoardButton = new JButton("<html>Unleash your competitive side and view the latest fitness leaderboards</html.");
        leaderBoardButton.setFont(new Font("Calibre", Font.BOLD, 20));
        //leaderBoardButton.setEnabled(false);
        optionPanel.add(leaderBoardButton);

        leaderBoardPage lbp = new leaderBoardPage();
        leaderBoardButton.addActionListener(lbp);

        pane.add(optionPanel);
    }

    public class profilePage implements ActionListener {
        public void actionPerformed(ActionEvent pp) {
            new ProfilePage(pane,id);
        }
    }

    public class recordPage implements ActionListener {
        public void actionPerformed(ActionEvent rp) {
            new RecordPage(pane,id);
        }
    }

    public class socialPage implements ActionListener {
        public void actionPerformed(ActionEvent sp) {
            new SocialPage(pane,id);
        }
    }

    public class leaderBoardPage implements ActionListener {
        public void actionPerformed(ActionEvent lbp) {
            new LeaderboardPage(pane,id);
        }
    }
}
