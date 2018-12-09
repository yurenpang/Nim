import java.util.ArrayList;
import java.util.HashMap;

public class NimSum {
    private HashMap<Integer, Integer> sizeMap;
    private Heap[] heaps;

    public NimSum(Heap[] heaps){
        this.sizeMap = new HashMap<>();
        this.heaps = heaps;

        updateMap();
    }

    public void updateMap() {
        for (Heap h : heaps) {
            sizeMap.put(h.getId(), h.beanSizeInAHeap());
        }

        System.out.println("New map: " + sizeMap);
    }

    private int calculateNimSum() {
        int sum = 0;
        for (Integer i : sizeMap.keySet()){
            sum = sum ^ sizeMap.get(i);
        }
        return sum;
    }

    public void makeNextMove() {
        int sum = calculateNimSum();
        System.out.println("The nim sum: " + sum);
        if(sum == 0) {
            for(Heap h : heaps) {
                if(h.beanSizeInAHeap() > 0){
                    h.PPositionRemove();
                    h.setBeanSize(h.beanSizeInAHeap() ^ sum);
                    updateMap();
                    break;
                }
            }
        } else {
            int power = 1;

            while (power <= sum) {
                power = power << 1;
            }
            power = power >> 1;
            System.out.println("The power is:" + power);

            for (Heap h : heaps) {
                int beanSize = h.beanSizeInAHeap();
                System.out.println("The bean size is: " + beanSize);
                if (beanSize >= power && (beanSize ^ sum) <= beanSize) {
                    System.out.println("The bean^sum is: " + (beanSize ^ sum));
                    h.setBeanSize((beanSize ^ sum));
                    h.removeeBeansAI();
                    updateMap();
                    break;
                }
            }
        }
    }
}
