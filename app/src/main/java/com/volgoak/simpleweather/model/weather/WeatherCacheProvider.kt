package com.volgoak.simpleweather.model.weather

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder

class WeatherCacheProvider(
        private val api: WeatherApi
) {
    /*private val memoryPolicy = MemoryPolicy.builder()
            .setExpireAfterWrite(cacheExpireTime)
            .build()

    private val orderListStore by lazy {
        StoreBuilder.key<OrdersKey, OrderData>()
                .fetcher { key -> api.activeOrdersList(key.count, key.offset) }
                .memoryPolicy(memoryPolicy)
                .open()
    }

    private val orderStore by lazy {
        StoreBuilder.key<String, OrderInfoData>()
                .fetcher { id -> api.orderInfo(id) }
                .memoryPolicy(memoryPolicy)
                .open()
    }

    private val contextStore by lazy {
        StoreBuilder.key<Unit, UserContext>()
                .fetcher { api.getUserContext() }
                .memoryPolicy(memoryPolicy)
                .open()
    }

    fun getOrderList(
            count: Int,
            offset: Int
    ) =
            orderListStore.fetch(OrdersKey(offset, count))
                    .onErrorResumeNext {
                        orderListStore.get(OrdersKey(offset, count))
                    }

    fun getOrderInfo(id: String) =
            orderStore.fetch(id)
                    .onErrorResumeNext {
                        orderStore.get(id)
                    }

    fun getUserContext() =
            contextStore.fetch(Unit)
                    .onErrorResumeNext {
                        contextStore.get(Unit)
                    }*/
}