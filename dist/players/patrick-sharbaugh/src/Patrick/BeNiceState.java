
package Patrick;

import com.summa.summit.spd.Decision;

class BeNiceState implements IState
{

    public BeNiceState() {}

    @Override
    public Decision play(float percentageMean)
    {
        return Decision.Cooperate;
    }
    
}
