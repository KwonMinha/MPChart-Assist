package com.appjam.assist.assist.model.request;

/**
 * Created by gominju on 2017. 6. 30..
 */

public class SignUp {
    public static SignUp signUp = new SignUp();

    private String name;
    private String email;
    private String pwd;
    private int age;
    private float height;
    private String token;
    private int type;   // 0 : 일반 회원가입, 1 : 페이스북 회원가입

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    private float weight;

    public String getMain_foot() {
        return main_foot;
    }

    public void setMain_foot(String main_foot) {
        this.main_foot = main_foot;
    }

    private String main_foot;
    private int back_num;
    private String position;
    private String detail_position;

    public String getWhatView() {
        return whatView;
    }

    public void setWhatView(String whatView) {
        this.whatView = whatView;
    }

    private String whatView;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    private int team_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public int getBack_num() {
        return back_num;
    }

    public void setBack_num(int back_num) {
        this.back_num = back_num;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDetail_position() {
        return detail_position;
    }

    public void setDetail_position(String detail_position) {
        this.detail_position = detail_position;
    }
}