/**
 * Created by pmg on 2015/9/14.
 */

public class InsertionLinkedList
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

    /**
     * first try, AC
     */
    /**
    public static ListNode sort(ListNode head)
    {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode tmp;
        for (ListNode p = dummy;  p.next != null;)
        {
            ListNode q;
            for (q = dummy; q != p; q = q.next)
                if (less(p.next.val, q.next.val))  // find the suitable position, do the insert operation
                {
                    tmp = p.next;
                    p.next = p.next.next;
                    tmp.next = q.next;
                    q.next = tmp;
                    break;
                }
            if (q == p) p = p.next; // current item is already in its right position.(until that time)
        }
        return dummy.next;
    }
     */

    /**
     * second try, AC
     * 上面的解法总是考虑在一个连起来的链表上操作，实际上不必如此，从原链表中依次取出元素，每次将元素插入其应当的位置，
     * 形成一个新链表
     */
    public static ListNode sort(ListNode head)
    {
        ListNode dummy = new ListNode(0);
        for (ListNode p = head; p != null;)
        {
            ListNode pre = dummy;
            for (; pre.next != null && less(pre.next.val, p.val); pre = pre.next) // find the position of p
                ;
            // insert after pre
            ListNode tmp = p.next;
            p.next = pre.next;
            pre.next = p;
            p = tmp;
        }
        return dummy.next;
    }

    /**
     * jiuzhang ref answer
     */
    /**
    public static ListNode sort(ListNode head)
    {
        ListNode dummy = new ListNode(0);

        while (head != null) {
            ListNode node = dummy;
            while (node.next != null && less(node.next.val, head.val))
                node = node.next;
            ListNode temp = head.next;
            head.next = node.next;
            node.next = head;
            head = temp;
        }
        return dummy.next;
    }
     */
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
