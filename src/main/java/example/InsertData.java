package example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Arrays;

public class InsertData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONObject row = new JSONObject();

        try {
            Connection conn = DBConnection.initializeDB();
            Statement statement = conn.createStatement();
            String query = "Select * from articles";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                row.put("id", rs.getInt("id"));
                row.put("article", rs.getString("article"));
                out.println(row);
//                out.println(rs.getInt("id"));
//                out.println(rs.getString("article"));
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Connection conn = DBConnection.initializeDB();

            String[] params = Parameters.getParamsFromPost(request);
            //out.println(Arrays.toString(params));
            int id = Integer.parseInt(params[0].substring(7, 8));
            int index = params[0].indexOf("}");
            String article = params[0].substring(22, index-1);
            out.println(article);

//            int id = Integer.parseInt(request.getParameter("id"));
//            String article = request.getParameter("article");

            PreparedStatement st = conn.prepareStatement("INSERT INTO articles(id, article) VALUES (?, ?);");
            st.setInt(1, id);
            st.setString(2, article);
            st.executeQuery();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
