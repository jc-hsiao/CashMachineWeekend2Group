package rocks.zipcode.atm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rocks.zipcode.atm.bank.PremiumAccount;
import sun.security.util.Password;

import javax.swing.*;
import java.text.DecimalFormat;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());

    VBox vbox = new VBox(10);
    GridPane loginGrid = new GridPane();
    GridPane mainGrid = new GridPane();
    GridPane signUpGrid = new GridPane();
    Label balanceNum = new Label("--");
    Text greetTxt = new Text("Hi, user");
    int tooLow = 10;

    //========= major set up =========//
    private void setUpUI(){
        vbox.setPrefSize(500, 500);
        vbox.setPadding(new Insets(70, 50,70,50));
        setUpLoginGrid();
        setUpMainGrid();
        setupSignUpGrid();
        vbox.getChildren().add(loginGrid);
    }

    //========= codes for the setting up login panel =========//
    private void setUpLoginGrid(){
        loginGrid.setId("grid");
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(20);
        loginGrid.setPadding(new Insets(40,20,40,20));

        Text header = new Text("ZipCloudBank");
        Label loginLabel1 = new Label("Account ID: ");
        Label loginLabel2 = new Label("PIN number: ");
        TextField idField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginBT= new Button("Log in");
        Button newAccountBT= new Button("Sign up");
        HBox HorizontalBox = new HBox(10);
        Text oops = new Text();

        header.setId("header");
        loginBT.setId("loginBT");
        newAccountBT.setId("loginBT");
        oops.setId("warning");

        idField.setPrefColumnCount(15);
        passwordField.setPrefColumnCount(15);
        HorizontalBox.setAlignment(Pos.BOTTOM_RIGHT);

        // :: login button action events :: //
        loginBT.setOnAction(e -> {
            cashMachine.login(idField.getText(), passwordField.getText());

            if (cashMachine.getErrorMessage() != null){
                oops.setText(cashMachine.getErrorMessage());
            } else {
                vbox.getChildren().clear();
                vbox.getChildren().add(mainGrid);
                //cashMachine.setCurrentUser(Integer.parseInt(field.getText()));
                //userName = cashMachine.getBank().accounts.get(cashMachine.getCurrentUser()).getAccountData().getName();
                //balance = cashMachine.getBank().accounts.get(cashMachine.getCurrentUser()).getAccountData().getBalance();
                greetTxt.setText("Hello! "+cashMachine.getCurrentUser().getName());
                balanceNum.setText("$" + new DecimalFormat("#.00").format(cashMachine.getCurrentUser().getBalance()));
            }

            //check if the balance of this user is kinda low, if it is, set the balance text to red
            if(cashMachine.getCurrentUser().getBalance() > tooLow)
                balanceNum.setId("balanceNum");
            else
                balanceNum.setId("balanceNum-danger");
        });
        newAccountBT.setOnAction((e -> {
            vbox.getChildren().clear();
            vbox.getChildren().add(signUpGrid);
        }));

        HorizontalBox.getChildren().add(oops);
        HorizontalBox.getChildren().add(newAccountBT);
        HorizontalBox.getChildren().add(loginBT);
        loginGrid.add(header, 0,0,2,1);
        loginGrid.add(loginLabel1,0,1);
        loginGrid.add(idField,1,1);
        loginGrid.add(loginLabel2,0,2);
        loginGrid.add(passwordField,1,2);
        loginGrid.add(HorizontalBox, 0,3,2,1);

        //this is for grid layout debugging
        //loginGrid.setGridLinesVisible(true);
    }


    //========= codes for the setting up main panel =========//
    private void setUpMainGrid(){
        mainGrid.setId("grid");
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(5);
        mainGrid.setPadding(new Insets(40,20,30,20));

        Text balanceTxt = new Text("Your current balance is: ");
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
        Button doneInsert = new Button("Deposit");
        Button doneWithdraw = new Button("Withdraw");

        greetTxt.setId("greetTxt");
        balanceTxt.setId("balanceTxt");
        withdrawBT.setId("wBT");
        depositBT.setId("dBT");
        logOutBT.setId("outBT");
        oops2.setId("warning");

        balanceNum.setPrefSize(300,50);
        balanceNum.setAlignment(Pos.CENTER_RIGHT);
        withdrawBT.setPrefSize(90,50);
        depositBT.setPrefSize(90,50);
        logOutHBox.setAlignment(Pos.CENTER_RIGHT);
        logOutHBox.getChildren().add(logOutBT);
        warningHBox.setAlignment(Pos.CENTER_RIGHT);
        warningHBox.setPadding(new Insets(0,5,0,0));
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.setPadding(new Insets(10,0, 10,0));

        //========== the cool pop up window ==========//
        Popup popup = new Popup();
        moneyVBox.setId("moneyVBox");
        moneyField.setId("moneyField");
        moneyField.setPrefWidth(220);
        doneInsert.setPrefSize(220,30);
        doneWithdraw.setPrefSize(220,30);
        Scene moneyScene = new Scene(moneyVBox);
        moneyScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        //===========================================//


        // :: action events for logout button :: //
        logOutBT.setOnAction(e -> {
            oops2.setText("");                  //reset error message when the current user log out
            cashMachine.exit();                 //clean up user data just in case
            vbox.getChildren().clear();         //wash out everything on the screen
            vbox.getChildren().add(loginGrid);  //bring the login panel back
        });

        // :: action events for withdraw button which creates pop-up :: //
        withdrawBT.setOnAction(e -> {
            oops2.setText("");
            if (!popup.isShowing()){
                moneyField.clear();
                moneyVBox.getChildren().clear();
                moneyVBox.getChildren().addAll(new ImageView(withdrawImg),moneyField,doneWithdraw);
                moneyStage.setTitle("Please Enter Amount");
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();//not sure what this do
        });

        // :: action events for withdraw button that will perform actual withdraw :: //
        doneWithdraw.setOnAction(e -> {
            //balance -= Double.parseDouble(moneyField.getText());
            //balanceNum.setText("$"+cashMachine.getCurrentUser());
            //cashMachine.withdraw(Double.parseDouble(moneyField.getText()));
            cashMachine.withdraw(moneyField.getText());
            balanceNum.setText("$"+ new DecimalFormat("#.00").format(cashMachine.getCurrentUser().getBalance()));

            if(cashMachine.getCurrentUser().getBalance() <= tooLow)
                balanceNum.setId("balanceNum-danger"); //this will make balance red if it's low

            //print special overdraft message "Overdraft warning!" if cashMachine has any special message
            if(cashMachine.getErrorMessage() != null)
                oops2.setText(cashMachine.getErrorMessage());
            else
                oops2.setText(cashMachine.getSpecialMessage());

            moneyStage.close();
        });

        // :: deposit action events which will bring a pop up :: //
        depositBT.setOnAction(e -> {
            oops2.setText("");
            if (!popup.isShowing()){
                moneyField.clear();
                moneyVBox.getChildren().clear();
                moneyVBox.getChildren().addAll(new ImageView(insertImg),moneyField,doneInsert);
                moneyStage.setTitle("Please Insert Money");
                moneyStage.setScene(moneyScene);
                moneyStage.show();
                popup.show(moneyStage);
            } else
                popup.hide();
        });

        // :: action events for deposit button that will perform actual deposit :: //
        doneInsert.setOnAction(e -> {
            //balance += Double.parseDouble(moneyField.getText());
            //cashMachine.getBank().accounts.get(cashMachine.getCurrentUser()).getAccountData().setBalance(balance);
            //cashMachine.getBank().deposit(cashMachine.getBank().accounts.get(cashMachine.getCurrentUser()).getAccountData(), Double.parseDouble(moneyField.getText()));
            cashMachine.deposit(moneyField.getText());
            balanceNum.setText("$"+new DecimalFormat("#.00").format(cashMachine.getCurrentUser().getBalance()));
            if(cashMachine.getCurrentUser().getBalance() > tooLow)
                balanceNum.setId("balanceNum");
            oops2.setText(cashMachine.getErrorMessage());
            moneyStage.close();
        });

        warningHBox.getChildren().add(oops2);
        buttonHBox.getChildren().add(withdrawBT);
        buttonHBox.getChildren().add(depositBT);
        mainGrid.add(greetTxt,0,0);
        mainGrid.add(balanceTxt,0,2);
        mainGrid.add(balanceNum,0,4);
        mainGrid.add(buttonHBox,0,5);
        mainGrid.add(warningHBox,0,6);
        mainGrid.add(logOutHBox,0,7);

        //this is for grid layout debugging
        //mainGrid.setGridLinesVisible(true);
    }

    public void setupSignUpGrid(){
        signUpGrid.setId("grid");
        signUpGrid.setAlignment(Pos.CENTER);
        signUpGrid.setHgap(10);
        signUpGrid.setVgap(5);
        signUpGrid.setPadding(new Insets(40,20,30,20));

        Text singUpInstructions = new Text("Enter new account information below:");
        Button finishBT = new Button("Finish");
        Button cancelBT = new Button("Cancel");
        HBox buttonHBox = new HBox(10);
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        PasswordField setPinField = new PasswordField();

        singUpInstructions.setId("signUpInstructions");
        finishBT.setId("loginBT");
        cancelBT.setId("loginBT");

        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.setPadding(new Insets(10,0, 10,0));

        // :: action events for logout button :: //
        cancelBT.setOnAction(e -> {
            cashMachine.exit();                 //clean up user data just in case
            vbox.getChildren().clear();         //wash out everything on the screen
            vbox.getChildren().add(loginGrid);  //bring the login panel back
        });

        // :: action events for withdraw button which creates pop-up :: //
        finishBT.setOnAction(e -> {
            cashMachine.createAccount( firstNameField.getText() + " " + lastNameField.getText(), emailField.getText(),  setPinField.getText());
            vbox.getChildren().clear();         //wash out everything on the screen
            vbox.getChildren().add(loginGrid);  //bring the login panel back
            });

        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");
        Label emailLabel = new Label("Email Address");
        Label setPinLabel = new Label("New PIN");
        buttonHBox.getChildren().add(cancelBT);
        buttonHBox.getChildren().add(finishBT);

        signUpGrid.add(firstNameLabel,0,2);
        signUpGrid.add(lastNameLabel,0,3);
        signUpGrid.add(emailLabel,0,4);
        signUpGrid.add(setPinLabel,0,5);
        signUpGrid.add(singUpInstructions,1,1);
        signUpGrid.add(firstNameField,1,2);
        signUpGrid.add(lastNameField,1,3);
        signUpGrid.add(emailField,1,4);
        signUpGrid.add(setPinField,1,5);
        signUpGrid.add(buttonHBox,1,6);

        //this is for grid layout debugging
        //signUpGrid.setGridLinesVisible(true);
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
