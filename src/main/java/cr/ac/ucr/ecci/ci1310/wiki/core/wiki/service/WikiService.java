package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service;

import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;

import java.util.List;

public interface WikiService {

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

    /**
     * Returns a list of 1000 Id's currently present in the database.
     * For debugging purposes.
     * @return Array containing 1000 Id's present in the database.
     */
    int[] getDataBaseIDs();



}
