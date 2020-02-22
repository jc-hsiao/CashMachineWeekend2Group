package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());

    private Parent createContent() {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        TextArea areaInfo = new TextArea();

        Button btnSubmit = new Button("Set Account ID");
        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(field.getText());
            cashMachine.login(id);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.withdraw(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });

        FlowPane flowpane = new FlowPane();

        flowpane.getChildren().add(btnSubmit);
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(btnExit);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }


    private TextField field = new TextField();
    private TextField field2 = new TextField();

    private Parent createPage() {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        vbox.setPadding(new Insets(30, 60, 60, 60));
        vbox.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setSpacing(10);


        Label header = new Label("-ATM APP-");
        header.setId("header");

        Label logInTxt1 = new Label("Account ID: ");
        Label logInTxt2 = new Label("PIN number: ");

        FlowPane div1 = new FlowPane();
        FlowPane div2 = new FlowPane();

        div1.getChildren().add(logInTxt1);
        div1.getChildren().add(field);
        div2.getChildren().add(logInTxt2);
        div2.getChildren().add(field2);

        Button btnLogin= new Button("Log in");
        btnLogin.setOnAction(e -> {
            vbox.getChildren().clear();
        });

        vbox.getChildren().addAll(header,div1,div2,btnLogin);


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
