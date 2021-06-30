/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.data;

import java.sql.Connection;
/*import java.sql.PreparedStatement;
import java.sql.ResultSet;*/
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    
    /**
     * Estabelece conexão com a base de dados e devolve ponto de acesso.
     * 
     * @return Conexão correspondente à ligação à base de dados.
     * @throws SQLException 
     */
    public static Connection connect() throws SQLException {  
        
        Connection con = null;
        
        System.out.println("Preparando ligação à base de dados...");
        
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/configurafacil?serverTimezone=GMT" , "root", "F6C1T12Y");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        
        System.out.println(con);
        
        return con;
      }
    
   /* public boolean isAdmin(String username, String password){
        ResultSet rs = null;
        try{
            String sql = "select * from administradores A INNER JOIN utilizadores U ON A.id_utizador == U.id_utilizador WHERE username = ? AND password = ?";
            PreparedStatement ps = this.con.prepareStatement(sql);
            
            ps.setString(1, username);
            ps.setString(2, password);
            ps.execute();
            rs = ps.getResultSet();
        }catch(SQLException e){
            return false;
        }
        
        return rs != null;
    }*/
    
    /**
     * Recebe uma conexão à base de dados e fecha-a.
     *
     * @param c Conexão
     * @throws SQLException 
     */
    public void close(Connection c)throws SQLException{
            c.close();

    }
}
