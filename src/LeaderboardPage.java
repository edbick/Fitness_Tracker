import javax.swing.*;
import java.awt.*;

public class LeaderboardPage extends JFrame{
    Container pane;
    int id;

    public LeaderboardPage(Container pane, int id){
        this.id = id;
        this.pane = pane;
        pane.removeAll();
        pane.repaint();
        pane.setLayout(new GridLayout(2, 2));
        pane.revalidate();
    }
}
