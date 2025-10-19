import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AwardsPage {
    Container pane;
    int id;
    JButton backBt;
    JPanel mainPanel,trendPanel;
    JLabel titleLabel,bestLabel,calTrend,stepTrend,disTrend,standTrend,exerciseTrend,workoutTrend;
    ImageIcon calTrendIcon,stepTrendIcon,disTrendIcon,standTrendIcon,exerciseTrendIcon,workoutTrendIcon;
    JScrollPane scroller;

    public AwardsPage(Container pane, int id){
        this.pane = pane;
        this.id = id;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(null);
        pane.revalidate();

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(790,2000));
        scroller = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setSize(790,640);
        scroller.setLocation(5,5);

        backBt = new JButton("BACK");
        backBt.setFont(new Font("Calibre", Font.BOLD, 20));
        backBt.setBounds(10,10,100,100);
        mainPanel.add(backBt);
        back bc = new back();
        backBt.addActionListener(bc);

        titleLabel = new JLabel("Your Award's");
        titleLabel.setFont(new Font("Serif",Font.BOLD,50));
        titleLabel.setBounds(250,10,350,100);
        mainPanel.add(titleLabel);

        bestLabel = new JLabel("Trends");
        bestLabel.setFont(new Font("Tahoma",Font.BOLD,20));
        bestLabel.setBounds(30,150,150,50);
        mainPanel.add(bestLabel);

        trendPanel = new JPanel();
        trendPanel.setLayout(new GridLayout(1,6));
        trendPanel.setBounds(10,180,750,200);
        mainPanel.add(trendPanel);

        calTrend = new JLabel();
        calTrendIcon = new ImageIcon(new ImageIcon("res/Image 21-09-2023 at 11.31.JPG").getImage().getScaledInstance(120,150,Image.SCALE_SMOOTH));
        calTrend.setIcon(calTrendIcon);
        trendPanel.add(calTrend);

        stepTrend = new JLabel();
        stepTrendIcon = new ImageIcon(new ImageIcon("res/Image 21-09-2023 at 11.31.JPG").getImage().getScaledInstance(120,150,Image.SCALE_SMOOTH));
        stepTrend.setIcon(stepTrendIcon);
        trendPanel.add(stepTrend);

        disTrend = new JLabel();
        disTrendIcon = new ImageIcon(new ImageIcon("res/Image 21-09-2023 at 11.31.JPG").getImage().getScaledInstance(120,150,Image.SCALE_SMOOTH));
        disTrend.setIcon(disTrendIcon);
        trendPanel.add(disTrend);

        standTrend = new JLabel();
        standTrendIcon = new ImageIcon(new ImageIcon("res/Image 21-09-2023 at 11.31.JPG").getImage().getScaledInstance(120,150,Image.SCALE_SMOOTH));
        standTrend.setIcon(standTrendIcon);
        trendPanel.add(standTrend);

        exerciseTrend = new JLabel();
        exerciseTrendIcon = new ImageIcon(new ImageIcon("res/Image 21-09-2023 at 11.31.JPG").getImage().getScaledInstance(120,150,Image.SCALE_SMOOTH));
        exerciseTrend.setIcon(exerciseTrendIcon);
        trendPanel.add(exerciseTrend);

        workoutTrend = new JLabel();
        workoutTrendIcon = new ImageIcon(new ImageIcon("res/Image 21-09-2023 at 11.31.JPG").getImage().getScaledInstance(120,150,Image.SCALE_SMOOTH));
        workoutTrend.setIcon(workoutTrendIcon);
        trendPanel.add(workoutTrend);
        pane.add(scroller);
    }

    public class back implements ActionListener {
        public void actionPerformed(ActionEvent bc){
            new ProfilePage(pane,id);
        }
    }
}
