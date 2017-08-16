package main.java.DAO;

import java.sql.*;
import java.time.LocalDate;

public class DaoStageAdd {
    private Connection connection = null;

    public void insertIntoDatabase(String name, String surname, LocalDate age, String address, String city,
                                   String telephoneNum, String email, String department, Long idNumber,
                                   LocalDate startDate, String contractType, LocalDate endDate, String payFreq,
                                   Long accountNum, double taxCoeficient, double netSalary) {

        try {
            String driver = "org.h2.Driver";
            Class.forName(driver);
            String password = "Kvark";
            String user = "Kvark";
            String URL = "jdbc:h2:~/test";
            connection = DriverManager.getConnection(URL, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into  information_schema.radnici  " +
                    " (ime, prezime, godište, adresa, grad, telefon, email, odjeljenje, JMBG, STARTDATE, CONTRACTTYPE, ENDDATE, PAYFREQ, ACCOUNTNUMBER, TAXCOEFICIENT, NETSALARY )"
                    + "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name.trim());
            preparedStatement.setString(2, surname.trim());
            preparedStatement.setDate(3, Date.valueOf(age));
            preparedStatement.setString(4, address.trim());
            preparedStatement.setString(5, city.trim());
            preparedStatement.setString(6, telephoneNum.trim());
            preparedStatement.setString(7, email.trim());
            preparedStatement.setString(8, department);
            preparedStatement.setLong(9, idNumber);
             preparedStatement.setDate(10, Date.valueOf(startDate));
            preparedStatement.setString(11, contractType);
            if (endDate == null){
                preparedStatement.setNull(12,java.sql.Types.DATE);
            }else preparedStatement.setDate(12, Date.valueOf(endDate));
             preparedStatement.setString(13, payFreq);
            preparedStatement.setLong(14, accountNum);
            preparedStatement.setDouble(15, taxCoeficient);
            preparedStatement.setDouble(16, netSalary);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Insert complete");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}