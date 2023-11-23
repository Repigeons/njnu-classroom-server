package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 校车时刻表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "shuttle")
public class Shuttle {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 路线方向
     */
    @Column(value = "route")
    private Integer route;

    /**
     * 发车时间
     */
    @Column(value = "start_time")
    private String startTime;

    /**
     * 起点站
     */
    @Column(value = "start_station")
    private String startStation;

    /**
     * 终点站
     */
    @Column(value = "end_station")
    private String endStation;

    /**
     * 发车数量
     */
    @Column(value = "shuttle_count")
    private Integer shuttleCount;

    /**
     * 工作日/双休日
     */
    @Column(value = "working")
    private String working;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
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
