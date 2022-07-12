package cli.actuator;

import cli.actuator.base.abstractActuator;
import cli.actuator.manage.commandManage;
import cli.base.Command;
import cli.parser.DefaultParser;
import com.google.common.base.Joiner;

import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/7/7 18:31
 * @Description:
 */

public class CliHandler {

    private final DefaultParser defaultParser = new DefaultParser();

    public String handler(String statement){
        if(statement.length() == 0){
            return "";
        }

        List<Command> parser = defaultParser.parser(statement);
        boolean isFirst = true;
        Command result = null;
        for (Command command : parser){

            if(!isFirst && result != null){
                command.setResource(result.getResult());
                command.setFileName(result.getFileName());
            }

            abstractActuator actuator = (abstractActuator) commandManage.getActuator(command);
            if(actuator == null){
                System.out.println("command not found: " + command.getName());
                return "";
            }
            if(actuator.match(command)){
                try {
                    result = actuator.operation(command);
                } catch (Exception e) {
                    return "";
                }
            }
            isFirst = false;
        }
        if (result == null){
            return "";
        }
        return Joiner.on("\n").join(result.getResult());
    }

}
