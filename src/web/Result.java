package web;

import db.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Thierry on 2/7/15.
 */
public class Result extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameters = request.getParameterMap();

        ArrayList<Post> posts = Post.getList(parameters.get("title")[0],
                parameters.get("bac")[0],
                parameters.get("duree")[0]);

        request.setAttribute("posts", posts);
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException{
        //do nothing
    }

}
