package myconstruction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/login_app_db"; // Cambia por tu DB
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Establecer conexión cn la BD
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // query que valida las credenciales
            String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            // consulta y valida la query
            if (preparedStatement.executeQuery().next()) {
                response.getWriter().println("Login exitoso!");
            } else {
                response.getWriter().println("Credenciales inválidas!");
            }

            // Cerrar la conexión
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al conectar a la base de datos.");
        }
    }
}