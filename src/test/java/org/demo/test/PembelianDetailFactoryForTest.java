package org.demo.test;

import org.demo.bean.PembelianDetail;

public class PembelianDetailFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PembelianDetail newPembelianDetail() {

		Integer idPembelianDetail = mockValues.nextInteger();

		PembelianDetail pembelianDetail = new PembelianDetail();
		pembelianDetail.setIdPembelianDetail(idPembelianDetail);
		return pembelianDetail;
	}
	
}
