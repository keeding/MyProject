package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Oracle_Batch {
	private void mian() {
		List list = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into RES_PNR_SEG_TF( ");
		sql.append("PNR_REF,");
		sql.append("SN_ID");// id
		sql.append(" )VALUES(?,APSISRSV.RES_PNR_SEG_TF_SEQ.nextval)");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.56.201:3306/mysql?characterEncoding=utf-8", "root", "root");
//			con = (Connection) ConnectionManager.getInstance().getConnection();//157.93
			// 关闭事务自动提交
			con.setAutoCommit(false);
			
			pst = (PreparedStatement) con.prepareStatement(sql.toString());
			for (int i = 0; i < list.size(); i++) {
				Rsv_New rsv = (Rsv_New) list.get(i);
				pst.setString(1, rsv.getPnr_ref());
				// 把一个SQL命令加入命令列表
				pst.addBatch();
			}
			// 执行批量更新
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class Rsv_New{
	private String pnr_ref;

	public String getPnr_ref() {
		return pnr_ref;
	}

	public void setPnr_ref(String pnr_ref) {
		this.pnr_ref = pnr_ref;
	}
}
