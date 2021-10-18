/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LocationDAO;
import DTO.LocationDTO;
import Extension.AI;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author WilliamTrung
 */
@WebServlet(name = "CreateLocationController", urlPatterns = {"/CreateLocationController"})
public class CreateLocationController extends HttpServlet {

    private final String SUCCESS = "ViewLocationController";
    private final String FAIL = "ViewLocationController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            String locationName = request.getParameter("locationName");
            String seat_temp = request.getParameter("seat");
            locationName = AI.inputVietnamese(locationName);
            int seat = 30;
            boolean check = true;
            if (locationName.isEmpty() || locationName.trim().isEmpty()) {
                check = false;
            }
            if (seat_temp != null && !seat_temp.isEmpty()) {
                seat = Integer.parseInt(seat_temp);
            }
            if (check) {
                LocationDTO location = new LocationDTO(null, seat, locationName);
                LocationDAO lDao = new LocationDAO();
                check = lDao.insertLocation(location);
                if (check) {
                    request.setAttribute("NOTIFICATION", "Add successfully!");
                    url = SUCCESS;
                }
            } else {
                //set index pool to view
                request.setAttribute("index", 1);
            }
        } catch (Exception e) {
            log("Error at CreateLocationController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
