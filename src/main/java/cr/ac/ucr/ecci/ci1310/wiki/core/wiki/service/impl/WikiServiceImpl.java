package cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl;

import cr.ac.ucr.ecci.ci1310.wiki.Cache;
import cr.ac.ucr.ecci.ci1310.wiki.CacheImpl;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.WikiDao;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.dao.impl.WikiDaoImpl;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.model.WikiEntry;

import java.util.Collections;
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
                    break;
               case 2:
                    break;
               case 3:
                    break;
               case 4:
                    break;
               default:
                    idCache = new CacheImpl();
                    titleCache = new CacheImpl();
          }
     }

     public WikiServiceImpl(){
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
