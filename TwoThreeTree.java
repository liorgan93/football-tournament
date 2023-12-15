public class TwoThreeTree {
    public Node root;
    public Node maxLeaf;

    public TwoThreeTree(boolean onlyId) {
        Node x,l,m;

        x = new Node(Integer.MAX_VALUE, null, onlyId, true);
        m = new Node(Integer.MAX_VALUE, null, onlyId, true);
        l = new Node(Integer.MIN_VALUE, null, onlyId, false);

        l.parent = x;
        m.parent = x;

        l.prev = m;
        m.next = l;

        x.left = l;
        x.middle = m;
        root = x;

        maxLeaf = m;
    }

    public void update_key(Node x) {
        x.nodeKey = x.left.nodeKey;
        if (x.middle != null)
            x.nodeKey = x.middle.nodeKey;
        if (x.right != null)
            x.nodeKey = x.right.nodeKey;

    }

    public void Set_Children(Node x, Node l, Node m, Node r) {
        x.left = l;
        x.middle = m;
        x.right = r;

        l.parent = x;

        if (m != null)
            l.prev = m;

        if (m != null)
        {
            m.parent = x;
            m.next = l;
            if (r != null)
                m.prev = r;
        }

        if (r != null) {
            r.parent = x;
            r.next = m;
        }

        update_key(x);
    }

    public Node InsertAndSplit(Node x, Node z) {
        Node l,m,r;

        l = x.left;
        m = x.middle;
        r = x.right;

        if (r == null)
        {
            if (l.nodeKey.Comparison(z.nodeKey))
            {
                if (l.next!=null)
                {
                    z.next = l.next;
                    l.next.prev = z;
                }
                Set_Children(x, z,l,m);
            }

            else
            {
                if (m.nodeKey.Comparison(z.nodeKey)) {
                    Set_Children(x, l, z, m);
                }
                else
                {
                    if (m.prev != null)
                    {
                        z.prev = m.prev;
                        m.prev.next = z;
                    }
                Set_Children(x, l, m, z);
                }
            }

            return null;
        }
        Node y = new Node(-999, null, false, true);
        if (l.nodeKey.Comparison(z.nodeKey))
        {
            if (l.next != null)
            {
                z.next = l.next;
                l.next.prev = z;
            }
            Set_Children(x, z,l,null);
            Set_Children(y,m,r,null);
        }
        else
        {
            if (m.nodeKey.Comparison(z.nodeKey))
            {
                Set_Children(x,l,z,null);
                Set_Children(y,m,r,null);

                m.next = z;
                z.prev = m;
            }
            else
            {
                if(r.nodeKey.Comparison(z.nodeKey))
                {
                    Set_Children(x,l,m,null);
                    Set_Children(y,z,r,null);

                    z.next = m;
                    m.prev = z;
                }
                else
                {
                    if (z.prev != null)
                    {
                        z.prev = r.prev;
                        r.prev.next = z;
                    }

                    Set_Children(x,l,m,null);
                    Set_Children(y,r,z,null);
                }
            }
        }
        return y;
    }


    public void Insert(TwoThreeTree T, Node z) {
        Node y = T.root;

        while (y.left != null)
        {
            if(y.left.nodeKey.Comparison(z.nodeKey))
                y = y.left;
            else {
                if (y.middle.nodeKey.Comparison(z.nodeKey))
                    y = y.middle;
                else
                    y = y.right;
            }
        }

        Node x = y.parent;
        z = InsertAndSplit(x,z);

        while (x != T.root)
        {
            x = x.parent;
            if (z != null)
                z = InsertAndSplit(x,z);
            else update_key(x);
        }
        if (z != null)
        {
            Node w = new Node(-999,null, false, true);
            Set_Children(w, x, z, null);
            T.root = w;
        }
    }

    public Node Search (Node x, NodeKey k)
    {
        if (x.left == null)
        {
            if ((x.nodeKey.id == k.id && x.nodeKey.score == -1) || (x.nodeKey.score != -1 && x.nodeKey.score == k.score && x.nodeKey.id == k.id))
                return x;
            else return null;
        }
        if (!(k.Comparison(x.left.nodeKey)))
            return Search(x.left,k);
        else
            if(!(k.Comparison(x.middle.nodeKey)))
            {
                return Search(x.middle, k);
            }

            else
            {
                return Search(x.right, k);
            }
    }

    public Node Borrow_Or_Merge(Node y) {
        Node z = y.parent;

        if (y == z.left)
        {
            Node x = z.middle;
            if (x.right != null)
            {
                Set_Children(y, y.left, x.left, null);
                Set_Children(x, x.middle, x.right, null);
            }
            else
            {
                Set_Children(x, y.left, x.left, x.middle);
                Set_Children(z, x, z.right, null);
            }
            return z;
        }

        if (y == z.middle)
        {
            Node x = z.left;
            if (x.right != null)
            {
                Set_Children(y, x.right, y.left, null);
                Set_Children(x, x.left, x.middle, null);
            }
            else
            {
                Set_Children(x, x.left, x.middle, y.left);
                Set_Children(z, x, z.right, null);

            }
            return z;
        }

        Node x = z.middle;
        if (x.right != null)
        {
            Set_Children(y, x.right, y.left, null);
            Set_Children(x, x.left, x.middle, null);
        }
        else
        {
            Set_Children(x, x.left, x.middle, y.left);
            Set_Children(z, z.left, x, null);
        }
        return z;
    }

    public void Delete(TwoThreeTree T, Node x)
    {
        Node y = x.parent;
        if (x == y.left)
        {
            y.middle.next = y.left.next;
            y.left.next.prev = y.left.prev;
            Set_Children(y, y.middle, y.right, null);
        }
        else if (x == y.middle)
        {
            y.left.prev = y.middle.prev;
            y.middle.prev.next = y.left;
            Set_Children(y, y.left, y.right,null);
        }
        else
        {
            y.middle.prev = y.right.prev;
            y.right.prev.next = y.middle;
            Set_Children(y, y.left, y.middle, null);
        }
        while (y != null)
        {
            if (y.middle == null)
            {
                if (y != T.root)
                    y = Borrow_Or_Merge(y);
                else
                {
                    T.root = y.left;
                    y.left.parent = null;
                    return;
                }
            }
            else
            {
                update_key(y);
                y = y.parent;
            }
        }
    }
}
