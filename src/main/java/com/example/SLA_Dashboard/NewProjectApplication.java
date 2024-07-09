package com.example.SLA_Dashboard;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

@SpringBootApplication

public class NewProjectApplication {
	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(NewProjectApplication.class, args);



		String url = "jdbc:mysql://localhost:3306/user_info";
		String username = "root";
		String password = "NeuralThumb@1409";

		Connection con= DriverManager.getConnection(url, username, password);
		String query= "insert into device_data values(?, ? , ?, ?);";
		PreparedStatement pst=con.prepareStatement(query);
		for(int i=16;;i++){

			Random random = new Random();
			int rn = (int)(Math.random()*3);
			if(rn==0)
				rn=3;
			String id="Device ".concat(Integer.toString(rn));

			float temp = random.nextFloat() * 30.0f;
			float hum = random.nextFloat() * 30.0f;
			pst.setLong(1, i);
			pst.setString(2, id);
			pst.setString(3, Float.toString(temp));
			pst.setString(4, Float.toString(hum));
			//pst.execute();
			Thread.sleep(1000);

		}
	}

}
