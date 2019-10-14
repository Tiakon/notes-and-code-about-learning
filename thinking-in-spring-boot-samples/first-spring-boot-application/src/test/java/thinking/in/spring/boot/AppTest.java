package thinking.in.spring.boot;

import org.junit.Test;
import thinking.in.spring.boot.controller.RequestController;
import thinking.in.spring.boot.entity.AccessToken;

public class AppTest {


    @Test
    public void getAccessTokenTest() {
        String appId = "wx059a3ff45ead2b18";
        String appSecret = "9307118ae16c9c88783688518cf9d3e6";
        AccessToken accessToken = RequestController.getAccessToken(appId, appSecret);
        System.out.println(accessToken);
    }

}
