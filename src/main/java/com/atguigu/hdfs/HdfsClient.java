package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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
    @Test
    public void testMkdir() throws URISyntaxException, IOException {
        //链接hadoop hdfs 分布式文件存储系统 NameNode内部通信
        URI uri = new URI("hdfs://hadoop102:8020");
        //配置文件
        Configuration configuration = new Configuration();
        //获取客户端对象
        FileSystem fileSystem = FileSystem.get(uri, configuration);
        //创建一个文件夹
        fileSystem.mkdirs(new Path("/xiyou/huaguoshan"));
        //关闭资源
        fileSystem.close();
    }
}
