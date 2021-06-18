package com.enesource.jump.web.redis.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.enesource.jump.web.redis.IRedisService;


@Service("redisService")
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    @Override
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }



    @Override
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));

                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public boolean expire(final String key, final long expire) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Boolean b = connection.expire(serializer.serialize(key), expire);
                return b;
            }
        });
        return result;

    }


    @Override
    public long lpush(final String key, final String value) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public long rpush(final String key, final String value) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    @Override
    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));

                return serializer.deserialize(res);
            }
        });
        return result;
    }

    @Override
    public String getListLastValue(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

                Long len = connection.lLen(serializer.serialize(key));

                if (len == 0) {
                    return null;
                }

                byte[] res = connection.lIndex(serializer.serialize(key), len - 1);

                return serializer.deserialize(res);
            }
        });
        return result;

    }

    @Override
    public Long ttl(final String key) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.ttl(serializer.serialize(key));
                return count;
            }
        });
        return result;
    }

    @Override
    public Boolean exists(final String key) {
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return connection.exists(serializer.serialize(key));
            }
        }, true);
    }

    @Override
    public Long del(final String key) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.del(serializer.serialize(key));
                return l;
            }

        });
        return result;
    }

    @Override
    public Long incr(final String key) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.incr(serializer.serialize(key));
                return l;
            }
        });
        return result;
    }

    @Override
    public Long incrBy(final String key, final Long incr) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.incrBy(serializer.serialize(key), incr);
                return l;
            }
        });
        return result;
    }

    @Override
    public boolean hSet(final String key, final String field, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.hSet(serializer.serialize(key), serializer.serialize(field), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public String hGet(final String key, final String field) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.hGet(serializer.serialize(key), serializer.serialize(field));

                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public long lLen(final String key) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.lLen(serializer.serialize(key));
                return l;
            }
        });
        return result;
    }

    @Override
    public List<byte[]> lRange(final String key, final long start, final long end) {
        List<byte[]> result = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                List<byte[]> l = connection.lRange(serializer.serialize(key), start, end);
                return l;
            }
        });
        return result;
    }

    @Override
    public Set<byte[]> keys(final String key) {
        Set<byte[]> result = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Set<byte[]> l = connection.keys(serializer.serialize(key));
                return l;
            }
        });
        return result;
    }

    @Override
    public Long hincr(final String key, final String field) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.hIncrBy(serializer.serialize(key), serializer.serialize(field), 1L);
                return l;
            }
        });
        return result;
    }

    @Override
    public Long sadd(final String key, final String value) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.sAdd(serializer.serialize(key), serializer.serialize(value));
                return l;
            }
        });
        return result;
    }

    @Override
    public Long scard(final String key) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long l = connection.sCard(serializer.serialize(key));
                return l;
            }
        });
        return result;
    }

    @Override
    public Boolean sismember(final String key, final String value) {
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Boolean b = connection.sIsMember(serializer.serialize(key), serializer.serialize(value));
                return b;
            }
        });
        return result;
    }

    @Override
    public Set<byte[]> smembers(final String key) {
        Set<byte[]> result = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Set<byte[]> l = connection.sMembers(serializer.serialize(key));
                return l;
            }
        });
        return result;
    }


}
