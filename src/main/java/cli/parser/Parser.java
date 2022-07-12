package cli.parser;

import cli.base.Command;

import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/6/30 22:09
 * @Description:
 */
public interface Parser {
    public List<Command> parser(String command);
}
