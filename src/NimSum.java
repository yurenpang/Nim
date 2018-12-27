import java.util.ArrayList;
import java.util.HashMap;

public class NimSum {
    private HashMap<Integer, Integer> beanHeapMap;
    private ArrayList<Integer> returnheapIDbeans;

    public NimSum(HashMap<Integer, Integer> beanHeapMap){
        this.beanHeapMap = beanHeapMap;
        this.returnheapIDbeans = new ArrayList<>(2);
    }

    /**
     * Make the N-position and P-position move based on the nim sum
     * get Heap ID and numBeans in the heap
     */
    public ArrayList<Integer> nextHeapIdBeans() {
        int sum = calculateNimSum();
        if(sum == 0) {
            pMove();
        } else {
            int power = calculateBiggestPowerofTwo(sum);
            nMove(power, sum);
        }
        return returnheapIDbeans;
    }

    /**
     * Helper function to calculate the nim sum (XOR)
     * @return
     */
    private int calculateNimSum() {
        int sum = 0;
        for (Integer i : beanHeapMap.keySet()){
            sum = sum ^ beanHeapMap.get(i);
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
     */
    private void pMove() {
        returnheapIDbeans.clear();
        for (Integer heapID : beanHeapMap.keySet()) {
            int numBeans = beanHeapMap.get(heapID);
            if (numBeans > 0) {
                returnheapIDbeans.add(heapID);
                returnheapIDbeans.add(numBeans-1);
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
        returnheapIDbeans.clear();
        for (Integer heapID : beanHeapMap.keySet()) {
            int numBeans = beanHeapMap.get(heapID);
            if (numBeans >= power && (numBeans ^ sum) <= numBeans) {
                returnheapIDbeans.add(heapID);
                returnheapIDbeans.add(numBeans^sum);
                break;
            }
        }
    }

}
