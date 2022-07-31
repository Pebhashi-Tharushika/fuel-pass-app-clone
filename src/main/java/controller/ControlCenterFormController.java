package controller;

import db.InMemoryDB;
import db.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Optional;

public class ControlCenterFormController {

    public AnchorPane pneControlCenter;
    public TableView<User> tblUser;
    public TextField txtSearch;
    public Spinner<Integer> txtQuota;
    public Button btnUpdate;
    public Button btnRemove;
    public TableColumn colAddress;

    public void initialize(){

        Platform.runLater(pneControlCenter::requestFocus);

        txtQuota.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,16));


        /*pneControlCenter.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number prev, Number current) {
                if(prev.doubleValue()==0.0)return;
                double diff = current.doubleValue() - prev.doubleValue();
                double prefWidth = colAddress.getWidth() + diff;
                if(prefWidth<177) prefWidth =177;
                colAddress.setPrefWidth(prefWidth);
            }
        });*/

        tblUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblUser.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("quota"));
        tblUser.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblUser.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblUser.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));

        /*ObservableList<User> olUsers = tblUser.getItems();
        for (User user : InMemoryDB.getUserDatabase()) {
            olUsers.add(user);
        }*/

        ObservableList<User> olUsers = FXCollections.observableArrayList(InMemoryDB.getUserDatabase());
        tblUser.setItems(olUsers);

        tblUser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String prev, String current) {
                if(prev.equals(current))return;
                ArrayList<User> foundUsers = InMemoryDB.findUsers(current);
                ObservableList<User> olFoundUsers = FXCollections.observableArrayList(foundUsers);
                tblUser.setItems(olFoundUsers);
            }
        });

        btnRemove.setDisable(true);
        txtQuota.setDisable(true);
        btnUpdate.setDisable(true);
        tblUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User prev, User current) {
                btnRemove.setDisable(tblUser.getSelectionModel().getSelectedItems().isEmpty());
                txtQuota.setDisable(btnRemove.isDisable());
                btnUpdate.setDisable(btnRemove.isDisable());

            }
        });

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        ObservableList<User> selectedUsers = tblUser.getSelectionModel().getSelectedItems();
        for (User selectedUser : selectedUsers) {
            selectedUser.setQuota(txtQuota.getValue());
        }
        tblUser.refresh();
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Optional<ButtonType> optionalButtonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete", ButtonType.YES, ButtonType.NO).showAndWait();
        if(optionalButtonType.get()==ButtonType.NO)return;
        ObservableList<User> selectedUsers = tblUser.getSelectionModel().getSelectedItems();
        for (User selectedUser : selectedUsers) {
            InMemoryDB.removeUser(selectedUser.getNic());
        }
        tblUser.getItems().removeAll(selectedUsers);
        tblUser.getSelectionModel().clearSelection();
    }
}
