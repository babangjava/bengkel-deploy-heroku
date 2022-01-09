package org.demo.test;

import org.demo.bean.Login;

public class LoginFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Login newLogin() {

		String userName = mockValues.nextString(45);

		Login login = new Login();
		login.setUserName(userName);
		return login;
	}
	
}
