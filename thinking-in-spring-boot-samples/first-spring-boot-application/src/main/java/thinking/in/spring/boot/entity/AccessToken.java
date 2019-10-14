package thinking.in.spring.boot.entity;

public class AccessToken {

    //获取到的凭证
    private String tokenName;

    //凭证有效时间  单位:秒
    private int expireSecond;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public int getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "tokenName='" + tokenName + '\'' +
                ", expireSecond=" + expireSecond +
                '}';
    }
}

