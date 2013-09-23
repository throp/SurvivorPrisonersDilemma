package com.summa.summit.spd

public class SurvivorPrisonersDilemma { 
  def players = []
  def gamesPerRound = 10
  def survivorMode = false

  public SurvivorPrisonersDilemma() { 
    players << new MrNiceGuy() << new JoeEvil() << new RupertRandom() << new BobBackstabber()
  }

  String play(int roundNum) { 

    println "\n\nROUND ${roundNum}"
    println "-" * 45

    def playerScores = [:]
    def playerOpponents = [:]
    players.each { p ->
      playerScores[p.getName()] = 0
      playerOpponents[p.getName()] = []
      p.onNewRound(gamesPerRound)
    }

    players.each { p1 ->

      def opponents = []

      players.each { p2 ->
        if(p1.getName() == p2.getName()) { 
          return
        }
        if(playerOpponents[p2.getName()].contains(p1.getName())) { 
          return
        }

        opponents << p2.getName()

        p1.onNewOpponent(p2.getName())
        p2.onNewOpponent(p1.getName())

        int p1TotalScore = 0
        int p2TotalScore = 0
        def games = 1..gamesPerRound
        println "\n\n${p1.name} (P1) vs. ${p2.name} (P2)\n"
        def header = ["Game", "P1 Dec", "P2 Dec", "P1 Score", "P2 Score"]
        header.each { printf "%-9s", [it]}
        println ""
        println "-" * 9 * 5
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
          out.each { printf "%-9s", [it]}
          println ""
        }
        println "-" * 9 * 5
        def totals = ["", "", "", p1TotalScore, p2TotalScore]
        totals.each { printf "%-9s", [it]}
        println ""
        playerScores[p1.getName()] += p1TotalScore
        playerScores[p2.getName()] += p2TotalScore
      }

      playerOpponents[p1.getName()] = opponents
    }

    println "\n\nSCORES"
    println "-" * 9 * 5

    playerScores.sort { it.value }
    int max = 0
    String roundWinner = ""
    playerScores.each { k, v -> 
      println "${k}: ${v}"
      if(v > max) { 
        max = v
        roundWinner = k
      }
    }

    return roundWinner
  }

  String getDecisionCode(Decision decision) { 
    return decision.toString().substring(0, 1)
  }

  void voteOffIsland(String roundWinner) {
    def votes = [:]
    players.each { p ->
      votes << [(p.getName()) : 0]
    }

    players.each { p -> 
      String votee = p.voteOffIsland()
      if(votee != null && votee != '' && votes[votee] != null) { 
        votes[votee]++
      }
    }

    println "\n\nVOTES"
    println "-" * 45
    println "${roundWinner}: ${votes[roundWinner]} (exempt)"
    votes.remove(roundWinner)
    votes.each { k, v -> println "${k}: ${v}" }

    String votedOff = votes.max { it.value }.key


    println "\n\nRound Winner: ${roundWinner}"
    println "Voted Off: ${votedOff}"

    def nextRoundPlayers = []
    players.each { p ->
      if(p.getName() == roundWinner || p.getName() != votedOff) {
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
        setNumGamesPerRound()
        break
      case "4":
        setSurvivorMode()
        break
      case "5":
        if(!survivorMode) play(1)
        else {        
          for(int i=1; players.size() > 1; i++) { 
            voteOffIsland(play(i))
          }
        }
        break;
      case "6":
        System.exit(0)
      default:
        println "Invalid choice! "
        gameLoop()
    }

  }

  void addPlayer() { 
    println "\n\n ADD PLAYER"
    println "-" * 45
    println "Type the fully qualified class name of the player (e.g. com.summa.summit.spd.MrNiceGuy)\n"

    String playerClassName = System.console().readLine(">")

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

    gameLoop()
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
3) Set # games per round (current = ${gamesPerRound})
4) Set survivor mode (current = ${survivorMode})
5) Play!
6) Exit

"""
  }

}
