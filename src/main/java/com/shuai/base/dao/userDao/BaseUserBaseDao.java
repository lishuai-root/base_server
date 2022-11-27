package com.shuai.base.dao.userDao;

import com.shuai.base.baseCommon.common.User;
import org.springframework.stereotype.Repository;

/**
 * @author 是李帅啊
 */

@Repository
public interface BaseUserBaseDao {
    int deleteByPrimaryKey(String userid);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User user);

    int releaseRestrictionUser(User user);

    User selectByLoginName(String loginName);

    int createTable();
}