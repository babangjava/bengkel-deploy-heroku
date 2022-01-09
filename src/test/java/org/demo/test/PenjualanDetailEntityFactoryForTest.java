package org.demo.test;

import org.demo.bean.jpa.PenjualanDetailEntity;

public class PenjualanDetailEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PenjualanDetailEntity newPenjualanDetailEntity() {

		Integer idPenjualanDetail = mockValues.nextInteger();

		PenjualanDetailEntity penjualanDetailEntity = new PenjualanDetailEntity();
		penjualanDetailEntity.setIdPenjualanDetail(idPenjualanDetail);
		return penjualanDetailEntity;
	}
	
}
