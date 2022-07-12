package cli.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/6/30 21:46
 * @Description:
 */

public class Option {

    private String name;

    private final List<String> args = new ArrayList<>();

    public Option(){

    }

    public Option(String name) {
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addArgs(String args){
        this.args.add(args);
    }

    public boolean hasArgs(String args){
        return this.args.contains(args);
    }
}
