package Adamo;

import com.summa.summit.spd.Decision;
import com.summa.summit.spd.Player;
import java.util.ArrayList;


public class Mosca implements Player{
	ArrayList<Opponent> ops = new ArrayList<Opponent>();
	Opponent current;
	int x = 0;
	
	@Override
	public String getName() {
		return "Adamo";
	}

	@Override
	public void onGamePlayed(Decision result) {
		current.decisions.add(result);
	}

	@Override
	public void onNewOpponent(String opponentName) {
		Opponent op = new Opponent();
		op.name = opponentName;
		op.points = 0;
		ops.add(op);
		current = op;
	}

	@Override
	public void onNewRound(int roundNumber) {
		System.out.print(roundNumber);
	}

	@Override
	public Decision play() {
		x++;		
		int c =0;
		int d = 0;
		
		for(Decision dec : current.decisions) {
			if (dec == Decision.Defect) {
				d++;
			}
			else {
				c++;
			}
			
			
		}
		
		if (d >= c || x%2 == 0) {
			return Decision.Defect;
		}
		
		return Decision.Cooperate;
	}

	@Override
	public String voteOffIsland() {
		Opponent v = ops.get(0);
		for (Opponent i : ops) {
			if (i.points < v.points) {
				v = i;
			}
		}
		
		return v.name;
	}
	
	private class Opponent {
		public String name;
		public int points;
		public ArrayList<Decision> decisions = new ArrayList<Decision>();
	}	

}
