// Name: Mia Zhang (Andrew ID: jingqiz)
// Course: 95771 A
// Assignment number: Project 1 Part 1

package edu.cmu.andrew.jingqiz;

import edu.colorado.nodes.ObjectNode;

import java.util.Random;

public class OrderedLinkedListOfIntegers {
    class IntegerNode {
        Integer data;
        IntegerNode next;

        public IntegerNode(Integer data) {
            this.data = data;
        }

        public IntegerNode(Integer data, IntegerNode next) {
            this.data = data;
            this.next = next;
        }
    }

    private IntegerNode head;
    private IntegerNode tail;
    private IntegerNode iterator;
    private int countNodes;

    OrderedLinkedListOfIntegers() {
        countNodes = 0;
    }

    public int countNodes(){
        return countNodes;
    }
    public void reset(){
        iterator=head;
    }

    public boolean hasNext(){
        return iterator!=null;
    }

    public Integer next(){
        Integer data = iterator.data;
        iterator=iterator.next;
        return data;
    }
    public void addLast(Integer integer) {
        if (countNodes == 0) {
            tail = new IntegerNode(integer,null);
            head = tail;
            iterator=head;
        } else {
            tail.next = new IntegerNode(integer,null);
            tail = tail.next;
        }
        countNodes++;
    }

    public void sortedAdd(Integer c) {
        if (countNodes == 0) {
            head = new IntegerNode(c, null);
            iterator=head;
        } else {
            if (c < head.data) {
                head = new IntegerNode(c, head);
            } else {
                IntegerNode cur = head, pre = head;
                while (cur != null && c >= cur.data) {
                    pre = cur;
                    cur = cur.next;
                }
                pre.next = new IntegerNode(c, cur);
            }
        }
        countNodes++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntegerNode node = head;
        while (node != null) {
            sb.append(node.data + " ");
            node = node.next;
        }
        return sb.toString();
    }

    // pre-condition: both of the input lists should not contain cycles
    public static OrderedLinkedListOfIntegers merge(OrderedLinkedListOfIntegers list1, OrderedLinkedListOfIntegers list2) {
        OrderedLinkedListOfIntegers mergedList = new OrderedLinkedListOfIntegers();

        IntegerNode head1 = list1.head;
        IntegerNode head2 = list2.head;
        while (head1 != null && head2 != null) {
            if (head1.data < head2.data) {
                mergedList.addLast(head1.data);
                head1 = head1.next;
            } else {
                mergedList.addLast(head2.data);
                head2 = head2.next;
            }
        }
        while (head1 != null) {
            mergedList.addLast(head1.data);
            head1 = head1.next;
        }
        while (head2 != null) {
            mergedList.addLast(head2.data);
            head2 = head2.next;
        }
        return mergedList;
    }

    public static void main(String[] args) {
        OrderedLinkedListOfIntegers list1 = new OrderedLinkedListOfIntegers();
        OrderedLinkedListOfIntegers list2 = new OrderedLinkedListOfIntegers();
        for (int i = 0; i < 20; i++) {
            list1.sortedAdd(new Random().nextInt(100));
            list2.sortedAdd(new Random().nextInt(100));
        }
        System.out.println("List 1: " + list1);
        System.out.println("List 1 nodes count: "+list1.countNodes());
        System.out.println("List 2: " + list2);
        System.out.println("List 2 nodes count: "+list2.countNodes());
        OrderedLinkedListOfIntegers mergedList=merge(list1, list2);
        mergedList.reset();
        System.out.print("Merged list: ");
        while(mergedList.hasNext()) {
            System.out.print(mergedList.next()+" ");
        }
        System.out.println("\nMerged list nodes count: "+mergedList.countNodes());
    }
}
