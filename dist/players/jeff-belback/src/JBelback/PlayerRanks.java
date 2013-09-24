package JBelback;


import java.util.ArrayList;
import java.util.Iterator;

public class PlayerRanks {
    ArrayList<PlayerRank> values = new ArrayList<PlayerRank>();
    
    public void AddScore(String opponent, int score){
        for(Iterator<PlayerRank> i = values.iterator(); i.hasNext(); ) {
            PlayerRank rank = i.next();
            if(rank.opponent == opponent){
                rank.points += score;
            }
        }
    }
    
    public String GetMax(){
        PlayerRank max = new PlayerRank("", -1);
        for(Iterator<PlayerRank> i = values.iterator(); i.hasNext(); ) {
            PlayerRank rank = i.next();
            if(rank.points > max.points)
                max = rank;
        }
        return max.opponent;
    }
    
    public String GetMin(){
        PlayerRank min = new PlayerRank("", 9999999);
        for(Iterator<PlayerRank> i = values.iterator(); i.hasNext(); ) {
            PlayerRank rank = i.next();
            if(rank.points < min.points)
                min = rank;
        }
        return min.opponent;
    }
}
