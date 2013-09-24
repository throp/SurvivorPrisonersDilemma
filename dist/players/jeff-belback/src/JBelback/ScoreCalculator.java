package JBelback;


import com.summa.summit.spd.Decision;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScoreCalculator {
    ArrayList<Points> values = new ArrayList<Points>();
    public ScoreCalculator(){
       values.add(new Points(Decision.Cooperate, Decision.Cooperate, 3));
       values.add(new Points(Decision.Cooperate, Decision.Defect, 1));
       values.add(new Points(Decision.Defect, Decision.Cooperate, 4));
       values.add(new Points(Decision.Defect, Decision.Defect, 2));
    }
    
    public int GetMyScore(GameResults results){
        for(Iterator<Points> i = values.iterator(); i.hasNext(); ) {
            Points pts = i.next();
            if(pts.decision1 == results.myDecision && pts.decision2 == results.opponentDecision)
                return pts.points;
        }
        throw new UnsupportedOperationException("WTF");
    }
    
    public int GetTheirScore(GameResults results){
        for(Iterator<Points> i = values.iterator(); i.hasNext(); ) {
            Points pts = i.next();
            if(pts.decision1 == results.opponentDecision && pts.decision2 == results.myDecision)
                return pts.points;
        }
        throw new UnsupportedOperationException("WTF");
    }
}
