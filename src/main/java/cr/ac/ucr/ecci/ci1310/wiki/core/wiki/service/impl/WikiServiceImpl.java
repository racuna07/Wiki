package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl;

import cr.ac.ucr.ecci.ci1310.cache.*;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.WikiDao;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.impl.WikiDaoImpl;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;

import java.util.List;

/**
 * Class implementation.
 */
public class WikiServiceImpl implements WikiService {
     private boolean usesCache; //Whether the cache is going to be used or not.
     private WikiDao wikiDao; //Wiki Dao object, who retrieves data from the database when needed.
     private Cache<Integer,WikiEntry> idCache; //A cache associated with the IDs.
     private Cache<String,List<WikiEntry>> titleCache; //A cache associated with the page´s bodies.

     /**
      * Class constructor.
      * @param cache: Designates the cache to be used.
      */
     public WikiServiceImpl(int cache){
          //Hardwired Dao
          this.wikiDao = new WikiDaoImpl();
          this.usesCache = true;
          switch (cache){
               case 1:
                    idCache = new RandomCache<>("idCache",100);
                    titleCache = new RandomCache<>("titleCache",100);
                    break;
               case 2:
                    idCache = new LRUCache<>("idCache",100);
                    titleCache = new LRUCache<>("titleCache",100);
                    break;
               case 3:
                    idCache = new FIFOCache<>("idCache",100);
                    titleCache = new FIFOCache<>("titleCache",100);
                    break;
               case 4:
                    idCache = new LIFOCache<>("idCache",100);
                    titleCache = new LIFOCache<>("titleCache",100);
                    break;

          }
     }

     /**
      * Class constructor. No cache used in this option.
      */
     public WikiServiceImpl(){
          this.wikiDao = new WikiDaoImpl();
          this.usesCache = false;
     }

     /**
      * Searches for the WikiEntry corresponding to the id received as parameter.
      * @param id WikiEntry id.
      * @return Resulting WikiEntry
      */
     @Override
     public WikiEntry findById(int id) {
          WikiEntry result= null;
          if(usesCache){
               result = idCache.get(id);
          }
          if(result == null) {
               //Cache Miss
               result = wikiDao.findById(id);
               if(usesCache && result != null)
                    idCache.put(id,result);

          }
          return result;
     }

     /**
      * Searches WikiEntries corresponding to the title received as parameter.
      * @param title WikiEntry title.
      * @return Resulting list of WikiEntries.
      */
     @Override
     public List<WikiEntry> findByTitle(String title) {
          List<WikiEntry> result = null;
          if(usesCache){
               result = titleCache.get(title);
          }
          if(result == null){
               //Cache Miss
               result = wikiDao.findByTitle(title);
               if(usesCache && result != null)
                    titleCache.put(title,result);
          }
          return result;
     }

     /**
      * Returns a list of 1000 Id's currently present in the database.
      * For debugging purposes.
      * @return Array containing 1000 Id's present in the database.
      */
     @Override
     public int[] getDataBaseIDs() {
          return wikiDao.getDataBaseIDs();
     }

}
