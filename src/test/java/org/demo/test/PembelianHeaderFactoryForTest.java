package org.demo.test;

import org.demo.bean.PembelianHeader;

public class PembelianHeaderFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PembelianHeader newPembelianHeader() {

		Integer idPembelian = mockValues.nextInteger();

		PembelianHeader pembelianHeader = new PembelianHeader();
		pembelianHeader.setIdPembelian(idPembelian);
		return pembelianHeader;
	}
	
}
