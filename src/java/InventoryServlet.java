import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InventoryServlet
 * Handles requests related to inventory management.
 */
@WebServlet("/InventoryServlet")
public class InventoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private InventoryDAO inventoryDAO;

    /**
     * Initializes the servlet.
     */
    public void init() {
        inventoryDAO = new InventoryDAO();
    }
    
    /**
     * Processes GET requests.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
   
        if (action == null || action.isEmpty()) {
            // If no action specified, forward to the default page
            request.getRequestDispatcher("InventoryItem.jsp").forward(request, response);
            return; // Added to stop further execution
        }

        switch (action) {
            case "add":
                // Forward to the add inventory page
                request.getRequestDispatcher("addInventory.jsp").forward(request, response);
                break;
            case "update":
                // Forward to the update inventory page
                List<InventoryItem> inventoryForUpdate = inventoryDAO.getInventory();
                request.setAttribute("inventory", inventoryForUpdate);
                request.getRequestDispatcher("updateInventory.jsp").forward(request, response);
                break;
            case "delete":
                // Forward to the delete inventory page
                List<InventoryItem> inventoryForDelete = inventoryDAO.getInventory();
                request.setAttribute("inventory", inventoryForDelete);
                request.getRequestDispatcher("deleteInventory.jsp").forward(request, response);
                break;
            case "viewSurplus":
                // Forward to the view surplus inventory page
                request.getRequestDispatcher("surplusItem.jsp").forward(request, response);
                break;
            default:
                // Forward to the default page if action is invalid
                request.getRequestDispatcher("InventoryItem.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Processes POST requests.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    // Handle add inventory operation
                    String itemName = request.getParameter("itemName");
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    String expirationDateStr = request.getParameter("expirationDate");
                    Date expirationDate = null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        expirationDate = dateFormat.parse(expirationDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing exception appropriately
                    }
                    
                    double retailerPrice = Double.parseDouble(request.getParameter("retailerPrice"));
                    boolean isDonation = Boolean.parseBoolean(request.getParameter("isDonation"));
                    boolean isSale = Boolean.parseBoolean(request.getParameter("isSale"));
                    double discountPrice = Double.parseDouble(request.getParameter("discountPrice"));

                    // Create an InventoryItem object with the obtained values
                    InventoryItem newItem = new InventoryItem();
                    newItem.setItemName(itemName);
                    newItem.setQuantity(quantity);
                    newItem.setExpirationDate(expirationDate);
                    newItem.setRetailerPrice(retailerPrice);
                    newItem.setDonation(isDonation);
                    newItem.setSale(isSale);
                    newItem.setDiscountPrice(discountPrice);
                    
                    // Pass the InventoryItem object to the addItemToInventory method
                    boolean itemAdded = inventoryDAO.addItemToInventory(newItem);
                    
                    // Redirect to the appropriate page based on the result
                    if (itemAdded) {
                        response.sendRedirect("InventoryItem.jsp");
                    } else {
                        // Handle the case where item addition fails
                        // You can redirect to an error page or display a message
                        response.sendRedirect("Failed.jsp");
                    }
                    break; 
                
                case "update":
                    // Handle update inventory operation
                    int itemId = Integer.parseInt(request.getParameter("itemId"));
                    String newItemName = request.getParameter("newItemName");
                    int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));
                    double newRetailerPrice = Double.parseDouble(request.getParameter("newRetailerPrice"));
                    boolean newIsDonation = Boolean.parseBoolean(request.getParameter("newIsDonation"));
                    boolean newIsSale = Boolean.parseBoolean(request.getParameter("newIsSale"));
                    double newDiscountPrice = Double.parseDouble(request.getParameter("newDiscountPrice"));
                    String newExpirationDateStr = request.getParameter("newExpirationDate");

                    SimpleDateFormat simpledateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date newExpirationDate;
                    try {
                        newExpirationDate = simpledateFormat.parse(newExpirationDateStr);
                        boolean updateSuccess = inventoryDAO.updateInventoryItem(itemId, newItemName, newQuantity, newExpirationDate, newRetailerPrice, newIsDonation, newIsSale, newDiscountPrice);
                        if (updateSuccess) {
                            // Redirect to update page if update is successful
                            response.sendRedirect("InventoryItem.jsp");
                        } else {
                            // Handle update failure
                            response.sendRedirect("InventoryItem.jsp"); // Redirect to an error page
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing exception
                        response.sendRedirect("InventoryItem.jsp"); // Redirect to an error page
                    }
                    break;

                case "delete":
                    // Handle delete inventory operation
                    String deleteItemIdStr = request.getParameter("itemId");
                    if (deleteItemIdStr != null && !deleteItemIdStr.isEmpty()) {
                        try {
                            int deleteItemId = Integer.parseInt(deleteItemIdStr);
                            boolean deleteSuccess = inventoryDAO.deleteInventoryItem(deleteItemId);
                            if (deleteSuccess) {
                                // Deletion successful
                                response.sendRedirect("InventoryItem.jsp");
                            } else {
                                // Deletion failed
                                // Log the error
                                System.err.println("Failed to delete item with ID: " + deleteItemId);
                                // Redirect to an error page or display an error message
                                response.sendRedirect("InventoryItem.jsp");
                            }
                        } catch (NumberFormatException e) {
                            // Handle invalid item ID
                            System.err.println("Invalid item ID: " + deleteItemIdStr);
                            response.sendRedirect("InventoryItem.jsp");
                        }
                    } else {
                        // Handle missing item ID parameter
                        System.err.println("Missing item ID parameter");
                        response.sendRedirect("InventoryItem.jsp");
                    }
                    break;
                
                case "viewSurplus":
                    // Handle view surplus inventory operation
                    String viewSurplusItemIdStr = request.getParameter("itemId");
                    if (viewSurplusItemIdStr != null && !viewSurplusItemIdStr.isEmpty()) {
                        try {
                            int surplusItemId = Integer.parseInt(viewSurplusItemIdStr);
                            boolean surplusSuccess = inventoryDAO.flagSurplusItem(surplusItemId);
                            if (surplusSuccess) {
                                // Flagging as surplus successful
                                response.sendRedirect("surplusItem.jsp");
                            } else {
                                // Flagging as surplus failed
                                // Log the error
                                System.err.println("Failed to flag item with ID as surplus: " + surplusItemId);
                                // Redirect to an error page or display an error message
                                response.sendRedirect("InventoryItem.jsp");
                            }
                        } catch (NumberFormatException e) {
                            // Handle invalid item ID
                            System.err.println("Invalid item ID: " + viewSurplusItemIdStr);
                            response.sendRedirect("InventoryItem.jsp");
                        }
                    } else {
                        // Handle missing item ID parameter
                        System.err.println("Missing item ID parameter");
                        response.sendRedirect("InventoryItem.jsp");
                    }
                    break;
            }     
        }
    }
}
