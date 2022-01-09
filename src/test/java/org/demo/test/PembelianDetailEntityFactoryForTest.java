package org.demo.test;

import org.demo.bean.jpa.PembelianDetailEntity;

public class PembelianDetailEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PembelianDetailEntity newPembelianDetailEntity() {

		Integer idPembelianDetail = mockValues.nextInteger();

		PembelianDetailEntity pembelianDetailEntity = new PembelianDetailEntity();
		pembelianDetailEntity.setIdPembelianDetail(idPembelianDetail);
		return pembelianDetailEntity;
	}
	
}
