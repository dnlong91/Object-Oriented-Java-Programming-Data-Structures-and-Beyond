package casestudy;

import java.util.*;

public class InsertDeleteGetRandom {
	private ArrayList<Integer> arrayList;
    private HashMap<Integer, Integer> hashMap;
    private Random rand;

    public InsertDeleteGetRandom() {
        arrayList = new ArrayList<Integer>();
        hashMap = new HashMap<Integer, Integer>();
        rand = new Random();
    }
    
    public boolean insert(int val) {
        if (!hashMap.containsKey(val)) {
            arrayList.add(val);
            hashMap.put(val, arrayList.size() - 1);
            return true;
        }
        return false;
    }
    
    public boolean remove(int val) {
        if (hashMap.containsKey(val)) {
            int valIndex = hashMap.get(val);
            int lastVal = arrayList.get(arrayList.size() - 1);
            // Remove from the array list by "swapping" to make sure it takes O(1) to remove from an array list
            //// The value of the very last position in the array is now at the position of the to-remove element
            arrayList.set(valIndex, lastVal);
            hashMap.put(lastVal, valIndex);
            //// Remove the duplicated value at the last position of the array
            arrayList.remove(arrayList.size() - 1);
            hashMap.remove(val);
            return true;
        }
        return false;
    }
    
    public int getRandom() {
        int randIndex = rand.nextInt(arrayList.size());
        return arrayList.get(randIndex);
    }
}