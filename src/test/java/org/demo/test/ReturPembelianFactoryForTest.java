package org.demo.test;

import org.demo.bean.ReturPembelian;

public class ReturPembelianFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReturPembelian newReturPembelian() {

		Integer idreturPembelian = mockValues.nextInteger();

		ReturPembelian returPembelian = new ReturPembelian();
		returPembelian.setIdreturPembelian(idreturPembelian);
		return returPembelian;
	}
	
}
