package com.wqp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * <p>
 * Title: JdbcTest
 * </p>
 * <p>
 * Description:通过单独的jdbc程序，总结其中的问题
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-4-22上午9:16:05
 * @version 1.0
 */
public class JDBCTest {

	public static void main(String[] args) {
		oracle();
	}
	// 测试MySQL连接
	public static void mysql() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:mysql://192.168.56.103:3306/mysql?characterEncoding=utf-8", "root", "root");
			String sql = "select * from user";
			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, "1");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("Host") + "  " + resultSet.getString("User"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(resultSet, preparedStatement, connection);
		}
	}
	//测试Oracle连接
	public static void oracle() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.56.101:1521:ORCL","scott","tiger");
			String sql = "select * from DEPT";
			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, "1");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("DNAME") + "  " + resultSet.getString("LOC"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			free(resultSet, preparedStatement, connection);
		}
	}
	//查询
	public static void query() {
		// 数据库连接
		Connection connection = null;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		PreparedStatement preparedStatement = null;
		// 结果 集
		ResultSet resultSet = null;
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 通过驱动管理类获取数据库链接
			connection = DriverManager.getConnection(
					"jdbc:mysql://192.168.56.103:3306/KEEDINGBLOG?characterEncoding=utf-8", "root", "root");
			// 定义sql语句 ?表示占位符
			String sql = "select * from notes where id = ?";
			// 获取预处理statement
			preparedStatement = connection.prepareStatement(sql);
			// 设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			preparedStatement.setString(1, "1");
			// 向数据库发出sql执行查询，查询出结果集
			resultSet = preparedStatement.executeQuery();
			// 遍历查询结果集
			while (resultSet.next()) {
				System.out.println(resultSet.getString("id") + "  " + resultSet.getString("headline"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(resultSet, preparedStatement, connection);
		}
	}
	//添加
	public static void exe() {
		String con = "";
		// 数据库连接
		Connection connection = null;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		PreparedStatement preparedStatement = null;
		// 结果 集
		ResultSet resultSet = null;
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 通过驱动管理类获取数据库链接
			connection = DriverManager.getConnection(
					"jdbc:mysql://192.168.56.103:3306/KEEDINGBLOG?characterEncoding=utf-8", "root", "root");
			String insert = "insert into notes (mark,headline,content) values('mark','Linux新个人笔记','内容')";
			// 获取预处理statement
			Statement st = connection.createStatement();
			int executeUpdate = st.executeUpdate(insert);
			System.out.println(executeUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free(resultSet, preparedStatement, connection);
		}
	}
	//释放资源
	public static void free(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection){
		// 释放资源
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
