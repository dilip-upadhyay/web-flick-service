//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.webflick.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Generated;

@Entity
@Table(
    name = "contractinfo"
)
public class ContractInfo implements Serializable {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    Integer id;
    @Column
    private String vender;
    @Column
    private String contractTermNo;
    @Column
    private Timestamp startDate;
    @Column
    private String partyPhone;
    @Column
    private String tokenId;
    @Column
    private String name;

    @Generated
    public Integer getId() {
        return this.id;
    }

    @Generated
    public String getVender() {
        return this.vender;
    }

    @Generated
    public String getContractTermNo() {
        return this.contractTermNo;
    }

    @Generated
    public Timestamp getStartDate() {
        return this.startDate;
    }

    @Generated
    public String getPartyPhone() {
        return this.partyPhone;
    }

    @Generated
    public String getTokenId() {
        return this.tokenId;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public void setId(Integer var1) {
        this.id = var1;
    }

    @Generated
    public void setVender(String var1) {
        this.vender = var1;
    }

    @Generated
    public void setContractTermNo(String var1) {
        this.contractTermNo = var1;
    }

    @Generated
    public void setStartDate(Timestamp var1) {
        this.startDate = var1;
    }

    @Generated
    public void setPartyPhone(String var1) {
        this.partyPhone = var1;
    }

    @Generated
    public void setTokenId(String var1) {
        this.tokenId = var1;
    }

    @Generated
    public void setName(String var1) {
        this.name = var1;
    }

    @Generated
    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else if (!(var1 instanceof ContractInfo)) {
            return false;
        } else {
            ContractInfo var2 = (ContractInfo)var1;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                Integer var3 = this.getId();
                Integer var4 = var2.getId();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getVender();
                String var6 = var2.getVender();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getContractTermNo();
                String var8 = var2.getContractTermNo();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                Timestamp var9 = this.getStartDate();
                Timestamp var10 = var2.getStartDate();
                if (var9 == null) {
                    if (var10 != null) {
                        return false;
                    }
                } else if (!var9.equals(var10)) {
                    return false;
                }

                String var11 = this.getPartyPhone();
                String var12 = var2.getPartyPhone();
                if (var11 == null) {
                    if (var12 != null) {
                        return false;
                    }
                } else if (!var11.equals(var12)) {
                    return false;
                }

                String var13 = this.getTokenId();
                String var14 = var2.getTokenId();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                String var15 = this.getName();
                String var16 = var2.getName();
                if (var15 == null) {
                    if (var16 != null) {
                        return false;
                    }
                } else if (!var15.equals(var16)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(Object var1) {
        return var1 instanceof ContractInfo;
    }

    @Generated
    public int hashCode() {
        int var1 = 1;
        Integer var2 = this.getId();
        var1 = var1 * 59 + (var2 == null ? 43 : var2.hashCode());
        String var3 = this.getVender();
        var1 = var1 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getContractTermNo();
        var1 = var1 * 59 + (var4 == null ? 43 : var4.hashCode());
        Timestamp var5 = this.getStartDate();
        var1 = var1 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getPartyPhone();
        var1 = var1 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getTokenId();
        var1 = var1 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getName();
        var1 = var1 * 59 + (var8 == null ? 43 : var8.hashCode());
        return var1;
    }

    @Generated
    public String toString() {
        Integer var10000 = this.getId();
        return "ContractInfo(id=" + var10000 + ", vender=" + this.getVender() + ", contractTermNo=" + this.getContractTermNo() + ", startDate=" + String.valueOf(this.getStartDate()) + ", partyPhone=" + this.getPartyPhone() + ", tokenId=" + this.getTokenId() + ", name=" + this.getName() + ")";
    }

    @Generated
    public ContractInfo(Integer var1, String var2, String var3, Timestamp var4, String var5, String var6, String var7) {
        this.id = var1;
        this.vender = var2;
        this.contractTermNo = var3;
        this.startDate = var4;
        this.partyPhone = var5;
        this.tokenId = var6;
        this.name = var7;
    }

    @Generated
    public ContractInfo() {
    }
}
