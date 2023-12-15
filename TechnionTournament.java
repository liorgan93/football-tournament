import java.util.ArrayList;

public class TechnionTournament implements Tournament{


    TechnionTournament(){
    };


    @Override
    public void init() {
        this.facultyById = new TwoThreeTree(true);
        this.playersById = new TwoThreeTree(true);
        this.facultyByScore = new TwoThreeTree(false);
        this.playersByGoals = new TwoThreeTree(false);
    }

    @Override
    public void addFacultyToTournament(Faculty faculty) {
        Faculty newFaculty = new Faculty(faculty.getId(), faculty.getName());
        NodeFaculty nodeFaculty = new NodeFaculty(newFaculty);
        Node newNode1 = new Node(faculty.getId(), nodeFaculty, true, false);
        Node newNode2 = new Node(faculty.getId(), nodeFaculty, 0);

        this.facultyById.Insert(this.facultyById, newNode1);
        this.playersByGoals.Insert(this.facultyByScore, newNode2);

    }

    @Override
    public void removeFacultyFromTournament(int faculty_id){
        NodeKey nodeKey1 = new NodeKey(faculty_id);
        Node facultyNode = this.facultyById.Search(this.facultyById.root, nodeKey1);
        NodeFaculty faculty = (NodeFaculty) facultyNode.value;
        this.facultyById.Delete(this.facultyById,facultyNode);

        int score = faculty.score;
        NodeKey nodeKey2 = new NodeKey(faculty_id, score);
        Node facultyNode1 = this.facultyByScore.Search(this.facultyByScore.root, nodeKey2);
        this.facultyByScore.Delete(this.facultyByScore, facultyNode1);
    }

    @Override
    public void addPlayerToFaculty(int faculty_id,Player player) {
        Player newPlayer = new Player(player.getId(), player.getName());
        NodeKey nodeKey1 = new NodeKey(faculty_id);

        Node facultyNode = this.facultyById.Search(this.facultyById.root, nodeKey1);
        NodePlayer nodePlayer = new NodePlayer(newPlayer);

        Node newNode1 = new Node(player.getId(), nodePlayer, true, false);
        Node newNode2 = new Node(player.getId(), nodePlayer, 0);


        NodeFaculty nodePlayerFaculty = (NodeFaculty) facultyNode.value;
        int i=0;
        while (nodePlayerFaculty.playersOfFaculty[i] != null)
        {
            i++;
        }
        nodePlayerFaculty.playersOfFaculty[i] = nodePlayer;

        this.playersById.Insert(this.playersById, newNode1);
        this.playersByGoals.Insert(this.playersByGoals, newNode2);
    }

    @Override
    public void removePlayerFromFaculty(int faculty_id, int player_id) {

        NodeKey nodeKey = new NodeKey(faculty_id);
        NodeFaculty facultyOfPlayer =(NodeFaculty) this.facultyById.Search(this.facultyById.root, nodeKey).value;
        int i=0;
        while(facultyOfPlayer.playersOfFaculty[i].player.getId() != player_id)
        {
            i++;
        }
        while (i < 10)
        {
            facultyOfPlayer.playersOfFaculty[i] = facultyOfPlayer.playersOfFaculty[i+1];
            i++;
        };
    }

    @Override
    public void playGame(int faculty_id1, int faculty_id2, int winner,
                         ArrayList<Integer> faculty1_goals, ArrayList<Integer> faculty2_goals) {

        NodeKey nodeKey1 = new NodeKey(faculty_id1);
        NodeKey nodeKey2 = new NodeKey(faculty_id2);

        NodeFaculty faculty1 = (NodeFaculty) this.facultyById.Search(this.facultyById.root, nodeKey1).value;
        NodeKey nodeKey3 = new NodeKey(faculty_id1, faculty1.score);
        NodeFaculty faculty2 = (NodeFaculty) this.facultyById.Search(this.facultyById.root, nodeKey2).value;
        NodeKey nodeKey4 = new NodeKey(faculty_id2, faculty2.score);


        Node faculty1Node = this.facultyByScore.Search(this.facultyByScore.root, nodeKey3);
        Node faculty2Node = this.facultyByScore.Search(this.facultyByScore.root, nodeKey4);

        this.facultyByScore.Delete(this.facultyByScore, faculty1Node);
        this.facultyByScore.Delete(this.facultyByScore, faculty2Node);

        if(winner == 0)
        {
            faculty1.score = faculty1.score + 1;
            faculty2.score = faculty2.score + 1;
        }
        if (winner == 1) {
            faculty1.score = faculty1.score + 3;
        }
        if (winner == 2)
        {
            faculty2.score = faculty2.score + 3;
        }
        Node newNode1 = new Node(faculty_id1, faculty1, faculty1.score);
        Node newNode2 = new Node(faculty_id2, faculty2, faculty2.score);
        this.facultyByScore.Insert(this.facultyByScore, newNode1);
        this.facultyByScore.Insert(this.facultyByScore, newNode2);


        NodeKey nodeKey = new NodeKey(faculty_id1);
        NodeFaculty faculty = (NodeFaculty) this.facultyById.Search(this.facultyById.root, nodeKey).value;

        for(int i = 0; i < faculty1_goals.size(); i++)
        {
            int j=0;
            while (faculty1_goals.get(i) != faculty.playersOfFaculty[j].player.getId())
            {
                j++;
            }
            NodeKey nodeKey5 = new NodeKey(faculty1_goals.get(i));
            NodePlayer player = (NodePlayer) this.playersById.Search(this.playersById.root, nodeKey5).value;
            NodeKey nodeKey6 = new NodeKey(player.player.getId(), player.score);
            Node currentPlayerNode = this.playersByGoals.Search(this.playersByGoals.root, nodeKey6);
            this.playersByGoals.Delete(this.playersByGoals, currentPlayerNode);

            faculty.playersOfFaculty[j].score++;
            Node newNode = new Node(player.player.getId(), player, faculty.playersOfFaculty[j].score);
            this.playersByGoals.Insert(this.playersByGoals, newNode);

        }

        nodeKey = new NodeKey(faculty_id2);
        faculty = (NodeFaculty) this.facultyById.Search(this.facultyById.root, nodeKey).value;

        for(int i = 0; i < faculty2_goals.size(); i++)
        {
            int j=0;
            while (faculty2_goals.get(i) != faculty.playersOfFaculty[j].player.getId())
            {
                j++;
            }
            NodeKey nodeKey5 = new NodeKey(faculty2_goals.get(i));
            NodePlayer player = (NodePlayer) this.playersById.Search(this.playersById.root, nodeKey5).value;
            NodeKey nodeKey6 = new NodeKey(player.player.getId(), player.score);
            Node playerNode = this.playersByGoals.Search(this.playersByGoals.root, nodeKey6);
            this.playersByGoals.Delete( this.playersByGoals, playerNode);


            faculty.playersOfFaculty[j].score++;
            Node newNode = new Node(player.player.getId(), player, faculty.playersOfFaculty[j].score);
            this.playersByGoals.Insert(this.playersByGoals, newNode);
        }
    }

