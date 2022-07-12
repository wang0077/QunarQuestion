package cli.actuator.manage;

import cli.actuator.base.Actuator;
import cli.actuator.catActuator;
import cli.actuator.grepActuator;
import cli.actuator.wcActuator;
import cli.base.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wAnG
 * @Date: 2022/7/7 18:35
 * @Description:
 */

public class commandManage {

    private static Map<String, Actuator> commandMap = new HashMap<>();

    static {
        commandMap.put("cat",new catActuator());
        commandMap.put("grep",new grepActuator());
        commandMap.put("wc",new wcActuator());
    }

    public static Actuator getActuator(Command command){
        return commandMap.get(command.getName());
    }

}
