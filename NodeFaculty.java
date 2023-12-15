public class NodeFaculty {
    Faculty faculty;
    int score;
    NodePlayer[] playersOfFaculty;

    public NodeFaculty(Faculty faculty)
    {
        this.faculty = faculty;
        this.score = 0;
        this.playersOfFaculty = new NodePlayer[11];
    }
}
