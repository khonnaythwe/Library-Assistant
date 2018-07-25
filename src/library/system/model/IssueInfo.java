/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.model;

import java.sql.Date;

/**
 *
 * @author Zin Min Htun
 */
public class IssueInfo {
    private int book_id;
    private int member_id;
    private Date issueDate;
    private int renewCount;

    public IssueInfo(int book_id, int member_id) {
        this.book_id = book_id;
        this.member_id = member_id;
    }

    public IssueInfo(int book_id, int member_id, Date issueDate, int renewCount) {
        this.book_id = book_id;
        this.member_id = member_id;
        this.issueDate = issueDate;
        this.renewCount = renewCount;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
    
}
