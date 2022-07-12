package cli.option;

import cli.base.Args;

/**
 * @Auther: wAnG
 * @Date: 2022/7/6 23:33
 * @Description:
 */
public abstract class BeforeArgsOption implements ArgsOption {

    @Override
    public int Level() {
        return 100;
    }

    public boolean isMatch(Args args){
        return args.getArgsName().equals(getArgs());
    }

    protected abstract String getArgs();

    public boolean isNext(){
        return true;
    }
}
