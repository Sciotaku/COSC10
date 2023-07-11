public class SinglyLinkedHT {

    Element head;
    Element tail;
    int size;

    private class Element {

        Element next;
        int data;

        public Element(int data, Element next) {
            this.data = data;
            this.next = next;
        }

    }


    // 2.1
    public void rotate(boolean direction) {

        if(size == 0 || size == 1) return;

        if(direction == true) {

            Element oldHead = head; // store the old head
            head = head.next; // update the head pointer
            tail.next = oldHead; // put the old head at the end of the list
            oldHead.next = null; // remove the old head's link to the now head pointer

        } else {
            // get the second to last element
            Element curr = head;
            while(curr.next.next != null) {
                curr = curr.next;
            }

            tail.next = head; // have the last element point to the head
            head = tail; // have the last element become the head
            curr.next = null; // break the link from the second to last element
        }

    }

    // 2.2
    // O(n) for both directions


}
