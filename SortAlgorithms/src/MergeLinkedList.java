/**
 * Created by pmg on 2015/9/14.
 */
public class MergeLinkedList
{
    private static class ListNode
    {
        Comparable val;
        ListNode next;
        ListNode(Comparable val)
        {
            this.val = val;
            this.next = null;
        }
    }

    public static ListNode sort(ListNode head) {
        // write your code here
        if (head == null || head.next == null) return head;

        // two methods to find the middle position of the sorted linked list,
        // they have the same computing complexity. O(2n)
        // 注：前后两个循环和一个循环两个指针动的复杂度是一样的
        // method1 : first loop calculate the length of the list, second loop move to the middle position
        /*
        int N = 0;
        ListNode p = head;
        for (; p != null; p = p.next)
            N++;
        int i = 1;
        for (p = head; i < N / 2; i++, p = p.next) ;
        */

        // method2: fast pointer and slow pointer
        ListNode fast = head, p = head; // p : slow pointer
        for (; fast.next != null && fast.next.next != null; fast = fast.next.next, p = p.next) ;
        ListNode l2 = p.next;
        p.next = null;
        ListNode l1 = head;
        l1 = sort(l1);
        l2 = sort(l2);
        return mergeTwoLists(l1, l2);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // write your code here
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode pointer = new ListNode(0);
        ListNode p = pointer;
        for (; l1 != null && l2 != null; p = p.next)
            if (less(l1.val, l2.val)) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
        p.next = (l1 == null) ? l2 : l1;
        return pointer.next;
    }

    private static boolean less(Comparable a, Comparable b)
    { return a.compareTo(b) < 0; }

    private static ListNode composeList(ListNode[] elems)
    {
        for (int i = 0; i < elems.length - 1; i++)
            elems[i].next = elems[i+1];
        return elems[0];
    }

    private static void show(ListNode lst)
    {
        for (; lst != null; lst = lst.next)
            StdOut.print(lst.val + " ");
        StdOut.println();
    }

    private static boolean isSorted(ListNode lst)
    {
        for (; lst != null && lst.next != null; lst = lst.next)
            if (less(lst.next.val, lst.val)) return false;
        return true;
    }

    public static void main(String[] args)
    {
        int N = 10;
        ListNode[] elems = new ListNode[N];
        for (int i = 0; i < N; i++)
            elems[i] = new ListNode(StdRandom.uniform());
        ListNode lst = composeList(elems);
        show(lst);
        ListNode lstSorted = sort(lst);
        show(lstSorted);
        if (isSorted(lst)) StdOut.println("Random input test passed!\n");
        else             StdOut.println("Random input test NOT passed!\n");
    }
}
