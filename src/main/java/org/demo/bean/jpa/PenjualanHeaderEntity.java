/*
 * Created on 25 Jul 2021 ( Time 02:07:33 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package org.demo.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "penjualan_header"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="penjualan_header" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="PenjualanHeaderEntity.countAll", query="SELECT COUNT(x) FROM PenjualanHeaderEntity x" )
} )
public class PenjualanHeaderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_penjualan_header_seq")
    @SequenceGenerator(name = "id_penjualan_header_seq", sequenceName = "id_penjualan_header_seq", allocationSize = 1)
    @Column(name="id_penjualan_header", nullable=false)
    private Integer    idPenjualanHeader ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="tanggal", nullable=false)
    private Date       tanggal      ;

    @Column(name="total", nullable=false)
    private BigDecimal total        ;

    @Column(name="nama_mekanik", length=45)
    private String     namaMekanik  ;

    @Column(name="ongkos_mekanik")
    private BigDecimal ongkosMekanik ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="penjualanHeader2", targetEntity=PenjualanDetailEntity.class)
    private List<PenjualanDetailEntity> listOfPenjualanDetail;

    @OneToMany(mappedBy="penjualanHeader2", targetEntity=ReturPenjualanEntity.class)
    private List<ReturPenjualanEntity> listOfReturPenjualan;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public PenjualanHeaderEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setIdPenjualanHeader( Integer idPenjualanHeader ) {
        this.idPenjualanHeader = idPenjualanHeader ;
    }
    public Integer getIdPenjualanHeader() {
        return this.idPenjualanHeader;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : tanggal ( DATETIME ) 
    public void setTanggal( Date tanggal ) {
        this.tanggal = tanggal;
    }
    public Date getTanggal() {
        return this.tanggal;
    }

    //--- DATABASE MAPPING : total ( DECIMAL ) 
    public void setTotal( BigDecimal total ) {
        this.total = total;
    }
    public BigDecimal getTotal() {
        return this.total;
    }

    //--- DATABASE MAPPING : nama_mekanik ( VARCHAR ) 
    public void setNamaMekanik( String namaMekanik ) {
        this.namaMekanik = namaMekanik;
    }
    public String getNamaMekanik() {
        return this.namaMekanik;
    }

    //--- DATABASE MAPPING : ongkos_mekanik ( DECIMAL ) 
    public void setOngkosMekanik( BigDecimal ongkosMekanik ) {
        this.ongkosMekanik = ongkosMekanik;
    }
    public BigDecimal getOngkosMekanik() {
        return this.ongkosMekanik;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfPenjualanDetail( List<PenjualanDetailEntity> listOfPenjualanDetail ) {
        this.listOfPenjualanDetail = listOfPenjualanDetail;
    }
    public List<PenjualanDetailEntity> getListOfPenjualanDetail() {
        return this.listOfPenjualanDetail;
    }

    public void setListOfReturPenjualan( List<ReturPenjualanEntity> listOfReturPenjualan ) {
        this.listOfReturPenjualan = listOfReturPenjualan;
    }
    public List<ReturPenjualanEntity> getListOfReturPenjualan() {
        return this.listOfReturPenjualan;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(idPenjualanHeader);
        sb.append("]:"); 
        sb.append(tanggal);
        sb.append("|");
        sb.append(total);
        sb.append("|");
        sb.append(namaMekanik);
        sb.append("|");
        sb.append(ongkosMekanik);
        return sb.toString(); 
    } 

}