    @Override
    public void getTopScorer(Player player) {
        NodePlayer nodeBestPlayer = (NodePlayer) this.playersByGoals.maxLeaf.next.value;
        Player bestPlayer = nodeBestPlayer.player;
        player.setId(bestPlayer.getId());
        player.setName((bestPlayer.getName()));

    }

    @Override
    public void getTopScorerInFaculty(int faculty_id, Player player) {
        Node faculty = this.facultyById.root;
        while(faculty.left != null)
        {
            if (faculty_id <= faculty.left.nodeKey.id)
                faculty = faculty.left;
            else
            {
                if(faculty_id <= faculty.middle.nodeKey.id)
                    faculty = faculty.middle;
                else
                {
                    if(faculty.right != null)
                        faculty = faculty.right;
                }
            }
        }
        NodeFaculty nodeFaculty = (NodeFaculty)faculty.value;
        NodePlayer[] playerAndScore = nodeFaculty.playersOfFaculty;

        player.setId(playerAndScore[0].player.getId());
        player.setName(playerAndScore[0].player.getName());
        int bestPlayerId = playerAndScore[0].player.getId();
        int bestScore = playerAndScore[0].score;

        int i=0;
        while (playerAndScore[i] != null && i != 11)
        {
            int score = playerAndScore[i].score;
            int id = playerAndScore[i].player.getId();
            if(bestScore < score || (bestScore == score && bestPlayerId > id))
            {
                player.setId(playerAndScore[i].player.getId());
                player.setName(playerAndScore[i].player.getName());
                bestPlayerId = playerAndScore[i].player.getId();
                bestScore = playerAndScore[i].score;
            }
            i++;
        }
    }

    @Override
    public void getTopKFaculties(ArrayList<Faculty> faculties, int k, boolean ascending) {
        Node facultiesByOrder = this.facultyByScore.maxLeaf;

        for (int i=0; i<k; i++)
        {
            facultiesByOrder = facultiesByOrder.next;
            if (ascending == false) {
                NodeFaculty nodeFaculty = (NodeFaculty) facultiesByOrder.value;
                Faculty faculty = nodeFaculty.faculty;
                faculties.add(faculty);

            }
        }
        if(ascending == false)
            return;
        else
        {
            for (int i=0; i<k; i++)
            {
                NodeFaculty nodeFaculty2 = (NodeFaculty) facultiesByOrder.value;
                Faculty faculty = nodeFaculty2.faculty;
                faculties.add(faculty);
                facultiesByOrder = facultiesByOrder.prev;
            }
        }
    }

    @Override
    public void getTopKScorers(ArrayList<Player> players, int k, boolean ascending) {
        Node playersByOrder = this.playersByGoals.maxLeaf;

        for (int i=0; i<k; i++)
        {
            playersByOrder = playersByOrder.next;
            if (ascending == false) {
                NodePlayer nodePlayer = (NodePlayer) playersByOrder.value;
                Player player = nodePlayer.player;
                players.add(player);

            }
        }
        if(ascending == false)
            return;
        else
        {
            for (int i=0; i<k; i++)
            {
                NodePlayer nodePlayer2 = (NodePlayer) playersByOrder.value;
                Player player = nodePlayer2.player;
                players.add(player);
                playersByOrder = playersByOrder.prev;


            }
        }
    }

    @Override
    public void getTheWinner(Faculty faculty) {
        NodeFaculty winner = (NodeFaculty) this.facultyByScore.maxLeaf.next.value;
        Faculty winnerFaculty = winner.faculty;
        faculty.setId(winnerFaculty.getId());
        faculty.setName((winnerFaculty.getName()));

    }

    ///TODO - add below your own variables and methods
    TwoThreeTree facultyById;
    TwoThreeTree playersById;
    TwoThreeTree facultyByScore;
    TwoThreeTree playersByGoals;
}
