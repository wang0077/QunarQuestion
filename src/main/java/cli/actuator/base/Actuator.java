package cli.actuator.base;

import cli.base.Command;

import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/7/2 18:19
 * @Description:
 */
public  interface Actuator {

    Command operation(Command command) throws Exception;

    boolean match(Command command);



}
