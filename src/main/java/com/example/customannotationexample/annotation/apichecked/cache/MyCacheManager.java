package com.example.customannotationexample.annotation.apichecked.cache;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 20:56
 */
@Slf4j
public class MyCacheManager {

        /**
         * 定义多长时间描扫一次过期的数据，默认为5秒
         */
        private static final long CLEAN_UP_PERIOD_IN_SEC = 5;

        /**
         * 使用Map数据结构存储缓存数据，key是字符串类型，value是软引用类型，当内存确实不够用的时候，jvm会回收对应的value引用对象
         */
        private final ConcurrentHashMap<String, SoftReference<Cache>> cacheMap = new ConcurrentHashMap<>();

        public MyCacheManager(){
            this(CLEAN_UP_PERIOD_IN_SEC);
        }

        public MyCacheManager(long period){
            long realPeriod = period > CLEAN_UP_PERIOD_IN_SEC ? period : CLEAN_UP_PERIOD_IN_SEC ;
            // 定义一个定时任务，清理已过期的缓存数据
            Thread cleanerThread = new Thread(() -> {
                while(!Thread.currentThread().isInterrupted()){
                    try {
                        Thread.sleep(realPeriod * 1000); // 每5秒清理缓存一次
                        for(Map.Entry<String, SoftReference<Cache>> entry : cacheMap.entrySet()){
                            Cache cache = entry.getValue().get();
                            if(cache.isExpiry()){
                                // 注释 log.warn("the entry of  key {}  is expiry ！" , entry.getKey());
                                System.out.println(realPeriod + " sec. is over, the entry of  key "+entry.getKey()+"  is expiry ！"  );
                                cacheMap.remove(entry.getKey());
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            });
            cleanerThread.setDaemon(true);
            cleanerThread.start();
        }

        /**
         * 清除所有缓存
         */
        public void clear(){
            cacheMap.clear();
        }

        /**
         * 返回缓存管理器里面缓存的元素的个数
         * @return
         */
        public long size(){
            if(cacheMap.isEmpty()){
                return 0L;
            }
            Set<Map.Entry<String, SoftReference<Cache>>> entrySet = cacheMap.entrySet();
            long count = 0L;
            for(Map.Entry<String, SoftReference<Cache>> entry : entrySet){
                SoftReference<Cache> value = entry.getValue();
                Cache cache = value.get();
                if(!cache.isExpiry()){
                    count++;
                }
            }
            return count;
        }

        /**
         * 增加或更新一个缓存数据
         * @param key
         * @param value
         * @param expiryTime -1表示不超期，单位为毫秒
         * @return 返回null或者旧的缓存值
         */
        public Object put(String key, Object value, long expiryTime){
            if(key == null){
                return null;
            }
            if(value == null){
                return extractObjVal(cacheMap.remove(key));
            }
            if(expiryTime <= 0){
                expiryTime = -1;
            }else{
                expiryTime = System.currentTimeMillis() + expiryTime;
            }
            return extractObjVal(cacheMap.put(key, new SoftReference<>(new Cache(value, expiryTime))));
        }

        /**
         * 删除缓存
         * @param key
         * @return
         */
        public Object remove(String key){
            if(key == null){
                return null;
            }
            return extractObjVal(cacheMap.remove(key));
        }

        /**
         * 从缓存中获取值，没有找到返回null，找到但已超期也返回null。
         * @param key
         * @return
         */
        public Object get(String key){
            if(key == null){
                return null;
            }
            SoftReference<Cache> softReference = cacheMap.get(key);
            if(softReference == null){
                return null;
            }else{
                Cache cache = softReference.get();
                if(cache.isExpiry()){
                    // 访问的时候被动删除超期的元素
                    cacheMap.remove(key);
                    return null;
                }else{
                    return cache.getValue();
                }
            }
        }

        /**
         * 抽取cache对象里面的缓存值
         * @param cacheSoftReference
         * @return
         */
        private Object extractObjVal(SoftReference<Cache> cacheSoftReference){
            return cacheSoftReference == null ? null : cacheSoftReference.get().getValue();
        }

        private static class Cache{
            private Object value; // 缓存数据对象
            private long expiryTime; // 超时时间，以时间戳的形式代表，-1表示不超期

            public Cache(Object value, long expiryTime){
                this.value = value;
                this.expiryTime = expiryTime;
            }

            /**
             * 判断缓存是否已经超期
             * @return true - 已超期，false - 未超期
             */
            public boolean isExpiry(){
                if(expiryTime == -1){
                    return false;
                }
                return System.currentTimeMillis() > expiryTime;
            }

            public Object getValue() {
                return value;
            }

            public void setValue(Object value) {
                this.value = value;
            }
        }

        public static void main(String[] args) throws InterruptedException {
            MyCacheManager cacheManager = new MyCacheManager();

            cacheManager.put("1", "jerry", 3 * 1000);
            System.out.println("get 1 emp :" + cacheManager.get("1"));
            Thread.sleep(5000);
            System.out.println("get 1 emp :" + cacheManager.get("1"));
            System.out.println("-------");

            cacheManager.put("2", "tom", 6 * 1000);

            System.out.println("get 2 emp :" + cacheManager.get("2"));
            Thread.sleep(5000);
            System.out.println("get 2 emp :" + cacheManager.get("2"));
            System.out.println("-------");


            cacheManager.put("3", "jack", 8 * 1000);
            System.out.println("get 3 emp :" + cacheManager.get("3"));
            Thread.sleep(5000);
            System.out.println("get 3 emp :" + cacheManager.get("3"));
        }



}
