package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl;

import cr.ac.ucr.ecci.ci1310.cache.*;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.WikiDao;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.impl.WikiDaoImpl;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;

import java.util.List;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class WikiServiceImpl implements WikiService {
     private boolean usesCache;
     private WikiDao wikiDao;
     private Cache<Integer,WikiEntry> idCache;
     private Cache<String,List<WikiEntry>> titleCache;

     public WikiServiceImpl(int cache){
          //Hardwired Dao
          this.wikiDao = new WikiDaoImpl();
          this.usesCache = true;
          switch (cache){
               case 1:
                    idCache = new RandomCache<Integer, WikiEntry>("idCache",100);
                    titleCache = new RandomCache<String, List<WikiEntry>>("titleCache",100);
                    break;
               case 2:
                    idCache = new LRUCache<Integer, WikiEntry>("idCache",100);
                    titleCache = new LRUCache<String, List<WikiEntry>>("titleCache",100);
                    break;
               case 3:
                    idCache = new FIFOCache<Integer, WikiEntry>("idCache",100);
                    titleCache = new FIFOCache<String, List<WikiEntry>>("titleCache",100);
                    break;
               case 4:
                    idCache = new LIFOCache<Integer, WikiEntry>("idCache",100);
                    titleCache = new LIFOCache<String, List<WikiEntry>>("titleCache",100);
                    break;

          }
     }

     public WikiServiceImpl(){
          this.wikiDao = new WikiDaoImpl();
          this.usesCache = false;
     }

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
}
