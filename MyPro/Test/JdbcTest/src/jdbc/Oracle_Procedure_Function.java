package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

import org.junit.Test;

import oracle.jdbc.OracleCallableStatement;


public class Oracle_Procedure_Function {
	//访问存储过程
	/**
	 * create or replace procedure secpro(eid in number, num out number)
		as
		begin
		  select sal into num from emp where empno=eid;
		end;
	 */
	@Test
	public void s1(){
		try {
			String Oracle_Url = "oracle.jdbc.driver.OracleDriver";
			String driver = "jdbc:oracle:thin:@192.168.56.201:1521:orcl";
			String sql = "{call scott.secPro(?,?)}";//call 用户.存储过程
			Class.forName(Oracle_Url);
			Connection con = null;
			con = DriverManager.getConnection(driver, "scott", "tiger");
			CallableStatement call = con.prepareCall(sql);
			call.setInt(1, 7369);
			call.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
//			call.registerOutParameter(2, Types.NUMERIC);//或者使用Types
			call.execute();
			String s2 = call.getString(2);
			System.out.println(s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//访问存储函数
		/**
	 * create or replace function secFun(eno in number)
		  return number
		as
		  ago_sal emp.sal%type;
		begin
		  select sal into ago_sal from emp where empno=eno;
		  return ago_sal;
		end;
	 */
	@Test
	public void s2(){
			try {
				String Oracle_Url = "oracle.jdbc.driver.OracleDriver";
				String driver = "jdbc:oracle:thin:@192.168.56.101:1521:orcl";
				String sql = "{?=call secFun(?)}";
				Class.forName(Oracle_Url);
				Connection con = null;
				CallableStatement call = null;
				con = DriverManager.getConnection(driver, "scott", "tiger");
				call = con.prepareCall(sql);
				call.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER);
				call.setInt(2, 7369);
				call.execute();
				String s2 = call.getString(1);
				System.out.println(s2);
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
