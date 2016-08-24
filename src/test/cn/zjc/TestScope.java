package cn.zjc;

import com.google.common.cache.*;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjinci
 * @version 2016/8/16 11:28
 * @function 测试缓存
 */
public class TestScope {


    @Test
    public void TestLoadingCache() throws Exception {
        LoadingCache<String, String> cacheBuilder = CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>() {
                    //key未命中的时候加载这个方法里面的value
                    @Override
                    public String load(String key) throws Exception {
                        String preKey = "hello " + key + "!";
                        return preKey;
                    }
                });

        cacheBuilder.put("name", "zjcscut");
        System.out.println(cacheBuilder.get("name"));
    }


    @Test
    public void TestCallableCache() throws Exception{
        Cache<String,String> cache = CacheBuilder
                .newBuilder()
                .maximumSize(1000)
                .build();
        cache.put("jerry","sssssssssssssssssssssss");

        String resultVal = cache.get("jerry", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello jerry !当缓存未命中的时候执行";
            }
        });

        System.out.println("jerry value:" + resultVal);

        resultVal = cache.get("peida", new Callable<String>() {
            public String call() {

                return "hello "+"peida"+"!";
            }
        });
        System.out.println("peida value : " + resultVal);
    }

    private static Cache<String,String> cacheFromCallable = null;

    public static <K,V> Cache<K,V> callableCached() throws Exception{
        Cache<K,V> cache = CacheBuilder
                .newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .refreshAfterWrite(10,TimeUnit.SECONDS)
                .recordStats()   //开启统计功能
                .build(
                        new CacheLoader<K, V>() {
                            @Override
                            public V load(K k) throws Exception {
                                return null;
                            }
                        }
                );
        return cache;
    }

    public String getCallableCache(final String userName){
        try {
            //Callable只有在缓存值不存在的时候才会调用
            return cacheFromCallable.get(userName, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("callback " + userName + " from DB");
                    return "hello " + userName + " !";
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void TestCallableCacheCall() throws Exception{
        final String u1name = "zjc";
        final String u2name = "evan";
        cacheFromCallable = callableCached();
        System.out.println(getCallableCache(u1name));
        System.out.println(getCallableCache(u2name));
        System.out.println("命中缓存========>" + getCallableCache(u1name));
        CacheStats stats =cacheFromCallable.stats();
        System.out.println("总次数:" + (stats.hitCount() + stats.missCount()) + " 命中次数:" + stats.hitCount()

        +" 成功加载次数:" + stats.loadSuccessCount() + " 异常加载次数:"+ stats.loadExceptionCount() + " 加载总时间:" + (stats.totalLoadTime()/1000000f)

        + "s 清除缓存个数:" + stats.evictionCount() + " 命中率:" + new DecimalFormat("#.##").format(stats.hitRate()));
    }

    @Test
    public void TestStr(){
        String address = "xxx省zzz市";
         address = address.substring(address.lastIndexOf("省") <= 0 ? 0 : address.lastIndexOf("省") + 1,
                address.indexOf("市") <= 0 ? 0 : address.indexOf("市")).trim();

        System.out.println(address);
    }

    @Test
    public void TestEnum(){
        System.out.println(DataBases.MYSQL);
        System.out.println(DataBases.MYSQL.name());
    }




}
