package interview;

import lombok.AllArgsConstructor;

public class HashmapFromScratch<K,V>{
    private static final int DEFAULT_CAPACITY = 1 << 4; //16

    private final Node<K,V>[] table;

    public HashmapFromScratch(){
        this.table = new Node[DEFAULT_CAPACITY];
    }

    private int getIndex(K key){
        int hashCode = key.hashCode();
        return hashCode & (table.length - 1);
    }

    public void put(K key, V value){
        int index = getIndex(key);
        table[index] = table[index] == null ? new Node<>(key, value, null) : table[index].put(key,value);
    }

    public V get(K key){
        int index = getIndex(key);
        return table[index] == null ? null : table[index].findValue(key);
    }

    public void remove(K key){
        int index = getIndex(key);
        if(table[index] != null)
            table[index] = table[index].remove(key);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                sb.append(table[i].toString());
            }
        }
        return sb.toString();
    }

    @AllArgsConstructor
    private static class Node<K,V>{
        private final K key;
        private final V value;
        private Node<K,V> next;

        public V findValue(K key){
            Node<K,V> current = this;
            while (current != null){
                if(key.equals(current.key))
                    return current.value;
                current = current.next;
            }
            return null;
        }

        public Node<K,V> remove(K key){
            Node<K,V> start = this;
            Node<K,V> current = start;
            Node<K,V> prev = null;
            while (current != null){
                if(key.equals(current.key)){
                    if(prev == null)
                        start = current.next; //changed start node
                    else
                        prev.next = current.next;//change prev to skip current item
                    break;
                }
                prev = current;
                current = current.next;
            }
            return start;
        }

        public Node<K,V> put(K key, V value) {
            remove(key);
            return new Node<>(key, value, this);
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            Node<K,V> current = this;
            while (current != null){
                sb.append("{").append(current.key).append(": ").append(current.value).append("}\n");
                current = current.next;
            }
            return sb.toString();
        }
    }


    public static void main(String[] args) {
        HashmapFromScratch<Integer,Integer> map = new HashmapFromScratch<>();
        final int max = 20;
        for(int i = 0; i < max; i++){
            map.put(i, 1500+i);
        }
        System.out.println(map);
        for(int i = 0; i < max; i++){
            System.out.println("remove "+i);
            map.remove(i);
            System.out.println(map);
        }
    }
}
