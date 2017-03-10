package com.xinran.qxviewslib.rxbus;

import io.reactivex.functions.Consumer;

/**
 * Created by houqixin on 2017/3/10.
 */
public class TestRxBus {
    public void registStudentResponse(){
        RxBusMannager.getInstance().register(StudentReponse.class).subscribe(new Consumer<StudentReponse>() {
            @Override
            public void accept(StudentReponse studentReponse) throws Exception {

            }
        });
    }
 private static  class NetManager{
     public void getDataFromNet(){
         //比如我在这里模拟我从网络收到数据了
         StudentReponse stu=new StudentReponse();
         stu.name="hou";
         stu.age=18;
         RxBusMannager.getInstance().post(stu);//然后所有注册的地方都可以得到这个回调消息

     }
 }
}
