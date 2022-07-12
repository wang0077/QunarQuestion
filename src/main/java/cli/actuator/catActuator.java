package cli.actuator;

import cli.actuator.base.abstractActuator;
import cli.base.Command;

/**
 * @Auther: wAnG
 * @Date: 2022/7/2 18:42
 * @Description:
 */

public class catActuator extends abstractActuator {

    private static final String COMMAND_NAME = "cat";

    private static final String PATH = "src/main/resources/";

    @Override
    public Command execute(Command command) throws Exception{
        command.setResult(command.getResource());
        return command;
    }

    @Override
    public boolean match(Command command) {
        return COMMAND_NAME.equals(command.getName());
    }


    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
