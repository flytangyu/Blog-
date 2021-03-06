package yd.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yd.blog.annotation.RedisCache;
import yd.blog.mapper.SysConfigMapper;
import yd.blog.model.SysConfig;
import yd.blog.service.SysConfigService;
/**
 * 
 * @author Administrator
 *
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    @RedisCache
    public Map<String, String> selectAll() {
        List<SysConfig> sysConfigs = sysConfigMapper.selectAll();
        Map<String,String>  map= new HashMap<String,String>(sysConfigs.size());
        for (SysConfig config : sysConfigs){
            map.put(config.getSysKey(),config.getSysValue());
        }
        return map;
    }

    @Override
    @RedisCache(flush = true)
    public int updateByKey(String key,String value) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setSysKey(key);
        sysConfig = sysConfigMapper.select(sysConfig).get(0);
        sysConfig.setSysValue(value);
        return sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
    }
}
