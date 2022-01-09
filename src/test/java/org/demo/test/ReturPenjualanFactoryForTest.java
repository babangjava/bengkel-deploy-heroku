package org.demo.test;

import org.demo.bean.ReturPenjualan;

public class ReturPenjualanFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReturPenjualan newReturPenjualan() {

		Integer idreturPenjualan = mockValues.nextInteger();

		ReturPenjualan returPenjualan = new ReturPenjualan();
		returPenjualan.setIdreturPenjualan(idreturPenjualan);
		return returPenjualan;
	}
	
}
