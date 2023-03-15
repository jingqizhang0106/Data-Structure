// Name: Mia Zhang (Andrew ID: jingqiz)
// Course: 95771 A
// Assignment number: Project 1 Part 1

import java.math.BigInteger;

public class SinglyLinkedList {
    private ObjectNode head;
    private ObjectNode tail;
    private int countNodes;
    private ObjectNode iterator;

    public SinglyLinkedList(){
        head = new ObjectNode(null,null);
        tail = new ObjectNode(null,null);
        countNodes=0;
        iterator=head;
    }

    public void addAtEndNode(Object c){
        tail.setLink(new ObjectNode(c, null));
        tail=tail.getLink();
        if(countNodes==0) {head=tail;iterator=head;}
        countNodes++;
    }

    public void addAtFrontNode(Object c){
        head = new ObjectNode(c,head);
        if(countNodes==0) tail=head;
        iterator=head;
        countNodes++;
    }

    public int countNodes(){
        return countNodes;
    }

    public Object getLast(){
        return tail.getData();
    }

    public Object getObjectAt(int index){
        if(index>countNodes-1) throw new IllegalArgumentException("index exceeds the length of the list");
        ObjectNode cur = head;
        for(int i=0;i<index;i++){
            cur=cur.getLink();
        }
        return cur.getData();
    }

    public void reset(){
        iterator=head;
    }

    public boolean hasNext(){
        return iterator!=null;
    }

    public Object next(){
        ObjectNode node = iterator;
        iterator=iterator.getLink();
        return node.getData();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        ObjectNode node = head;
        while (node != null) {
            sb.append(node.getData());
            node = node.getLink();
        }
        return sb.toString();
    }

    public static void main(String[] a){
        SinglyLinkedList list = new SinglyLinkedList();
        list.addAtEndNode(new BigInteger("2"));
        list.addAtEndNode(new BigInteger("3"));
        list.addAtFrontNode(new BigInteger("1"));

        System.out.println("Number of nodes = " + list.countNodes());
        System.out.println("Object at index 1 is: " + list.getObjectAt(1));
        System.out.println("Last object is: " + list.getLast());
        System.out.println(list.toString());

        list.reset();
        while(list.hasNext()) {
            System.out.println(list.next());
        }
    }
}
