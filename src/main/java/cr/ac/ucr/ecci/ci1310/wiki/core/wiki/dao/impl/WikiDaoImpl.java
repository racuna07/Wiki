package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.impl;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.WikiDao;
import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;

import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class WikiDaoImpl implements WikiDao {

    private Connection connection;

    public WikiDaoImpl(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/atomite";
            connection = DriverManager.getConnection(url,"root","LaboratorioBases");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public WikiEntry findById(int id) {
        WikiEntry wikiEntry = null;
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rs != null){

        }
        return wikiEntry;
    }

    @Override
    public List<WikiEntry> findByTitle(String title) {
        WikiEntry wikiEntry = null;
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rs != null){

        }
        return Collections.singletonList(wikiEntry);
    }
}
