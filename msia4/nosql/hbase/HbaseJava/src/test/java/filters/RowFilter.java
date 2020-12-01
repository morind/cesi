package filters;

import helper.PrintValues;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class RowFilter {
    public static Logger LOG = LoggerFactory.getLogger(RowFilter.class);

    @Test
    public void testRowFilter() throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        Filter filter = new org.apache.hadoop.hbase.filter.RowFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("1")));

        Scan userScan = new Scan();
        userScan.setFilter(filter);
        ResultScanner userScanResult = table.getScanner(userScan);

        for (Result res : userScanResult) {
            PrintValues.printAllValues(res);
        }

        userScanResult.close();


    }
}
