import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FitnessTracker extends JFrame {
    JMenuBar menuBar;
    JMenu help, register;
    JMenuItem exit,signUp,logOut;
    Container pane = this.getContentPane();

    public FitnessTracker() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        help = new JMenu("Help");
        menuBar.add(help);

        register = new JMenu("Register");
        menuBar.add(register);

        signUp = new JMenuItem("Sign Up");
        register.add(signUp);

        newAccount na = new newAccount();
        signUp.addActionListener(na);

        logOut = new JMenuItem("Log Out");
        help.add(logOut);

        exit = new JMenuItem("Exit");
        help.add(exit);

        systemClose sc = new systemClose();
        exit.addActionListener(sc);

        logPage lp = new logPage();
        logOut.addActionListener(lp);

        new LogPage(pane);
    }

    public static class systemClose implements ActionListener {
        public void actionPerformed(ActionEvent sc) {
            System.exit(0);
        }
    }

    public class newAccount implements ActionListener{
        public void actionPerformed(ActionEvent na){
            new SignUp(pane);
        }
    }

    public class logPage implements ActionListener {
        public void actionPerformed(ActionEvent lp) {
            int a = JOptionPane.showConfirmDialog(logOut, "Are you sure?");
            if (a == JOptionPane.YES_OPTION) {
                new LogPage(pane);
            }
        }
    }

    public static void main(String[] args) {
        FitnessTracker gui = new FitnessTracker();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(800, 700);
        //gui.setResizable(false);
        gui.setTitle("Fitness Tracker");
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
    }
}