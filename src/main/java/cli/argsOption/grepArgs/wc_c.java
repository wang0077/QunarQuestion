package cli.argsOption.grepArgs;

import cli.base.Command;
import cli.option.BeforeArgsOption;
import com.google.common.collect.Lists;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/7/9 16:22
 * @Description:
 */

public class wc_c extends BeforeArgsOption {

    private static final String ARGS = "c";

    @Override
    public Command process(Command command) {
        List<String> resource = command.getResource();
        StringBuilder builder = new StringBuilder();
        if(command.getResult().size() > 0){
            builder.append(command.getResult().get(0));
        }
        builder.append(resource.stream().mapToInt(item -> item.getBytes().length).sum()).append(" ");
        command.setResult(Lists.newArrayList(builder.toString()));
        return command;
    }

    @Override
    protected String getArgs() {
        return ARGS;
    }

    @Override
    public boolean isNext() {
        return false;
    }

    @Override
    public int Level() {
        return 20;
    }
}
