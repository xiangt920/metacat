/*
 * Copyright 2016 Netflix, Inc.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.netflix.metacat.s3.connector.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

/**
 * Created by amajumdar on 12/19/14.
 */
@Entity
@javax.persistence.Table(name="table_object",
        indexes = { @Index(name="table_object_i1", columnList = "name") },
        uniqueConstraints= @UniqueConstraint(name="table_object_u1", columnNames = {"database_id", "name"}))
@NamedQueries({
        @NamedQuery(
                name = Table.NAME_QUERY_GET_BY_SOURCE_DATABASE_TABLE_NAMES,
                query = "select t from Table t where t.database.source.name=:sourceName and t.database.name=:databaseName and t.name in (:tableNames)"
        )
})
public class Table extends BaseTable{
    public static final String NAME_QUERY_GET_BY_SOURCE_DATABASE_TABLE_NAMES = "getBySourceDatabaseTableNames";
    private Database database;
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "database_id", nullable = false)
    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "table")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}