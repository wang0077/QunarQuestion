package cli.actuator;

import cli.actuator.base.abstractActuator;
import cli.base.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: wAnG
 * @Date: 2022/7/6 23:02
 * @Description:
 */

public class grepActuator extends abstractActuator {

    private static final String COMMAND_NAME = "grep";

    private static final String PATH = "src/main/resources/";

    @Override
    public Command execute(Command command) {
        String target = command.getTarget().get(0);
        List<String> resources = command.getResource();
        List<String> result;
        result = resources.stream()
                .filter(item -> item.contains(target))
                .collect(Collectors.toList());
        command.setResult(result);
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
