package mtree;

import java.io.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
/**
 *
 * @author vasilisa
 */
public class MTree {
    public static int CountOfPivots = 10000;    
    public static Point[] pivots; 
    
    public static void main(String[] args) throws IOException{
        int UMax = 20;
        int UMin = 10;
        
        int CountOfPoints = CountOfPivots;
        ChoosingPivots test = new ChoosingPivots();
        test.CreateFile(CountOfPoints);

//MapReduceConfig
        if(args.length != 2){
            System.err.println("Usage: mtree.Mtree <input path> <output path>");
            System.exit(-1);
        }
        
        String path_in = args[0];
        String path_out = args[1];
        int i = 0;
        while(CountOfPivots > 1){ 
            CountOfPoints = CountOfPivots;
            CountOfPivots = 2*CountOfPivots/(3*UMin) + 1;
            
            pivots = new Point[CountOfPoints];
            pivots = test.Choose(CountOfPoints, CountOfPivots, path_in + i + "/pivots");
            
            JobConf conf = new JobConf(MTree.class);
            conf.setJobName("MTree");
       
            conf.setMapperClass(MTreeMapper.class);
            conf.setReducerClass(MTreeReducer.class);
        
            conf.setOutputKeyClass(IntWritable.class);//number of pivot
            conf.setOutputValueClass(Text.class);//points
            
            FileInputFormat.addInputPath(conf, new Path(path_in + i + "/pivots"));
            i++;
            FileOutputFormat.setOutputPath(conf, new Path(path_out + i));
            Point.level = i;

            JobClient.runJob(conf);
                      
        }
      
    }
}