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
        System.out.println("result");
        res.getWriter().println("result");
    }

    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        String title = parameters.get("title")[0];
        String durant = "";
        String bac = "";
        if (parameters.keySet().contains("duree")) {
            durant = parameters.get("duree")[0];
        }
        if (parameters.keySet().contains("bac")) {
            bac = parameters.get("bac")[0];
        }
        String[] fields = parameters.get("field");
        res.getWriter().println(title);
        res.getWriter().println(durant);
        res.getWriter().println(bac);
        for (String f : fields) {
            res.getWriter().println(f);
        }


    }

}
