package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 位置坐标 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "positions")
public class Positions {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 名称
     */
    @Column(value = "name")
    private String name;

    /**
     * 纬度
     */
    @Column(value = "latitude")
    private Float latitude;

    /**
     * 经度
     */
    @Column(value = "longitude")
    private Float longitude;

    /**
     * 1=教学楼 2=校车站
     */
    @Column(value = "kind")
    private Integer kind;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }
}
