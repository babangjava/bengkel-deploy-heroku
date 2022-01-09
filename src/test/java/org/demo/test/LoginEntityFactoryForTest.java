package org.demo.test;

import org.demo.bean.jpa.LoginEntity;

public class LoginEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public LoginEntity newLoginEntity() {

		String userName = mockValues.nextString(45);

		LoginEntity loginEntity = new LoginEntity();
		loginEntity.setUserName(userName);
		return loginEntity;
	}
	
}
