package com.twu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        //管理员账户设置
        Admin admin = new Admin("Admin","123");
        List<HotSearch> hotSearch = new ArrayList<HotSearch>();
        hotSearch.add(new HotSearch("我在那儿",1));
        List<Users> users = new ArrayList<Users>();
        //登录界面
        String name = LoadIn(admin, users);
        //热搜主界面
        while (true){
            if(name.equals("Admin")){
                name = AdminInterface(admin, hotSearch, users, name);
            }else {
                name = UsersInterface(admin, hotSearch, users, name);
            }
        }
    }

    private static String UsersInterface(Admin admin, List<HotSearch> hotSearch, List<Users> users, String name) throws IOException {
        System.out.println("-----欢迎【" + name + "】来到热搜排行榜-----");
        System.out.println("1 查看热搜排行榜");
        System.out.println("2 给热搜事件投票");
        System.out.println("3 购买热搜");
        System.out.println("4 添加热搜");
        System.out.println("5 退出");
        System.out.println("请输入你的选择:");
        BufferedReader hotBR = new BufferedReader(new InputStreamReader(System.in));
        String hotChoice = hotBR.readLine();
        switch (hotChoice){
            case "1":
                //查看热搜
                System.out.println("-排名-----事件-----热度-");
                ViewHotSearch(hotSearch);
                break;
            case "2":
                //投票热搜
                VoteHotSearch(name, hotSearch, users);
                break;
            case "3":
                //购买热搜
                break;
            case "4":
                //添加热搜
                AddHotSearch(hotSearch,1);
                break;
            case "5":
                //退出
                name = LoadIn(admin, users);
                break;
            default:
                System.out.println("输入无效请重新选择");
                break;
        }
        return name;
    }

    private static String AdminInterface(Admin admin, List<HotSearch> hotSearch, List<Users> users, String name) throws IOException {
        System.out.println("-----欢迎【" + name + "】来到热搜管理系统-----");
        System.out.println("1 查看热搜排行榜");
        System.out.println("2 添加热搜");
        System.out.println("3 添加超级热搜");
        System.out.println("4 退出");
        System.out.println("请输入你的选择:");
        BufferedReader hotBR = new BufferedReader(new InputStreamReader(System.in));
        String hotChoice = hotBR.readLine();
        switch (hotChoice){
            case "1":
                //查看热搜
                System.out.println("-排名-----事件-----热度-");
                ViewHotSearch(hotSearch);
                break;
            case "2":
                //添加热搜
                AddHotSearch(hotSearch,1);
                break;
            case "3":
                //添加超级热搜
                AddHotSearch(hotSearch,2);
                break;
            case "4":
                //退出
                name = LoadIn(admin, users);
                break;
            default:
                System.out.println("输入无效请重新选择");
                break;
        }
        return name;
    }

    private static String LoadIn(Admin admin, List<Users> users) throws IOException {
        boolean flag = true;
        String name = "";
        while(flag){
            //登录界面
            System.out.println("-----欢迎来到热搜排行榜，你是？-----");
            System.out.println("1 用户");
            System.out.println("2 管理员");
            System.out.println("3 退出");
            System.out.println("请输入你的选择:");
            BufferedReader loadBR = new BufferedReader(new InputStreamReader(System.in));
            String loadChoice = loadBR.readLine();
            switch (loadChoice){
                case "1":
                    //用户
                    boolean nameFlag = false;
                    System.out.println("请输入您的昵称:");
                    name = loadBR.readLine();
                    if (users.isEmpty()){
                        Users hotUser = new Users(name);
                        users.add(hotUser);
                        System.out.println("欢迎您新用户，登陆成功！");
                    }else {
                        for (Users i: users){
                            if (i.getName().equals(name)){
                                nameFlag = true;
                                break;
                            }
                        }
                        if (nameFlag){
                            System.out.println("用户已存在，登录成功！");
                        }else {
                            Users hotUser = new Users(name);
                            users.add(hotUser);
                            System.out.println("欢迎您新用户，登陆成功！");
                        }
                    }
                    flag = false;
                    break;
                case "2":
                    //管理员
                    while (true){
                        System.out.println("请输入您的账号:");
                        String account = loadBR.readLine();
                        System.out.println("请输入您的密码:");
                        String password = loadBR.readLine();
                        if (admin.getName().equals(account)&& admin.getPassWord().equals(password)){
                            System.out.println("登陆成功!");
                            flag = false;
                            name = "Admin";
                            break;
                        }
                        System.out.println("账号密码不正确，请重新登录");
                    }
                    break;
                case "3":
                    //退出
                default:
                    System.out.println("谢谢你的使用");
                    System.exit(0);
                    break;
            }
        }
        return name;
    }

    private static void ViewHotSearch(List<HotSearch> hotSearch) {
        Stream.iterate(0,i -> i+1).limit(hotSearch.size()).forEach(i -> {
            System.out.println("  " + (i+1) + "    "+ hotSearch.get(i).getName() + "    "+ hotSearch.get(i).getPoll());
        });
    }

    private static void VoteHotSearch(String name, List<HotSearch> hotSearch, List<Users> users) throws IOException {
        Integer poll = 0;
        for(Users i : users){
            if (i.getName().equals(name)){
                poll = i.getUserPoll();
            }
        }
        System.out.println("请输入你要投票的热搜名称:");
        BufferedReader voteInput = new BufferedReader(new InputStreamReader(System.in));
        String voteName = voteInput.readLine();
        boolean flag = false;
        for(HotSearch i: hotSearch){
            if(i.getName().equals(voteName)){
                flag = true;
                break;
            }
        }
        if (flag){
            System.out.println("请输入你要投票的热搜名称（您还有" + poll + "票）");
            String votePoll = voteInput.readLine();

            Integer votePollInt = new Integer(votePoll);
            if (new Integer(votePoll) > poll){
                System.out.println("投票票数大于已拥有，投票失败！");
            }else{
                for(Users i : users){
                    if (i.getName().equals(name)){
                        i.setUserPoll(i.getUserPoll()-votePollInt);
                    }
                }
                for(HotSearch i: hotSearch){
                    if (i.getName().equals(voteName)){
                        i.setPoll(i.getLevel()*(i.getPoll()+votePollInt));
                    }
                }
                Collections.sort(hotSearch);
            }
        }else {
            System.out.println("不存在该热搜！");
        }
    }

    private static  void AddHotSearch(List<HotSearch> hotSearches, int level) throws IOException {
        boolean flag = false;
        System.out.println("请输入你要添加的热搜名称:");
        BufferedReader hotInput = new BufferedReader(new InputStreamReader(System.in));
        String hotName = hotInput.readLine();
        for(HotSearch i: hotSearches){
            if(i.getName().equals(hotName)){
                flag = true;
                break;
            }
        }
        if (flag){
            System.out.println("该热搜已存在，不可重复添加");
        }else {
            hotSearches.add(new HotSearch(hotName, level));
        }
    }
}

