package main.java.model;
import java.time.LocalDate;

public class Worker {
    private String name;
    private String surname;
    private LocalDate age;
    private String address;
    private String city;
    private String telephoneNum;
    private String email;
    private String department;
    private String nameSurname;
    private Long idNumber;
    private LocalDate startDate;
    private String contractType;
    private LocalDate endDate;
    private String payFreq;
    private Long accountNum;
    private double taxCoeficient;
    private double netSalary;

    //Constructors
    public Worker() {
    }

    public Worker(String name, String surname, LocalDate age, String address, String city, String telephoneNum,
                  String email, String department, Long idNumber, LocalDate startDate,
                  String contractType, String payFreq, Long accountNum, double taxCoeficient, double netSalary) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.city = city;
        this.telephoneNum = telephoneNum;
        this.email = email;
        this.department = department;
        this.idNumber = idNumber;
        this.startDate = startDate;
        this.contractType = contractType;
        this.payFreq = payFreq;
        this.accountNum = accountNum;
        this.taxCoeficient = taxCoeficient;
        this.netSalary = netSalary;
    }

    public Worker(String name, String surname, LocalDate age, String address, String city, String telephoneNum,
                  String email, String nameSurname, Long idNumber, LocalDate startDate, String contractType,
                  LocalDate endDate, String payFreq, Long accountNum, double taxCoeficient, double netSalary) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.city = city;
        this.telephoneNum = telephoneNum;
        this.email = email;
        this.department = department;
        this.nameSurname = nameSurname;
        this.idNumber = idNumber;
        this.startDate = startDate;
        this.contractType = contractType;
        this.endDate = endDate;
        this.payFreq = payFreq;
        this.accountNum = accountNum;
        this.taxCoeficient = taxCoeficient;
        this.netSalary = netSalary;
    }
    public Worker(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public Worker(String name, String prezime, LocalDate age, String address, String city, String telephoneNum, String email, String department) {
        this.name = name;
        this.surname = prezime;
        this.age = age;
        this.address = address;
        this.city = city;
        this.telephoneNum = telephoneNum;
        this.email = email;
        this.department = department;
    }
    public Worker(String name, String prezime) {
        this.name = name;
        this.surname = prezime;
    }

    //Getters and Setters
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getAge() {
        return age.toString();
    }
    public void setAge(LocalDate age) {
        this.age = age;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getNameSurname() {
        return nameSurname;
    }
    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getTelephoneNum() {
        return telephoneNum;
    }
    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getIdNumber() {return idNumber;}
    public void setIdNumber(Long idNumber) {this.idNumber = idNumber;}
    public LocalDate getStartDate() {return startDate;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public String getContractType() {return contractType;}
    public void setContractType(String contractType) {this.contractType = contractType;}
    public LocalDate getEndDate() {return endDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public String getPayFreq() {return payFreq;}
    public void setPayFreq(String payFreq) {this.payFreq = payFreq;}
    public Long getAccountNum() {return accountNum;}
    public void setAccountNum(Long accountNum) {this.accountNum = accountNum;}
    public double getTaxCoeficient() {return taxCoeficient;}
    public void setTaxCoeficient(double taxCoeficient) {this.taxCoeficient = taxCoeficient;}
    public double getNetSalary() {return netSalary;}
    public void setNetSalary(double netSalary) {this.netSalary = netSalary;}


    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", telephoneNum='" + telephoneNum + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", nameSurname='" + nameSurname + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", startDate=" + startDate +
                ", contractType='" + contractType + '\'' +
                ", endDate=" + endDate +
                ", payFreq='" + payFreq + '\'' +
                ", accountNum=" + accountNum +
                ", taxCoeficient=" + taxCoeficient +
                ", netSalary=" + netSalary +
                '}';
    }
}




