package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
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



    private Parent createPage() {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(500, 500);
        vbox.setPadding(new Insets(110, 70,70,70));
        //vbox.setBackground(new Background(new BackgroundImage()));
        //vbox.setSpacing(10);

        GridPane grid = new GridPane();
        grid.setId("grid");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(40,20,40,20));

        Text header = new Text("ZipCloudBank");
        header.setId("header");
        Label logInTxt1 = new Label("Account ID: ");
        Label logInTxt2 = new Label("PIN number: ");
        TextField field = new TextField();
        PasswordField field2 = new PasswordField();
        field.setPrefColumnCount(15);
        field2.setPrefColumnCount(15);

        Button loginBT= new Button("Log in");
        HBox BTBox = new HBox(10);
        BTBox.setAlignment(Pos.BOTTOM_RIGHT);
        Text oops = new Text();
        oops.setId("warning");
        BTBox.getChildren().add(oops);
        BTBox.getChildren().add(loginBT);

        loginBT.setOnAction(e -> {
           //vbox.getChildren().clear();
           oops.setText("Oops message!");
        });


        grid.add(header, 0,0,2,1);
        grid.add(logInTxt1,0,1);
        grid.add(field,1,1);
        grid.add(logInTxt2,0,2);
        grid.add(field2,1,2);
        grid.add(BTBox, 1,3);
        vbox.getChildren().addAll(grid);

        return vbox;
    }



    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createPage());
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
