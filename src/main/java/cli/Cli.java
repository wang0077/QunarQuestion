package cli;

import cli.actuator.CliHandler;

import java.util.Scanner;

/**
 * @Auther: wAnG
 * @Date: 2022/6/30 21:44
 * @Description:
 */

public class Cli {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        CliHandler handler = new CliHandler();
        while (true){
            command = scanner.nextLine();
            System.out.println(handler.handler(command));
        }
    }
}
