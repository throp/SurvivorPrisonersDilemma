package com.summa.summit.spd

public class JoeEvil implements Player { 

  def opponents = [:]
  def currentOpponent = ""

  Decision play() { 
    return Decision.Defect // always be evil!
  }

  String voteOffIsland() { 
    return opponents.isEmpty() ? currentOpponent : opponents.max { it.value }.key
  }

  String getName() { 
    return "Joe Evil"
  }

  void onGamePlayed(Decision opponentsDecision) { 
    if(opponentsDecision == Decision.Cooperate) {
      opponents[currentOpponent]++ // vote off the nicest person
    }
  }

  void onNewOpponent(String opponentName) { 
    currentOpponent = opponentName
    opponents << [(currentOpponent) : 0]
  }

  void onNewRound(int numGames) { 
    currentOpponent = ""
    opponents = [:]
  }

}