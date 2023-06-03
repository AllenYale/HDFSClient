package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * ClassName: HdfsClient
 * Description:
 *
 * @ Author: Hanyuye
 * @ Date: 2023/6/3 23:18
 * @ Version: v1.0
 */

/**
 * 客户端代码常用套路
 * 1：获取一个客户端对象
 * 2：执行相关操作命令
 * 3：关闭资源
 * ex. HDFS zookeeper
 */
public class HdfsClient {

    private FileSystem fileSystem;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        //链接hadoop hdfs 分布式文件存储系统 NameNode内部通信
        URI uri = new URI("hdfs://hadoop102:8020");
        //配置文件
        Configuration configuration = new Configuration();
//        代码中配置conf
        configuration.set("dfs.replication", "2");

        //用户
        String user = "atguigu";

        //获取客户端对象
        fileSystem = FileSystem.get(uri, configuration, user);
    }
    @After
    public void close() throws IOException {
        //关闭资源
        fileSystem.close();
    }

    @Test
    public void testMkdir() throws URISyntaxException, IOException, InterruptedException {
        //创建一个文件夹
        fileSystem.mkdirs(new Path("/xiyou/huaguoshan1"));
    }

    /**
     * 参数优先级
     * hdfs-default.xml => hdfs-site.xml=> 在项目资源目录下的配置文件 =》代码里面的配置
     *
     * @throws IOException
     */
    @Test
    public void testPut() throws IOException {
        //参数解读：参数1：删除源数据；参数2：是否允许覆盖；参数3：（本地）源数据路径；参数4：目的地路径
        fileSystem.copyFromLocalFile(false, true, new Path("F:\\sunwukong.txt"), new Path("hdfs://hadoop102/xiyou/huaguoshan"));
    }










}
