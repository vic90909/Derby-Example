package derby;

import model.FuelCar;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCExample {

    public static final String DB_URL = "jdbc:derby:G:/My Drive/Master/Anul 1/PPOO/DerbyExample/DB;create=true";
    public static final String USER = "admin"; //"nbuser";
    public static final String PASS = "admin"; //"nbuser";
    public static Statement stmt = null;

    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //conn.setAutoCommit(false);
            System.out.println("Connected succesfully!");
            stmt = conn.createStatement();

            dropTable("Fuel");
            dropTable("Electric");

            System.out.println("Creating table...");
            String sqlCreateFuelCar = "CREATE TABLE Fuel(ID BIGINT PRIMARY KEY, MANUFACTURER VARCHAR(20), MODEL VARCHAR(100), COLOR VARCHAR(20)," +
                    "NO_OF_DOORS INT, PRICE DECIMAL(8,0), IS_MANUAL SMALLINT, ENGINE BIGINT, FUEL_CAPACITY BIGINT, HP INT, IS_GAS SMALLINT)";
            stmt.executeUpdate(sqlCreateFuelCar);

            System.out.println("Creating table...");
            String sqlCreateElectricCar = "CREATE TABLE Electric(ID BIGINT PRIMARY KEY, MANUFACTURER VARCHAR(20), MODEL VARCHAR(20), COLOR VARCHAR(20)," +
                    "NO_OF_DOORS INT, PRICE DECIMAL(8,0), POWER BIGINT, BATTERY_CAPACITY BIGINT, RANGE BIGINT)";
            stmt.executeUpdate(sqlCreateElectricCar);

            //Drop sequence
            stmt.execute("DROP SEQUENCE ELECTRIC_SEQ RESTRICT");
            stmt.execute("DROP SEQUENCE FUEL_SEQ RESTRICT");

            //Create sequence
            createSequence("ELECTRIC_SEQ","BIGINT", 1, 9999999);
            createSequence("FUEL_SEQ","BIGINT", 1, 9999999);

            ResultSet resultSetSeqElectric = stmt.executeQuery("SELECT NEXT VALUE FOR FUEL_SEQ from SYS.SYSSEQUENCES");
            resultSetSeqElectric.next();

            FuelCar fuelCar1 = FuelCar.builder()
                    .id(resultSetSeqElectric.getLong(1))
                    .manufacturer("Mercedes")
                    .model("AMG 63S GT 4 Doors Coupe")
                    .color("Black")
                    .noOfDoors(4)
                    .price(BigDecimal.valueOf(120000))
                    .isManual(false)
                    .engine(3996L)
                    .fuelCapacity(65L)
                    .hp(630)
                    .isGas(true)
                    .build();

            resultSetSeqElectric.next();
            FuelCar fuelCar2 = FuelCar.builder()
                    .id(resultSetSeqElectric.getLong(1))
                    .manufacturer("Mercedes")
                    .model("AMG 63S GT 4 Doors Coupe")
                    .color("Black")
                    .noOfDoors(4)
                    .price(BigDecimal.valueOf(120000))
                    .isManual(false)
                    .engine(3996L)
                    .fuelCapacity(65L)
                    .hp(630)
                    .isGas(true)
                    .build();


            System.out.println("Inserting records into fuel...");
            for (FuelCar each : Arrays.asList(fuelCar1, fuelCar2)) {
                insertIntoFuel(each );
            }

            List<FuelCar> fuelCarList = getAllFromFuel();
            for (FuelCar each : fuelCarList)
                System.out.println(each.toString());

            System.out.println("\nUpdating second fuel car...");
            String sqlUpdate = "UPDATE Fuel set MODEL = 'S Class 63 AMG' where id = " + fuelCarList.get(1).getId();
            stmt.executeUpdate(sqlUpdate);

            showAllFromFuel();

            System.out.println("\nDeleting first fuel car...");
            deleteFromFuel(fuelCarList.get(0).getId());

            showAllFromFuel();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if ((conn != null) && (stmt != null))
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    static List<FuelCar> getAllFromFuel() throws SQLException {
        List<FuelCar> fuelCarList = new ArrayList<>();
        ResultSet rs = stmt.executeQuery("SELECT * from Fuel");

        while (rs.next()) {
            fuelCarList.add(FuelCar.builder()
                    .id(rs.getLong(1))
                    .manufacturer(rs.getString(2))
                    .model(rs.getString(3))
                    .color(rs.getString(4))
                    .noOfDoors(rs.getInt(5))
                    .price(rs.getBigDecimal(6))
                    .isManual(rs.getBoolean(7))
                    .engine(rs.getLong(8))
                    .fuelCapacity(rs.getLong(9))
                    .hp(rs.getInt(10))
                    .isGas(rs.getBoolean(11))
                    .build());
        }

        return fuelCarList;
    }

    static void showAllFromFuel() throws SQLException {
        List<FuelCar> fuelCarList = getAllFromFuel();
        for (FuelCar each : fuelCarList)
            System.out.println(each.toString());

    }

    static void insertIntoFuel(FuelCar each) throws SQLException {
        String sqlInsert = "INSERT INTO fuel VALUES(" + each.getId() + ",'" + each.getManufacturer() + "','" + each.getModel() + "','" + each.getColor() + "'," + each.getNoOfDoors() + "," + each.getPrice() + "," + (each.isManual() ? 1 : 0) + "," + each.getEngine() + "," + each.getFuelCapacity() + "," + each.getHp() + "," + (each.isGas() ? 1 : 0) + ")";
        System.out.println(sqlInsert);
        stmt.executeUpdate(sqlInsert);
    }

    static void deleteFromFuel(Long id) throws SQLException {
        String sqlDelete = "DELETE from fuel where id = " + id;
        stmt.executeUpdate(sqlDelete);
    }

    static void dropTable(String tableName) throws SQLException {
        stmt.executeUpdate("DROP TABLE "+tableName);
    }

    static void createSequence(String name, String valueType, int startValue, int maxValue) throws SQLException {
        stmt.execute("CREATE SEQUENCE "+name+" AS "+valueType+" START WITH "+startValue+" MAXVALUE "+maxValue+" CYCLE");
    }
}