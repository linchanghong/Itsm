/**
 * @Title: ProxoolServlet.java
 * @Package com.sccl.serviceManager.bugManager.api
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-23 上午11:19:55
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.logicalcobwebs.proxool.ProxoolFacade;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.api.ProxoolServlet.java
 * @ClassName: ProxoolServlet
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-23
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-23 上午11:19:55
 * @Version:v1.0
 */
public class ProxoolServlet extends HttpServlet {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = -1514576424847656158L;

    /* 
     * <p>Title: doGet</p>
     * <p>Description: </p>
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }

    /* 
     * <p>Title: doPost</p>
     * <p>Description: </p>
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

    /* 
     * <p>Title: destroy</p>
     * <p>Description: </p>
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        ProxoolFacade.shutdown();
    }

    /* 
     * <p>Title: init</p>
     * <p>Description: </p>
     * @throws ServletException
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
    }

}
