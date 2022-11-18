package com.tang.newcloud.service.statistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 网站统计日数据
 * @TableName statistics_daily
 */
@TableName(value ="statistics_daily")
@Data
public class Daily extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 统计日期
     */
    private String dateCalculated;

    /**
     * 注册人数
     */
    private Integer registerNum;

    /**
     * 登录人数
     */
    private Integer loginNum;

    /**
     * 每日播放视频数
     */
    private Integer videoViewNum;

    /**
     * 每日新增课程数
     */
    private Integer courseNum;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Daily other = (Daily) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDateCalculated() == null ? other.getDateCalculated() == null : this.getDateCalculated().equals(other.getDateCalculated()))
            && (this.getRegisterNum() == null ? other.getRegisterNum() == null : this.getRegisterNum().equals(other.getRegisterNum()))
            && (this.getLoginNum() == null ? other.getLoginNum() == null : this.getLoginNum().equals(other.getLoginNum()))
            && (this.getVideoViewNum() == null ? other.getVideoViewNum() == null : this.getVideoViewNum().equals(other.getVideoViewNum()))
            && (this.getCourseNum() == null ? other.getCourseNum() == null : this.getCourseNum().equals(other.getCourseNum()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDateCalculated() == null) ? 0 : getDateCalculated().hashCode());
        result = prime * result + ((getRegisterNum() == null) ? 0 : getRegisterNum().hashCode());
        result = prime * result + ((getLoginNum() == null) ? 0 : getLoginNum().hashCode());
        result = prime * result + ((getVideoViewNum() == null) ? 0 : getVideoViewNum().hashCode());
        result = prime * result + ((getCourseNum() == null) ? 0 : getCourseNum().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dateCalculated=").append(dateCalculated);
        sb.append(", registerNum=").append(registerNum);
        sb.append(", loginNum=").append(loginNum);
        sb.append(", videoViewNum=").append(videoViewNum);
        sb.append(", courseNum=").append(courseNum);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}