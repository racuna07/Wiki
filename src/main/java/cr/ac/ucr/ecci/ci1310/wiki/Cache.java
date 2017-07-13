package cr.ac.ucr.ecci.ci1310.wiki;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public interface Cache<K,V> {
    String getName();
    V get(K var1);
    void  put(K var1, V var2);
    void evict(K var1);
    void clear();
}
