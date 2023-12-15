public class Node {

    public Node parent, left, middle, right;
    public Node next, prev;
    NodeKey nodeKey;
    public Object value;

    public Node(int id, Object value, boolean onlyId, boolean max)
    {
        this.parent = null;

        this.left = null;
        this.middle = null;
        this.right = null;

        this.next = null;
        this.prev = null;

        this.value = value;

        if(id == -999)
        {
            this.nodeKey = null;
        }
        else
        {
            if (onlyId == false)
            {
                if(max == true) {
                    this.nodeKey = new NodeKey(id, Integer.MAX_VALUE);
                }
                else
                {
                    this.nodeKey = new NodeKey(id, Integer.MIN_VALUE);

                }
            }
            else
            {
                this.nodeKey = new NodeKey(id);
            }
        }



    }

    public Node(int id, Object value, int score)
    {
        this.parent = null;

        this.left = null;
        this.middle = null;
        this.right = null;

        this.next = null;
        this.prev = null;

        this.value = value;

        if(id == -999)
        {
            this.nodeKey = null;
        }

        this.nodeKey = new NodeKey(id, score);

    }



}
