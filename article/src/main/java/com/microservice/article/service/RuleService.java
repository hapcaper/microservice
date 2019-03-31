package com.microservice.article.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.microservice.article.pojo.vo.RuleVO;
import com.microservice.article.dao.RuleDao;

@Service
public class RuleService {

    @Resource
    private RuleDao ruleDao;

    
    public int insert(RuleVO ruleVO){
        return ruleDao.insert(ruleVO);
    }

    
    public int insertSelective(RuleVO ruleVO){
        return ruleDao.insertSelective(ruleVO);
    }

    
    public int insertList(List<RuleVO> ruleVOs){
        return ruleDao.insertList(ruleVOs);
    }

    
    public int updateByPrimaryKeySelective(RuleVO ruleVO){
        return ruleDao.updateByPrimaryKeySelective(ruleVO);
    }

    public List<RuleVO> findByName(String name) {
        return ruleDao.findByName(name);
    }

    public List<RuleVO> findByRuleGroupId(Long ruleGroupId) {
        return ruleDao.findByRuleGroupId(ruleGroupId);
    }

}
