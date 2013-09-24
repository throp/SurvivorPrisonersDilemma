package JBelback;


import com.summa.summit.spd.Decision;
import java.util.ArrayList;
import java.util.Iterator;

public class GameResultData {
    ArrayList<GameResults> results = new ArrayList<GameResults>();
    ArrayList<GameTotals> totals = new ArrayList<GameTotals>();
    public void add(GameResults result) {
        results.add(result);
        addToTotal(result);
    }
    
    public Decision GetExpected(String opponent){
        for(Iterator<GameTotals> i = totals.iterator(); i.hasNext(); ) {
            GameTotals tot = i.next();
            if(tot.opponent == opponent){
                double c = GetCooperatePerc(tot);
                double d = GetDefectPerc(tot);
                if(c > d){
                    return Decision.Cooperate;
                }
                return Decision.Defect;
            }
        }
        
        return Decision.Defect;
    }
    
    private void addToTotal(GameResults result) {
        for(Iterator<GameTotals> i = totals.iterator(); i.hasNext(); ) {
            GameTotals tot = i.next();
            if(tot.opponent == result.opponent){
                tot.total++;
                if(result.opponentDecision == Decision.Cooperate) {
                    tot.cooperate++;
                } else {
                    tot.defect++;
                }
            }
        }
    }
    
    private double GetCooperatePerc(GameTotals totals){
        if(totals.total == 0)
            return 0.5;
        
        return (double)totals.cooperate/(double)totals.total;
    }
    private double GetDefectPerc(GameTotals totals){
        if(totals.total == 0)
            return 0.5;
        
        return (double)totals.defect/(double)totals.total;
    }
}