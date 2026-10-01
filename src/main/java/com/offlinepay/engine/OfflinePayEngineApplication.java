package com.offlinepay.engine;

import com.offlinepay.engine.Database.DBConnection;
import com.offlinepay.engine.model.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.offlinepay.engine.model.Account;


import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import com.offlinepay.engine.Database.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.offlinepay.engine.service.PaymentService;

import java.math.BigDecimal;


@SpringBootApplication
public class OfflinePayEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfflinePayEngineApplication.class, args);
		PaymentService paymentService = new PaymentService();

		paymentService.processPayment(
				1L,
				2L,
				new BigDecimal("500.00")
		);

		}
	}



