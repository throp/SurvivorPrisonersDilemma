package Patrick;

import com.summa.summit.spd.Decision;
import java.util.Random;

class DecideState implements IState
{
    
    private Random random = new Random();

    public DecideState() {}

    @Override
    public Decision play(float percentageMean)
    {
        
        if (percentageMean < .25 || percentageMean > .75) {
            return Decision.Defect;
        } else  {
            float choice = random.nextFloat();
            
            if (choice <= percentageMean) {
                return Decision.Defect;
            } else {
                return Decision.Cooperate;
            }
        }
        
                
    }
    
}
