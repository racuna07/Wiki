package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.impl;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.WikiDao;
import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;


import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class WikiDaoImpl implements WikiDao {

    private Connection connection;

    public WikiDaoImpl() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/wiki";
            connection = DriverManager.getConnection(url, "root", "LaboratorioBases");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);

        }

    }

    @Override
    public WikiEntry findById(int id) {
        WikiEntry wikiEntry = null;
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select p.page_id, p.page_title, convert(t.old_text using utf8) as text from page p, text t, revision r where p.page_id = "+id+ " and p.page_latest = r.rev_id and r.rev_text_id = t.old_id;");
            if (rs.next() != false) {
                rs.first();
                wikiEntry = buildListResult(rs).get(0);
            }else {
                System.out.println("Ningún resultado coincide su búsqueda.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wikiEntry;
    }

    @Override
    public List<WikiEntry> findByTitle(String title) {
        List<WikiEntry> wikiEntryList = null;
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select p.page_id, p.page_title, convert(t.old_text using utf8) as text from page p, text t, revision r where p.page_title like '%"+title+"%' and p.page_latest = r.rev_id and r.rev_text_id = t.old_id;");
            if (rs.next() != false) {
                rs.first();
                wikiEntryList = buildListResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wikiEntryList;
    }


    private List<WikiEntry> buildListResult(ResultSet rs) throws SQLException {
        LinkedList<WikiEntry> result = new LinkedList<>();
        do{
            int id = rs.getInt("p.page_id");
            String title = rs.getString("p.page_title");
            String text = rs.getString("text");
            WikiEntry wikiEntry = new WikiEntry(id,title,text);
            result.add(wikiEntry);

        }while (rs.next());
        return result;
    }
}
