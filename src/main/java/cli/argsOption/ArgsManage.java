package cli.argsOption;

import cli.argsOption.grepArgs.wc_c;
import cli.argsOption.grepArgs.wc_i;
import cli.base.Command;
import cli.option.AfterArgsOption;
import cli.option.BeforeArgsOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wAnG
 * @Date: 2022/7/7 16:49
 * @Description:
 */

public class ArgsManage {

    private static final Map<String, List<BeforeArgsOption>> beforeOption = new HashMap<>();

    private static final Map<String, List<AfterArgsOption>> afterOption = new HashMap<>();

    static {
        List<BeforeArgsOption> beforeArgsOptions = new ArrayList<>();
        beforeArgsOptions.add(new wc_i());
        beforeArgsOptions.add(new wc_c());
        beforeOption.put("wc",beforeArgsOptions);
    }

    public static List<BeforeArgsOption> getBeforeArgsOption(Command command){
        return beforeOption.get(command.getName());
    }

    public static List<AfterArgsOption> getAfterArgsOption(Command command){
        return afterOption.get(command.getName());
    }

}
