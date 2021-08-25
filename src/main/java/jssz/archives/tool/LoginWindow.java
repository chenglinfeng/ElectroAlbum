package jssz.archives.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private JFrame loginForm = new JFrame("数码照片档案册自动封装软件");

	public LoginWindow() {

		loginForm.setSize(700, 450);
		loginForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		loginForm.setLocationRelativeTo(null);// 居于屏幕中央
		loginForm.setResizable(false);
		loginForm.setLayout(null);

		Container cp = loginForm.getContentPane();

		JLabel label1 = new JLabel("欢迎使用数码照片档案册自动封装软件v1.0");
		label1.setBounds(160, 30, 400, 20);	
		label1.setFont(new Font("字体", Font.PLAIN, 20));
		JLabel label2 = new JLabel("国网通用版");
		label2.setBounds(300, 75, 100, 20);
		label2.setFont(new Font("字体", Font.PLAIN, 20));
		JLabel usernameLabel = new JLabel("用户名：");
		usernameLabel.setBounds(230, 155, 80, 30);
		usernameLabel.setFont(new Font("字体", Font.PLAIN, 20));
		final JTextField username = new JTextField();
		username.setBounds(310, 155, 150, 30);
		username.setText("Admin");
		JLabel passwordLabel = new JLabel("密   码：");
		passwordLabel.setBounds(230, 225, 200, 30);
		passwordLabel.setFont(new Font("字体", Font.PLAIN, 20));
		final JPasswordField password = new JPasswordField();
		password.setBounds(310, 225, 150, 30);
		password.setText("******");
		password.setEchoChar('*');
		JButton loginButton = new JButton("登录");
		loginButton.setBounds(400, 295, 70, 35);
		loginButton.setFont(new Font("宋体", Font.PLAIN, 18));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if (name.getText().trim().length() == 0 || new
				// String(password.getPassword()).trim().length() == 0) {
				// JOptionPane.showMessageDialog(null, "用户名密码不允许为空");
				// return;
				// }
				// else {
				loginForm.dispose();
				new jssz.archives.tool.WordGeneratorForm();
				// }
			}
		});
		JLabel label3 = new JLabel("国网江苏省电力有限公司苏州供电分公司开发 2019年7月");
		label3.setBounds(160, 380, 500, 30);
		label3.setAlignmentX(CENTER_ALIGNMENT);
		label3.setFont(new Font("字体", Font.PLAIN, 15));
		cp.add(label1);
		cp.add(label2);
		cp.add(usernameLabel);
		cp.add(username);
		cp.add(passwordLabel);
		cp.add(password);
		cp.add(loginButton);
		cp.add(label3);
		loginForm.setVisible(true);
	}

	public static void main(String[] args) {
		new LoginWindow();
		

	}

}
