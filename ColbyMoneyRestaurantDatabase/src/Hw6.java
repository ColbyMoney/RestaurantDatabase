import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;
import java.util.Calendar;
import java.sql.*;

import java.util.ArrayList;

import static java.util.Collections.shuffle;

/** entire program is an identical copy of Restaurant.java except with the Hw6.java name */
public class Hw6 extends Application {
    VBox menuPane = new VBox(-60); //food pictures
    VBox orderPane = new VBox(150); //fields for number of items
    VBox descPane = new VBox(160); //description of items
    VBox labelPane = new VBox(0); //title at top

    //select the daily special
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        // Load the JDBC driver
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded");

        // Connect to a database
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/sys" , "root", "root");
        System.out.println("Database connected");

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a statement
        ResultSet resultSet = statement.executeQuery(
                "SELECT price FROM restaurantmenu WHERE item_id = 3");
        System.out.println(resultSet);*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        // Load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded");

        // Connect to a database
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/sys" , "root", "root");
        System.out.println("Database connected");

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a statement
        ResultSet menu = statement.executeQuery(
                "SELECT * FROM restaurantmenu;");

        menu.next();
        String item1name = menu.getString("name");
        double clifBarPrice = menu.getFloat("price");
        menu.next();
        double cheezItPrice = menu.getFloat("price");
        String item2name = menu.getString("name");
        menu.next();
        double peanutButterPretzelPrice = menu.getFloat("price");
        String item3name = menu.getString("name");

        // Close the connection
        //connection.close();
        System.out.println(date);

        //TextField dailySpecial = new TextField();
        TextField cheezIt = new TextField();
        TextField clifBar = new TextField();
        TextField peanutButterPretzel = new TextField();

        //build labelPane
        labelPane.setAlignment(Pos.CENTER);
        labelPane.getChildren().add(new Label("Colby's Crazy Cuisine"));

        //build up menuPane
        menuPane.setAlignment(Pos.CENTER);
        //menuPane.getChildren().add(new ImageView("image/menuItemDailySpecial/" + ((calendar.DAY_OF_WEEK % 7)+1) + ".png"));
        menuPane.getChildren().add(new ImageView("image/menuItem/clifBar.png"));
        menuPane.getChildren().add(new ImageView("image/menuItem/cheezIt.png"));
        menuPane.getChildren().add(new ImageView("image/menuItem/peanutButterPretzel.png"));

        //build up orderPane
        orderPane.setAlignment(Pos.CENTER);
        //orderPane.getChildren().add(dailySpecial);
        orderPane.getChildren().add(cheezIt);
        orderPane.getChildren().add(clifBar);
        orderPane.getChildren().add(peanutButterPretzel);

        //build up descPane
        descPane.setAlignment(Pos.CENTER);
        //descPane.getChildren().add(new Text("Daily Special $2.00"));
        descPane.getChildren().add(new Text(item1name + " " + clifBarPrice));
        descPane.getChildren().add(new Text(item2name + " " + cheezItPrice));
        descPane.getChildren().add(new Text(item3name + " " + peanutButterPretzelPrice));

        BorderPane displayPane = new BorderPane();

        Button btn = new Button("Place Order");

        displayPane.setTop(labelPane);
        displayPane.setLeft(menuPane);
        displayPane.setRight(orderPane);
        displayPane.setCenter(descPane);
        displayPane.setBottom(btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage stage = new Stage();
                VBox ordersPane = new VBox(10);

                //read inputs from the text fields
                //int dailySpecials = Integer.parseInt(dailySpecial.getText());
                int cheezIts = Integer.parseInt(cheezIt.getText());
                int clifBars = Integer.parseInt(clifBar.getText());
                int peanutButterPretzels = Integer.parseInt(peanutButterPretzel.getText());
                double total = (cheezIts*cheezItPrice)+(clifBars*clifBarPrice)+(peanutButterPretzels*peanutButterPretzelPrice);

                //build up ordersPane
                //ordersPane.getChildren().add(new Text(dailySpecials + " Daily Specials"));
                ordersPane.getChildren().add(new Text(cheezIts + " Cheez-Its"));
                ordersPane.getChildren().add(new Text(clifBars + " Clif Bars"));
                ordersPane.getChildren().add(new Text(peanutButterPretzels + " Peanut Butter Pretzels"));
                ordersPane.getChildren().add(new Text("Order Total: $" + total));

                //create the receipt
                BorderPane receipt = new BorderPane();
                receipt.setTop(ordersPane);

                Scene scene = new Scene(receipt);
                stage.setTitle("ColbyCrazyCuisineMenu");
                stage.setScene(scene);
                stage.show();
                try {
                    statement.executeUpdate(
                            "INSERT INTO restaurantorders(order_id, item1_qty, item2_qty, item3_qty)" +
                                    "VALUES (null,"+cheezIts+","+clifBars+","+peanutButterPretzels+");");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Create the scene and add to stage
        Scene scene = new Scene(displayPane);
        primaryStage.setTitle("ColbyCrazyCuisineMenu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }//end Start
}//end Restaurant

