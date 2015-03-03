package web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Thierry on 2/7/15.
 */
public class Result extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.getWriter().println("no result");

    }

    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        String title = parameters.get("title")[0];
        String durant = "";
        String bac = "";
        String field = "";
        if (parameters.keySet().contains("duree")) {
            durant = parameters.get("duree")[0];
        }
        if (parameters.keySet().contains("bac")) {
            bac = parameters.get("bac")[0];
        }
        if (parameters.keySet().contains("field")) {
            field = parameters.get("field")[0];
        }
        res.getWriter().println("title : " + title);
        res.getWriter().println("duration : " + durant);
        res.getWriter().println("bac : " + bac);
        res.getWriter().println("field : " + field);
    }

}
