package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

import org.junit.Test;

import oracle.jdbc.OracleCallableStatement;

public class Mysql_Procedure_Function2 {
	//访问存储过程
	/**
	 * 	drop procedure if exists firPro;
		delimiter //
		create procedure firPro (in i int, out o varchar(22))
		begin
			select content,headline from notes where id>2;
			select headline into o from notes where id=i;
		end //
		delimiter ;;
		
		call firPro(2,@name);
		select @name;
	 */
	@Test
	public void s1(){
		try {
			String driver = "com.mysql.jdbc.Driver";
			String mysql_url = "jdbc:mysql://192.168.56.101:3306/KEEDINGBLOGS";
			ResultSet rs = null;
			String sql = "{call firPro(?,?)}";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(mysql_url, "root", "root");
			CallableStatement call = con.prepareCall(sql);
			call.setInt(1, 2);
			call.registerOutParameter(2, Types.VARCHAR);
			boolean execute = call.execute();
		  while (execute) {
		    rs = call.getResultSet();
		    while(rs.next()){
			    String id = rs.getString(1);
			    String name = rs.getString(2);
			    System.out.println("ID = "+id+"/Readline = " + name);
		    }
		    execute = call.getMoreResults();
		  }
		  // 获取存储过程的返回值
		  String name = call.getString(2); // 获得第二个参数，因为第二个参数是输出类型的
		  System.out.println("Name = " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//访问光标
	/**
	 * 
		create or replace package mypkg as
		  type empcursor is ref cursor;
		  procedure querylist(dno in number, emplist out empcursor);
		end mypkg;
		
		create or replace package body mypkg as
		  procedure querylist(dno in number, emplist out empcursor) as
		  begin
		    open emplist for select * from emp where deptno=dno;
		  end querylist;
		end mypkg;
	 */
	@Test
	public void s3(){
		try {
			String Oracle_Url = "oracle.jdbc.driver.OracleDriver";
			String driver = "jdbc:oracle:thin:@192.168.56.101:1521:orcl";
			String sql = "{call mypkg.querylist(?,?)}";
			Class.forName(Oracle_Url);
			Connection con = null;
			OracleCallableStatement call = null;
			con = DriverManager.getConnection(driver, "scott", "tiger");
			call = (OracleCallableStatement) con.prepareCall(sql);
			call.setInt(1, 20);
			call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = call.getCursor(2);
			while(rs.next()){
				System.out.println(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//关闭资源
	public void closeResouce(Connection con, ResultSet rs, OracleCallableStatement ocs){
		try {
            if(rs!=null)rs.close();  
            if(ocs!=null)ocs.close();  
            if(con!=null)con.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
