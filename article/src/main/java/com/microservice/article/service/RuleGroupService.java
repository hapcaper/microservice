package com.microservice.article.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.microservice.article.pojo.vo.RuleGroupVO;
import com.microservice.article.dao.RuleGroupDao;

@Service
public class RuleGroupService {

    @Resource
    private RuleGroupDao ruleGroupDao;

    
    public int insert(RuleGroupVO ruleGroupVO){
        return ruleGroupDao.insert(ruleGroupVO);
    }

    
    public int insertSelective(RuleGroupVO ruleGroupVO){
        return ruleGroupDao.insertSelective(ruleGroupVO);
    }

    
    public int insertList(List<RuleGroupVO> ruleGroupVOs){
        return ruleGroupDao.insertList(ruleGroupVOs);
    }

    
    public int updateByPrimaryKeySelective(RuleGroupVO ruleGroupVO){
        return ruleGroupDao.updateByPrimaryKeySelective(ruleGroupVO);
    }

    public RuleGroupVO findByName(String name) {
        return ruleGroupDao.findByName(name);
    }
}
