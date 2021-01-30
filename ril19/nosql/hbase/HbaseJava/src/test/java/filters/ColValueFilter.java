package filters;


import helper.PrintValues;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ColValueFilter {
    public static Logger LOG = LoggerFactory.getLogger(ColValueFilter.class);

    @Test
    public void testColValueFilter() throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("for_user"),
                CompareOperator.EQUAL,
                new BinaryComparator(Bytes.toBytes("Daniel")));

        filter.setFilterIfMissing(true);

        Scan userScan = new Scan();
        userScan.setFilter(filter);
        ResultScanner userScanResult = table.getScanner(userScan);

        for (Result res : userScanResult) {

            PrintValues.printAllValues(res);
        }

        userScanResult.close();
    }
}
