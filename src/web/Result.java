package web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Thierry on 2/7/15.
 */
public class Result extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("result");
        res.getWriter().println("result");
    }

    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("post result");
        res.getWriter().println(req.getParameterMap());
    }

}
