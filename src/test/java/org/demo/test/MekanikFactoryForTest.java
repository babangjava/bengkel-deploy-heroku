package org.demo.test;

import org.demo.bean.Mekanik;

public class MekanikFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Mekanik newMekanik() {

		String idMekanik = mockValues.nextString(15);

		Mekanik mekanik = new Mekanik();
		mekanik.setIdMekanik(idMekanik);
		return mekanik;
	}
	
}
