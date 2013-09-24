package j;

import java.util.ArrayList;

import com.summa.summit.spd.Decision;

public class PlayHistory {
	private String playerName;
	private ArrayList<DecisionSet> decisionHistory = new ArrayList<DecisionSet>();
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void addDecision(Decision myDecision, Decision theirDecision){
		DecisionSet decisionSet = new DecisionSet();
		decisionSet.myDecision = myDecision;
		decisionSet.theirDecision = theirDecision;
		decisionHistory.add(decisionSet);
	}

	
	public DecisionSet getLastDecision(){
		int size = decisionHistory.size();
		
		if(size == 0)
			return null;
		
		DecisionSet decision = decisionHistory.get(size-1);
		return decision;
	}
	public int getDefectCount() {
		int defectCount = 0;
		for (DecisionSet singleHistory : decisionHistory) {
			if(singleHistory.theirDecision == Decision.Defect)
				defectCount++;
		}
		
		return defectCount;
	}
	public int getPlayCount() {
		return decisionHistory.size();
	}
	
}
