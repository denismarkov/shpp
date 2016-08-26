import java.util.*;

/**
 * Created by Denis on 26.07.2016.
 * Test custom collections
 */
public class collections {
    public static void main(String[] args) {
        Random rg = new Random();
        try {
            myArrayListTest(rg);
            myDequeTest(rg);
            myPriorityQueTest(rg);
            myHashMapTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Test ArrayList */
    private static void myArrayListTest(Random rg) throws Exception {
        MyArrayList<Double> myArrayList = new MyArrayList<>();
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            double temp = rg.nextDouble();
            myArrayList.add(temp);
            arrayList.add(temp);
        }
        int j = 0;
        for (Double myValue : myArrayList) {
            double value = arrayList.get(j++);
            if (!myValue.equals(value)) {
                throw new Exception("Not equals value in ArrayList foreach");
            }
        }
        int random = rg.nextInt(19);
        if (!myArrayList.get(random).equals(arrayList.get(random))) {
            throw new Exception("Not equals ArrayList get");
        }
        if (!myArrayList.remove(random).equals(arrayList.remove(random))) {
            throw new Exception("Not equals ArrayList poll");
        }
        myArrayList.add(random, (double)random);
        arrayList.add(random, (double)random);
        for(int i = 0; i < myArrayList.size(); i++) {
            if(!myArrayList.get(i).equals(arrayList.get(i))) {
                throw new Exception("Not equals ArrayList");
            }
        }

    }

    /* Test Deque */
    private static void myDequeTest(Random rg) throws Exception {
        MyDeque<Integer> myDeque = new MyDeque<>();
        Deque<Integer> deque = new ArrayDeque<>();
        while (myDeque.size() < 20) {
            int temp = rg.nextInt();
            myDeque.add(temp);
            deque.add(temp);
        }
        for (int j = 0; j < myDeque.size(); j++) {
            if (!myDeque.peekLast().equals(deque.peekLast())) {
                throw new Exception("Not equals Deque last peek");
            }
            if (!myDeque.poll().equals(deque.poll())) {
                throw new Exception("Not equals Deque poll");
            }
        }

    }

    /* Test PriorityQue */
    private static void myPriorityQueTest(Random rg) throws Exception {
        byte[] bytes = new byte[20];
        rg.nextBytes(bytes);
        MyPriorityQue<Node> pq = new MyPriorityQue<>();
        PriorityQueue<Node> original = new PriorityQueue<>();
        for (int i = 0; i < 20; i++) {
            Node n = new Node(rg.nextInt(), bytes[i], null, null);
            original.add(n);
            pq.add(n);
        }
        while (!pq.isEmpty()) {
            if (!pq.poll().equals(original.poll())) {
                throw new Exception("Not equals values in PriorityQue");
            }
        }

    }

    /* Test HashMap */
    private static void myHashMapTest() throws Exception {
        MyHashMap<String, Integer> hashMap = new MyHashMap<>();
        hashMap.put("Key zero", 10);
        hashMap.remove("Key zero");
        int iterateCount = 20;
        for (int i = 0; i < iterateCount; i++) {
            String key = "Key " + Integer.toString(i);
            hashMap.put(key, i);
        }
        hashMap.put(null, 101);
        if (!hashMap.remove(null)) {
            throw new Exception("Can't remove null key in HashMap");
        }
        hashMap.remove("Key 4");
        if (hashMap.containsKey("Key 4")) {
            throw new Exception("Incorrect remove key in HashMap");
        }
        hashMap.put("Key 20", null);
        if (!hashMap.containsKey("Key 20")) {
            throw new Exception("Incorrect put null value in HashMap");
        }
        hashMap.put("Key 3", 1);
        if (hashMap.get("Key 3") != 1) {
            throw new Exception("Incorrect set value in HashMap");
        }
        int iterateAfter = 0;
        for (MyHashMap.Entry entry : hashMap) {
            iterateAfter++;
        }
        if (iterateAfter != iterateCount) {
            throw new Exception("Drop date in HashMap");
        }
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        HashMap<String, Integer> HashMap =  new HashMap<>();
        for(int i = 0; i < 100; i++) {
            String key = "Key " + i;
            myHashMap.put(key, i);
            HashMap.put(key, i);
        }
        for(Map.Entry entry : HashMap.entrySet()) {
            String key = (String)entry.getKey();
            Integer value = (Integer)entry.getValue();
            if(!myHashMap.containsKey(key) || !myHashMap.get(key).equals(value)) {
                throw new Exception("Not equals hashmap");
            }
        }
    }

}
