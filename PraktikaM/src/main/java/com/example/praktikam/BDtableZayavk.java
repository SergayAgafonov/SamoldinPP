package com.example.praktikam;

public class BDtableZayavk {
    private Integer Code;
    private String Name;
    private String Phone;
    private String Rem;



    public BDtableZayavk(int Code, String Name, String Phone, String Rem) {
        this.Code = Code;
        this.Name = Name;
        this.Phone = Phone;
        this.Rem = Rem;
    }

    public BDtableZayavk() {

    }
    public Integer getIDCode(){
        return Code;
    }


    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getRem() {
        return Rem;
    }

    public void setRem(String Rem) {
        this.Rem = Rem;
    }

}
