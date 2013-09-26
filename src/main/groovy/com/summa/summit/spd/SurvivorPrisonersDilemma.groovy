package com.summa.summit.spd

public class SurvivorPrisonersDilemma { 
  def players = []
  def playerScores = [:]
  def playerFiles = [:]
  def playerOpponents = [:]
  def gamesPerRound = 10
  def survivorMode = false

  public SurvivorPrisonersDilemma() { 
    players << new MrNiceGuy() << new JoeEvil() << new RupertRandom() << new BobBackstabber()
  }

  void initPlayerFiles() {
    playerFiles = [:]
    players.each { p ->
      def file = new File("output/${p.getName()}.log")
      playerFiles << [(p.getName()) : new groovy.io.GroovyPrintStream(file)] 
    }
  }

  void initPlayerLists() { 
    playerScores = [:]
    playerOpponents = [:]
    players.each { p ->
      playerScores[p.getName()] = 0
      playerOpponents[p.getName()] = []
      p.onNewRound(gamesPerRound)
    }
  }

  void log(Player p1, Player p2, String out) { 
    playerFiles[p1.getName()].println out
    playerFiles[p2.getName()].println out
  }

  void log(Player p1, Player p2, String s, List list) { 
    playerFiles[p1.getName()].printf s list
    playerFiles[p2.getName()].printf s list
  }

  Set<String> play(int roundNum) { 
    
    println "\n\n"
    println "*" * 45
    println "* ROUND ${roundNum}"
    println "*" * 45

    initPlayerLists()

    players.each { p1 ->

      def opponents = []

      players.each { p2 ->
        if(p1.getName() == p2.getName()) return
        if(playerOpponents[p2.getName()].contains(p1.getName())) return

        opponents << p2.getName()

        p1.onNewOpponent(p2.getName())
        p2.onNewOpponent(p1.getName())

        int p1TotalScore = 0
        int p2TotalScore = 0
        def games = 1..gamesPerRound
        log(p1, p2, "\n\nROUND ${roundNum} - ${p1.name} (P1) vs. ${p2.name} (P2)\n")
        def header = ["Game", "P1 Dec", "P2 Dec", "P1 Score", "P2 Score"]
        header.each { 
          playerFiles[p1.getName()].printf "%-9s", [it] 
          playerFiles[p2.getName()].printf "%-9s", [it]
        }
        log(p1, p2, "")
        log(p1, p2, "-" * 9 * 5)

        games.each { game ->
          Decision p1Decision = p1.play()
          Decision p2Decision = p2.play()
          p1.onGamePlayed(p2Decision)
          p2.onGamePlayed(p1Decision)
          int p1Score = calculateScore(p1Decision, p2Decision)
          int p2Score = calculateScore(p2Decision, p1Decision)
          String p1DecisionCode = getDecisionCode(p1Decision)
          String p2DecisionCode = getDecisionCode(p2Decision)
          p1TotalScore += p1Score
          p2TotalScore += p2Score
          def out = [game, p1DecisionCode, p2DecisionCode, p1Score, p2Score]
          out.each { 
            playerFiles[p1.getName()].printf "%-9s", [it] 
            playerFiles[p2.getName()].printf "%-9s", [it]
          }
          log(p1, p2, "")
        }
        log(p1, p2, "-" * 9 * 5)
        def totals = ["", "", "", p1TotalScore, p2TotalScore]
        totals.each { 
          playerFiles[p1.getName()].printf "%-9s", [it] 
          playerFiles[p2.getName()].printf "%-9s", [it]
        }
        log(p1, p2, "")
        playerScores[p1.getName()] += p1TotalScore
        playerScores[p2.getName()] += p2TotalScore
      }

      playerOpponents[p1.getName()] = opponents
    }

    println "\n\nSCORES"
    println "-" * 9 * 5

    playerScores.sort { it.value }.each { k, v -> println "${k}: ${v} "}
    int max = playerScores.max { it.value }.value
    def roundWinners = []
    playerScores.each { k, v ->
      if(v == max) roundWinners << k
    }

    return roundWinners
  }

  String getDecisionCode(Decision decision) { 
    return decision.toString().substring(0, 1)
  }

