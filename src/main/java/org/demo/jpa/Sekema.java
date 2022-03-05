package org.demo.jpa;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sekema")
public class Sekema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEKEMA_SEQ")
    @SequenceGenerator(name = "SEKEMA_SEQ", sequenceName = "SEKEMA_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "kode")
    private String kode;

    @Column(name = "totalModal")
    private BigDecimal totalModal;

    @Transient
    private String mode;

    @OneToMany(mappedBy="sekema", targetEntity=SekemaDetail.class, cascade=CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SekemaDetail> listOfSekemaDetails;

    public Sekema() {
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

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public BigDecimal getTotalModal() {
        return totalModal;
    }

    public void setTotalModal(BigDecimal totalModal) {
        this.totalModal = totalModal;
    }

    public List<SekemaDetail> getListOfSekemaDetails() {
        return listOfSekemaDetails;
    }

    public void setListOfSekemaDetails(List<SekemaDetail> listOfSekemaDetails) {
        this.listOfSekemaDetails = listOfSekemaDetails;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
