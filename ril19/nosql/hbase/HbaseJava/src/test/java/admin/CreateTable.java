package admin;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CreateTable {
    public static Logger LOG = LoggerFactory.getLogger(CreateTable.class);

    @Test
    public void testCreateTable() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");

        Connection connection = ConnectionFactory.createConnection(conf);

        Admin admin = connection.getAdmin();

        HTableDescriptor tableName = new HTableDescriptor(TableName.valueOf("notifications"));

        tableName.addFamily(new HColumnDescriptor("attributes"));
        tableName.addFamily(new HColumnDescriptor("metrics"));

        if (!admin.tableExists(tableName.getTableName())) {
            LOG.info("Creating table. ");
            admin.createTable(tableName);
            LOG.info(" Done.");
        }
    }
}
