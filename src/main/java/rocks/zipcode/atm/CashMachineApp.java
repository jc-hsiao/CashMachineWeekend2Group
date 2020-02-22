package rocks.zipcode.atm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());

    VBox vbox = new VBox(10);
    GridPane loginGrid = new GridPane();
    GridPane mainGrid = new GridPane();
    GridPane premGrid = new GridPane();

    private void setUpUI(){
        vbox.setPrefSize(500, 500);
        vbox.setPadding(new Insets(70, 50,70,50));

        setUpLoginGrid();
        setUpMainGrid();
        setUpMainGrid2();

        vbox.getChildren().add(loginGrid);

    }

    private void setUpLoginGrid(){
        loginGrid.setId("grid");
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(20);
        loginGrid.setPadding(new Insets(40,20,40,20));

        Text header = new Text("ZipCloudBank");
        Label logInTxt1 = new Label("Account ID: ");
        Label logInTxt2 = new Label("PIN number: ");
        TextField field = new TextField();
        PasswordField field2 = new PasswordField();
        Button loginBT= new Button("Log in");
        HBox BTBox = new HBox(10);
        Text oops = new Text();

        header.setId("header");
        field.setPrefColumnCount(15);
        field2.setPrefColumnCount(15);

        loginBT.setId("loginBT");
        BTBox.setAlignment(Pos.BOTTOM_RIGHT);
        oops.setId("warning");
        BTBox.getChildren().add(oops);
        BTBox.getChildren().add(loginBT);
        loginBT.setOnAction(e -> {
            cashMachine.login(Integer.parseInt(field.getText()), field2.getText());

            if (cashMachine.getErrorMessage() != null){
                oops.setText("Login failed!");
            } else {
                vbox.getChildren().clear();
                vbox.getChildren().add(mainGrid);
            }

        });

        loginGrid.add(header, 0,0,2,1);
        loginGrid.add(logInTxt1,0,1);
        loginGrid.add(field,1,1);
        loginGrid.add(logInTxt2,0,2);
        loginGrid.add(field2,1,2);
        loginGrid.add(BTBox, 1,3);
    }




    private void setUpMainGrid(){
        mainGrid.setId("grid");
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(5);
        mainGrid.setPadding(new Insets(40,20,40,20));

        Text greetTxt = new Text("Hi, regular user");
        Text balanceTxt = new Text("Your current balance is: ");
        Label balanceNum = new Label("$1000.00");
        Button withdrawBT= new Button("Withdraw");
        Button depositBT= new Button("Deposit");
        Button logOutBT= new Button("Log Out");
        HBox buttonHBox = new HBox(10);
        HBox logOutHBox = new HBox(10);
        HBox warningHBox = new HBox(10);
        Text oops2 = new Text();
        Stage moneyStage = new Stage();
        VBox moneyVBox = new VBox();
        TextField moneyField = new TextField();

        Image insertImg = new Image("insert.gif");
        Image withdrawImg = new Image("withdraw.gif");
        Image badImg = new Image("nope.jpg");

        Button doneInsert = new Button("Deposit");
        Button doneWithdraw = new Button("Withdraw");


        greetTxt.setId("greetTxt");
        balanceTxt.setId("balanceTxt");

        balanceNum.setPrefSize(300,50);
        balanceNum.setAlignment(Pos.CENTER_RIGHT);
        balanceNum.setId("balanceNum");

        withdrawBT.setPrefSize(90,50);
        depositBT.setPrefSize(90,50);

        withdrawBT.setId("wBT");
        depositBT.setId("dBT");
        logOutBT.setId("outBT");
        logOutBT.setOnAction(e -> {
            oops2.setText("");
            vbox.getChildren().clear();
            vbox.getChildren().add(loginGrid);
        });

        buttonHBox.getChildren().add(withdrawBT);
        buttonHBox.getChildren().add(depositBT);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.setPadding(new Insets(10,0, 10,0));
        oops2.setId("warning");

        //pop up
        Popup popup = new Popup();
        moneyVBox.setId("moneyVBox");
        moneyField.setId("moneyField");
        moneyField.setPrefWidth(220);
        doneInsert.setPrefSize(220,30);
        doneWithdraw.setPrefSize(220,30);
        Scene moneyScene = new Scene(moneyVBox);
        moneyScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        withdrawBT.setOnAction(e -> {
            oops2.setText("");
            if (!popup.isShowing()){
                moneyVBox.getChildren().clear();
                moneyStage.setTitle("Please Enter Amount");
                ImageView imageView1 = new ImageView(withdrawImg);
                moneyVBox.getChildren().addAll(imageView1,moneyField,doneWithdraw);
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();
        });
        doneWithdraw.setOnAction(e -> {
            oops2.setText("You don't have enough money!");
            moneyStage.close();

        });


        depositBT.setOnAction(e -> {
            oops2.setText("");
            if (!popup.isShowing()){
                moneyVBox.getChildren().clear();
                moneyStage.setTitle("Please Insert Money");
                ImageView imageView2 = new ImageView(insertImg);
                moneyVBox.getChildren().addAll(imageView2,moneyField,doneInsert);
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();
        });
        doneInsert.setOnAction(e -> {

            //this will close the window
            moneyStage.close();
        });

        logOutHBox.setAlignment(Pos.CENTER_RIGHT);
        logOutHBox.getChildren().add(logOutBT);
        warningHBox.setAlignment(Pos.CENTER_RIGHT);
        warningHBox.setPadding(new Insets(0,5,0,0));
        warningHBox.getChildren().add(oops2);


        mainGrid.add(greetTxt,0,0);
        mainGrid.add(balanceTxt,0,2);
        mainGrid.add(balanceNum,0,4);
        mainGrid.add(buttonHBox,0,5);
        mainGrid.add(warningHBox,0,6);
        mainGrid.add(logOutHBox,0,7);

        //mainGrid.setGridLinesVisible(true);
    }



    private void setUpMainGrid2(){
        premGrid.setId("premGrid");
        premGrid.setAlignment(Pos.CENTER);
        premGrid.setHgap(10);
        premGrid.setVgap(5);
        premGrid.setPadding(new Insets(40,20,40,20));

        Text greetTxt = new Text("Hi, regular user");
        Text balanceTxt = new Text("Your current balance is: ");
        Label balanceNum = new Label("$1000.00");
        Button withdrawBT= new Button("Withdraw");
        Button depositBT= new Button("Deposit");
        Button logOutBT= new Button("Log Out");
        HBox buttonHBox = new HBox(10);
        HBox logOutHBox = new HBox(10);
        HBox warningHBox = new HBox(10);
        Text oops2 = new Text();
        Stage moneyStage = new Stage();
        VBox moneyVBox = new VBox();
        TextField moneyField = new TextField();

        Image insertImg = new Image("insert.gif");
        Image withdrawImg = new Image("withdraw.gif");
        Image badImg = new Image("nope.jpg");

        Button doneInsert = new Button("Deposit");
        Button doneWithdraw = new Button("Withdraw");


        greetTxt.setId("greetTxt");
        balanceTxt.setId("balanceTxt");

        balanceNum.setPrefSize(300,50);
        balanceNum.setAlignment(Pos.CENTER_RIGHT);
        balanceNum.setId("balanceNum");

        withdrawBT.setPrefSize(90,50);
        depositBT.setPrefSize(90,50);

        withdrawBT.setId("wBT");
        depositBT.setId("dBT");
        logOutBT.setId("outBT");
        logOutBT.setOnAction(e -> {
            oops2.setText("");
            vbox.getChildren().clear();
            vbox.getChildren().add(loginGrid);
        });

        buttonHBox.getChildren().add(withdrawBT);
        buttonHBox.getChildren().add(depositBT);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.setPadding(new Insets(10,0, 10,0));
        oops2.setId("warning");

        //pop up
        Popup popup = new Popup();
        moneyVBox.setId("moneyVBox");
        moneyField.setId("moneyField");
        moneyField.setPrefWidth(220);
        doneInsert.setPrefSize(220,30);
        doneWithdraw.setPrefSize(220,30);
        Scene moneyScene = new Scene(moneyVBox);
        moneyScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        withdrawBT.setOnAction(e -> {
            oops2.setText("");
            if (!popup.isShowing()){
                moneyVBox.getChildren().clear();
                moneyStage.setTitle("Please Enter Amount");
                ImageView imageView1 = new ImageView(withdrawImg);
                moneyVBox.getChildren().addAll(imageView1,moneyField,doneWithdraw);
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();
        });
        doneWithdraw.setOnAction(e -> {
            oops2.setText("You don't have enough money!");
            moneyStage.close();

        });


        depositBT.setOnAction(e -> {
            oops2.setText("");
            if (!popup.isShowing()){
                moneyVBox.getChildren().clear();
                moneyStage.setTitle("Please Insert Money");
                ImageView imageView2 = new ImageView(insertImg);
                moneyVBox.getChildren().addAll(imageView2,moneyField,doneInsert);
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();
        });
        doneInsert.setOnAction(e -> {

            //this will close the window
            moneyStage.close();
        });

        logOutHBox.setAlignment(Pos.CENTER_RIGHT);
        logOutHBox.getChildren().add(logOutBT);
        warningHBox.setAlignment(Pos.CENTER_RIGHT);
        warningHBox.setPadding(new Insets(0,5,0,0));
        warningHBox.getChildren().add(oops2);


        premGrid.add(greetTxt,0,0);
        premGrid.add(balanceTxt,0,2);
        premGrid.add(balanceNum,0,4);
        premGrid.add(buttonHBox,0,5);
        premGrid.add(warningHBox,0,6);
        premGrid.add(logOutHBox,0,7);

        //mainGrid.setGridLinesVisible(true);
    }



    @Override
    public void start(Stage stage) throws Exception {
        setUpUI();
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
