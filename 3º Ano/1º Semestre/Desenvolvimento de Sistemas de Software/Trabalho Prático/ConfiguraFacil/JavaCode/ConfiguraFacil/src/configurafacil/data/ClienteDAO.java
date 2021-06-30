/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.data;

import configurafacil.business.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author João
 */
public class ClienteDAO {
    
    /**
     * Coloca um cliente na base de dados.
     * 
     * @param c cliente a inserir
     * @throws SQLException  Atira a exceção caso exista erros SQL
     */
    public void put(Cliente c)throws SQLException{
        Connection con = null;
        
        con = Connect.connect();
        PreparedStatement st = con.prepareStatement("INSERT INTO cliente VALUES(?,?,?)");
        
        st.setString(1, c.getNome());
        st.setString(2, c.getNif());
        st.setString(2, c.getContacto());
        st.executeUpdate();
        
        con.close();
    }
    
    /**
     * Constrói um cliente dado o seu ID
     * @param key Id do cliente
     * @return Cliente caso exista, de outra maneira, devolve null.
     * @throws SQLException  Atira exceção caso ocorram erros SQL
     */
    public Cliente get(Object key) throws SQLException {
        Cliente c = null;
        Connection con = Connect.connect();
            
        PreparedStatement ps = con.prepareStatement("select * from cliente where username = ?");
        ps.setString(1, key.toString());
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            c = new Cliente(rs.getString("nome"),rs.getString("nif"),rs.getString("contacto"));
        }
        con.close();
            
        return c;
        }
}
