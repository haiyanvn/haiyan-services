/*
 * Copyright (c) 2014 HaiyanVN Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.haiyanvn.restp.apps.todo.model;

import org.haiyanvn.restp.models.BaseEntity;
import org.haiyanvn.restp.models.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "feature")
public class Feature extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FeatureStatus status;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endEnd;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private FeaturePriority priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FeatureStatus getStatus() {
        return status;
    }

    public void setStatus(FeatureStatus status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndEnd() {
        return endEnd;
    }

    public void setEndEnd(Date endEnd) {
        this.endEnd = endEnd;
    }

    public FeaturePriority getPriority() {
        return priority;
    }

    public void setPriority(FeaturePriority priority) {
        this.priority = priority;
    }
}
