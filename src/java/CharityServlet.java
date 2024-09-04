import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CharityServlet
 * 
 * This servlet handles requests related to charity donations.
 */
// Import statements omitted for brevity

@WebServlet("/CharityServlet")
public class CharityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InventoryDAO inventoryDAO = new InventoryDAO(); // Handle database error
        List<InventoryItem> donationItems = inventoryDAO.getDonationItems();
        request.setAttribute("donationItems", donationItems);
        request.getRequestDispatcher("ClaimInformation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("claim")) {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            Timestamp claimDate = new Timestamp(System.currentTimeMillis());

            try (Connection connection = DBConnection.getConnection()) {
                ClaimDAO claimDAO = new ClaimDAO(connection);
                int quantityClaimed = claimDAO.getQuantityClaimed(itemId);
                quantityClaimed++;

                ClaimPOJO claim = new ClaimPOJO(itemId, claimDate, quantityClaimed);
                boolean success = claimDAO.addOrUpdateClaim(claim);

                if (success) {
                    InventoryDAO inventoryDAO = new InventoryDAO();
                    success = inventoryDAO.decreaseInventoryItemQuantity(itemId);
                    
                    
                    if (success) {
                        // Claim successful
                        response.sendRedirect("ClaimInformation.jsp?claimMessage=Claim successful");
                        return;
                    }
                }
            } 
            
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Claim unsuccessful or invalid action
        response.sendRedirect("ClaimInformation.jsp?claimMessage=Claim unsuccessful");
    }
}