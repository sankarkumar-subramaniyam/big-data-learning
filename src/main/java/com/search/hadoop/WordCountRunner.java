package com.search.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 * List all the files :
 * hdfs dfs -ls /
 * <p>
 * View the file content :
 * hadoop fs -cat /output-2/part-00000
 * <p>
 * Delete the files & Dirs :
 * hadoop fs -rm /*.txt
 * hadoop fs -rm -r -f /out*
 * <p>
 * Add files to hadoop :
 * Ex :
 * hadoop fs -put ~/Documents/Source/pocs/sample/data.txt /
 * hadoop fs -put ~/Documents/Source/pocs/sample/data.txt /data-2.txt
 * <p>
 * <p>
 * Run :
 * hadoop jar /Users/ssubramaniyam/Documents/Source/pocs/search-hadoop/lib/sample-test.jar com.search.hadoop.WordCountRunner /data.txt /output
 * hadoop jar /Users/ssubramaniyam/Documents/Source/pocs/search-hadoop/lib/sample-test.jar com.search.hadoop.WordCountRunner /data-2.txt /output-2
 * hadoop jar /Users/ssubramaniyam/Documents/Source/pocs/search-hadoop/build/libs/hadoop-1.0-SNAPSHOT.jar com.search.hadoop.WordCountRunner /data-2.txt /output-3
 * <p>
 * <p>
 * Local Installation :
 * /usr/local/Cellar/hadoop/
 * /usr/local/Cellar/hbase/
 * <p>
 *
 *
 * <p>
 * hdfs dfs -mkdir /user
 * hdfs dfs -chown mapred:mapred /user
 * <p>
 * hdfs dfs -mkdir /tmp
 * hdfs dfs -chown mapred:mapred /tmp
 */
public class WordCountRunner {
    public static void main(String[] args) throws IOException {

        JobConf conf = new JobConf(WordCountRunner.class);
        conf.setJobName("WordCount");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(WordCountMapper.class);
        conf.setCombinerClass(WordCountReducer.class);
        conf.setReducerClass(WordCountReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
