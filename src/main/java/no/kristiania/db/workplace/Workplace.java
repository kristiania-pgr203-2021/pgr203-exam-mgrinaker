package no.kristiania.db.workplace;

public class Workplace {
    private long workplaceId;
    private String workplaceName;
    private String workplaceAddress;

    public long getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(long workplaceId) {
        this.workplaceId = workplaceId;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public String getWorkplaceAddress() {
        return workplaceAddress;
    }

    public void setWorkplaceAddress(String workplaceAddress) {
        this.workplaceAddress = workplaceAddress;
    }
}
