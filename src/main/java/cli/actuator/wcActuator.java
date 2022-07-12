package cli.actuator;

import cli.actuator.base.abstractActuator;
import cli.base.Command;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/7/7 19:32
 * @Description:
 */

public class wcActuator extends abstractActuator {

    private static final String COMMAND_NAME = "wc";

    private static final String PATH = "src/main/resources/";

    @Override
    public Command execute(Command command) {
        List<String> resource = getResources(command);
        int lineCount = resource.size();
        long byteCount = resource.stream()
                .mapToInt(item -> item.getBytes().length)
                .sum();
        long charCount = resource.stream().mapToInt(String::length).sum();
        StringBuilder builder = new StringBuilder();
        builder.append(lineCount).append(" ")
                .append(byteCount).append(" ")
                .append(charCount).append(" ");
        command.getFileName().forEach(fileName -> builder.append(fileName).append(" "));
        command.addResult(builder.toString());
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

    @Override
    protected void SkipCommonOperation(Command command) {
        String result = command.getResult().get(0);
        StringBuilder builder = new StringBuilder(result);
        command.getFileName().forEach(fileName -> builder.append(fileName).append(" "));
        command.setResult(Lists.newArrayList(builder.toString()));
    }
}
