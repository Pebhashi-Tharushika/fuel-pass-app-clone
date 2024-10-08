package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Navigation {
    private static AnchorPane pneContainer;

    public static void init(AnchorPane pneContainer){
        Navigation.pneContainer = pneContainer;
    }

    public static Object navigate(Routes route) throws IOException {
        URL resource;
        pneContainer.getChildren().clear();
        Stage window = (Stage)pneContainer.getScene().getWindow();
        switch (route){
            case WELCOME:
                resource = Navigation.class.getResource("/view/WelcomeForm.fxml");
                window.setTitle("National Fuel Pass App - Welcome");
                break;
            case REGISTRATION:
                resource = Navigation.class.getResource("/view/RegisterForm.fxml");
                window.setTitle("National Fuel Pass App - Registration");
                break;
            case LOGIN:
                resource = Navigation.class.getResource("/view/LoginForm.fxml");
                window.setTitle("National Fuel Pass App - Login");
                break;
            case ADMIN_LOGIN:
                resource = Navigation.class.getResource("/view/AdminLoginForm.fxml");
                window.setTitle("National Fuel Pass App - Admin Login");
                break;
            case DASHBOARD:
                resource = Navigation.class.getResource("/view/UserDashboard.fxml");
                window.setTitle("National Fuel Pass App - Dashboard");
                break;
            default:
                resource = Navigation.class.getResource("/view/ControlCenterForm.fxml");
                window.setTitle("National Fuel Pass App - Control Center");
                break;
        }
        //Parent container = FXMLLoader.load(resource);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent container = fxmlLoader.load();
        pneContainer.getChildren().add(container);
        AnchorPane.setLeftAnchor(container,0.0);
        AnchorPane.setRightAnchor(container,0.0);
        AnchorPane.setTopAnchor(container,0.0);
        AnchorPane.setBottomAnchor(container,0.0);

        return fxmlLoader.getController();
    }
}
