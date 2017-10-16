package com.kemal.workers.DAO;

import java.time.LocalDate;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public interface AddWorkerDAO {
    void insertIntoDatabase(String name, String surname, LocalDate age, String address, String city,
                       String telephoneNum, String email, String department, Long idNumber,
                       LocalDate startDate, String contractType, LocalDate endDate, String payFreq,
                       Long accountNum, double taxCoeficient, double netSalary);

}
