package JBelback;


import com.summa.summit.spd.Decision;
import com.summa.summit.spd.Player;
import java.util.ArrayList;

public class SpaghettiCoder implements Player {
    ScoreCalculator calc = new ScoreCalculator();
    PlayerRanks ranks = new PlayerRanks();
    GameResultData results = new GameResultData();
    String currentOpponent = "";
    Decision lastDecision;

  public Decision play() { 
      lastDecision = results.GetExpected(currentOpponent);
      return lastDecision;
  }

  public String voteOffIsland() { 
      return ranks.GetMin();
  }

  public String getName() { 
    return "SpaghettiCoder";
  }

  public void onGamePlayed(Decision opponentDecision) { 
      GameResults result = new GameResults();
      result.myDecision = lastDecision;
      result.opponentDecision = opponentDecision;
      result.myPoints = calc.GetMyScore(result);
      result.opponentPoints = calc.GetTheirScore(result);
      results.add(result);
      
      ranks.AddScore(currentOpponent, result.opponentPoints);
  }

  public void onNewOpponent(String opponentName) { 
    currentOpponent = opponentName;
  }

  public void onNewRound(int numGames) { 
        ranks = new PlayerRanks();
  }

}
