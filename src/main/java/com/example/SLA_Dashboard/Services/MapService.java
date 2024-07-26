package com.example.SLA_Dashboard.Services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Service
public class MapService {

    @SneakyThrows
    public List<String>  getSchema() {
        Properties prop=new Properties();
        FileInputStream finpt=new FileInputStream("C:/Users/hp/Desktop/Project/DBConfig.properties");
        prop.load(finpt);

        List<String> schema=new ArrayList<>();

        String url = prop.getProperty("url");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");

        Connection con= DriverManager.getConnection(url, username, password);
        String query=prop.getProperty("query");
        Statement st=con.createStatement();
        boolean count=st.execute(query);

        if(count){
            ResultSet rs=st.getResultSet();
            schema.add(rs.getMetaData().getColumnName(1));
            schema.add(rs.getMetaData().getColumnName(2));
            schema.add(rs.getMetaData().getColumnName(3));
            schema.add(rs.getMetaData().getColumnName(4));
            schema.add(rs.getMetaData().getColumnName(5));
            schema.add(rs.getMetaData().getColumnName(6));

        }
        return schema;
    }
    @SneakyThrows
    public Map<String, String> getProperties() {
        Properties prop=new Properties();
        FileInputStream finpt=new FileInputStream("C:/Users/hp/Desktop/Project/DASHBOARD.properties");
        prop.load(finpt);


        Map<String, String> map=new HashMap<String, String>();
        map.put("interval", prop.getProperty("interval"));
        map.put("sidePanelWidth", prop.getProperty("sidePanelWidth"));
        map.put("initialPoint", prop.getProperty("initialPoint"));
        map.put("finalPoint", prop.getProperty("finalPoint"));
        return map;
    }
}
