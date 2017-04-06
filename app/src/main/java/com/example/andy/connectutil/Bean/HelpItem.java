package com.example.andy.connectutil.Bean;

/**
 * Created by 95815 .
 * Date:2017/4/4.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class HelpItem {

    private String helpName;
    private String helpInfo;
public HelpItem(String name, String info){
    this.helpName = name;
    this.helpInfo =info;

}
    public String getHelpName() {
        return helpName;
    }

    public void setHelpName(String helpName) {
        this.helpName = helpName;
    }

    public String getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(String helpInfo) {
        this.helpInfo = helpInfo;
    }
}
