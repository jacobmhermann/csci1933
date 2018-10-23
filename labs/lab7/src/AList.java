/*
 * Modified from Frank M. Carrano's 
 * Data Structures and Abstractions with Java (3rd Edition)
 */
import java.util.Arrays;

public class AList{

    private Object[] list;
    private int numberOfEntries;

    public AList(){
        list = new Object[25];
        numberOfEntries = 0;
    }

    public AList(int initialCapacity){
        numberOfEntries = 0;
        Object[] tempList = new Object[initialCapacity];
        list = tempList;
    }

    public void add(Object newEntry){
        ensureCapacity();
        list[numberOfEntries] = newEntry;
        numberOfEntries++;
    }

    public Object get(int item){
        return list[item];
    }

    public int getLength(){
        return numberOfEntries;
    }

    public boolean isEmpty(){
        return numberOfEntries == 0;
    }

    public Object[] toArray(){
        Object[] result = new Object[numberOfEntries];
        for(int index = 0; index < numberOfEntries; index++){
          result[index] = list[index];
        }

        return result;
    }

    private void ensureCapacity(){
        if (numberOfEntries == list.length){
            list = Arrays.copyOf(list, 2 * list.length);
        }
    }
}
