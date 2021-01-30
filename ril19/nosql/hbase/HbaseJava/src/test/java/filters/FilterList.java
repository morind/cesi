package filters;


import helper.PrintValues;
import org.apache.commons.lang.time.DateUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterList {
    public static Logger LOG = LoggerFactory.getLogger(FilterList.class);

    @Test
    public void testFilterList() throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        SingleColumnValueFilter userFilter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("for_user"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("Daniel")));

        userFilter.setFilterIfMissing(true);

        SingleColumnValueFilter typeFilter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("type"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("Friend Request")));

        typeFilter.setFilterIfMissing(true);

        List<Filter> listOfFilters = new ArrayList<>();
        listOfFilters.add(typeFilter);
        listOfFilters.add(userFilter);

        org.apache.hadoop.hbase.filter.FilterList filters = new org.apache.hadoop.hbase.filter.FilterList(listOfFilters);


        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -3);
        Scan userTypeScan = new Scan();
        userTypeScan.setTimeRange(startDate.getTime(),endDate.getTime());
        userTypeScan.setFilter(filters);
        ResultScanner userTypeScanResult = table.getScanner(userTypeScan);

        for (Result res : userTypeScanResult) {

            PrintValues.printAllValues(res);
        }
        userTypeScanResult.close();
    }
}
