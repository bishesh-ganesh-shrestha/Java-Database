package demonstratingJDBCRowSet;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class JdbcRowSetTest {
    static final String DATABASE_URL = "jdbc:mysql://localhost/books";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    
    public JdbcRowSetTest(){
        try{
            JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
            rowSet.setUrl(DATABASE_URL);
            rowSet.setUsername(USERNAME);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT * FROM Authors");
            rowSet.execute();
            
            ResultSetMetaData metaData = rowSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Author Table of Books Database\n");
            
            for(int i=1;i<=numberOfColumns;i++)
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            System.out.println();
            
            while(rowSet.next()){
                for ( int i = 1; i <= numberOfColumns; i++ )
                    System.out.printf( "%-8s\t",rowSet.getObject( i ));
                System.out.println("");
            }
            rowSet.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.exit( 1 );
        }
    }
    public static void main( String args[] ){
        JdbcRowSetTest application = new JdbcRowSetTest();
    }
}
