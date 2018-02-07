package com.search.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;


public class FileWriteToHDFS {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI(conf.get("fs.default.name")), conf);
        if (fileSystem instanceof DistributedFileSystem) {
            System.out.println("HDFS is the underlying filesystem");
        } else {
            System.out.println("Other type of file system " + fileSystem.getClass());
        }

//Source file in the local file system
        String localSrc = "/Users/ssubramaniyam/Documents/Source/pocs/sample/Data_file.txt";
//Destination file in HDFS
        String dst = "/tmp/Data_file.txt";

//Input stream for the file in local file system to be written to HDFS
        InputStream in = new BufferedInputStream(new FileInputStream(localSrc));

//Get configuration of Hadoop system
        //conf.set("fs.defaultFS", "hdfs://localhost:9000/");
        System.out.println("Connecting to -- " + conf.get("fs.default.name"));

//Destination file in HDFS
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        OutputStream out = fs.create(new Path(dst));

//Copy file from local to HDFS
        IOUtils.copyBytes(in, out, 4096, true);

        System.out.println(dst + " copied to HDFS");

    }
}
