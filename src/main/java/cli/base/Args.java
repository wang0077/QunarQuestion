package cli.base;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

/**
 * @Auther: wAnG
 * @Date: 2022/7/6 23:52
 * @Description:
 */

@Getter
@Setter
@ToString
public class Args {

    private String argsName;

    private String argsMatchName;

    public Args(){};

    public Args(String argsName){
        this.argsName = argsName;
    }
}
