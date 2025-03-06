package insertingAndDisplaying;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class InsertAndDisplayAuthors {
    static final String DATABASE_URL = "jdbc:mysql://localhost/books";

    public static void main( String args[] ){
        Connection connection = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection(DATABASE_URL, "root", "" );
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Authors VALUES(?,?,?)");
            
            pstmt.setInt(1,11);
            pstmt.setString(2, "Ram");
            pstmt.setString(3, "Shrestha");
            pstmt.addBatch();
            
            pstmt.setInt(1,21);
            pstmt.setString(2, "Syam");
            pstmt.setString(3, "Bucks");
            pstmt.addBatch();
            
            pstmt.executeBatch();
            connection.commit();

            resultSet = pstmt.executeQuery("SELECT AuthorID, FirstName, LastName FROM Authors");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println( "Authors Table of Books Database:\n" );
            
            for ( int i = 1; i <= numberOfColumns; i++ )
                System.out.printf( "%-8s\t",metaData.getColumnName( i ));
            System.out.println();
            
            while(resultSet.next()){
                for ( int i = 1; i <= numberOfColumns; i++ )
                    System.out.printf( "%-8s\t",resultSet.getObject( i ));
                System.out.println();
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }finally{
            try{
                resultSet.close();
                connection.close();
            }catch ( Exception exception ){
                exception.printStackTrace();
            }
        }
    }
}
