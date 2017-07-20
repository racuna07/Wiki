package cr.ac.ucr.ecci.ci1310.wiki.tests;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl.WikiServiceImpl;
import javax.swing.JOptionPane;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class Tester {

    private WikiService noCacheService;
    private WikiService randomService;
    private WikiService queueService;
    private WikiService stackService;
    private WikiService lruService;
    private String[] servicesNames;

    public Tester(){
        servicesNames = new String[] {"NoCache","RandomCache","FIFOCache","LIFOCache","LRUCache"};
        noCacheService = new WikiServiceImpl();
        randomService = new WikiServiceImpl(1);
        queueService = new WikiServiceImpl(3);
        stackService = new WikiServiceImpl(4);
        lruService = new WikiServiceImpl(2);
        this.test(true);
        this.test(false);
    }



    public void test(boolean pattern){
        long[] times = new long[5];
        int[] randomNums = this.getRandomNumbers(1000,1000);
        int[] idNumbers =  this.noCacheService.getDataBaseIDs();
        if (pattern) {
            //Repeats the first numbers 10 times.
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < idNumbers.length/10; j++) {
                    this.updateTimes(times, idNumbers, j);
                }
            }
        }
        else {
            for (int i = 0; i < idNumbers.length; i++) {
                this.updateTimes(times, idNumbers, randomNums[i]);
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.print("Average query times ");
        if(pattern){
            System.out.println("repeating a pattern of 100 numbers 10 times.");
        }else{
            System.out.println("choosing 1000 numbers randomly.");
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < 5; i++) {
            times[i] /= idNumbers.length;
            double averageQueryTime = times[i]/1000000.0;
            System.out.printf("%s: %f ms.\n", servicesNames[i],averageQueryTime);
        }

        double t = times[0]/1000000.0;
        for (int i = 1; i < 5 ; i++) {
            double averageQueryTime = times[i]/1000000.0;
            double percentageDifference = ((t - averageQueryTime)/t)*100;
            System.out.printf("Usinga a %s resulted in an average query time  improvement of %f%s.\n", servicesNames[i],percentageDifference,"%");
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }

    private void updateTimes(long[] times, int[] idNums, int j){
        long t1 = System.nanoTime();
        noCacheService.findById(idNums[j]);
        long t2 = System.nanoTime();
        times[0] += t2-t1;
        t1 = System.nanoTime();
        randomService.findById(idNums[j]);
        t2 = System.nanoTime();
        times[1] += t2-t1;
        t1 = System.nanoTime();
        queueService.findById(idNums[j]);
        t2 = System.nanoTime();
        times[2] += t2-t1;
        t1 = System.nanoTime();
        stackService.findById(idNums[j]);
        t2 = System.nanoTime();
        times[3] += t2-t1;
        t1 = System.nanoTime();
        lruService.findById(idNums[j]);
        t2 = System.nanoTime();
        times[4] += t2-t1;
    }

    private int[] getRandomNumbers(int n,int range){
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            int number = (int) (Math.random()*n);
            numbers[i] = number;
        }
        return numbers;
    }


    public static void main(String[] args) {
        Tester tester = new Tester();
    }

    //Pruebas de consultas individuales, no se utilizan.
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
}
