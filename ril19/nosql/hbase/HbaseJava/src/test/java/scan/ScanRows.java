package scan;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;


import java.io.IOException;

import helper.PrintValues;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScanRows {
    public static Logger LOG = LoggerFactory.getLogger(ScanRows.class);

    @Test
    public void testScanRows() throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        Scan fullScan = new Scan();
        ResultScanner fullScanResult = table.getScanner(fullScan);
        for (Result res : fullScanResult) {
            PrintValues.printAllValues(res);
        }
        fullScanResult.close();

        Scan colScan = new Scan();
        colScan.addFamily(Bytes.toBytes("metrics"));
        ResultScanner colScanResult = table.getScanner(colScan);
        for (Result res : colScanResult) {
            PrintValues.printAllValues(res);
        }
        colScanResult.close();

        Scan rangeScan = new Scan();
        rangeScan.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"))
                .setStartRow(Bytes.toBytes("2"))
                .setStopRow(Bytes.toBytes("2"));


        ResultScanner rangeScanResult = table.getScanner(rangeScan);
        for (Result res : rangeScanResult) {
            PrintValues.printAllValues(res);
        }
        rangeScanResult.close();


    }



}