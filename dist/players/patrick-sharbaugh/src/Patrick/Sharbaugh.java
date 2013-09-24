package Patrick;

import com.summa.summit.spd.Decision;
import com.summa.summit.spd.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class Sharbaugh implements Player
{
    private HashMap<String, StateManager> _otherPlayers = new HashMap<String, StateManager>();
    private ArrayList<String> _playersPlayedThisRound = new ArrayList<String>();
    private String _currentOpponent = "";

    @Override
    public Decision play()
    {
        return _otherPlayers.get(_currentOpponent).play();
    }

    @Override
    public String voteOffIsland()
    {
        float maxScore = 0;
        String maxName = "";
        
        for (int i = 0; i < _playersPlayedThisRound.size(); i++)
        {
            float currentScore = _otherPlayers.get(_playersPlayedThisRound.get(i)).getUnpredicability();
            
            if (currentScore > maxScore) {
                maxScore = currentScore;
                maxName = _playersPlayedThisRound.get(i);
            }            
        }
        
        return maxName;
    }

    @Override
    public String getName()
    {
        return "Sharbaugh";
    }

    @Override
    public void onNewRound(int i)
    {
        _playersPlayedThisRound.clear();
    }

    @Override
    public void onNewOpponent(String name)
    {
        if (!_otherPlayers.containsKey(name)) {
            _otherPlayers.put(name, new StateManager());
        }
        
        _currentOpponent = name;
    }

    @Override
    public void onGamePlayed(Decision dcsn)
    {
        _otherPlayers.get(_currentOpponent).onGamePlayed(dcsn);
    }
    
}
