package cr.ac.ucr.ecci.ci1310.wiki.tests;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl.WikiServiceImpl;
import javax.swing.JOptionPane;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class Tester {

    WikiService noCacheService;
    WikiService randomService;
    WikiService queueService;
    WikiService stackService;
    WikiService lruService;

    public Tester(){
        noCacheService = new WikiServiceImpl();
        randomService = new WikiServiceImpl(1);
        queueService = new WikiServiceImpl(3);
        stackService = new WikiServiceImpl(4);
        lruService = new WikiServiceImpl(2);
        this.test(true);
    }

    public double idCalc(int a){
        lruService.findById(a); // Result should be now in the respective cache

        long t1 = System.nanoTime();
        noCacheService.findById(a);
        long t2 = System.nanoTime();

        long ta = System.nanoTime();
        lruService.findById(a);
        long tb = System.nanoTime();

        t1 = t2 - t1;
        ta = tb - ta;
        return  ( (t1 - ta)/ t1)*100;
    }

    public void idTest(){
        System.out.println("----Starting id test----");
        int a = Integer.parseInt( JOptionPane.showInputDialog("Please insert the desired page id") );
        double result = this.idCalc(a);
        System.out.println("Usage of cache imposed a time improvement of "+result+"%.");
    }

    public double titleCalc(String title){
        lruService.findByTitle(title); //Result is now in the respective cache

        long t1 = System.nanoTime();
        noCacheService.findByTitle(title);
        long t2 = System.nanoTime();

        long ta = System.nanoTime();
        lruService.findByTitle(title);
        long tb = System.nanoTime();

        t1 = t2 - t1;
        ta = tb - ta;
        return  ( (t1 - ta)/ t1)*100;
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
            int id = (int) (1000 * Math.random());
            sum += this.idCalc(id);
        }
        System.out.println("Average time improvement upon cache usage is "+sum/iterations+"%.");
    }

    public void test(boolean pattern){
        int range = 1000;
        long[] times = new long[5];
        int[] randomNums = this.getRandomNumbers(range);
        if (pattern) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < randomNums.length; j++) {
                    this.updateTimes(times, randomNums, j);
                }
            }
        }
        else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < randomNums.length; j++) {
                    this.updateTimes(times, randomNums, j);
                }
                randomNums = this.getRandomNumbers(range);
            }
        }

        long t = times[0];
        long t1 = ( (t - times[1]) / t )*100;
        System.out.println("Average usage of random cache imposed a time improvement of "+t1+"%.");

        long t2 = ( (t - times[2]) / t )*100;
        System.out.println("Average usage of random cache imposed a time improvement of "+t2+"%.");

        long t3 = ( (t - times[3]) / t )*100;
        System.out.println("Average usage of random cache imposed a time improvement of "+t3+"%.");

        long t4 = ( (t - times[4]) / t )*100;
        System.out.println("Average usage of random cache imposed a time improvement of "+t4+"%.");
    }

    private void updateTimes(long[] times, int[] randomNums, int j){
        long t1 = System.nanoTime();
        noCacheService.findById(randomNums[j]);
        long t2 = System.nanoTime();
        times[0] += t2-t1;
        t1 = System.nanoTime();
        randomService.findById(randomNums[j]);
        t2 = System.nanoTime();
        times[1] += t2-t1;
        t1 = System.nanoTime();
        queueService.findById(randomNums[j]);
        t2 = System.nanoTime();
        times[2] += t2-t1;
        t1 = System.nanoTime();
        stackService.findById(randomNums[j]);
        t2 = System.nanoTime();
        times[3] += t2-t1;
        t1 = System.nanoTime();
        lruService.findById(randomNums[j]);
        t2 = System.nanoTime();
        times[4] += t2-t1;
    }

    private int[] getRandomNumbers(int n){
        int[] numbers = new int[100];
        for (int i = 0; i < 100; i++) {
            numbers[i] = (int) (Math.random()*n);
        }
        return numbers;
    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.test(true);
    }
}
