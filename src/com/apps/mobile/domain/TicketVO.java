package com.apps.mobile.domain;

/**
 * Created by IntelliJ IDEA.
 * User: hejun
 * Date: 11-12-29
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public class TicketVO {

    private UserAccount account;
    private Long ticket;

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }
}
