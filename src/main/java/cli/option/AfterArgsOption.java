package cli.option;

import cli.base.Args;

/**
 * @Auther: wAnG
 * @Date: 2022/7/6 23:34
 * @Description:
 */
public abstract class AfterArgsOption implements ArgsOption{

    @Override
    public int Level() {
        return 100;
    }

    public boolean isMatch(Args args){
        return args.getArgsName().equals(getArgs());
    }

    protected abstract String getArgs();


}
