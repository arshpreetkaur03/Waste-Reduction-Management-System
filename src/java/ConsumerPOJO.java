import java.util.Date;

/**
 * Represents a consumer purchase.
 */
public class ConsumerPOJO {
    private int purchaseId;
    private int itemId;
    private Date purchaseDate;
    private int quantityClaimed;

    /**
     * Constructs a new ConsumerPOJO instance.
     *
     * @param itemId         The ID of the item purchased.
     * @param purchaseDate   The date of the purchase.
     * @param quantityClaimed The quantity claimed for the purchase.
     */
    public ConsumerPOJO(int itemId, Date purchaseDate, int quantityClaimed) {
        this.itemId = itemId;
        this.purchaseDate = purchaseDate;
        this.quantityClaimed = quantityClaimed;
    }

    /**
     * Gets the purchase ID.
     *
     * @return The purchase ID.
     */
    public int getPurchaseId() {
        return purchaseId;
    }

    /**
     * Sets the purchase ID.
     *
     * @param purchaseId The purchase ID to set.
     */
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * Gets the item ID of the purchase.
     *
     * @return The item ID of the purchase.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets the item ID of the purchase.
     *
     * @param itemId The item ID to set.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets the date of the purchase.
     *
     * @return The date of the purchase.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the date of the purchase.
     *
     * @param purchaseDate The date of the purchase to set.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets the quantity claimed for the purchase.
     *
     * @return The quantity claimed for the purchase.
     */
    public int getQuantityClaimed() {
        return quantityClaimed;
    }

    /**
     * Sets the quantity claimed for the purchase.
     *
     * @param quantityClaimed The quantity claimed to set.
     */
    public void setQuantityClaimed(int quantityClaimed) {
        this.quantityClaimed = quantityClaimed;
    }
}
