package example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Tokenize extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONObject row = new JSONObject();

        try {
            Connection conn = DBConnection.initializeDB();
            Statement statement = conn.createStatement();
            String query = "Select * from intents";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                row.put("id", rs.getInt("id"));
                row.put("intent", rs.getString("intent"));
                out.println(row);
//                out.println(rs.getInt("id"));
//                out.println(rs.getString("intent"));
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Connection conn = DBConnection.initializeDB();

            String[] params = Parameters.getParamsFromPost(request);
            int id = Integer.parseInt(params[0].substring(12, 13));
            out.println(id);

//            int id = Integer.parseInt(request.getParameter("id"));

            PreparedStatement st = conn.prepareStatement("SELECT article FROM articles WHERE id = (?)");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            String article = null;
            while(rs.next()) {
                article = rs.getString("article");
            }
            out.println(article);

            nlpPipeline.init();
            String intent = nlpPipeline.findIntent(article);
            out.println(intent);

            PreparedStatement insertSt = conn.prepareStatement("INSERT INTO intents(id, intent) VALUES (?, ?);");
            insertSt.setInt(1, id);
            insertSt.setString(2, intent);
            insertSt.executeQuery();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
