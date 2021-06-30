/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.data;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import configurafacil.business.Componente;
import java.sql.SQLException;


public class ComponenteDAO {
    
    /**
     * Constroi a List de todos os componentes existentes na base de dados.
     * 
     *@return Lista de componentes existentes
     *@throws SQLException Atira exceção caso existam problemas SQL
     */
    public static List<Componente> carregarComponentes(){
        Connection con = null;
        List<Componente> lista = new ArrayList<Componente>();
        
        
        try{
            con = Connect.connect();


            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT CO.id_componente as 'codigo', CO.componentescol AS 'designacao', CO.preco, CO.stock AS 'quantidade', CA.designacao AS 'tipo'  FROM componentes CO INNER JOIN categorias CA ON CO.categoria = CA.id_categoria");
            
            while(rs.next()){
                
                List<Integer> precedentes = new ArrayList<Integer>();
                List<Integer> incompativeis = new ArrayList<Integer>();
                
                PreparedStatement ps = con.prepareStatement("SELECT id_depende as 'codigo' FROM dependencias WHERE id_componente = ?");
                ps.setString(1, rs.getString("codigo"));
                ResultSet rs2 = ps.executeQuery();
                
                while(rs2.next())
                    precedentes.add(Integer.parseInt(rs2.getString("codigo")));
                
                ps = con.prepareStatement("SELECT id_incompativel as 'codigo' FROM incompativeis WHERE id_componente = ?");
                ps.setString(1, rs.getString("codigo"));
                rs2 = ps.executeQuery();
                
                while(rs2.next())
                    incompativeis.add(Integer.parseInt(rs2.getString("codigo")));
                
                Componente c = new Componente(Integer.parseInt(rs.getString("codigo")), rs.getString("designacao"), Double.parseDouble(rs.getString("preco")), Integer.parseInt(rs.getString("quantidade")), rs.getString("tipo"), precedentes, incompativeis);
               
                lista.add(c);
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        
        
        return lista;
    }
    
    /**
     * Actualiza na base de dados a renda de um determinado quarto.
     * @param key ID do quarto
     * @param preco Nova renda
     * @throws SQLException Atira exceção caso ocorram erros SQL
     */
    public void updateStock(int key, int quantidade) throws SQLException {
        Connection con = Connect.connect();
        
        PreparedStatement ps = con.prepareStatement("UPDATE componentes SET stock = stock + ? WHERE id_componente = ?");
        ps.setDouble(1,quantidade);
        ps.setInt(2,key);
        ps.executeUpdate();
        con.close();
    }
}
