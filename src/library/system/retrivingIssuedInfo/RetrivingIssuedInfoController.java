/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.retrivingIssuedInfo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.system.booklist.BooklistController;
import library.system.dao.IssueDAO;
import library.system.model.IssueInfo;

/**
 * FXML Controller class
 *
 * @author Zin Min Htun
 */
public class RetrivingIssuedInfoController implements Initializable {

   
    @FXML
    private TableView<IssueInfo> issueTable;
    @FXML
    private TableColumn<IssueInfo, Integer> bookIdColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> memberIdColumn;
    @FXML
    private TableColumn<IssueInfo, Date> issuedDateColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> renewCountColumn;
    private IssueDAO issueDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        issueDao = new IssueDAO();
        initColumn();
        loadTableData();
    } 
    
    private void initColumn() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        renewCountColumn.setCellValueFactory(new PropertyValueFactory<>("renewCount"));
    }
    
    private void loadTableData(){
        try {
            ObservableList<IssueInfo> list = issueDao.getIssueInfos();
            issueTable.getItems().setAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
