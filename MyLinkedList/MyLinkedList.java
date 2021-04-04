/**
* LeetCode's Design Linked List challenge solved.
* Doubly linked list.
* @author Lucas Paz
*/
class MyLinkedList {

    private class Node {
        
        int val = 0;
        Node prev;
        Node next;
        
        public Node() {}
        
        public Node(int val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
        
    }
    
    private int length = 0;
    private Node head;
    private Node tail;
    
    /** Initialize your data structure here. */
    public MyLinkedList() {
        this.length = 0; 
        this.head = null; 
        this.tail = null;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public Node getNodeAtIndex(int index) {

        if (length == 0 || index < 0 || index >= length) return null;
        
        int distanceFromHead = index - 0;
        int distanceFromTail = (length - 1) - index;
        
        Node node;
        
        if (distanceFromHead <= distanceFromTail) {
            node = this.head;
            for (int i = 0; i < index; i++) node = node.next;
        } else {
            node = this.tail;
            for (int i = length - 1; i > index; i--) node = node.prev;
        }
        
        return node;
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        
        Node nodeAtIndex = getNodeAtIndex(index);
        
        return (nodeAtIndex == null) ? -1 : nodeAtIndex.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        
        Node newNode = new Node(val, null, this.head);
        
        if (this.head != null) this.head.prev = newNode;
        
        this.head = newNode;
        
        length++;
        
        if (length == 1) this.tail = newNode;
        
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        
        Node newNode = new Node(val, this.tail, null);
        
        if (tail != null) this.tail.next = newNode;
        
        this.tail = newNode;
        
        length++;
        
        if (length == 1) this.head = newNode;
        
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        
        if (index < 0 || index > length) return;
        
        if (index == 0) {
            addAtHead(val);
            return;
        }
        
        if (index == length) {
            addAtTail(val);
            return;
        }
        
        Node nodeAtIndex = getNodeAtIndex(index);
        
        Node nodeBefore = nodeAtIndex.prev;
        
        Node newNode = new Node(val, nodeBefore, nodeAtIndex);
        
        nodeBefore.next = newNode;
        nodeAtIndex.prev = newNode;
            
        length++;
        
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        
        if (length == 0 || index < 0 || index >= length) return;
        
        if (length == 1) {
            this.head = null;
            this.tail = null;
            length--;
            return;
        }
        
        if (index == 0) {
            Node nodeAfterHead = this.head.next;
            nodeAfterHead.prev = null;
            this.head = null;
            length--;
            this.head = nodeAfterHead;
            return;
        }
        
        if (index == length - 1) {
            Node nodeBeforeTail = this.tail.prev;
            nodeBeforeTail.next = null;
            this.tail = null;
            length--;
            this.tail = nodeBeforeTail;
            return;
        }
        
        Node node = getNodeAtIndex(index);
        
        Node nodeBefore = node.prev;
        Node nodeAfter = node.next;
        
        nodeBefore.next = nodeAfter;
        nodeAfter.prev = nodeBefore;
        
        node = null;
        
        length--;
        
    }
    
}
