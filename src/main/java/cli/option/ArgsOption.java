package cli.option;

import cli.base.Command;

/**
 * @Auther: wAnG
 * @Date: 2022/7/6 23:31
 * @Description:
 */
public interface ArgsOption {

    int Level();

    Command process(Command command);

}
