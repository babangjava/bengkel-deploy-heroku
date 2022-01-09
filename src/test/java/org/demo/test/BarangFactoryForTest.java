package org.demo.test;

import org.demo.bean.Barang;

public class BarangFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Barang newBarang() {

		String kodeBarang = mockValues.nextString(10);

		Barang barang = new Barang();
		barang.setKodeBarang(kodeBarang);
		return barang;
	}
	
}
