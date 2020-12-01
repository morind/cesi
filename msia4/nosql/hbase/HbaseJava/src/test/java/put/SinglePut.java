package put;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SinglePut {
    public static Logger LOG = LoggerFactory.getLogger(SinglePut.class);

    @Test
    public void testSinglePut() throws IOException{

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("notifications"));
        //HTable table = new HTable(conf, "notifications");

        Put put =new Put(Bytes.toBytes("1"));

        put.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"), Bytes.toBytes("Comment"));
        put.addColumn(Bytes.toBytes("attributes"),Bytes.toBytes("for_user"),Bytes.toBytes("Chaz"));
        put.addColumn(Bytes.toBytes("metrics"),Bytes.toBytes("open"),Bytes.toBytes("0"));

        table.put(put);

    }
}
