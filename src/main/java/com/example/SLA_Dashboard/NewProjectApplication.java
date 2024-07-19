package com.example.SLA_Dashboard;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication

public class NewProjectApplication {
	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(NewProjectApplication.class, args);

		String url = "jdbc:mysql://localhost:3306/user_info";
		String username = "root";
		String password = "NeuralThumb@1409";

		Connection con= DriverManager.getConnection(url, username, password);
		String query=  "insert into device_data values(?, ? , ?, ?, ?, ?);";
		PreparedStatement pst=con.prepareStatement(query);
		for(int i=1;;i++){
			LocalDate randomDate = generateRandomDate(2020, 2024);
			LocalTime randomTime = generateRandomTime();

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
			pst.setTime(5, Time.valueOf(randomTime) );
			pst.setDate(6, java.sql.Date.valueOf(randomDate) );
			pst.execute();
			Thread.sleep(1000);
		}
	}
	public static LocalDate generateRandomDate(int startYear, int endYear) {
		long startEpochDay = LocalDate.of(startYear, 1, 1).toEpochDay();
		long endEpochDay = LocalDate.of(endYear, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
		return LocalDate.ofEpochDay(randomDay);
	}

	public static LocalTime generateRandomTime() {
		int randomHour = ThreadLocalRandom.current().nextInt(0, 24);
		int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);
		int randomSecond = ThreadLocalRandom.current().nextInt(0, 60);
		return LocalTime.of(randomHour, randomMinute, randomSecond);
	}



}
