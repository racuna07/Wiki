package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.impl;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.WikiDao;
import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;


import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class implementation. Defines the Interface´s abstract methods.
 */
public class WikiDaoImpl implements WikiDao {

    private Connection connection; //A Connection object used to connect to the MySQL DBMS.

    /**
     * Class constructor.
     */
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

    /**
     * Searches for the WikiEntry corresponding to the id received as parameter.
     * @param id WikiEntry id.
     * @return Resulting WikiEntry
     */
    @Override
    public WikiEntry findById(int id) {
        WikiEntry wikiEntry = null;
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select p.page_id, p.page_title, convert(t.old_text using utf8) as text from page p, text t, revision r where p.page_id = "+id+ " and p.page_latest = r.rev_id and r.rev_text_id = t.old_id;");
            if (rs.next()) {
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

    /**
     * Searches WikiEntries corresponding to the title received as parameter.
     * @param title WikiEntry title.
     * @return Resulting list of WikiEntries.
     */
    @Override
    public List<WikiEntry> findByTitle(String title) {
        List<WikiEntry> wikiEntryList = null;
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select p.page_id, p.page_title, convert(t.old_text using utf8) as text from page p, text t, revision r where p.page_title like '%"+title+"%' and p.page_latest = r.rev_id and r.rev_text_id = t.old_id;");
            if (rs.next()) {
                rs.first();
                wikiEntryList = buildListResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wikiEntryList;
    }

    /**
     * Returns a list of 1000 Id's currently present in the database.
     * For debugging purposes.
     * @return Array containing 1000 Id's present in the database.
     */
    @Override
    public int[] getDataBaseIDs() {
        int[] result = new int[1000];
        int i = 0;
        ResultSet rs;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("select page_id from page order by page_id limit 1000;");
            rs.first();
            do{
                int pageId = rs.getInt("page_id");
               result[i++] = pageId;
            }while(rs.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @param rs: The set of tuples that satisfy the SQL previously query made.
     * @return A list with the entry objects from the Result Set.
     * @throws SQLException: If some error while interacting with the Result Set manifests.
     */
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
