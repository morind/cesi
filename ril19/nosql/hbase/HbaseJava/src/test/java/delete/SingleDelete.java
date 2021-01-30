package delete;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class SingleDelete {
    public static Logger LOG = LoggerFactory.getLogger(SingleDelete.class);

    @Test
    public void testSingleDelete() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        Delete delete =new Delete(Bytes.toBytes("2"));

        delete.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        delete.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("for_user"));

        table.delete(delete);

        table.close();

    }
}
