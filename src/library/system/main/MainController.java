/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.dao.IssueDAO;
import library.system.dao.MemberDAO;
import library.system.model.Book;
import library.system.model.IssueInfo;
import library.system.model.Member;
import library.system.util.ShowMessage;

/**
 *
 * @author Sithu
 */
public class MainController implements Initializable {

    @FXML
    private Button homeBtn;
    @FXML
    private Button addBookBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homeView;
    @FXML
    private JFXButton bookListBtn;
    @FXML
    private JFXButton memberBtn;
    @FXML
    private JFXButton memberListBtn;
    @FXML
    private JFXTextField searchBookField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;
    @FXML
    private Text availableText;

    private BookDAO bookDao;
    private MemberDAO memberDao;
    private IssueDAO issueDao;

    @FXML
    private JFXTextField searchMemberField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField issuedBookSearch;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text IssuedDateText;
    @FXML
    private Text RenewCountText;
    @FXML
    private JFXButton returnBtn;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton IssueInfoBtn;
    @FXML
    private MenuItem dbConfigItem;
    
    private final String defaultStyle = "-fx-border-width:0 0 0 5px;-fx-border-color:#123456";
    private final String activeStyle = "-fx-border-width:0 0 0 5px;-fx-border-color:#ffffff";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeActive();
        bookDao = new BookDAO();
        memberDao = new MemberDAO();
        issueDao = new IssueDAO();
    }

    @FXML
    private void loadHomeView(ActionEvent event) {
        homeActive();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homeView);
    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {
        
        bookActive();
        loadView("/library/system/addbook/addbook.fxml");

    }

    @FXML
    private void loadBookListView(ActionEvent event) throws IOException {
        
        bookListActive();
        loadView("/library/system/booklist/booklist.fxml");

    }

    @FXML
    private void loadAddMemberView(ActionEvent event) throws IOException {
        
        memberActive();
        loadView("/library/system/addmember/addmember.fxml");

    }

    @FXML
    private void loadMemberListView(ActionEvent event) throws IOException {
        
        memberListActive();
        loadView("/library/system/memberlist/memberlist.fxml");

    }
    
     @FXML
    private void loadIssueInfoView(ActionEvent event) throws IOException {
        
        issueInfoActive();
        loadView("/library/system/retrivingIssuedInfo/retrivingIssuedInfo.fxml");
        
    }

    @FXML
    private void searchBookInfo(ActionEvent event) {

        clearBookCache();

        String idStr = searchBookField.getText();
        if (idStr == null) {
            System.out.println("Please enter book id.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Book book = bookDao.getBook(id);
            if (book != null) {

                String title = book.getTitle();
                String author = book.getAuthor();
                String publisher = book.getPublisher();
                boolean available = book.isAvailable();

                titleText.setText(title);
                authorText.setText(author);
                publisherText.setText(publisher);

                if (available) {
                    availableText.setText("Available");

                } else {
                    availableText.setText("Not Available");
                }

            } else {
                System.out.println("Cannot find the book.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Input.");

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {

        clearMemberCache();

        String idStr = searchMemberField.getText();
        if (idStr == null) {
            System.out.println("Please enter member id first.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Member member = memberDao.getMember(id);
            if (member != null) {

                String name = member.getName();
                String mobile = member.getMobile();
                String address = member.getAddress();

                nameText.setText(name);
                mobileText.setText(mobile);
                addressText.setText(address);

            } else {
                System.out.println("Cannot find the member.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Input.");

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearBookCache() {
        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
        availableText.setText("-");
    }

    private void clearMemberCache() {
        nameText.setText("-");
        mobileText.setText("-");
        addressText.setText("-");
    }

    @FXML
    private void issueBook(ActionEvent event) {

        String bookIdStr = searchBookField.getText();
        String memberIdStr = searchMemberField.getText();

        if (bookIdStr.isEmpty() || memberIdStr.isEmpty()) {
            System.out.println("Please fill require ids!");
            return;
        }

        int book_id;
        int member_id;

        try {
            book_id = Integer.parseInt(searchBookField.getText());
            member_id = Integer.parseInt(searchMemberField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        try {
            Book book = bookDao.getBook(book_id);
            if (book.isAvailable()) {
                issueDao.saveIssueInfo(new IssueInfo(book_id, member_id));
                bookDao.updateAvailable(book_id, false);
                System.out.println("Done!");
            } else {
                System.out.println("This book is already issued.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void searchIssuedBook(ActionEvent event) {

        clearIssuedBookCache();

        String bookIdStr = issuedBookSearch.getText();
        if (bookIdStr.isEmpty()) {
            System.out.println("Pleae Fill in Issued Book's ID.");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input.");
            return;
        }

        try {
            IssueInfo issueInfo = IssueDAO.getIssueInfo(bookId);
            if (issueInfo != null) {

                Book book = bookDao.getBook(issueInfo.getBook_id());
                Member member = memberDao.getMember(issueInfo.getMember_id());

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisherText.setText(book.getPublisher());

                mNameText.setText(member.getName());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());

                IssuedDateText.setText("Issued Date: " + issueInfo.getIssueDate());
                RenewCountText.setText("Renew Count: " + issueInfo.getRenewCount());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Cannot find the issued book for this ID.");
                alert.show();
            }
        } catch (SQLException ex) {
            System.out.println("This book isn't in the borrow list.");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void returnBook(ActionEvent event) throws SQLException {

        String bookIdStr = issuedBookSearch.getText();
        if (bookIdStr.isEmpty()) {
            ShowMessage.showErrorMessage("Input Error!", "Fill The Book's ID First!");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            ShowMessage.showErrorMessage("Invalid Input!", "Your Input is INVALID!");
            return;
        }

        IssueInfo issueInfo = issueDao.getIssueInfo(bookId);
        if (issueInfo != null) {

            Optional<ButtonType> option = ShowMessage.showComfirmMessage("Confirmation", "Are You Sure You Want To Return This Book?");
            if (option.get() == ButtonType.OK) {
                issueDao.deleteIssueInfo(bookId);
                bookDao.updateAvailable(bookId, true);
            }

        } else {
            ShowMessage.showErrorMessage("Error", "Can't find this book.");
        }
    }

    @FXML
    private void renewCount(ActionEvent event) {
        String bookIdStr = issuedBookSearch.getText();
        if (bookIdStr.isEmpty()) {
            ShowMessage.showErrorMessage("Input Error!", "Fill The Book's ID First!");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            ShowMessage.showErrorMessage("Invalid Input!", "Your Input is INVALID!");
            return;
        }

        try {
            IssueInfo issueInfo = issueDao.getIssueInfo(bookId);
            if (issueInfo != null) {
//                int renew = issueInfo.getRenewCount()+1;
//                issueDao.updateRenewCount(bookId,renew);
                issueDao.updateRenewCount(bookId);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearIssuedBookCache() {
        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");
        mNameText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");
        IssuedDateText.setText("-");
        RenewCountText.setText("-");
    }

    private void loadView(String url) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(url));

        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void loadDbConfigView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/system/config/configview.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(centerPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void homeActive() {
        homeBtn.setStyle(activeStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssueInfoBtn.setStyle(defaultStyle);
        
    }
    private void bookActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(activeStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssueInfoBtn.setStyle(defaultStyle);
        
    }
    private void bookListActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(activeStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssueInfoBtn.setStyle(defaultStyle);
        
    }
    private void memberActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(activeStyle);
        memberListBtn.setStyle(defaultStyle);
        IssueInfoBtn.setStyle(defaultStyle);
        
    }
    private void memberListActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(activeStyle);
        IssueInfoBtn.setStyle(defaultStyle);
        
    }
    private void issueInfoActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssueInfoBtn.setStyle(activeStyle);
        
    }

   
}
