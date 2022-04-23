package POC;
import java.util.ArrayList;
import java.util.List;
public class Test {
    public static void main(String[] args) throws Exception{

        long start1 = System.nanoTime();

        List<StoreName> storeNames = new ArrayList<>();
        storeNames.add(StoreName.coop);
        storeNames.add(StoreName.maxima);
        storeNames.add(StoreName.prisma);
        storeNames.add(StoreName.rimi);
        storeNames.add(StoreName.selver);

        ArrayList<String> products = new ArrayList<>();
        products.add("makaron");
        products.add("sai");
        products.add("piim");


        ArrayList<Thread> listOfWorkers = new ArrayList<>();
        int freeProcessors = Runtime.getRuntime().availableProcessors();

        for (String product : products) {
            for (StoreName storeName : storeNames) {
                if (listOfWorkers.size() > freeProcessors) {
                    for (Thread worker : listOfWorkers) {
                        worker.join();
                        listOfWorkers.remove(worker);
                    }
                }
                if (listOfWorkers.size() <= freeProcessors) {
                    Thread worker = new Thread(new Worker(storeName, product));
                    listOfWorkers.add(worker);
                    worker.start();
                }
            }
        }
        for (Thread worker : listOfWorkers) {
            worker.join();
            listOfWorkers.remove(worker);
        }


        long end = System.nanoTime();
        System.out.println(end - start1);
    }
}
