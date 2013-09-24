package j;

import java.util.ArrayList;

import com.summa.summit.spd.Decision;
import com.summa.summit.spd.Player;

public class m implements Player {
	ArrayList<PlayHistory> history = new ArrayList();
	PlayHistory currentHistory = null;
	Decision myCurrentDecision = null;

	@Override
	public String getName() {
		return "SummaNoid 3000";
	}

	@Override
	public void onGamePlayed(Decision result) {
		currentHistory.addDecision(myCurrentDecision, result);
	}

	@Override
	public void onNewOpponent(String opponentName) {
		for (PlayHistory singleHistory : history) {
			if (singleHistory.getPlayerName().equals(opponentName)) {
				currentHistory = singleHistory;
				return;
			}
		}

		PlayHistory newOp = new PlayHistory();
		newOp.setPlayerName(opponentName);
		history.add(newOp);
		currentHistory = newOp;
	}

	@Override
	public void onNewRound(int roundNumber) {
		// history.clear();
	}

	@Override
	public Decision play() {

		DecisionSet lastDecision = currentHistory.getLastDecision();
		if (lastDecision != null) {
			myCurrentDecision = lastDecision.theirDecision;
		} else {
			myCurrentDecision = Decision.Cooperate;
		}

		return myCurrentDecision;
	}

	@Override
	public String voteOffIsland() {
		int maxPlayCount = 0;
		for (PlayHistory singleHistory : history) {
			if (singleHistory.getPlayCount() > maxPlayCount)
				maxPlayCount = singleHistory.getPlayCount();
		}

		String worstPlayer = "";
		int worstPlayerDefectCount = 0;
		String secondWorstPlayer = "";

		for (PlayHistory singleHistory : history) {
			if (singleHistory.getPlayCount() >= maxPlayCount) {
				int defectCount = singleHistory.getDefectCount();
				if (defectCount > worstPlayerDefectCount) {
					worstPlayerDefectCount = defectCount;
					secondWorstPlayer = worstPlayer;					
					worstPlayer = singleHistory.getPlayerName();
					
					if(secondWorstPlayer.equals("")){
						secondWorstPlayer = worstPlayer;
					}
				}
			}
		}
		
		return secondWorstPlayer;
	}

}
