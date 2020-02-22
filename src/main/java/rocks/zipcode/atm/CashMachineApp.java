package rocks.zipcode.atm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

//    private Parent createContent() {
//        VBox vbox = new VBox(10);
//        vbox.setPrefSize(600, 600);
//
//        TextArea areaInfo = new TextArea();
//
//        Button btnSubmit = new Button("Set Account ID");
//        btnSubmit.setOnAction(e -> {
//            int id = Integer.parseInt(field.getText());
//            cashMachine.login(id);
//
//            areaInfo.setText(cashMachine.toString());
//        });
//
//        Button btnDeposit = new Button("Deposit");
//        btnDeposit.setOnAction(e -> {
//            int amount = Integer.parseInt(field.getText());
//            cashMachine.deposit(amount);
//
//            areaInfo.setText(cashMachine.toString());
//        });
//
//        Button btnWithdraw = new Button("Withdraw");
//        btnWithdraw.setOnAction(e -> {
//            int amount = Integer.parseInt(field.getText());
//            cashMachine.withdraw(amount);
//
//            areaInfo.setText(cashMachine.toString());
//        });
//
//        Button btnExit = new Button("Exit");
//        btnExit.setOnAction(e -> {
//            cashMachine.exit();
//
//            areaInfo.setText(cashMachine.toString());
//        });
//
//        FlowPane flowpane = new FlowPane();
//
//        flowpane.getChildren().add(btnSubmit);
//        flowpane.getChildren().add(btnDeposit);
//        flowpane.getChildren().add(btnWithdraw);
//        flowpane.getChildren().add(btnExit);
//        vbox.getChildren().addAll(field, flowpane, areaInfo);
//        return vbox;
//    }


    VBox vbox = new VBox(10);
    GridPane loginGrid = new GridPane();
    GridPane mainGrid = new GridPane();

    private void setUpUI(){
        vbox.setPrefSize(500, 500);
        vbox.setPadding(new Insets(70, 50,70,50));

        setUpLoginGrid();
        setUpMainGrid();

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
            vbox.getChildren().clear();
            vbox.getChildren().add(mainGrid);
            //oops.setText("Oops message!");
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
        moneyStage.setTitle("Please insert Money");
        VBox moneyVBox = new VBox();
        moneyVBox.setId("moneyVBox");
        TextField moneyField = new TextField();
        moneyField.setId("moneyField");
        moneyVBox.getChildren().add(moneyField);
        Scene moneyScene = new Scene(moneyVBox);
        moneyScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        //Handles clicks of the money buttons
        withdrawBT.setOnAction(e -> {
            if (!popup.isShowing()){
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();
        });
        depositBT.setOnAction(e -> {
            //oops2.setText("Oops message! asj lasjdl aksjd a!");
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
