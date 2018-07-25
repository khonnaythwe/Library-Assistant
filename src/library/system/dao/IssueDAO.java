/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.system.database.Database;
import library.system.model.Book;
import library.system.model.IssueInfo;

/**
 *
 * @author Zin Min Htun
 */
public class IssueDAO {

    public static IssueInfo getIssueInfo(int bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.issue where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);
        ResultSet result = stmt.executeQuery();

        IssueInfo issueInfo = null;

        if (result.next()) {
            int memberId = result.getInt("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");

            issueInfo = new IssueInfo(bookId, memberId, issueDate, renewCount);
        }
        return issueInfo;
    }
    
    public ObservableList<IssueInfo> getIssueInfos() throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        String sql = "select * from lbdb.issue";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        ObservableList<IssueInfo> list = FXCollections.observableArrayList();

        while (result.next()) {
            int bookId = result.getInt("book_id");
            int memberId = result.getInt("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");

           IssueInfo issueInfo = new IssueInfo(bookId, memberId, issueDate, renewCount);
           list.add(issueInfo);
        }

        return list;
    }

    public void saveIssueInfo(IssueInfo issueInfo) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into lbdb.issue (book_id,member_id,issue_date,renew_count) values (?,?,now(),0)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, issueInfo.getBook_id());
        stmt.setInt(2, issueInfo.getMember_id());
        // stmt.setDate(3, issueInfo.getIssueDate());
        //stmt.setInt(4, issueInfo.getRenewCount());

        stmt.execute();
    }

    public void deleteIssueInfo(int bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from lbdb.issue where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);
        stmt.execute();
    }

//    public void updateRenewCount(int bookId, int renew) throws SQLException {
//        Connection conn = Database.getInstance().getConnection();
//        String sql = " update lbdb.issue set renew_count=? where book_id=?";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setInt(1, renew);
//        stmt.setInt(2, bookId);
//        stmt.execute();
//    }

    public void updateRenewCount(int bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = " update lbdb.issue set renew_count=renew_count+1 where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);
        stmt.execute();
    }

}
