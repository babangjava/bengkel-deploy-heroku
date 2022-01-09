package org.demo.test;

import org.demo.bean.jpa.PembelianHeaderEntity;

public class PembelianHeaderEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PembelianHeaderEntity newPembelianHeaderEntity() {

		Integer idPembelian = mockValues.nextInteger();

		PembelianHeaderEntity pembelianHeaderEntity = new PembelianHeaderEntity();
		pembelianHeaderEntity.setIdPembelian(idPembelian);
		return pembelianHeaderEntity;
	}
	
}
