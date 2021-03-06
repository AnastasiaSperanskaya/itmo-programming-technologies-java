package com.javatunes.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class ItemDAOMain {
    public static void main(String[] args) throws SQLException {
        MusicItem mi;
        Connection conn = null;

        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/JavaTunesDB");

        ItemDAO itemDAO = new ItemDAO(conn);

        mi = itemDAO.searchById(1L);
        System.out.println(mi.toString());

        //mi = itemDAO.searchById(100L);
        //System.out.println(mi.toString());

        Collection<MusicItem> cmi = itemDAO.searchByKeyword("Ray");
        System.out.println(cmi.toString());

        cmi = itemDAO.searchByKeyword("of");
        System.out.println(cmi.toString());

        MusicItem mi1 = new MusicItem(1L, "AUSLANDER", "RAMMSTEIN", new Date(119, 9, 2),
                BigDecimal.valueOf(140.0), BigDecimal.valueOf(120.0));
        itemDAO.create(mi1);

        cmi = itemDAO.searchByKeyword("AUSLANDER");
        System.out.println(cmi.toString());

        itemDAO.close();

        ItemDAO itemDAO1 = new ItemDAO(conn);

        ItemDAOThread thread1 = new ItemDAOThread(itemDAO,1,2);
        ItemDAOThread thread2 = new ItemDAOThread(itemDAO1, 2,1);
        thread1.start();
        itemDAO.printAll();
        System.out.println();
        thread2.start();
        itemDAO1.printAll();

        itemDAO.close();
        itemDAO1.close();
    }
}