package com.groupnine.oss.user.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.groupnine.oss.user.entity.FavortiteGoods;
import com.groupnine.oss.user.entity.Searches;
import com.groupnine.oss.user.service.UserService;

@WebServlet("/getGoodsFavorite.action")
public class GetGoodsFavoriteAction extends HttpServlet {

    public GetGoodsFavoriteAction() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        /* 1. read parameters */

        boolean hasLogined = request.getSession().getAttribute("userLoginStatus").equals("true")
                ? true
                : false;
        String uid = (String) request.getSession().getAttribute("userId");
        String nPerPage = request.getParameter("maxNumInOnePage");
        String pageNum = request.getParameter("pageNum");
        // debug begin
        // hasLogined = true;
        // uid = request.getParameter("uid");
        // debug end

        /* 2. invoke service */

        // 为使得返回 JSON 为空数组，而不是 null
        ArrayList<FavortiteGoods> goods = new ArrayList<FavortiteGoods>();
        if (hasLogined && Searches.isMaxNumInOnePageWF(nPerPage) &&
                Searches.isCurrentPageWF(pageNum)) {
            goods = UserService.getGoodsFavorite(uid, nPerPage, pageNum);
        }

        /* 3. return JSON */

        Gson gson = new Gson();
        gson.toJson(goods, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
