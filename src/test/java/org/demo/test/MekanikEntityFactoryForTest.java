package org.demo.test;

import org.demo.bean.jpa.MekanikEntity;

public class MekanikEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public MekanikEntity newMekanikEntity() {

		String idMekanik = mockValues.nextString(15);

		MekanikEntity mekanikEntity = new MekanikEntity();
		mekanikEntity.setIdMekanik(idMekanik);
		return mekanikEntity;
	}
	
}
