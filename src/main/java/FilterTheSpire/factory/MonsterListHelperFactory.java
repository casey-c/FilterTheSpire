package FilterTheSpire.factory;

import FilterTheSpire.utils.helpers.Act1MonsterListHelper;
import FilterTheSpire.utils.helpers.Act2MonsterListHelper;
import FilterTheSpire.utils.helpers.Act3MonsterListHelper;
import FilterTheSpire.utils.helpers.MonsterListHelper;
import org.apache.commons.lang3.NotImplementedException;

public class MonsterListHelperFactory {
    public static MonsterListHelper getMonsterListHelperFromActNumber(int actNumber) {
        switch (actNumber) {
            case 1:
                return Act1MonsterListHelper.getInstance();
            case 2:
                return Act2MonsterListHelper.getInstance();
            case 3:
                return Act3MonsterListHelper.getInstance();
            default:
                throw new NotImplementedException("Must use Acts 1 through 3");
        }
    }
}
