package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

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

    /**
     * 文件下载
     * 参数解析：useRawLocalFileSystem 是否使用CRC校验
     *
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException {
        fileSystem.copyToLocalFile(false, new Path("hdfs://hadoop102/xiyou/huaguoshan"), new Path("F:\\test_download_upload"), false);
    }

    //删除
    @Test
    public void testRm() throws IOException {
        //删除文件
        fileSystem.delete(new Path("/jdk-8u212-linux-x64.tar.gz"), false);

        //删除非空目录，必须递归删除
        fileSystem.delete(new Path("hdfs://hadoop102/xiyou"), true);

        //删除空目录，不用递归删除
//        fileSystem.delete(new Path("hdfs://hadoop102/jinguo"), true);
    }

    //文件更名、移动
    @Test
    public void testmv() throws IOException {
        fileSystem.rename(new Path("/input/word.txt"), new Path("/cls.txt"));

        //目录更名
        fileSystem.rename(new Path("/input"), new Path("/output"));
    }

    //获取文件详情信息
    @Test
    public void fileDetail() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("================" + fileStatus.getPath() + "================");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());

            //获取块存储位置
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            Arrays.toString(blockLocations);
        }
    }

    @Test
    public void isFileOrDir() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));

        for (FileStatus status : fileStatuses) {
            if(status.isFile()){
                String name = status.getPath().getName();
                System.out.println("文件：" + name);
            }else {
                System.out.println("目录：" + status.getPath().getName());
            }
        }
    }


}
