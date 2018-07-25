/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.config;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;

import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import library.system.model.DbConfig;
import library.system.util.DbConfigLoader;

/**
 * FXML Controller class
 *
 * @author Zin Min Htun
 */
public class ConfigviewController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private TextField userNameField;
    
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private PasswordField passwordField;
    @FXML
    private JFXButton cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DbConfig dbConfig = DbConfigLoader.loadDbConfig();

        if (dbConfig == null) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, 3306);
            portSpinner.setValueFactory(valueFactory);
        } else {
            hostField.setText(dbConfig.getHost());
            userNameField.setText(dbConfig.getUser());
            passwordField.setText(dbConfig.getPassword());

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, dbConfig.getPort());
            portSpinner.setValueFactory(valueFactory);
        }

//       
    }

    @FXML
    private void saveDbConfig(ActionEvent event) {

        String host = hostField.getText();
        String port = portSpinner.getValue().toString();
        String user = userNameField.getText();
        String password = passwordField.getText();

        DbConfig dbConfig = new DbConfig(host, Integer.parseInt(port), user, password);
        DbConfigLoader.saveDBConfig(dbConfig);

        Stage currentStage = (Stage) hostField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) hostField.getScene().getWindow();
        currentStage.close();
    }

}
