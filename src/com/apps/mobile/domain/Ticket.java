package com.apps.mobile.domain;


import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: hejun
 * Date: 11-12-29
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
public class Ticket {

    private LinkedHashMap<Long, UserAccount> ticketMap;

    private Ticket() {

        ticketMap = new LinkedHashMap<Long, UserAccount>();
    }

    private static Ticket ref = new Ticket();

    public static synchronized Long getTicketNumber(UserAccount userAccount) {

        if (userAccount == null) {

            throw new IllegalArgumentException("用户账户不能为空");
        }


        if (ref == null) {
            ref = new Ticket();
        }


        Long ticketNumber = Calendar.getInstance().getTime().getTime();

        ref.ticketMap.put(ticketNumber, userAccount);

        return ticketNumber;

    }

    public static synchronized UserAccount getUserAccount(Long ticketNumber) throws InvalidTicketException {

        if (ticketNumber == null) {

            throw new IllegalArgumentException("ticket 不能为空");

        }

        if (ref.ticketMap.containsKey(ticketNumber)) {

            UserAccount account = ref.ticketMap.get(ticketNumber);

            return account;

        } else {

            throw new InvalidTicketException();
        }
    }

    public static synchronized void destoryTicket(Long ticketNumber) throws InvalidTicketException {

        if (ticketNumber == null) {

            throw new IllegalArgumentException("ticket 不能为空");

        }

        if (ref.ticketMap.containsKey(ticketNumber)) {

            ref.ticketMap.remove(ticketNumber);

        } else {

            throw new InvalidTicketException();

        }
    }

    public static synchronized void checkTicket(Long ticketNumber) throws InvalidTicketException {

        if (ticketNumber == null) {

            throw new IllegalArgumentException("ticket 不能为空");

        }

        if (!ref.ticketMap.containsKey(ticketNumber)) {
            throw new InvalidTicketException();
        }

    }
}
