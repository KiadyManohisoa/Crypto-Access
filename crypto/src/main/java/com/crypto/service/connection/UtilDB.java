package com.crypto.service.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilDB {
    
    String base ; 
    String user ;
    String pwd ;
    Connection connection ;
    
    public UtilDB(String base, String user, String pwd) throws Exception{
         try {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/";  // Modifiez en fonction de votre configuration
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcUrl+base, user, pwd);
            
        }
        catch(ClassNotFoundException e){
            System.err.println("Pilote postgres JDBC introuvable !");
            throw e;
        } 
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            throw e;
        }
    }
}
