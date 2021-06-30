/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurafacil.data;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import configurafacil.business.Componente;
import configurafacil.business.Pacote;
import java.sql.SQLException;
import java.sql.PreparedStatement;



public class PacoteDAO {
        
     /**
     * Controi um pacote, dado o seu ID
     * @param key ID do pacote
     * @return Pacote
     * @throws SQLException Atira exceção caso existam problemas SQL
     */
        public Pacote getPacote(Object key)throws SQLException{
            
            List<Componente> lista = new ArrayList<Componente>();
            String nome = null;
            double preco = 0;
            double desconto = 0;
            
            Connection conn = null;
                conn = Connect.connect();
                
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT P.id_pacote AS 'codigo_pacote', P.designacao AS 'pacote', P.desconto AS 'desconto', Subtable.*, CO.id_componente as 'codigo_componente', CO.componentescol AS 'componente', CO.preco AS 'preco', CO.stock AS 'quantidade', CA.designacao AS 'tipo' " +
                        "FROM componentes CO INNER JOIN categorias CA ON CO.categoria = CA.id_categoria " +
                                            "INNER JOIN linhas_de_pacote LP ON CO.id_componente = LP.id_componente " +
                                            "INNER JOIN pacotes P ON P.id_pacote = LP.id_pacote " +
                                            "INNER JOIN (SELECT P.id_pacote AS 'codigo_pacote', SUM(CO.preco) as 'preco', P.desconto as 'desconto', SUM(CO.preco) * (1-P.desconto) as 'precofinal' " +
                                                        "FROM componentes CO INNER JOIN linhas_de_pacote LP ON CO.id_componente = LP.id_componente " +
                                                        "INNER JOIN pacotes P ON P.id_pacote = LP.id_pacote " +
                                                        "WHERE LP.id_pacote = ? " +
                        "GROUP BY P.id_pacote) as Subtable ON P.id_pacote = Subtable.codigo_pacote " +
                        "WHERE LP.id_pacote = ?;"
                        );
                
                ps.setInt(1, Integer.parseInt(key.toString()));
                ps.setInt(2, Integer.parseInt(key.toString()));
                ResultSet rs = ps.executeQuery();
                rs.next();
                
                nome = rs.getString("pacote");
                preco = rs.getDouble("precofinal");
                desconto = rs.getDouble("desconto");
                
                do{
                    List<Integer> dependentes = new ArrayList<Integer>();
                    List<Integer> incompativeis = new ArrayList<Integer>();
                    ps = conn.prepareStatement("SELECT id_incompativel as 'codigo' FROM incompativeis WHERE id_componente = ?");
                    ps.setString(1, rs.getString("codigo_componente"));
                    ResultSet rs2 = ps.executeQuery();
                
                while(rs2.next())
                    incompativeis.add(Integer.parseInt(rs2.getString("codigo")));
                    Componente c = new Componente(rs.getInt("codigo_componente"),rs.getString("componente"),rs.getDouble("preco"),rs.getInt("quantidade"),rs.getString("tipo"), dependentes, incompativeis);
                    lista.add(c);
                }while(rs.next());
            
            Pacote p = new Pacote(nome,preco,desconto,lista);
            return p; 
            
            
        }
}
