package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao;

import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;

import java.util.List;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public interface WikiDao {
    /**
     * Searches for the WikiEntry corresponding to the id received as parameter.
     * @param id WikiEntry id.
     * @return Resulting WikiEntry
     */
    WikiEntry findById(int id);

    /**
     * Searches WikiEntries corresponding to the title received as parameter.
     * @param title WikiEntry title.
     * @return Resulting list of WikiEntries.
     */
    List<WikiEntry> findByTitle(String title);
}
