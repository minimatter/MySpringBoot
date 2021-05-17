package end.bean;

public class User {
    private int id;
    private String userName;
    private String passWord;
    private String rid;
    private String ridStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRidStr() {
        return ridStr;
    }

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }
}
