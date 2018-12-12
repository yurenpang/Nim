import java.util.HashMap;

public class NimSum {
    private HashMap<Integer, Integer> sizeMap;
    private Heap[] heaps;
    private boolean isEmpty;   //Determine there is no beans; it's time to get out of the nimsum object

    public NimSum(Heap[] heaps){
        this.sizeMap = new HashMap<>();
        this.heaps = heaps;
        this.isEmpty = false;

        updateMap();
    }

    /**
     * Update the number of beans in the map
     */
    public void updateMap() {
        for (Heap h : heaps) {
            sizeMap.put(h.getId(), h.getbeanSizeInAHeap());
        }
    }

    /**
     * Make the N-position and P-position move based on the nim sum
     */
    public void makeNextMove() {
        int sum = calculateNimSum();
        if(sum == 0) {
            pMove(sum);
        } else {
            int power = calculateBiggestPowerofTwo(sum);
            nMove(power, sum);
        }
    }

    /**
     * helper function to calculate the nim sum (XOR)
     * @return
     */
    private int calculateNimSum() {
        int sum = 0;
        for (Integer i : sizeMap.keySet()){
            sum = sum ^ sizeMap.get(i);
        }
        return sum;
    }

    /**
     * Calculate the leftist column with odd number of 1's
     * @param sum
     * @return
     */
    private int calculateBiggestPowerofTwo(int sum) {
        int power = 1;

        while (power <= sum) {
            power = power << 1;
        }
        power = power >> 1;
        return power;
    }

    /**
     * P-position when the nim sum if 0
     * That is, the computer does not have a winning strategy unless the player makes a mistake in the next few steps
     * It will remove the last ball in the first heap (which still has beans)
     * @param sum
     */
    private void pMove(int sum) {
        for(Heap h : heaps) {
            if(h.getbeanSizeInAHeap() > 0){
                h.removeLastBean();
                h.setBeanSize(h.getbeanSizeInAHeap() ^ sum);
                updateMap();
                break;
            }
        }
    }

    /**
     * N-position when the nim sum if greater than 0
     * That is, the computer does have a winning strategy. When the computer makes this step, the user is guaranteed to lose
     * It will move the corresponding beans in the heaps
     * @param power
     * @param sum
     */
    private void nMove(int power, int sum) {
        for (Heap h : heaps) {
            int beanSize = h.getbeanSizeInAHeap();
            if (beanSize >= power && (beanSize ^ sum) <= beanSize) {
                h.setBeanSize((beanSize ^ sum));
                h.removeBeansAI();
                updateMap();
                break;
            }
        }
    }

    public int getBeanSizeAfterComputer() {
        int counter = 0;
        for (Heap h:heaps) {
            counter = counter + h.getbeanSizeInAHeap();
        }
        return counter;
    }
}
