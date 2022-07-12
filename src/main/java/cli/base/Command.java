package cli.base;

import cli.base.Args;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wAnG
 * @Date: 2022/6/30 22:05
 * @Description:
 */

@ToString
public class Command {

    private String name;

    private final List<Args> args = new ArrayList<>();

    private final List<String> target = new ArrayList<>();

    private List<String> resource = new ArrayList<>();

    private List<String> result = new ArrayList<>();

    private List<String> fileName = new ArrayList<>();

    private boolean IsFileOpen = true;

    public Command(){};

    public Command(String name){
        this.name = name;
    }

    public boolean isFileOpen() {
        return IsFileOpen;
    }

    public void setFileOpen(boolean fileOpen) {
        IsFileOpen = fileOpen;
    }

    public String getName(){
        return this.name;
    }

    public void addArgs(Args args){
        this.args.add(args);
    }

    public void addArgs(List<Args> args){
        this.args.addAll(args);
    }

    public void addResource(String resource){
        this.resource.add(resource);
    }

    public void setResource(List<String> resource){
        this.resource = resource;
    }

    public void addResource(List<String> resource){
        if(resource == null){
            return;
        }
        this.resource.addAll(resource);
    }

    public void addResult(String result){
        this.result.add(result);
    }

    public void addFileName(String fileName){
        this.fileName.add(fileName);
    }

    public void setFileName(List<String> fileName){
        this.fileName = fileName;
    }

    public List<String> getFileName(){
        return this.fileName;
    }

    public void setResult(List<String> result){
        this.result = result;
    }

    public List<String> getResult(){
        return this.result;
    }

    public List<Args> getArgs(){
        return this.args;
    }

    public List<String> getResource(){
        return this.resource;
    }

    public List<String> getTarget(){
        return this.target;
    }

    public void addTarget(String target){
        if(target == null){
            return;
        }
        this.target.add(target);
    }
}
