public class SinglyLinkedList {

    Element head;
    int size;

    private class Element {

        Element next;
        int data;

        public Element(int data, Element next) {
            this.data = data;
            this.next = next;
        }

    }

    // 2.3
    public void rotate(boolean direction) {

        if(size == 0 || size == 1) return;

        if(direction == true) { // O(n)
            // get the last element
            Element curr = head;
            while(curr.next != null) {
                curr = curr.next;
            }

            Element oldHead = head; // store the old head
            head = head.next; // update the head pointer
            curr.next = oldHead; // put the old head at the end of the list
            oldHead.next = null; // remove the old head's link to the now head pointer

        } else { // O(n)
            // get the second to last element
            Element curr = head;
            while(curr.next.next != null) {
                curr = curr.next;
            }

            curr.next.next = head; // have the last element point to the head
            head = curr.next; // have the last element become the head
            curr.next = null; // break the link from the second to last element
        }

    }

}
