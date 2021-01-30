package get;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

import helper.PrintValues;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleGet {
    public static Logger LOG = LoggerFactory.getLogger(SingleGet.class);

    @Test
    public void testSingleGet() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        Get get =new Get(Bytes.toBytes("2"));

        get.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        get.addColumn(Bytes.toBytes("metrics"), Bytes.toBytes("open"));

        Result result = table.get(get);

        byte[] val= result.getValue(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        LOG.info("Value:" + Bytes.toString(val));

        PrintValues.printAllValues(result);

    }




}