  void voteOffIsland(Set<String> roundWinners) {
    def votes = [:]
    players.each { p ->
      votes << [(p.getName()) : 0]
    }

    // Figure out who everyone voted off the island
    players.each { p -> 
      String votee = p.voteOffIsland()
      if(votee != null && votee != '' && votes[votee] != null) { 
        votes[votee]++
      }
    }

    // Make the winners exempt from voting off the island
    println "\n\nVOTES"
    println "-" * 45
    roundWinners.each { roundWinner ->
      println "${roundWinner}: ${votes[roundWinner]} (exempt)"
      votes.remove(roundWinner)
    }
    
    // If everyone tied and no more people to vote off, then declare the winner!
    if(votes.isEmpty()) { 
      println "\n\nWINNERS: ${roundWinners}"
      players = []
      return
    }

    // Vote people off the island!
    def voteOffs = [:]
    def maxVotes = votes.max { it.value }.value
    votes.sort{ it.value }.each { k, v -> 
      println "${k}: ${v}" 
      if(v == maxVotes) voteOffs << [(k) : playerScores[k]]
    }

    def votedOff = voteOffs.max { it.value }.key

    // Print out the round summary stuff - round winners and voted off
    println "\n"
    roundWinners.each { roundWinner ->
      println "Round Winner: ${roundWinner}"
    }
    println "Voted Off: ${votedOff}"

    // Set up the players for next round (excluding the guy who got voted off)
    def nextRoundPlayers = []
    players.each { p ->
      if(roundWinners.contains(p.getName()) || p.getName() != votedOff) {
        nextRoundPlayers << p
      } 
    }

    players = nextRoundPlayers
  }

  int calculateScore(Decision p1Decision, Decision p2Decision) { 
    if(p1Decision == Decision.Cooperate) { 
      return p2Decision == Decision.Cooperate ? 3 : 1
    }
    else { 
      return p2Decision == Decision.Cooperate ? 4 : 2
    }
  }


  public static void main(String [] args) { 
    def game = new SurvivorPrisonersDilemma()

    args.each { game.addPlayer(it) }

    println "\nWELCOME TO SURVIVOR PRISONER'S DILEMMA!\n"

    game.gameLoop()
  }


  void gameLoop() { 
    printMainMenu()
    def choice = System.console().readLine(">")

    switch(choice) { 
      case "1":
        printPlayers()
        break;
      case "2":
        addPlayer()
        break;
      case "3":
        removePlayer()
        break;
      case "4":
        setNumGamesPerRound()
        break
      case "5":
        setSurvivorMode()
        break
      case "6":
        initPlayerFiles()
        if(!survivorMode) play(1)
        else {        
          for(int i=1; players.size() > 1; i++) { 
            voteOffIsland(play(i))
          }
        }
        break;
      case "7":
        System.exit(0)
      default:
        println "Invalid choice! "
        gameLoop()
    }

  }

  void removePlayer() {
    println "\n\n REMOVE PLAYER"
    println "-" * 45
    println "Type the name of the player to remove\n"

    def playerName = System.console().readLine(">")
    def removePlayer = null
    players.each { p ->
      if(p.getName() == playerName) { 
        removePlayer = p
      }
    }

    if(removePlayer == null) println "No player ${playerName} exists!"
    else players.remove(removePlayer)

    gameLoop()
  }

  void addPlayer() { 
    println "\n\n ADD PLAYER"
    println "-" * 45
    println "Type the fully qualified class name of the player (e.g. com.summa.summit.spd.MrNiceGuy)\n"

    addPlayer(System.console().readLine(">"))
    gameLoop()
  }

  void addPlayer(String playerClassName) { 
    try { 
      def newPlayer = Class.forName(playerClassName).newInstance()
      if(players.findAll { it.getName() == newPlayer.getName() }.size() > 0) { 
        println "Player already exists!"
      } else { 
        players << newPlayer
      }
    }
    catch(Exception e) { 
      println "Invalid class name! "
    }
  }

  void printPlayers() { 
    println "\n\n VIEW PLAYERS"
    println "-" * 45
    players.each { p -> 
      println p.getName()
    }
    println "\n\nType 'go' to continue..."
    System.console().readLine(">")
    gameLoop()
  }

  void setNumGamesPerRound() { 

    println "\n\n SET # GAMES PER ROUND"
    println "-" * 45
    println "Enter the number of games to play per round (currently ${gamesPerRound})"

    String num = System.console().readLine(">")

    try { 
      gamesPerRound = Integer.parseInt(num)
    }
    catch (Exception e) { 
      println "\nInvalid number!"
    }

    gameLoop()

  }

  void setSurvivorMode() { 
    println "\n\n SET SURVIVOR MODE"
    println "-" * 45
    println "Type 'yes' or 'no'"

    String inSurvivorMode = System.console().readLine(">")

    survivorMode = inSurvivorMode == 'yes'
    gameLoop()
  }  

  void printMainMenu() { 
    println """

MAIN MENU
-----------------------------
1) View players
2) Add players
3) Remove player
4) Set # games per round (current = ${gamesPerRound})
5) Set survivor mode (current = ${survivorMode})
6) Play!
7) Exit

"""
  }

}
