package org.demo.jpa;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "sekema_detail")
public class SekemaDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "satuan")
    private String satuan;

    @Column(name = "jumlah")
    private Integer jumlah;

    @Column(name = "harga")
    private BigDecimal harga;

    @Transient
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name="sekema", referencedColumnName="id")
    private Sekema sekema;

    public SekemaDetail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Sekema getSekema() {
        return sekema;
    }

    public void setSekema(Sekema sekema) {
        this.sekema = sekema;
    }
}
