package Patrick;

import com.summa.summit.spd.Decision;

public class StateManager
{
    private float _timesNice = 0;
    private float _timesMean = 0;
    
    private IState _beNice = new BeNiceState();
    private IState _beMean = new BeMeanState();
    private IState _decide = new DecideState();
    
    private IState _current = _beMean;
    
    private int _timesPlayed = 0;


    public Decision play()
    {
        float percentageMean = _timesMean/(_timesMean + _timesNice);
        
        _timesPlayed++;
        
        if (_timesPlayed == 11) {
            _current = _beNice;
        } else if (_timesPlayed == 21) {
            _current = _decide;
        }
        
        return _current.play(percentageMean);
    }
    
    public void onGamePlayed(Decision dcsn)
    {
        if (dcsn == Decision.Cooperate) {
            _timesNice++;
        } else {
            _timesMean++;
        }
    }
    
    public float getUnpredicability() {
        return Math.abs(1f/((_timesMean/(_timesMean + _timesNice)) - .5f));
    }
    
   
}
