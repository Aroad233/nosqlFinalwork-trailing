package EntityClass.rent;

import EntityClass.items.Items;

import java.util.Date;

public class ItemState {
    private String itemName;
    private String Id;
    private Date brrowDate;
    private Date returnDate;
    private float needMoney;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Date getBrrowDate() {
        return brrowDate;
    }

    public void setBrrowDate(Date brrowDate) {
        this.brrowDate = brrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public float getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(float needMoney) {
        this.needMoney = needMoney;
    }
}
