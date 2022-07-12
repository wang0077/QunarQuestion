package cli.actuator.base;

import cli.argsOption.ArgsManage;
import cli.base.Command;
import cli.option.AfterArgsOption;
import cli.option.BeforeArgsOption;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: wAnG
 * @Date: 2022/7/8 22:17
 * @Description:
 */

public abstract class abstractActuator implements Actuator {

    protected abstract Command execute(Command command) throws Exception;

    protected void SkipCommonOperation(Command command) {

    }

    public Command operation(Command command) throws Exception {
        command.addResource(getResources(command));
        boolean isNext = true;
        isNext = preOperation(command, isNext);
        if (!isNext) {
            SkipCommonOperation(command);
        }else {
            command = execute(command);
        }
        AfterOperation(command);
        return command;
    }

    protected boolean preOperation(Command command, boolean isNext) {
        List<BeforeArgsOption> beforeArgsOption = ArgsManage.getBeforeArgsOption(command);
        if (beforeArgsOption == null || beforeArgsOption.size() == 0) {
            return isNext;
        }
        beforeArgsOption = beforeArgsOption.stream().filter(option ->
                        command.getArgs().stream()
                                .anyMatch(option::isMatch))
                .collect(Collectors.toList());

        beforeArgsOption.sort(Comparator.comparingInt(BeforeArgsOption::Level));

        for (BeforeArgsOption option : beforeArgsOption) {
            option.process(command);
            if (isNext) {
                isNext = option.isNext();
            }
        }
        return isNext;
    }

    protected void AfterOperation(Command command) {
        List<AfterArgsOption> afterArgsOption = ArgsManage.getAfterArgsOption(command);
        if (afterArgsOption == null || afterArgsOption.size() == 0) {
            return;
        }

        afterArgsOption = afterArgsOption.stream().filter(option ->
                        command.getArgs().stream()
                                .anyMatch(option::isMatch))
                .collect(Collectors.toList());

        afterArgsOption.sort(Comparator.comparingInt(AfterArgsOption::Level));

        for (AfterArgsOption option : afterArgsOption) {
            option.process(command);
        }
    }


    protected List<String> getResources(Command command) {
        if (command.getFileName().size() > 0 && !command.isFileOpen() && command.getFileName().stream().allMatch(this::isFile)) {
            return command.getFileName().stream().map(this::openFile).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return null;
    }

    protected List<String> openFile(String fileName) {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(getPath() + fileName));
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(getCommandName() + " " + fileName + " : " + "No such file or directory");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private boolean isFile(String fileName) {
        File file = new File(getPath() + fileName);
        return file.exists();
    }

    protected abstract String getPath();

    protected abstract String getCommandName();

}
