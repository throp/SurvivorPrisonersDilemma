package Patrick;

import com.summa.summit.spd.Decision;

class BeMeanState implements IState
{

    public BeMeanState() { }

    @Override
    public Decision play(float percentageMean)
    {
        return Decision.Defect;
    }
    
}
