/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.data;

import configurafacil.business.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {
    
    /**
     * Coloca um funcionario na base de dados.
     * 
     * @param String username
     * @param String password
     * @throws SQLException  Atira a exceção caso exista erros SQL
     */
    public void put(String username, String password)throws SQLException{
        Connection c = null;
        
        c = Connect.connect();
        PreparedStatement st = c.prepareStatement("INSERT INTO funcionarios VALUES(?,?)");
        
        st.setString(1, username);
        st.setString(2, password);
        st.executeUpdate();
        
        c.close();
    }
    
    /**
      * Verifica se o username de um funcionario já existe.
      * 
      * @param username Username
      * @return boolean a indicar se estão correctos.
      * @throws SQLException  Atira a exceção caso exista erros SQL
      */
    public boolean jaExiste(String username){
        boolean jaExiste = false;
        Connection c;
        
        try{
            c = Connect.connect();
            PreparedStatement st = c.prepareStatement("SELECT * from funcionarios WHERE username = ? ");

            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                jaExiste = true;
            }
            c.close();
        } catch(SQLException e){}
        
        return jaExiste;
    }
    
    /**
      * Verifica se o username e password do funcionario estão correctos.
      * 
      * @param username Username
      * @param password Password
      * @return boolean a indicar se estão correctos.
      * @throws SQLException  Atira a exceção caso exista erros SQL
      */
    public boolean exists(String username, String password) throws SQLException {
        boolean exists = false;
        Connection con = null;

        con = Connect.connect();
            
        PreparedStatement ps = con.prepareStatement("select * from funcionarios where username = ? and password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        

        ResultSet rs = ps.executeQuery();

        if(rs.next())
            exists = true;

        con.close();

        return exists;
    }
    
    /**
      * Remove um funcionario dado o seu username.
      * 
      * @param username Username
      * @return boolean a indicar se estão correctos.
      * @throws SQLException  Atira a exceção caso exista erros SQL
      */
    public void removeFuncionario(String username) throws SQLException{
        Connection con = null;
        
        con = Connect.connect();
        
        PreparedStatement ps = con.prepareStatement("DELETE from funcionarios WHERE username = ?");
        ps.setString(1, username);
        
        ResultSet rs = ps.executeQuery();
    }
}
