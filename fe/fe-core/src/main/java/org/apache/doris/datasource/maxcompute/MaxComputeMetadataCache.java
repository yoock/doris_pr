// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.datasource.maxcompute;

import org.apache.doris.common.Config;
import org.apache.doris.datasource.TablePartitionValues;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MaxComputeMetadataCache {
    private final Cache<MaxComputeCacheKey, TablePartitionValues> partitionValuesCache;

    public MaxComputeMetadataCache() {
        partitionValuesCache = Caffeine.newBuilder().maximumSize(Config.max_hive_partition_cache_num)
                .expireAfterAccess(Config.external_cache_refresh_time_minutes, TimeUnit.MINUTES)
                .build();
    }

    public TablePartitionValues getCachedPartitionValues(MaxComputeCacheKey tablePartitionKey,
            Function<? super MaxComputeCacheKey, ? extends TablePartitionValues> loader) {
        return partitionValuesCache.get(tablePartitionKey, loader);
    }

    public void cleanUp() {
        partitionValuesCache.invalidateAll();
    }

    public void cleanDatabaseCache(String dbName) {
        List<MaxComputeCacheKey> removeCacheList = partitionValuesCache.asMap().keySet()
                .stream()
                .filter(k -> k.getDbName().equalsIgnoreCase(dbName))
                .collect(Collectors.toList());
        partitionValuesCache.invalidateAll(removeCacheList);
    }

    public void cleanTableCache(String dbName, String tblName) {
        MaxComputeCacheKey cacheKey = new MaxComputeCacheKey(dbName, tblName);
        partitionValuesCache.invalidate(cacheKey);
    }
}
