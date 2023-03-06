package com.xdt.dataset_server.utils;

import lombok.Data;

/**
 * @author XDT
 * @ClassName Msg
 * @Description: TODO
 * @Date 2023/2/28 11:10
 **/
@Data
public class Msg {
    private boolean flag;
    private String msg1;
    private String msg2;


    public static Msg msgReslut(boolean flag, String msg1){
        return result(flag, msg1, null);
    }

    public static Msg msgReslut2Msg(boolean flag, String msg1, String msg2){
        return result(flag, msg1, msg2);
    }


    public static Msg result(boolean flag, String msg1, String msg2){
        Msg m = new Msg();
        m.setFlag(flag);
        m.setMsg1(msg1);
        m.setMsg2(msg2);
        return m;
    }
}
