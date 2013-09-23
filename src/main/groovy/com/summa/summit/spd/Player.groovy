package com.summa.summit.spd

public interface Player { 

   Decision play()

   String voteOffIsland()

   String getName()

   void onNewRound(int numGames)

   void onNewOpponent(String opponentId)

   void onGamePlayed(Decision opponentsDecision)
}