package com.mycompany.jta;


import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.jta.JtaTransactionManager;

public class MyJtaTest {

	private JtaTransactionManager txManager;

	protected JdbcTemplate babasport_jdbcTemplate;

	/**
	 * sshdb sql jdbcTemplate
	 */
	protected JdbcTemplate ssh_jdbcTemplate;

	public void setTxManager(JtaTransactionManager txManager) {
		this.txManager = txManager;
	}

	/**
	 * babasport sql jdbcTemplate
	 * 
	 * @return
	 */
	public JdbcTemplate getBabasport_jdbcTemplate() {
		return babasport_jdbcTemplate;
	}

	public JdbcTemplate getSsh_jdbcTemplate() {
		return ssh_jdbcTemplate;
	}

	public void setBabasport_jdbcTemplate(JdbcTemplate babasport_jdbcTemplate) {
		this.babasport_jdbcTemplate = babasport_jdbcTemplate;
	}

	public void setSsh_jdbcTemplate(JdbcTemplate ssh_jdbcTemplate) {
		this.ssh_jdbcTemplate = ssh_jdbcTemplate;
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext("applicationContext-jta.xml");
		MyJtaTest mjt = (MyJtaTest) cpx.getBean("main");

		mjt.updateMultiple();
		
	}
	
	/**
	 * 同时修改两个数据库的表中内容
	 * 
	 * @throws RollbackException
	 */
	public void updateMultiple() {

		if (null == this.txManager) {
			System.out.println("txManager为空");
			return;
		}

		UserTransaction userTx = this.txManager.getUserTransaction();
		if (null == userTx) {
			System.out.println("userTx为空");
			return;
		}

		try {

			userTx.begin();

			this.ssh_jdbcTemplate.execute("insert into tmp (uuid,id) values ('cycc',15)");

			this.babasport_jdbcTemplate
					.execute("insert into tmp (uuid,id) values ('ddd',2w112)");

			userTx.commit();
		} catch (Exception e) {
			System.out.println("捕获到异常，进行回滚" + e.getMessage());
			e.printStackTrace();
			try {
				userTx.rollback();
			} catch (IllegalStateException e1) {
				System.out.println("IllegalStateException:" + e1.getMessage());
			} catch (SecurityException e1) {
				System.out.println("SecurityException:" + e1.getMessage());
			} catch (SystemException e1) {
				System.out.println("SystemException:" + e1.getMessage());
			}
			// System.out.println("sql语句操作失败");
		}
	}
}
