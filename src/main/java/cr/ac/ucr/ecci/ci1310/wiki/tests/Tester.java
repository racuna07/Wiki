package cr.ac.ucr.ecci.ci1310.wiki.tests;

import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.WikiService;
import cr.ac.ucr.ecci.ci1310.wiki.core.wiki.service.impl.WikiServiceImpl;

/**
 * A class in charge of testing and evaluating Cache´s performance.
 */
public class Tester {

    private WikiService noCacheService; //A service that uses no cache
    private WikiService randomService; //A service that uses a Random cache
    private WikiService queueService; //A service that uses a Queue cache
    private WikiService stackService; //A service that uses a Stack cache
    private WikiService lruService; //A service that uses a "Last Recently Used" cache
    private String[] servicesNames; //A vector of the names associated to each cache in order.

    /**
     * Class constructor.
     */
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


    /**
     * Principal testing method. Add uses random numbers to randomly select from a set of valid IDs
     * in the database. Then it executes 1000 tests, sums the total time as well as the average
     * comparison advantage of using each cache in comparison with the service that uses no cache,
     * and displays both.
     * @param pattern: Whether or not the test follows a pattern of IDs or it chooses all the IDs
     *               randomly.
     */
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

    /**
     * A method that measures the time taken for each service to perform a query, and then it saves
     * them to an array.
     * @param times: A time vector
     * @param idNums: An array with the random values.
     * @param j: The random value to be used from the array.
     */
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

    /**
     * A method that generates an array of pseudorandom integer values.
     * @param n: Number of values to be generated.
     * @param range: The range of the generated values.
     * @return An array of random numbers.
     */
    private int[] getRandomNumbers(int n, int range){
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
}
