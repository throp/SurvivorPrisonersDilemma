package com.summa.summit.spd

public class Samuel implements Player { 

  def opponents = [:]
  def currentOpponent = ""

  def C = Decision.Cooperate
  def D = Decision.Defect

  def dictionaryDictionary = [:]
  def moveDictionary = [:]
  def moveBuffer = []

  def ourLastDecision = null

  Random rand = new Random()

  def getEV = { us, them ->
    (us && them) ? 3 : (us ? 1 : (them ? 4 : 2) )
  }

  def themEV = { us, them->
    (them && us) ? 3 : (them ? 1 : (us ? 4 : 2) )
  }

  Decision play() { 
    return calcEV(getTheirNextMove())
  }

  String voteOffIsland() { 
    opponents.isEmpty() ? currentOpponent : opponents.max{it.value}.key
  }

  String getName() { 
    "SAY WHAT AGAIN!"
  }

  def calcEV = { move ->
    if(move==null){ move = rand.nextBoolean() }
    def EVC = getEV(C,move)
    def EVD = getEV(D,move)
    return EVC > EVD ? C : D
  }

  def getTheirNextMove = {
    int hash = 0
    for(int i = 0; i < 31; i++) {
      if(moveBuffer[i])
        hash++
      hash *= 2
    }
    if(moveBuffer[31])
      hash++
    if(moveDictionary[hash])
      System.out.println("____________________________________________________________________________________________")
    return moveDictionary[hash]
  }

  def bufferToInt = {
    int hash = 0
    for(int i = 2; i < 33; i++) {
      if(moveBuffer[i]) { hash++ }
        hash *= 2
    }
    if(moveBuffer[33])
      hash++
  }

  void onGamePlayed(Decision oppDecision) { 
      moveBuffer.add(0, oppDecision == C)
      moveBuffer.add(0, ourLastDecision == C)

      if(moveBuffer.size() > 32) {
        moveDictionary[bufferToInt()] = oppDecision
        moveBuffer.pop()
        moveBuffer.pop()
      }

      opponents[currentOpponent] += themEV(ourLastDecision == C, oppDecision == C)
  }

  void onNewOpponent(String opponentName) {
    if(!dictionaryDictionary[currentOpponent]) { dictionaryDictionary[currentOpponent] = moveDictionary }
    moveBuffer = []
    moveDictionary = [:]

    currentOpponent = opponentName
    opponents << [(currentOpponent) : 0]
  }

  void onNewRound(int numGames) { 
    currentOpponent = ""
    opponents.each{it = 0}
  }

}