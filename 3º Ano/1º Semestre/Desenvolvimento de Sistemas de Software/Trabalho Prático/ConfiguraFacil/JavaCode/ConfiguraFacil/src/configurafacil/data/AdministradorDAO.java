/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.data;

import configurafacil.business.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author João
 */
public class AdministradorDAO {
    
    /**
     * Coloca um administrador na base de dados.
     * 
     * @param a Administrador
     * @throws SQLException  Atira a exceção caso exista erros SQL
     */
    public void put(Administrador a)throws SQLException{
        Connection c = null;
        
        c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO administrador VALUES(?,?)");
        
        st.setString(1, a.getUsername());
        st.setString(2, a.getPassword());
        st.executeUpdate();
        
        c.close();
    }
    
    /**
     * Constroi um Administrador dado o seu username
     * 
     * @param key Username
     * @return Administrador
     * @throws SQLException Atira exceção caso existam problemas SQL
     */
    public Administrador get(Object key) throws SQLException {
        Administrador a = null;
        Connection c = Connect.connect();
            
        PreparedStatement ps = c.prepareStatement("select * from administrador where username = ?");
        ps.setString(1, key.toString());
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            a = new Administrador(rs.getString("username"),rs.getString("password"));
        }
        c.close();
            
        return a;
    }
    
    /**
      * Verifica se o username e password do administrador estão correctos.
      * 
      * @param username Username
      * @param password Password
      * @return boolean a indicar se estão correctos.
      * @throws SQLException  Atira a exceção caso exista erros SQL
      */
    public boolean exists(String username, String password) throws SQLException {
        boolean exists = false;
        Connection con = null;
        System.out.println("abc");
        con = Connect.connect();
        PreparedStatement ps = con.prepareStatement("select * from administradores WHERE username = ? and password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        System.out.println("sqpl" + ps.toString());
        
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            exists = true;

        con.close();

        return exists;
    }
}

