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
        List<HotSearch> buyList = new ArrayList<HotSearch>();
//        hotSearch.add(new HotSearch("我在那儿",1));
//        hotSearch.add(new HotSearch("我是热搜第二",1));
//        hotSearch.add(new HotSearch("我是热搜第一",1));
//        hotSearch.add(new HotSearch("我是被购买的",1));
        List<Users> users = new ArrayList<Users>();
        //登录界面
        String name = LoadIn(admin, users);
        //热搜主界面
        while (true){
            if(name.equals("Admin")){
                name = AdminInterface(admin, hotSearch, buyList,users, name);
            }else {
                name = UsersInterface(admin, hotSearch, buyList, users, name);
            }
        }
    }

    private static String UsersInterface(Admin admin, List<HotSearch> hotSearch, List<HotSearch> buyList, List<Users> users, String name) throws IOException {
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
                ViewHotSearch(hotSearch, buyList);
                break;
            case "2":
                //投票热搜
                VoteHotSearch(name, hotSearch, buyList, users);
                break;
            case "3":
                //购买热搜
                BuyHotSearch(hotSearch, buyList);
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

    private static String AdminInterface(Admin admin, List<HotSearch> hotSearch, List<HotSearch> buyList, List<Users> users, String name) throws IOException {
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
                ViewHotSearch(hotSearch, buyList);
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

    private static void ViewHotSearch(List<HotSearch> hotSearch , List<HotSearch> buyList) {
        List<HotSearch> viewList = new ArrayList<HotSearch>();
        Stream.iterate(0, i -> i+1).limit(hotSearch.size()).forEach(i ->{
            viewList.add(hotSearch.get(i));
        } );
        Stream.iterate(0, i -> i+1).limit(buyList.size()).forEach(i -> {
            viewList.add(buyList.get(i).getRank()-1, buyList.get(i));
        });
        Stream.iterate(0,i -> i+1).limit(viewList.size()).forEach(i -> {
            System.out.println("  " + (i+1) + "    "+ viewList.get(i).getName() + "    "+ viewList.get(i).getPoll());
        });
    }

    private static void VoteHotSearch(String name, List<HotSearch> hotSearch, List<HotSearch> buyList, List<Users> users) throws IOException {
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
        for(HotSearch i: buyList){
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
                        break;
                    }
                }
                for(HotSearch i: hotSearch){
                    if (i.getName().equals(voteName)){
                        i.setPoll(i.getLevel()*(i.getPoll()+votePollInt));
                        break;
                    }
                }
                for(HotSearch i: buyList){
                    if (i.getName().equals(voteName)){
                        i.setPoll(i.getLevel()*(i.getPoll()+votePollInt));
                        break;
                    }
                }
                Collections.sort(hotSearch);
            }
        }else {
            System.out.println("不存在该热搜！");
        }
    }

    private static void AddHotSearch(List<HotSearch> hotSearches, int level) throws IOException {
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

    private static void BuyHotSearch(List<HotSearch> hotSearches, List<HotSearch> buyList) throws IOException {
        BufferedReader buyBR = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入你要购买的热搜名称:");
        String buyChoice = buyBR.readLine();
        System.out.println("请输入你要购买的热搜排名:");
        Integer buyRank = new Integer(buyBR.readLine());
        System.out.println("请输入你要购买的热搜金额:");
        Integer buyPrice = new Integer(buyBR.readLine());
        boolean flagC = false;
        boolean flagR = false;
        for(HotSearch i: hotSearches){
            if (i.getName().equals(buyChoice)){
                flagC = true;
                break;
            }
        }
        for(HotSearch i: buyList){
            if (i.getName().equals(buyChoice)){
                flagC = true;
                break;
            }
        }
        if (buyRank <= (buyList.size()+hotSearches.size())){
            flagR = true;
        }
        if (!flagC){
            System.out.println("输入热搜不存在，请重新操作！");
        }
        if(!flagR){
            System.out.println("输入排名过大，请重新操作！");
        }
        if (flagC && flagR){
            BuyMetod(hotSearches, buyList, buyChoice, buyRank, buyPrice);
        }
    }

    private static void BuyMetod(List<HotSearch> hotSearches, List<HotSearch> buyList, String buyChoice, Integer buyRank, Integer buyPrice) {
        boolean flag = true;
        if (!buyList.isEmpty()){
            //查找当前排名是否被购买
            for (int i = 0; i < buyList.size(); i++) {
                if (buyList.get(i).getRank().equals(buyRank)){
                    if (buyPrice <= buyList.get(i).getPrice()){
                        System.out.println("金额过低，购买失败！");
                        flag = false;
                    }else {
                        boolean flagB = true;
                        for (int j = 0; j < buyList.size(); j++) {
                            if (buyList.get(j).getName().equals(buyChoice) && buyList.get(j).getRank().equals(buyRank)){
                                buyList.get(j).setPrice(buyPrice);
                                flagB = false;
                                flag = false;
                                break;
                            }else if (buyList.get(j).getName().equals(buyChoice) && !buyList.get(j).getRank().equals(buyRank)){
                                buyList.get(j).setPrice(buyPrice);
                                buyList.get(j).setRank(buyRank);
                                buyList.remove(i);
                                flagB = false;
                                flag = false;
                                break;
                            }
                        }
                        if (flagB){
                            buyList.remove(i);
                            for (int j = 0; j < hotSearches.size(); j++) {
                                if (hotSearches.get(j).getName().equals(buyChoice)){
                                    hotSearches.get(j).setPrice(buyPrice);
                                    hotSearches.get(j).setRank(buyRank);
                                    buyList.add(hotSearches.remove(j));
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            }
            //没有被购买查看被购买的热搜在那个List中，然后添加到buyList中
            //查看是否再buyList中,在内的话，改变价格和排名即可

            for (int i = 0; i < buyList.size(); i++) {
                if (buyList.get(i).getName().equals(buyChoice)){
                    buyList.get(i).setPrice(buyPrice);
                    buyList.get(i).setRank(buyRank);
                    flag = false;
                    break;
                }
            }
            //不在buyList中则找到hotSearchs中该对象，删除该对象，并添加到buyList中
            if (flag){
                for (int i = 0; i < hotSearches.size(); i++) {
                    if (hotSearches.get(i).getName().equals(buyChoice)){
                        hotSearches.get(i).setPrice(buyPrice);
                        hotSearches.get(i).setRank(buyRank);
                        buyList.add(hotSearches.remove(i));
                        break;
                    }
                }
            }
        }else {
            for (int i = 0; i < hotSearches.size(); i++) {
                if (hotSearches.get(i).getName().equals(buyChoice)){
                    hotSearches.get(i).setPrice(buyPrice);
                    hotSearches.get(i).setRank(buyRank);
                    buyList.add(hotSearches.remove(i));
                    break;
                }
            }
        }
    }
}

