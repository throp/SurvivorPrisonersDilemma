import com.summa.summit.spd.*

public class FrankDux implements Player { 

  def opponents = [:]
  def currentOpponent = ""
  def gameIndex = 0
  def numGames = 0
  def opponentsLastDecision = Decision.Cooperate
  def myLastDecision = Decision.Cooperate

  Decision play() { 
    def dec = Decision.Cooperate
    if(gameIndex == 0) dec = Decision.Cooperate
    else if(gameIndex == numGames - 1) dec = Decision.Defect
    else if((new Date().getTime() % 8) == 0) dec = Decision.Defect
    else dec = opponentsLastDecision

    myLastDecision = dec
    return dec
  }

  String voteOffIsland() { 
    return opponents.isEmpty() ? currentOpponent : opponents.max { it.value }.key
  }

  String getName() { 
    return "Frank Dux"
  }

  void onGamePlayed(Decision opponentsDecision) { 
    opponentsLastDecision = opponentsDecision
    gameIndex++
   
    opponents[currentOpponent] += calculateScore(opponentsLastDecision, myLastDecision)
  }

  void onNewOpponent(String opponentName) { 
    gameIndex = 0
    currentOpponent = opponentName
    opponents << [(currentOpponent) : 0]
  }

  void onNewRound(int games) { 
    numGames = games
    currentOpponent = ""
    opponents = [:]
  }

  int calculateScore(Decision p1Decision, Decision p2Decision) { 
    if(p1Decision == Decision.Cooperate) { 
      return p2Decision == Decision.Cooperate ? 3 : 1
    }
    else { 
      return p2Decision == Decision.Cooperate ? 4 : 2
    }
  }

}