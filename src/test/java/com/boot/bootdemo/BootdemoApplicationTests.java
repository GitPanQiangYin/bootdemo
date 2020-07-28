package com.boot.bootdemo;


        import ch.qos.logback.core.util.FileUtil;
        import com.boot.bootdemo.entity.User;
        import com.boot.bootdemo.util.UserRepository;
        import io.swagger.models.auth.In;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.cache.annotation.Cacheable;
        import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
        import org.springframework.data.elasticsearch.core.query.IndexQuery;
        import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
        import org.springframework.data.elasticsearch.core.query.StringQuery;
        import org.springframework.mail.MailSender;
        import org.springframework.mail.SimpleMailMessage;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.io.*;
        import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Cacheable
public class BootdemoApplicationTests {

    public static void main(String[] args) {
        /**
         * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
         * 解决netty冲突后初始化client时还会抛出异常
         * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
         */
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(BootdemoApplicationTests.class, args);
    }

    @Test
    public void contextLoads() {

    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    private MailSender javaMailSender;


    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

/*    @Test
    public void redisTest(){

        User user = new User();
        user.setId(11);
        user.setUsername("test");
        user.setPassword("hello redis");
        redisTemplateService.set("key1",user);

        User us = redisTemplateService.get("key1",User.class);
        System.out.println(us.getUsername()+":  "+us.getPassword());
    }*/



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

        @Test
       public void test(){
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin");
        user.setAge(18);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(String.valueOf(user.getId()))
                .withObject(user)
                .build();
        String documentId = elasticsearchTemplate.index(indexQuery);
        System.out.println(documentId);
    }

    // 测试elasticsearchTemplate搜索
  @Test
  public void search() throws IOException {

      String json = "{\n" +
                   "        \"match\" : {\n" +
                   "            \"username\" : \"admin\"\n" +
                   "        }\n" +
                   "    }";
            StringQuery query = new StringQuery(json);
             query.addIndices("user");
             query.addTypes("test");

             List<User> articles = elasticsearchTemplate.queryForList(query, User.class);
             if(articles.size() > 0) {
                     for (User a : articles){
                             System.out.println(a.toString());
                         }
                 }
         }
     //测试UserRepository类的保存
    @Test
    public void test01(){
        User user = new User();
        user.setId(3);
        user.setUsername("pqy1");
        user.setPassword("123456");
        user.setAge(18);
        userRepository.save(user);
    }

    @Test
    public void forindByAuthor1(){


       /* userRepository.deleteAll();
       List<User> userList = userRepository.findByUsername("pqy");
        if (userList.size()>0){
            for (User user : userList){
                System.out.println(user.toString());
            }
        }*/
    }

}
