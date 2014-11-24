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

package org.haiyanvn.restp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @Column(name = "uuid", unique = true, columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    @JsonProperty("hiddenUUID")
    private UUID uuid;

    @JsonProperty("uuid")
    @Transient
    private UUID visibleUUID;

    @Column(name = "date_created")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date dateCreated;

    @Column(name = "date_updated")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date dateUpdated;

    @Column(name = "deleted")
    private Boolean deleted;

    @PostLoad
    public void onBaseAfterLoad() {
        visibleUUID = uuid;
    }

    @PrePersist
    public void onBaseSave() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (dateCreated == null) {
            dateCreated = new Date();
        }
        if (deleted == null) {
            deleted = false; // Create new entry
        }
        dateUpdated = new Date();
    }

    @PreUpdate
    public void onBaseUpdate() {
        this.setDateUpdated(new Date());
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getVisibleUUID() {
        return visibleUUID;
    }

    public void setVisibleUUID(UUID visibleUUID) {
        this.visibleUUID = visibleUUID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
