package Miscelaneous;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public  class Utils {

    public static Date TimestampToDate (Timestamp timestamp){
            Timestamp ts=new Timestamp(System.currentTimeMillis());
            Date date=new Date(ts.getTime());
            return date;
    }
}
