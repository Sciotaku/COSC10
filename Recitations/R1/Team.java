package R1;

import Lec1.Ball;

public class Team
{
    private String mascotName;
    private int currScore;

    Team(String mascotName)
    {
        this.mascotName = mascotName;
        this.currScore = 0;
    }

    public String getName()
    {
        return mascotName;
    }

    public int getCurrScore()
    {
        return currScore;
    }

    public void score()
    {
        currScore = currScore + 2;
    }

    public static void main(String[] args) {
        Team Team1 = new Team("Barcelona");
        Team Team2 = new Team("Real Madrid");
        Team1.score();
        Team2.score();
        Team2.score();
        if (Team1.getCurrScore() > Team2.getCurrScore())
        {
            System.out.println(Team1.getName());
        } else if (Team1.getCurrScore() < Team2.getCurrScore())
        {
            System.out.println(Team2.getName());
        }
    }
}
