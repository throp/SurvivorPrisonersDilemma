package com.summa.summit.spd

public class BobBackstabber implements Player { 

  def opponents = []

  def game = 0

  Decision play() { 
    game++
    return game % 5 == 0 ? Decision.Defect : Decision.Cooperate
  }

  String voteOffIsland() { 
    int mod = new Date().getTime() % opponents.size()
    return opponents[mod]  // vote off someone random
  }

  String getName() { 
    return "Bob Backstabber"
  }

  void onGamePlayed(Decision opponentDecision) { 

  }

  void onNewOpponent(String opponentName) { 
    opponents << opponentName
    game = 0
  }

  void onNewRound(int numGames) { 
    opponents = []
  }


}