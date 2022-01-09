package org.demo.test;

import org.demo.bean.jpa.ReturPembelianEntity;

public class ReturPembelianEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReturPembelianEntity newReturPembelianEntity() {

		Integer idreturPembelian = mockValues.nextInteger();

		ReturPembelianEntity returPembelianEntity = new ReturPembelianEntity();
		returPembelianEntity.setIdreturPembelian(idreturPembelian);
		return returPembelianEntity;
	}
	
}
