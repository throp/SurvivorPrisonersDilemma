package com.summa.summit.spd

public class RupertRandom implements Player { 

  def opponents = []

  Decision play() { 
    return new Date().getTime() % 2 == 0 ? Decision.Cooperate : Decision.Defect
  }

  String voteOffIsland() { 
    int mod = new Date().getTime() % opponents.size()
    return opponents[mod]  // vote off someone random
  }

  String getName() { 
    return "Rupert Random"
  }

  void onGamePlayed(Decision opponentDecision) { 

  }

  void onNewOpponent(String opponentName) { 
    opponents << opponentName
  }

  void onNewRound(int numGames) { 
    opponents = []
  }


}