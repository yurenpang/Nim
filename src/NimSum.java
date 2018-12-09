import java.util.ArrayList;
import java.util.HashMap;

public class NimSum {
    private HashMap<Heap, ArrayList<Bean>> nimMap;
    private HashMap<Integer, Integer> sizeMap;
    private Heap[] heaps;

    public NimSum(Heap[] heaps){
        this.nimMap = new HashMap<>();
        this.sizeMap = new HashMap<>();
        this.heaps = heaps;

        createMap();
    }

    private void createMap() {
        for (Heap h : heaps) {
            nimMap.put(h, h.getBeansInAHeap());
            sizeMap.put(h.getId(), h.beanSizeInAHeap());
        }

        System.out.println(sizeMap);
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
        if(sum == 0) {
            for(Heap h : heaps) {
                if(h.beanSizeInAHeap() > 0){
                    h.PPositionRemove();
                    break;
                }
            }
        } else {
            int power = 1;

            while (power <= sum) {
                power = power << 1;
            }
            power = power >> 1;
            System.out.println(power);

            for (Heap h : heaps) {
                int beanSize = h.beanSizeInAHeap();
                if (beanSize >= power) {
                    h.setBeanSize(beanSize ^ sum);
                    h.removeeBeansAI();
                    break;
                }
            }
        }
    }
}
