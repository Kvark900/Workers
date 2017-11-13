package com.kemal.workers.model;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate age;

    @Transient
    private String nameSurname;

    private ContactInformation contactInformation;
    private EmploymentInformation employmentInformation;

    public Worker() {
    }

    public Worker(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public void setNameSurname() {

        this.nameSurname = this.name + " " + this.surname;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public EmploymentInformation getEmploymentInformation() {
        return employmentInformation;
    }

    public void setEmploymentInformation(EmploymentInformation employmentInformation) {
        this.employmentInformation = employmentInformation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }

    public String getNameSurname() {
        return nameSurname;
    }

}

