package main.java.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.model.Worker;

import java.sql.*;

public class WorkerDAOElecImpl implements WorkerDAO {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private final String driver = "org.h2.Driver";
    private final String URL = "jdbc:h2:~/test";
    private final String user = "Kvark";
    private final String password = "Kvark";

    //Get Worker (nameSurname)
    public ObservableList<Worker> getWorkersNameSurname() {
        ObservableList<Worker> nameSurname = FXCollections.observableArrayList();
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT *FROM information_schema.radnici WHERE odjeljenje = 'Electrical'");

            while (resultSet.next()) {
                String imePrezime = resultSet.getString("ime") + " " + resultSet.getString("prezime");
                nameSurname.addAll(( new Worker(imePrezime)));
            }
            statement.close();
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nameSurname;
    }

    //Delete worker
    public void deleteSelectedWorker (Worker worker){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, user, password);
            preparedStatement = connection.prepareStatement("DELETE FROM information_schema.radnici WHERE ime = ? AND prezime = ? AND odjeljenje = 'Electrical'");
            preparedStatement.setString(1, worker.getNameSurname().split(" ")[0]);
            preparedStatement.setString(2, worker.getNameSurname().split(" ")[1]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Get Worker (allInfo)
    public Worker getWorkersInfo(String nameSurname) {
        Worker worker = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, user, password);
            preparedStatement = connection.prepareStatement("SELECT *FROM" +
                    " information_schema.radnici WHERE ime = ? AND prezime = ?");
            preparedStatement.setString(1, nameSurname.split(" ")[0]);
            preparedStatement.setString(2, nameSurname.split(" ")[1]);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                worker = new Worker(resultSet.getString("ime"), resultSet.getString("prezime"),
                        resultSet.getDate("godište").toLocalDate(), resultSet.getString("grad"),
                        resultSet.getString("adresa"), resultSet.getString("telefon"),
                        resultSet.getString("email"), resultSet.getString("odjeljenje"),
                        resultSet.getLong("jmbg"), resultSet.getDate("startDate").toLocalDate(),
                        resultSet.getString("contractType"), resultSet.getDate("endDate").toLocalDate(),
                        resultSet.getString("payFreq"), resultSet.getLong("accountNumber"),
                        resultSet.getDouble("taxCoeficient"), resultSet.getDouble("netSalary"));

                statement.close();
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return worker;
    }
}
