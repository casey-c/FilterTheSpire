package FilterTheSpire.factory;

import FilterTheSpire.utils.Act1Helper;
import FilterTheSpire.utils.Act2Helper;
import FilterTheSpire.utils.Act3Helper;
import FilterTheSpire.utils.ActHelper;
import org.apache.commons.lang3.NotImplementedException;

public class MonsterListHelperFactory {
    public static ActHelper getMonsterListHelperFromActNumber(int actNumber) {
        switch (actNumber) {
            case 1:
                return Act1Helper.getInstance();
            case 2:
                return Act2Helper.getInstance();
            case 3:
                return Act3Helper.getInstance();
            default:
                throw new NotImplementedException("Must use Acts 1 through 3");
        }
    }
}
