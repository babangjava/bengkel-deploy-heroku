package org.demo.test;

import org.demo.bean.jpa.PenjualanHeaderEntity;

public class PenjualanHeaderEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PenjualanHeaderEntity newPenjualanHeaderEntity() {

		Integer idPenjualanHeader = mockValues.nextInteger();

		PenjualanHeaderEntity penjualanHeaderEntity = new PenjualanHeaderEntity();
		penjualanHeaderEntity.setIdPenjualanHeader(idPenjualanHeader);
		return penjualanHeaderEntity;
	}
	
}
