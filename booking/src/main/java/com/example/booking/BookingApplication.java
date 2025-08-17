package com.example.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})   // this is needed because this error was coming
/**
 * Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
 *
 * Reason: Failed to determine a suitable driver class
 */
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
		System.out.println("BookingApplication started");
	}

}
