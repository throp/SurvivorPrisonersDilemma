import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.summa.summit.spd.Decision;
import com.summa.summit.spd.Player;

public class ElSanto implements Player {

	// Opponent - cooperate positive / defect negative
	Map<String, Integer> hist = new HashMap<String, Integer>();

	Map<String, Integer> currentRound = new HashMap<String, Integer>();

	String currentOpponent;

	private int numGames;

	@Override
	public String getName() {
		return "El Santo";
	}

	@Override
	public void onGamePlayed(Decision decision) {
		int diff = decision.equals(Decision.Cooperate) ? 1 : -1;
		int curr = currentRound.get(currentOpponent);
		currentRound.put(currentOpponent, curr + diff);
	}

	@Override
	public void onNewOpponent(String opp) {
		if (!hist.containsKey(opp)) {
			hist.put(opp, 0);
		}
		currentRound.put(opp, 0);

		currentOpponent = opp;
	}

	@Override
	public void onNewRound(int numGames) {
		this.numGames = numGames;
		currentRound.clear();
	}

	@Override
	public Decision play() {
		Decision decision = (currentRound.get(currentOpponent) >= 0 ? Decision.Cooperate
				: Decision.Defect);

		Decision historyDecision = (hist.get(currentOpponent) > 0 ? Decision.Cooperate
				: Decision.Defect);

		if (decision.equals(historyDecision)) {
			return decision;
		} else {
			int mod = (int) new Date().getTime() % hist.size();
			if (Math.random() > 0.5) {
				return historyDecision;
			} else {
				return decision;
			}
		}

	}

	@Override
	public String voteOffIsland() {
		String opponentToVoteOff = "";
		int minVal = 0;

		for (String opp : hist.keySet()) {

			int histDec = hist.get(opp);
			int currentDec = currentRound.get(opp) == null ? -1 : currentRound
					.get(opp);
			hist.put(opp, histDec + currentDec);

			if (hist.get(opp) <= minVal) {
				opponentToVoteOff = opp;
			}
		}

		return opponentToVoteOff;
	}

}
