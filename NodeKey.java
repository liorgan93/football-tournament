public class NodeKey {
    int id;
    int score;

    NodeKey(int id, int score)
    {
        this.id = id;
        this.score = score;
    }
    NodeKey(int id)
    {
        this.id = id;
        this.score = -1;
    }

    public boolean Comparison(NodeKey key2)
    {
        if(this.score == -1)
        {
            if(this.id > key2.id)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if (this.score > key2.score || (this.score == key2.score && this.id < key2.id))
                return true;
            else
                return false;
        }
    }
}
