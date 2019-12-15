package kirdmt.com.docsworkersvr.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class ExcelData {

    public String action = "addItem";
    public String stage = null;
    public String name = null;
    public String roomNumber = null;
    public String need = null;
    public String category = null;
    public String responsible = null;
    public String notes = null;
    public String added = null;

    @NonNull
    @PrimaryKey
    private long id = 0;

    public ExcelData() {

    }

    public String getAction() {
        return action;
    }

    public String getStage() {
        return stage;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getNeed() {
        return need;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getNotes() {
        return notes;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /*public String getHouseIndex() {
        return houseIndex;
    }

    public void setHouseIndex(String houseIndex) {
        this.houseIndex = houseIndex;
    }*/
}
