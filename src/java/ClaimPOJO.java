import java.util.Date;

/**
 * The ClaimPOJO class represents a claim made for a specific item.
 */
public class ClaimPOJO {
    private int claimId;
    private int itemId;
    private Date claimDate;
    private int quantityClaimed;

    /**
     * Constructs a ClaimPOJO object with the specified item ID, claim date, and quantity claimed.
     *
     * @param itemId          The ID of the item for which the claim is made.
     * @param claimDate       The date when the claim was made.
     * @param quantityClaimed The quantity of the item claimed.
     */
    public ClaimPOJO(int itemId, Date claimDate, int quantityClaimed) {
        this.itemId = itemId;
        this.claimDate = claimDate;
        this.quantityClaimed = quantityClaimed;
    }

    /**
     * Gets the ID of the claim.
     *
     * @return The ID of the claim.
     */
    public int getClaimId() {
        return claimId;
    }

    /**
     * Sets the ID of the claim.
     *
     * @param claimId The ID of the claim.
     */
    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    /**
     * Gets the ID of the item for which the claim is made.
     *
     * @return The ID of the item.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets the ID of the item for which the claim is made.
     *
     * @param itemId The ID of the item.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets the date when the claim was made.
     *
     * @return The date of the claim.
     */
    public Date getClaimDate() {
        return claimDate;
    }

    /**
     * Sets the date when the claim was made.
     *
     * @param claimDate The date of the claim.
     */
    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    /**
     * Gets the quantity of the item claimed.
     *
     * @return The quantity claimed.
     */
    public int getQuantityClaimed() {
        return quantityClaimed;
    }

    /**
     * Sets the quantity of the item claimed.
     *
     * @param quantityClaimed The quantity claimed.
     */
    public void setQuantityClaimed(int quantityClaimed) {
        this.quantityClaimed = quantityClaimed;
    }
}