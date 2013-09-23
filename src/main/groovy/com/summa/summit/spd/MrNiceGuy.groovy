package com.summa.summit.spd

public class MrNiceGuy implements Player { 

  def opponents = [:]
  def currentOpponent = ""

  Decision play() { 
    return Decision.Cooperate // always be nice!
  }

  String voteOffIsland() { 
    return opponents.isEmpty() ? currentOpponent : opponents.max { it.value }.key
  }

  String getName() { 
    return "Mr. Nice Guy"
  }

  void onGamePlayed(Decision opponentsDecision) { 
    if(opponentsDecision == Decision.Defect) {
      opponents[currentOpponent]++ // vote off the meanest person
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