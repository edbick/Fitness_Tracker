import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProfilePage extends JFrame{
    Container pane;
    JPanel topPanel,profilePanel,firstPanel,userPanel,statsPanel,midProfilePanel,trendsPanel,
            bestsPanel,rightProfilePanel,goalsPanel,awardsPanel,graphPanel;
    JButton homeButton,editNameBt,editUserBt,editPassBt,awardBt;
    JLabel profileLabel,nameLabel,userLabel,passLabel,statsTitle,calorieLabel,stepsLabel,distanceLabel,
            standingLabel,exerciseLabel,workoutsLabel,bestsLabel,bestCalorieLabel,bestStepsLabel,bestDistanceLabel,
            bestStandingLabel,bestExerciseLabel,bestWorkoutLabel,trendsLabel,goalsLabel,awardsLabel,graphLabel,
            calorieTrend,stepsTrend,distanceTrend,standingTrend,exerciseTrend,workoutTrend;
    JTextField userField,nameField,passField;
    String username,password,name;
    int id,dayNum,avgCalories,avgSteps,avgStanding,avgExercise,avgWorkouts,bestCalories,bestSteps,bestStanding,
            bestExercise,bestWorkout,trendCalories,trendSteps,trendStanding,trendExercise,trendWorkout;
    double avgDistance,bestDistance,trendDistance;
    JScrollPane scroller,scrollTrend;

    public ProfilePage(Container pane,int id){
        this.id = id;
        this.pane = pane;
        getUserInfo();

        pane.removeAll();
        pane.repaint();
        pane.setLayout(null);
        pane.revalidate();

        topPanel = new JPanel();
        topPanel.setBounds(0,0,800,160);
        topPanel.setLayout(null);

        homeButton = new JButton("HOME");
        homeButton.setFont(new Font("Calibre", Font.BOLD, 20));
        homeButton.setBounds(10,10,100,100);
        topPanel.add(homeButton);

        backHome bc = new backHome();
        homeButton.addActionListener(bc);

        profileLabel = new JLabel("Your Profile");
        profileLabel.setFont(new Font("Serif", Font.BOLD, 50));
        profileLabel.setBounds(180,10,300,100);
        topPanel.add(profileLabel);

        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(3,3));
        userPanel.setBounds(510,10,270,150);

        nameLabel = new JLabel("Name", SwingConstants.RIGHT);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        userPanel.add(nameLabel);

        nameField = new JTextField(name);
        nameField.setEditable(false);
        userPanel.add(nameField);

        editNameBt = new JButton("Edit");
        userPanel.add(editNameBt);
        editNameBt.setActionCommand("name");

        userLabel = new JLabel("Username", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        userPanel.add(userLabel);

        userField = new JTextField(username);
        userField.setEditable(false);
        userPanel.add(userField);

        editUserBt = new JButton("Edit");
        userPanel.add(editUserBt);
        editUserBt.setActionCommand("user");

        passLabel = new JLabel("Password", SwingConstants.RIGHT);
        passLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        userPanel.add(passLabel);

        passField = new JTextField();
        passField.setEditable(false);
        userPanel.add(passField);

        editPassBt = new JButton("Edit");
        userPanel.add(editPassBt);
        editPassBt.setActionCommand("pass");

        topPanel.add(userPanel);
        pane.add(topPanel);

        editDetails ed = new editDetails();
        editUserBt.addActionListener(ed);
        editNameBt.addActionListener(ed);
        editPassBt.addActionListener(ed);

        profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setPreferredSize(new Dimension(720,780));
        scroller = new JScrollPane(profilePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.getVerticalScrollBar().setPreferredSize(new Dimension(15,400));
        scroller.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        scroller.setSize(750,400);
        scroller.setLocation(25,170);

        firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(1,3,10,10));
        firstPanel.setBounds(10,10,710,375);
        profilePanel.add(firstPanel);

        statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        statsPanel.setLayout(new GridLayout(7,1));

        statsTitle = new JLabel("This week averages are",SwingConstants.CENTER);
        statsTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        statsPanel.add(statsTitle);

        calorieLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        calorieLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        statsPanel.add(calorieLabel);

        stepsLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        stepsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        statsPanel.add(stepsLabel);

        distanceLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        distanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        statsPanel.add(distanceLabel);

        standingLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        standingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        statsPanel.add(standingLabel);

        exerciseLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        exerciseLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        statsPanel.add(exerciseLabel);

        workoutsLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        workoutsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        calculateAverages();
        statsPanel.add(workoutsLabel);
        firstPanel.add(statsPanel);

        midProfilePanel = new JPanel();
        midProfilePanel.setLayout(new GridLayout(2,1,10,10));

        trendsPanel = new JPanel();
        //trendsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        trendsPanel.setLayout(new GridLayout(7,1));
        scrollTrend = new JScrollPane(trendsPanel,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollTrend.getVerticalScrollBar().setPreferredSize(new Dimension(15,400));
        scrollTrend.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        scrollTrend.setSize(750,400);

        trendsLabel = new JLabel("Trends", SwingConstants.CENTER);
        trendsLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        trendsPanel.add(trendsLabel);

        calorieTrend = new JLabel("",SwingConstants.CENTER);
        calorieTrend.setFont(new Font("Tahoma", Font.PLAIN, 17));
        trendsPanel.add(calorieTrend);

        stepsTrend = new JLabel("",SwingConstants.CENTER);
        stepsTrend.setFont(new Font("Tahoma", Font.PLAIN, 17));
        trendsPanel.add(stepsTrend);

        distanceTrend = new JLabel("",SwingConstants.CENTER);
        distanceTrend.setFont(new Font("Tahoma", Font.PLAIN, 17));
        trendsPanel.add(distanceTrend);

        standingTrend = new JLabel("",SwingConstants.CENTER);
        standingTrend.setFont(new Font("Tahoma", Font.PLAIN, 17));
        trendsPanel.add(standingTrend);

        exerciseTrend = new JLabel("",SwingConstants.CENTER);
        exerciseTrend.setFont(new Font("Tahoma", Font.PLAIN, 17));
        trendsPanel.add(exerciseTrend);

        workoutTrend = new JLabel("",SwingConstants.CENTER);
        workoutTrend.setFont(new Font("Tahoma", Font.PLAIN, 17));
        trendsPanel.add(workoutTrend);
        midProfilePanel.add(scrollTrend);
        calculateTrends();

        bestsPanel = new JPanel();
        bestsPanel.setLayout(new GridLayout(7,1));
        bestsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        bestsLabel = new JLabel("Personal bests", SwingConstants.CENTER);
        bestsLabel.setFont(new Font("Tahoma",Font.BOLD,18));
        bestsPanel.add(bestsLabel);

        bestCalorieLabel = new JLabel("",SwingConstants.CENTER);
        bestCalorieLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bestsPanel.add(bestCalorieLabel);

        bestStepsLabel = new JLabel("",SwingConstants.CENTER);
        bestStepsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bestsPanel.add(bestStepsLabel);

        bestDistanceLabel = new JLabel("",SwingConstants.CENTER);
        bestDistanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bestsPanel.add(bestDistanceLabel);

        bestStandingLabel = new JLabel("",SwingConstants.CENTER);
        bestStandingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bestsPanel.add(bestStandingLabel);

        bestExerciseLabel = new JLabel("",SwingConstants.CENTER);
        bestExerciseLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bestsPanel.add(bestExerciseLabel);

        bestWorkoutLabel = new JLabel("",SwingConstants.CENTER);
        bestWorkoutLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        bestsPanel.add(bestWorkoutLabel);

        calculateBests();
        midProfilePanel.add(bestsPanel);
        firstPanel.add(midProfilePanel);

        rightProfilePanel = new JPanel();
        rightProfilePanel.setLayout(new GridLayout(2,1,10,10));

        goalsPanel = new JPanel();
        goalsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        goalsPanel.setLayout(new GridLayout(4,1));

        goalsLabel = new JLabel("Goals", SwingConstants.CENTER);
        goalsLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        goalsPanel.add(goalsLabel);
        rightProfilePanel.add(goalsPanel);

        awardsPanel = new JPanel();
        awardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        awardsPanel.setLayout(new GridLayout(2,1));

        awardsLabel = new JLabel("Awards", SwingConstants.CENTER);
        awardsLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        awardsPanel.add(awardsLabel);

        awardBt = new JButton("View Your awards here");
        awardsPanel.add(awardBt);

        awardPage ap = new awardPage();
        awardBt.addActionListener(ap);

        rightProfilePanel.add(awardsPanel);
        firstPanel.add(rightProfilePanel);

        graphPanel = new JPanel();
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        graphPanel.setBounds(10,410,710,350);

        graphLabel = new JLabel("Your statistics", SwingConstants.CENTER);
        graphLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        graphPanel.add(graphLabel);
        profilePanel.add(graphPanel);

        pane.add(scroller);
    }

    public class backHome implements ActionListener{
        public void actionPerformed(ActionEvent bc){
            new HomePage(pane,id);
        }
    }

    public class editDetails implements ActionListener{
        public void actionPerformed(ActionEvent ed){
            String btName = ed.getActionCommand();

            switch(btName){
                case "name":
                    nameField.setEditable(true);
                    editNameBt.setText("Done");
                    editNameBt.setActionCommand("nameDone");
                    break;
                case "user":
                    userField.setEditable(true);
                    editUserBt.setText("Done");
                    editUserBt.setActionCommand("userDone");
                    break;
                case "pass":
                    passField.setEditable(true);
                    if(passField.getText().isEmpty()){
                        passField.setText(password);
                    }
                    editPassBt.setText("Done");
                    editPassBt.setActionCommand("passDone");
            }

            switch(btName){
                case "nameDone":
                    nameField.setEditable(false);
                    editNameBt.setText("Edit");
                    editNameBt.setActionCommand("name");
                    name = nameField.getText();
                    changeUserInfo(name,"name");
                    break;
                case "userDone":
                    userField.setEditable(false);
                    editUserBt.setText("Edit");
                    editUserBt.setActionCommand("user");
                    username = userField.getText();
                    changeUserInfo(username,"username");
                    break;
                case "passDone":
                    passField.setEditable(false);
                    editPassBt.setText("Edit");
                    editPassBt.setActionCommand("pass");
                    password = passField.getText();
                    changeUserInfo(password,"password");
                    passField.setText("");
            }
        }
    }

    public void changeUserInfo(String newValue, String type){
        String query = "UPDATE users SET " + type + "=? WHERE id=?";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2", "test2");
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newValue);
            st.setInt(2, id);
            st.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void getUserInfo(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2", "test2");
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE id=?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                name = rs.getString("name");
                username = rs.getString("username");
                password = rs.getString("password");
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void calculateAverages(){
        DayOfWeek dayName = (LocalDate.now()).getDayOfWeek();
        switch(dayName){
            case MONDAY:
                dayNum = 1;
                break;
            case TUESDAY:
                dayNum = 2;
                break;
            case WEDNESDAY:
                dayNum = 3;
                break;
            case THURSDAY:
                dayNum = 4;
                break;
            case FRIDAY:
                dayNum = 5;
                break;
            case SATURDAY:
                dayNum = 6;
                break;
            default:
                dayNum = 7;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(dayNum);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2", "test2");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM userFitnessData WHERE UserID=? AND date>?");
            st1.setInt(1, id);
            st1.setDate(2, Date.valueOf(startDate));
            ResultSet rs = st1.executeQuery();
            while(rs.next()) {
                avgCalories += rs.getInt("calories");
                avgSteps += rs.getInt("steps");
                avgDistance += rs.getDouble("distance");
                avgStanding += rs.getInt("standing");
                avgExercise += rs.getInt("exercise");
                avgWorkouts += rs.getInt("workout");
            }

            avgDistance /= dayNum;
            BigDecimal roundedDistance = BigDecimal.valueOf(avgDistance).setScale(2, RoundingMode.HALF_UP);
            avgDistance = roundedDistance.doubleValue();

            calorieLabel.setText((avgCalories /= dayNum) + " calories burnt a day");
            stepsLabel.setText((avgSteps /= dayNum) + " steps a day");
            distanceLabel.setText((avgDistance) + " km a day");
            standingLabel.setText((avgStanding /= dayNum) + " minutes standing a day");
            exerciseLabel.setText((avgExercise /= dayNum) + " minutes exercising a day");
            workoutsLabel.setText((avgWorkouts /= dayNum) + " workouts a day");

            PreparedStatement st2 = conn.prepareStatement("INSERT INTO UserAverages(UserID,date,avgCalories,avgSteps,avgDistance," +
                    "avgStanding,avgExercise,avgWorkout)" + " VALUES(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE avgCalories=?,avgSteps=?," +
                    "avgDistance=?,avgStanding=?,avgExercise=?,avgWorkout=?");
            st2.setInt(1, id);
            st2.setDate(2, Date.valueOf(currentDate.minusDays(dayNum-1)));
            st2.setInt(3, avgCalories);
            st2.setInt(4,avgSteps);
            st2.setDouble(5, avgDistance);
            st2.setInt(6, avgStanding);
            st2.setInt(7, avgExercise);
            st2.setInt(8, avgWorkouts);
            st2.setInt(9, avgCalories);
            st2.setInt(10,avgSteps);
            st2.setDouble(11, avgDistance);
            st2.setInt(12, avgStanding);
            st2.setInt(13, avgExercise);
            st2.setInt(14, avgWorkouts);
            st2.execute();

        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
    }

    public void calculateBests(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT MAX(calories) AS max_calories ,MAX(steps) AS max_steps,MAX(distance) AS max_distance," +
                    "MAX(standing) AS max_standing,MAX(exercise) AS max_exercise,MAX(workout) AS max_workout FROM UserFitnessData WHERE UserID=?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
            bestCalories = rs.getInt("max_calories");
            bestSteps = rs.getInt("max_steps");
            bestDistance = rs.getDouble("max_distance");
            bestStanding = rs.getInt("max_standing");
            bestExercise = rs.getInt("max_exercise");
            bestWorkout = rs.getInt("max_workout");
            }
            bestCalorieLabel.setText(bestCalories + " Calories");
            bestStepsLabel.setText(bestSteps + " Steps");
            bestDistanceLabel.setText(bestDistance + " KM");
            bestStandingLabel.setText(bestStanding + " Minutes standing");
            bestExerciseLabel.setText(bestExercise + " Minutes exercising");
            bestWorkoutLabel.setText(bestWorkout + " Workouts");

        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
    }

    public class awardPage implements ActionListener{
        public void actionPerformed(ActionEvent ap){
            new AwardsPage(pane,id);
        }
    }

    public String getTrendArrow(int trendNum){
        if(trendNum > 0){
            return "↑";
        } else if(trendNum < 0){
            return "↓";
        } else{
            return "↔";
        }
    }

    public void calculateTrends(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT avgCalories,avgSteps,avgDistance,avgStanding,avgExercise,avgWorkout" +
                    " FROM UserAverages WHERE date = ? AND UserID = ?");
            st.setDate(1,Date.valueOf(LocalDate.now().minusDays(dayNum + 6)));
            st.setInt(2,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                trendCalories = rs.getInt("avgCalories");
                trendSteps = rs.getInt("avgSteps");
                trendDistance = rs.getDouble("avgDistance");
                trendStanding = rs.getInt("avgStanding");
                trendExercise = rs.getInt("avgExercise");
                trendWorkout = rs.getInt("avgWorkout");
            }

            //last week avg minus this week avg - if positive then avg has gone up else gone down
            trendCalories = avgCalories - trendCalories;
            trendSteps = avgSteps - trendSteps;
            trendDistance = avgDistance - trendDistance;
            BigDecimal roundedDistance = BigDecimal.valueOf(trendDistance).setScale(2, RoundingMode.HALF_UP);
            trendDistance = roundedDistance.doubleValue();
            trendStanding = avgStanding - trendStanding;
            trendExercise = avgExercise - trendExercise;
            trendWorkout = avgWorkouts - trendWorkout;

            calorieTrend.setText("Calories burnt: " + getTrendArrow(trendCalories) + " " + trendCalories);
            stepsTrend.setText("Steps walked: " + getTrendArrow(trendSteps) + " " + trendSteps);
            Double distance = new Double(trendDistance);
            distanceTrend.setText("Distance travelled: " + getTrendArrow(distance.intValue()) + " " + trendDistance);
            standingTrend.setText("Standing time:  " + getTrendArrow(trendStanding) + " " + trendStanding);
            exerciseTrend.setText("Exercise time: " + getTrendArrow(trendExercise) + " " + trendExercise);
            workoutTrend.setText("Workout number: " + getTrendArrow(trendWorkout) + " " + trendWorkout);

        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
    }
}
