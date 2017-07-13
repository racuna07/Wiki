package cr.ac.ucr.ecci.ci1310.wiki.tests;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl.WikiServiceImpl;
import javax.swing.JOptionPane;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class Tester {

    private WikiService wikiService;
    private WikiService wikiServiceCache;

    public Tester(){
        int i = 1;
        wikiService = new WikiServiceImpl();
        wikiServiceCache = new WikiServiceImpl(i);
    }

    public double idCalc(int a){
        wikiServiceCache.findById(a); // Result should be now in the respective cache

        long t1 = System.nanoTime();
        wikiService.findById(a);
        long t2 = System.nanoTime();

        long ta = System.nanoTime();
        wikiServiceCache.findById(a);
        long tb = System.nanoTime();

        t1 = t2 - t1;
        ta = tb - ta;
        return  (t1 - ta/ t1)*100;
    }

    public void idTest(){
        System.out.println("----Starting id test----");
        int a = Integer.parseInt( JOptionPane.showInputDialog("Please insert the desired page id") );
        double result = this.idCalc(a);
        System.out.println("Usage of cache imposed a time improvement of "+result+"%.");
    }

    public double titleCalc(String title){
        wikiServiceCache.findByTitle(title); //Result is now in the respective cache

        long t1 = System.nanoTime();
        wikiService.findByTitle(title);
        long t2 = System.nanoTime();

        long ta = System.nanoTime();
        wikiServiceCache.findByTitle(title);
        long tb = System.nanoTime();

        t1 = t2 - t1;
        ta = tb - ta;
        return  (t1 - ta/ t1)*100;
    }

    public void titleTest(){
        System.out.println("----Starting title test----");
        String title = JOptionPane.showInputDialog("Please insert the desired title to look for.");
        double imp = this.titleCalc(title);
        System.out.println("Usage of cache imposed a time improvement of "+imp+"%.");
    }

    public void randomIdTest(){
        System.out.println("----Random Id test----");
        long sum = 0;
        int iterations = 100;
        for (int i = 0; i < iterations; i++) {
            int id = (int) (100000 * Math.random());
            sum += this.idCalc(id);
        }
        System.out.println("Average time improvement upon cache usage is "+sum/iterations+"%.");
    }



    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.idTest();
    }


}
