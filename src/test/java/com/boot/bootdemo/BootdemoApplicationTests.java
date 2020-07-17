package com.boot.bootdemo;


        import ch.qos.logback.core.util.FileUtil;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.mail.MailSender;
        import org.springframework.mail.SimpleMailMessage;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootdemoApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    private MailSender javaMailSender;

    @Test
    public void emailSend(){
        readTxt("C:\\Users\\Administrator\\Desktop\\SQL.txt");
       /* SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("panqiangyin@163.com");
        message.setTo("335238758@qq.com");
        message.setSubject("测试");
        message.setText("测试");
        javaMailSender.send(message);*/
    }


    public static Double cycleArea(Double r){
        Double area = 0.00 ;
        try{
            if (r>=0){
               area =Math.PI*r*r;
            }else{
                System.out.println("半径小于0");
                throw new  Exception("半径r小于0");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("圆的面积为"+area);
        return area;
    }


    public static void readTxt(String filePath) {
        int sum = 0; //汉字数
        int n = 0;  //字母数
        int m = 0;  //数字数
        filePath = "C:test.txt";
        String Reg="^[\u4e00-\u9fa5]{1}$";
        try {
            File file = new File(filePath);
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    for(int i=0;i<lineTxt.length();i++) {
                        char tmp = lineTxt.charAt(i);
                        if((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')){
                            n++;
                        }else if ((tmp >= '0') && (tmp <= '9')){
                            m++;
                        }else {
                            String a=Character.toString(lineTxt.charAt(i));
                            if (a.matches(Reg)){
                                sum++;
                            }
                        }
                    }
                    System.out.println(lineTxt);
                }
                br.close();
                String result = "中文:"+sum+",英文:"+n+",数字："+m;
                FileWriter fw = null;
                fw = new FileWriter("D:result.txt");
                fw.write(result);
                if(fw!=null){
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
        System.out.println("中文:"+sum+",英文:"+n+",数字："+m);

    }

    void test(){
        System.out.println("test");
    }
}
