package com.drk.tools.config;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drk.tools.constants.ApplicationConstants;
import com.drk.tools.exceptions.DbException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SqliteConfig {

	@Autowired
	AppConfig appConfig;

	public Connection sqliteConnection(String type) {
		Connection con = null;
		try {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("exception ocured while conneting with sql lite " + e.getMessage());
			}

			if (type.equals("quantycalc")) {

				con = DriverManager.getConnection(
						"jdbc:sqlite:" + appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_QC_DB);

			} else if (type.equals("flipbook")) {

				con = DriverManager.getConnection(
						"jdbc:sqlite:" + appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_FLIP_DB);

			} /*else if (type.equals("flipbook-images")) {

				con = DriverManager.getConnection(
						"jdbc:sqlite:" + appConfig.getHomeDir() + "/db/" + ApplicationConstants.SQLITE_FLIP_IMG_DB);

			}*/

		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		return con;
	}

}
