package org.example;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RConnection conn = null;
        try {
            conn = new RConnection();
            REXP exp = conn.eval("R.version.string");
            System.out.println("version: "+exp.asString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("==================================");
            System.out.println("Exception: "+e.getMessage());
            System.out.println("==================================");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        System.out.println( "연결 종료" );
    }
}
