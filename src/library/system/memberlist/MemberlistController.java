/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.memberlist;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.MemberDAO;
import library.system.model.Member;
import library.system.updatemember.UpdatememberController;

/**
 * FXML Controller class
 *
 * @author Zin Min Htun
 */
public class MemberlistController implements Initializable {

    @FXML
    private TableView<Member> memberTable;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;

    private MemberDAO memberDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        memberDao = new MemberDAO();
        initColumn();
        loadTableData();
    }

    private void initColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @FXML
    private void loadMemberEditView(ActionEvent event) throws IOException {
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

        if (selectedMember == null) {
            System.out.println("Please select member you want to update.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/system/updatemember/updatemember.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        UpdatememberController control = loader.getController();
        control.InitData(selectedMember);
        
        stage.initOwner(memberTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        
        loadTableData();
    }

    @FXML
    private void deleteMember(ActionEvent event) {

        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            System.out.println("Please select member you want to delete.");
            return;
        }
        try {
            memberDao.deleteMembers(selectedMember.getId());
            memberTable.getItems().remove(selectedMember);
        } catch (SQLException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadTableData() {

        try {
            ObservableList<Member> members = memberDao.getMembers();
            memberTable.getItems().setAll(members);
        } catch (SQLException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
