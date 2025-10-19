import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class FriendDataPage extends JFrame {
    Container pane;
    int id,dayNum,friendId,avgCalories,avgSteps,avgStanding,avgExercise,avgWorkouts,trendCalories,
            trendSteps,trendStanding,trendExercise,trendWorkout,bestCalories,bestSteps,bestStanding,
            bestExercise,bestWorkout;
    double avgDistance,trendDistance,bestDistance;
    JPanel topPanel,mainPanel,avgPanel,secondPanel,trendsPanel,bestsPanel;
    JButton backButton;
    JLabel titleLabel,avgLabel,calorieLabel,stepsLabel,distanceLabel,standingLabel,exerciseLabel,
            workoutsLabel,trendsLabel,calorieTrend,stepsTrend,distanceTrend,standingTrend,
            exerciseTrend,workoutTrend,bestsLabel,bestCalorieLabel,bestStepsLabel,bestDistanceLabel,
            bestStandingLabel,bestExerciseLabel,bestWorkoutLabel;
    String friend;

    public FriendDataPage(Container pane,int id,int friendId,String friend){
        this.id = id;
        this.friendId = friendId;
        this.pane = pane;
        this.friend = friend;
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

        titleLabel = new JLabel(friend);
        titleLabel.setFont(new Font("Serif",Font.BOLD,50));
        titleLabel.setBounds(300,10,300,100);
        topPanel.add(titleLabel);
        pane.add(topPanel);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2,10,10));
        mainPanel.setBounds(25,170,750,400);

        avgPanel = new JPanel();
        avgPanel.setLayout(new GridLayout(7,1));
        avgPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        avgLabel = new JLabel(" weekly averages are",SwingConstants.CENTER);
        avgLabel.setFont(new Font("Tahoma",Font.BOLD,18));
        avgPanel.add(avgLabel);

        calorieLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        calorieLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        avgPanel.add(calorieLabel);

        stepsLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        stepsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        avgPanel.add(stepsLabel);

        distanceLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        distanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        avgPanel.add(distanceLabel);

        standingLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        standingLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        avgPanel.add(standingLabel);

        exerciseLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        exerciseLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        avgPanel.add(exerciseLabel);

        workoutsLabel = new JLabel("No data for this week",SwingConstants.CENTER);
        workoutsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        getAverages();
        avgPanel.add(workoutsLabel);
        mainPanel.add(avgPanel);

        secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(2,1,10,10));

        trendsPanel = new JPanel();
        trendsPanel.setLayout(new GridLayout(7,1));
        trendsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        trendsLabel = new JLabel(" Trends", SwingConstants.CENTER);
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
        calculateTrends();
        secondPanel.add(trendsPanel);

        bestsPanel = new JPanel();
        bestsPanel.setLayout(new GridLayout(7,1));
        bestsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        bestsLabel = new JLabel(" Personal bests", SwingConstants.CENTER);
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
        secondPanel.add(bestsPanel);
        mainPanel.add(secondPanel);
        pane.add(mainPanel);
    }

    public class back implements ActionListener{
        public void actionPerformed(ActionEvent bc){
            new friendsPage(pane,id);
        }
    }

    public void getAverages(){
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
        LocalDate startDate = currentDate.minusDays(dayNum - 1);

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB","test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT * FROM UserAverages WHERE date=? AND UserID=?");
            st.setDate(1,Date.valueOf(startDate));
            st.setInt(2,friendId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                avgCalories = rs.getInt("avgCalories");
                avgSteps = rs.getInt("avgSteps");
                avgDistance = rs.getDouble("avgDistance");
                avgStanding = rs.getInt("avgStanding");
                avgExercise = rs.getInt("avgExercise");
                avgWorkouts = rs.getInt("avgWorkout");
            }
            calorieLabel.setText(avgCalories + " calories burnt a day");
            stepsLabel.setText(avgSteps + " steps a day");
            distanceLabel.setText(avgDistance + " km a day");
            standingLabel.setText(avgStanding + " minutes standing a day");
            exerciseLabel.setText(avgExercise + " minutes exercising a day");
            workoutsLabel.setText(avgWorkouts + " workouts a day");

        } catch(SQLException sqlException){
            sqlException.printStackTrace();
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
            PreparedStatement st = conn.prepareStatement("SELECT avgCalories,avgSteps,avgDistance,avgStanding,avgExercise," +
                    "avgWorkout FROM UserAverages WHERE date = ? AND UserID = ?");
            st.setDate(1,Date.valueOf(LocalDate.now().minusDays(dayNum + 6)));
            st.setInt(2,friendId);
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

    public void calculateBests(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FitnessDB", "test2","test2");
            PreparedStatement st = conn.prepareStatement("SELECT MAX(calories) AS max_calories ,MAX(steps) AS max_steps," +
                    "MAX(distance) AS max_distance,MAX(standing) AS max_standing,MAX(exercise) AS max_exercise,MAX(workout) AS " +
                    "max_workout FROM UserFitnessData WHERE UserID=?");
            st.setInt(1,friendId);
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
}
