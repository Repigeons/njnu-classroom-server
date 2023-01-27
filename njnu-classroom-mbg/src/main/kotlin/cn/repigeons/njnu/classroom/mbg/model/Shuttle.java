package cn.repigeons.njnu.classroom.mbg.model;

/**
 * 校车时刻表
 */
public class Shuttle {
    /**
     * @mbg.generated
     */
    private Integer id;

    /**
     * 路线方向
     */
    private Short route;

    /**
     * 发车时间
     */
    private String startTime;

    /**
     * 起点站
     */
    private String startStation;

    /**
     * 终点站
     */
    private String endStation;

    /**
     * 发车数量
     */
    private Integer shuttleCount;

    /**
     * 工作日/双休日
     */
    private String working;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getRoute() {
        return route;
    }

    public void setRoute(Short route) {
        this.route = route;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public Integer getShuttleCount() {
        return shuttleCount;
    }

    public void setShuttleCount(Integer shuttleCount) {
        this.shuttleCount = shuttleCount;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }
}