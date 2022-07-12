package cli.parser;

import cli.base.Args;
import cli.base.Command;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: wAnG
 * @Date: 2022/6/30 22:10
 * @Description:
 */

public class DefaultParser implements Parser {

    private static boolean isFirst = true;

    @Override
    public List<Command> parser(String commandStr) {
        isFirst = true;
        ArrayList<String> commands = Lists.newArrayList(Splitter.on("|").split(commandStr));
        return commands.stream()
                .map(this::parserCommand)
                .collect(Collectors.toList());
    }

    public Command parserCommand(String command) {
        ArrayList<String> block = Lists.newArrayList(Splitter.on(" ").omitEmptyStrings().split(command));
        String opName = block.get(0);
        String args = null;
        String target = null;
        String fileName = null;
        Command resultCommand = new Command(opName);
        boolean isFileOpen = true;
        if(block.size() > 1){
            args = block.get(1);
            if(args.contains("-")){
                if(block.size() == 3){
                    if(isFirst){
                        fileName = block.get(2);
                    }else {
                        target = block.get(2);
                    }
                }else if (block.size() == 4){
                    target = block.get(2);
                    fileName = block.get(3);
                }
            }else {
                fileName = block.get(1);
                args = null;
                if(block.size() == 3){
                    target = fileName;
                    fileName = block.get(2);
                }else if(!isFirst && block.size() == 2){
                    target = fileName;
                    fileName = null;
                }
            }
            if(fileName != null){
                resultCommand.addFileName(fileName);
            }

            if(target != null){
                resultCommand.addTarget(target);
            }
            if (args != null) {
                for (int i = 1; i < args.length(); i++) {
                    resultCommand.addArgs(new Args(String.valueOf(args.charAt(i))));
                }
            }
        }
        if(isFirst){
            resultCommand.setFileOpen(false);
        }
        isFirst = false;
        return resultCommand;
    }

}
