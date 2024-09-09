package controller;

import db.InMemoryDB;
import db.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import util.Navigation;
import util.Routes;

import java.io.IOException;
import java.io.InputStream;

public class RegisterFormController {
    public AnchorPane pneRegisterForm;
    public TextField txtNIC;
    public Label lblNICStatus;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtAddress;
    public Button btnRegister;

    public void setDisableCmp(boolean disable){
        txtFirstName.setDisable(disable);
        txtLastName.setDisable(disable);
        txtAddress.setDisable(disable);
        btnRegister.setDisable(disable);
    }

    public void initialize(){

        Platform.runLater(txtNIC::requestFocus);

        txtNIC.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldText, String currectText) {
                setDisableCmp(true);
                if(currectText.isBlank()){
                    lblNICStatus.setText("Please enter valid NIC number to proceed");
                    lblNICStatus.setTextFill(Color.BLACK);
                }else {
                    if(isValidNIC(currectText)){
                        lblNICStatus.setText("Valid NIC ✔");
                        lblNICStatus.setTextFill(Color.GREEN);
                        setDisableCmp(false);
                    } else{
                        lblNICStatus.setText("Invalid NIC ✖");
                        lblNICStatus.setTextFill(Color.RED);
                    }
                }
            }
        });
    }

    public static boolean isValidNIC(String input){
        if(input.length()!=10)return false;
        if(!(input.endsWith("v") || input.endsWith("V"))) return false;
        if(!input.substring(0,9).matches("\\d+"))return false;
        return true;
    }
    public void lblLoginHereOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        String nic = txtNIC.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        if(firstName.isBlank()){
            new Alert(Alert.AlertType.ERROR,"First Name can not be empty").showAndWait();
            txtFirstName.requestFocus();
            return;
        } else if (!isName(firstName)) {
            new Alert(Alert.AlertType.ERROR,"First name is invalid. Please enter valid first name").showAndWait();
            txtFirstName.requestFocus();
            return;
        } else if (!isName(lastName)) {
            new Alert(Alert.AlertType.ERROR,"Last name is invalid. Please enter valid last name").showAndWait();
            txtLastName.requestFocus();
            return;
        } else if (address.trim().length()<3) {
            new Alert(Alert.AlertType.ERROR,"address is invalid. Please enter valid address").showAndWait();
            txtAddress.requestFocus();
            return;
        }

        boolean result =InMemoryDB.registerUser(new User(nic,firstName,lastName,address, 16));

        if(result){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully registered...You will be redirected to the login screen");
            InputStream resourceAsStream = this.getClass().getResourceAsStream("/image/tick.png");
            Image img = new Image(resourceAsStream);
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(48);
            imgView.setFitHeight(48);
            alert.setGraphic(imgView);
            alert.setHeaderText("Registered");
            alert.setTitle("Congratulations");
            alert.getDialogPane().setMinWidth(500);
            alert.getDialogPane().setMinHeight(170);

            alert.show();

            lblLoginHereOnMouseClicked(null);
        }else{
            new Alert(Alert.AlertType.ERROR,"NIC is already registered. Please double check your NIC").showAndWait();
            txtNIC.requestFocus();
        }


    }

    public boolean isName(String input){   // we use public due to assert. but if we does not test this method, we can use private
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (! Character.isLetter(aChar) && aChar!=' ') {
                return false;
            }
        }
        return true;
    }
}
