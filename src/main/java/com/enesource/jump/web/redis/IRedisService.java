package com.enesource.jump.web.redis;

import java.util.List;
import java.util.Set;

public interface IRedisService {
	
	public boolean set(String key, String value);  
    
    public String get(String key);

    public boolean expire(String key,long expire);
      
    public long lpush(String key,String value);  
      
    public long rpush(String key,String value);  
      
    public String lpop(String key);  
    
    public long lLen(String key);
    
    public String getListLastValue(String key);
    
    /**
     * 当 key 不存在时，返回 -2
     * 当 key 存在但没有设置剩余生存时间时，返回 -1
     * 否则，以毫秒为单位，返回 key 的剩余生存时间
     * @param key
     * @return
     */
    public Long ttl(String key);
    
    public Boolean exists(String key);
    
    public Long del(String key);
    
    public Long incr(String key);
    
    public Long hincr(String key, String field);
    
    public Long incrBy(String key, Long incr);
    
    public boolean hSet(String key, String field, String value);
    
    public String hGet(String key, String field);
    
    public List<byte[]> lRange(String key, long start, long end);
    
    
    public Set<byte[]> keys(String key);
    
    // SET
    
    /**
     * 向集合添加一个或多个成员
     * @param key
     * @param value
     * @return
     */
    public Long sadd(String key,String value);
    
    /**
     * 返回集合中元素的数量
     * @param key
     * @return
     */
    public Long scard(String key);
    
    /**
     * 命令判断成员元素是否是集合的成员
     * @param key
     * @param value
     * @return
     */
    public Boolean sismember(String key,String value);
    
    
    /**
     * 返回集合中的所有的成员
     * @param key
     * @return
     */
    public Set<byte[]> smembers(String key);
}
