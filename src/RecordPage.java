import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class RecordPage extends JFrame {
    Container pane;
    int id;
    JPanel topPanel, inputPanel, messagePanel;
    JButton homeButton, enterButton;
    JLabel recordLabel, messageLabel, calorieLabel, stepsLabel, distanceLabel, standingLabel, exerciseLabel, workoutLabel, emptyLabel;
    JTextField calorieField, stepsField, distanceField, standingField, exerciseField, workoutField;

    public RecordPage(Container pane, int id) {
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

        recordLabel = new JLabel("Add Today's Activity");
        recordLabel.setFont(new Font("Serif", Font.BOLD, 50));
        recordLabel.setBounds(170, 10, 600, 100);
        topPanel.add(recordLabel);
        pane.add(topPanel);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        inputPanel.setBounds(150, 130, 500, 400);

        calorieLabel = new JLabel("Calories burnt: ", SwingConstants.RIGHT);
        calorieLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(calorieLabel);

        calorieField = new JTextField();
        calorieField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(calorieField);

        stepsLabel = new JLabel("Number of steps: ", SwingConstants.RIGHT);
        stepsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(stepsLabel);

        stepsField = new JTextField();
        stepsField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(stepsField);

        distanceLabel = new JLabel("Distance travelled (kilometers): ", SwingConstants.RIGHT);
        distanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(distanceLabel);

        distanceField = new JTextField();
        distanceField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(distanceField);

        standingLabel = new JLabel("Time standing (minutes): ", SwingConstants.RIGHT);
        standingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(standingLabel);

        standingField = new JTextField();
        standingField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(standingField);

        exerciseLabel = new JLabel("Time exercising (minutes): ", SwingConstants.RIGHT);
        exerciseLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(exerciseLabel);

        exerciseField = new JTextField();
        exerciseField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(exerciseField);

        workoutLabel = new JLabel("Number of workouts: ", SwingConstants.RIGHT);
        workoutLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(workoutLabel);

        workoutField = new JTextField();
        workoutField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(workoutField);
        pane.add(inputPanel);

        emptyLabel = new JLabel();
        inputPanel.add(emptyLabel);

        enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        inputPanel.add(enterButton);

        inputData imp = new inputData();
        enterButton.addActionListener(imp);

        messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(1, 1));
        messagePanel.setBounds(10, 570, 780, 50);

        messageLabel = new JLabel("Enter your data here at the end of the day to add it to your profile.", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        messagePanel.add(messageLabel);
        pane.add(messagePanel);
    }

    public class inputData implements ActionListener {
        public void actionPerformed(ActionEvent inp) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2", "test2");
                PreparedStatement st2 = conn.prepareStatement("INSERT INTO UserFitnessData(UserID,date,calories,steps,distance,standing,exercise,workout) " +
                        "VALUES(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE calories=?,steps=?,distance=?,standing=?,exercise=?,workout=?");
                st2.setInt(1, id);
                st2.setDate(2, Date.valueOf(LocalDate.now()));
                st2.setInt(3, Integer.parseInt(calorieField.getText()));
                st2.setInt(4, Integer.parseInt(stepsField.getText()));
                st2.setDouble(5, Double.parseDouble(distanceField.getText()));
                st2.setInt(6, Integer.parseInt(standingField.getText()));
                st2.setInt(7, Integer.parseInt(exerciseField.getText()));
                st2.setInt(8, Integer.parseInt(workoutField.getText()));
                st2.setInt(9,Integer.parseInt(calorieField.getText()));
                st2.setInt(10,Integer.parseInt(stepsField.getText()));
                st2.setDouble(11,Double.parseDouble(distanceField.getText()));
                st2.setInt(12,Integer.parseInt(standingField.getText()));
                st2.setInt(13,Integer.parseInt(exerciseField.getText()));
                st2.setInt(14,Integer.parseInt(workoutField.getText()));
                st2.execute();
                messageLabel.setText("Thank you, great job with your activity today!");

            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                messageLabel.setText("<html><center>Sorry the data you have entered is invalid. Please check you have entered a value for each statistic and" +
                        " that the values are correct</center></html>");
            }
        }
    }

    public class backHome implements ActionListener {
        public void actionPerformed(ActionEvent bc) {
            new HomePage(pane, id);
        }
    }
}
