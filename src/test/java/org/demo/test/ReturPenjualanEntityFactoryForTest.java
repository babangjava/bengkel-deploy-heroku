package org.demo.test;

import org.demo.bean.jpa.ReturPenjualanEntity;

public class ReturPenjualanEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReturPenjualanEntity newReturPenjualanEntity() {

		Integer idreturPenjualan = mockValues.nextInteger();

		ReturPenjualanEntity returPenjualanEntity = new ReturPenjualanEntity();
		returPenjualanEntity.setIdreturPenjualan(idreturPenjualan);
		return returPenjualanEntity;
	}
	
}
