package mtree;

import java.util.Random;
import java.io.*;
import java.util.Set;
import java.util.HashSet;
/**
 *
 * @author vasilisa
 */
public class ChoosingPivots {

    static public void WriteFile (String point, String file){
        Writer writer = null;
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new
                    FileOutputStream(file, true)));
                writer.write(point + "\n");
        }catch (IOException e){}
            finally {try{writer.close();} catch(IOException e){}};
        
    }
    
    void CreateFile(int CountOfPoints){
        Writer writer = null;
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new
                    FileOutputStream("tree/level-0/pivots")));
            Random rand = new Random();
            for(int i = 0; i < CountOfPoints; i++){
                for (int j = 0; j < 5; j++){
                    writer.write(100*rand.nextDouble() + " ");
                }
                writer.write("0 0");
                writer.write("\n");
            }
        }catch (IOException e){}
            finally {try{writer.close();} catch(IOException e){}};
    }   
    
    Point[] Choose(int CountOfPoints, int CountOfPivots, String file){            
        Set set = new HashSet();
        Random rand = new Random();
        Point[] pivots = new Point[CountOfPivots];
        for(int i = 0; i < CountOfPivots; i++){
            while(! set.add(rand.nextInt(CountOfPoints))){}
        }

        try{
            BufferedReader reader = new BufferedReader(new 
                InputStreamReader(new FileInputStream(file)));
            String line;
            int i = 0;
            int k = -1;
            while((line = reader.readLine()) != null){
                k++;
                if(!set.contains(k)){continue;}
                Point a = new Point(line, i);
                pivots[i++] = a;
            }
        } catch(IOException e){}  
        return pivots;
    }
    
}
