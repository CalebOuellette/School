import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.io.FileInputStream;
import java.sql.*;

public class Main {
    static Connection conn = null;
    static Properties connectProps = new Properties();
    public static void main(String[] args) throws Exception{


        String paramsFile = "ConnectionParameters.txt";
        if (args.length >= 1) {
            paramsFile = args[0];
        }
        connectProps.load(new FileInputStream(paramsFile));
        Class.forName("com.mysql.jdbc.Driver");
        String dburl = connectProps.getProperty("dburl");
        String username = connectProps.getProperty("user");
        conn = DriverManager.getConnection(dburl, connectProps);

        String sqlStatement = readFile("test.txt", Charset.defaultCharset());

        PreparedStatement pstmt;
        pstmt = conn.prepareStatement(sqlStatement);

        ResultSet results = pstmt.executeQuery();
	// write your code here
    }

    public static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
