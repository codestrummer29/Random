package monday.com.random.APIresponse;

import monday.com.random.POJO.User_Info;

/**
 * Created by Saahil on 08/05/17.
 */

public class Login_Response {
    private int status;
    private String msg;
    private User_Info content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User_Info getContent() {
        return content;
    }

    public void setContent(User_Info content) {
        this.content = content;
    }
}
